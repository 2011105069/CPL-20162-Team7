from __future__ import print_function
import sys
import socket
import os
import thread
import time

import imghdr
import ConfigParser
import argparse
import subprocess

import pymysql
import re

#parse argument (config file).
parser = argparse.ArgumentParser()
parser.add_argument("-c", "--conf", help="location of conf file", type=str)
args = parser.parse_args()

#parse config file.
Config = ConfigParser.ConfigParser()
if args.conf:
	Config.read(args.conf)

else :
	print("no configure file")
	sys.exit()

Sadr = Config.get('Option', 'Sadr')
Port = Config.get('Option', 'Port')
LabScript = Config.get('Option', 'Lab_Script')
ImgSaveDir = Config.get('Option', 'Received_Image_Directory')
dbname =  Config.get('Option', 'dbname')
dbuname = Config.get('Option', 'dbuname')
dbpass = Config.get('Option', 'dbpass')

#database.
conn = pymysql.connect(host='localhost', user=dbuname, password=dbpass, db=dbname, charset = 'utf8')
curs = conn.cursor()


def insert(table,req):
	print('db insert')
	#print(req)

	if(str(req[0])=='REGISTER'):
		colnval = '(uid,u_name,sex,birth_year,gid) values(\"'+str(req[1])+'\", \"'+str(req[2])+'\", \''+str(req[3])+'\', '+str(req[4])+', '+str(req[5])+')'
	elif(str(req[0])=='PICTURE'):
		ss=time.strftime("%Y%m%d")
		#print(ss)
		colnval = 'values('+ss+ ',\"'+str(req[2])+'\",\"' +str(req[4])+ '\",'+str(req[3]) +')'


	sql = "insert into " + table + " " + colnval
	print(sql)
	
	
	curs.execute(sql)
	conn.commit()

	pass

def history_(connection, req):
	#date selecton function is not implemented.
	
	sql = "select * from history where uid=\""+str(req[1])+ "\" order by date desc"
	curs.execute(sql)
	rows = curs.fetchall()
	#print(rows)

	d=0
	xx=len(rows)
	#print(len(rows))

	#print(rows[1])
	d=1
	date=str(unicode(rows[d][0]))

	res=str()

	while(d<xx):

		date=str(unicode(rows[d][0]))
		uid=str(rows[d][1])
		foodname=str(rows[d][2])
		quant =float(rows[d][3])
		u='_'

		sql = "select calorie,carbohydrate,protein,fat,fiber,vita_c,ca,na,fe from nutri_info where d_name=\""+foodname+"\""
		curs.execute(sql)
		rows2 = curs.fetchall()
		irows=list()

		for i in rows2[0]:
			irows.append(float(i))


		mrows=list()
		for i in irows:
			mrows.append(i*float("{0:.2f}".format(quant)))

		for i in mrows:
			#print(str(i), end='')
			#print('_', end='')
			res+= str(i) +'_'

		res+='#'
		d+=1

	#print(res)
	#print(str.encode(res))
	slen = str(len(res)) +'_'
	#print('slen is ' + slen)
	connection.send(str.encode(slen))

	print(str.encode(connection.recv(100)))
	
	sent =connection.send(str.encode(res))
	print(len(res))
	print(res)
	print('history info sending done')

	pass


#TODO 1118: register, login, databse connection.
def register_(connection, req):
	insert('member_info', req)
	sql= "select * from member_info"
	curs.execute(sql)
	rows = curs.fetchall()

	for i in rows:
		print(i)

	print('register : '+ str(req[1]) + ' success')

	#sql = 'insert into member_info (uid,u_name,sex,birth_year,gid) vaules('

	pass

def login_(connection, req):
	#nothing todo yet

	uid = req[1]
	sql= "select uid from member_info where uid =\""+ str(uid) +"\""
	curs.execute(sql)
	rows = curs.fetchall()

	if rows:
		print("login success")
		print('id : ' + str(req[1]))
		pass
	else:
		print("no such id " + str(req[1]))



def label_image(img, connection, req):
	#call lab_image.py
	print(LabScript + ' ' + img)
	batcmd = 'sudo python ' + LabScript + ' ' + img
	result = subprocess.check_output(batcmd, shell=True)
	print('--------------')
	print(result)
	res = result.split('(')[0]
	s = result.split('=')[1]
	s = s.split(')')[0]
	res += s
	res = res.replace('  ','-')
	food = res.split('-')[0]

	print('Result is : ' +str(res))
	res = 'result_'+str(res)+'_'
	connection.send(str.encode(res))
	connection.close()

	#bad coding.
	req[4]=food

	insert("history",req)

	pass

def picture_(connection,req):
	#make img file name.
	cn = str(connection).replace(' ','_')
	cn = cn.replace('<','')
	cn = cn.replace('>','')
	fname = ImgSaveDir + cn
	rlen =0

	f=open(fname, 'wb+')

	try:
		#print('come in pictrue')
		flen = int(req[1])
		print('flen is ' + str(flen))		

		while rlen < flen:
			data = connection.recv(1024)

			if len(data):
				f.write(data)
				rlen+=len(data)

			else:
				print(str(rlen))
				print('img save done ')
	except:
		e = sys.exc_info()[0]
		print(e)
				
	finally:
		#print(str(rlen))
		#detect img file type, using imghdr.
		imgtype = imghdr.what(fname)
		for filename in os.listdir(ImgSaveDir):
			if filename.startswith(cn):
				os.rename(ImgSaveDir + filename, fname+'.'+ imgtype)
				fname = fname + '.' + imgtype
				print('file name is %s' %fname)
		f.close()
		#connection.close()
		#print('connection closed')
		thread.start_new_thread(label_image, (fname,connection, req, ))


def app_handler(connection):
	#TODO 11/15 ##### howto Receive string message(?) connection.recv() to string so idendify request string. #######
	##### implementation while loop for request parser ###########
	try:
		
		connection.send(str.encode("OK_\n\n"))

		req= str.decode(connection.recv(1024))
		print('\n'+req)
		req=req.split('_')
		rqn = req[0]

		if(rqn=='REGISTER'):
			thread.start_new_thread(register_,(connection, req))
			pass
		elif(rqn=='LOGIN'):
			thread.start_new_thread(login_,(connection, req))
			pass
		elif(rqn=='PICTURE'):
			print('picture start')
			thread.start_new_thread(picture_,(connection, req))
		elif(rqn=='HISTORY'):
			print('history start')
			thread.start_new_thread(history_,(connection, req))

	except:
		e = sys.exc_info()[0]
		print(e)

	finally:
		pass

def rasp_login(connection, req):
	print(req)
	
	uid = req[1]
	sql= "select gid from member_info where uid =\""+ str(uid) +"\""
	curs.execute(sql)
	rows = curs.fetchall()

	if rows:
		print("login success")
		print('id : ' + str(req[1]))
		pass
	else:
		print("no such id " + str(req[1]))

	return int(str(rows[0]))


def rasp_history(connection, req, gid):
	#It may have error.
	#date selecton function is not implemented.
	#the amount of data is fixed in raspberyy program.

	sql= "select uid from memeber_info where gid=\"" +str(gid) +"\""
	curs.execute(sql)
	rows2 = curs.fetchall()
	totmsg=''
	
	for uid in rows2:

		sql = "select * from history where uid=\""+str(uid) + "\" order by date desc"
		curs.execute(sql)
		rows = curs.fetchall()
		#print(rows)

		d=0
		xx=len(rows)

		d=1
		date=str(unicode(rows[d][0]))

		res=str()

		while(d<xx):

			date=str(unicode(rows[d][0]))
			uid=str(rows[d][1])
			foodname=str(rows[d][2])
			quant =float(rows[d][3])
			u='_'

			sql = "select calorie,carbohydrate,protein,fat,fiber,vita_c,ca,na,fe from nutri_info where d_name=\""+foodname+"\""
			curs.execute(sql)
			rows2 = curs.fetchall()
			irows=list()

			for i in rows2[0]:
				irows.append(float(i))


			mrows=list()
			for i in irows:
				mrows.append(i*float("{0:.2f}".format(quant)))

			for i in mrows:
				#print(str(i), end='')
				#print('_', end='')
				res+= str(i) +'_'

			#rasp don't need this '#'' and length of msg.
			#res+='#'
			d+=1

		totmsg = totmsg + res
		
	sent =connection.send(str.encode(totmsg))
	#print(len(res))
	#print(sent)
	print('history info sending done')

	pass
			

def rasp_handler(connection, req):

	#After connection firtst req is treated specially. so recv is in finally statement.
	#Message type is RASP_LOGIN_ID_
	#RASP_HISTORY_ID_

	gid=0

	while connection:
		try:
			
			connection.send(str.encode("OK_"))
			
			print(req)
			req=req.split('_')
			rqn = req[0]

			if(rqn=='LOGIN'):
				print('rasp login')
				gid = rasp_login(connection, req)
			if(rqn=='HISTORY'):
				print('rasp history')
				rasp_history(connection, req, gid)
				pass

		except:
			pass

		finally:
			req= str.decode(connection.recv(1024))
			req = req.split('_')[1]

1
def main():
	#create a tcp/ip socket.
	sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


	#bind the socket to the port
	s_addr = (Sadr, int(Port))
	print('start up on %s port %s' %s_addr)
	sock.bind(s_addr)
	sock.listen(1)

	flag=1

	while True:
		if(flag==1):
			print('wating for connection')
		connection, c_addr = sock.accept()
		if(flag==1):
			print('connection from %s', c_addr)
		
		cl = str.decode(connection.recv(1024))
		cl = str(cl)
		cl2=cl.split('_')[0]
		cl3 = cl.split('_')[1]
		
		if(cl2=='APP'):
			thread.start_new_thread(app_handler,(connection, ))
			if(flag==1):
				print('app started')
				flag=0
		elif(cl2=='RASP'):
			print('rasp came')
			thread.start_new_thread(rasp_handler,(connection, cl3, ))

main()
