import sys
import socket
import os
import thread

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

conn = pymysql.connect(host='localhost', user=dbuname, password=dbpass, db=dbname, charset = 'utf8')
curs = conn.cursor()


def insert(table,req):

	colnval = '(uid,u_name,sex,birth_year,gid) values(\"'+str(req[1])+'\", \"'+str(req[2])+'\", \''+str(req[3])+'\', '+str(req[4])+', '+str(req[5])+')'
	sql = "insert into " + table + " " + colnval
	print('sql is')
	print(sql)
	curs.execute(sql)
	conn.commit()


	pass

def history_(connection, req):
	tex ='hi im seven\n'
	connection.send(str.encode(tex))
	print('sending done history')
	pass


#TODO 1118: register, login, databse connection.
def register_(connection, req):
	print('regisger came')
	insert('member_info', req)
	print(req)

	#sql = 'insert into member_info (uid,u_name,sex,birth_year,gid) vaules('
	pass

def login_(connection, req):
	pass


def label_image(img, connection, c_addr):
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
	res = res.replace('  ',' ')
	#print(res)
	#connection.send(res)
	#connection.close()

def picture_(connection,req):
	#make img file name.
	cn = str(connection).replace(' ','_')
	cn = cn.replace('<','')
	cn = cn.replace('>','')
	fname = ImgSaveDir + cn
	rlen =0

	f=open(fname, 'wb+')

	try:
		print('come in pictrue')
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
		print(str(rlen))
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
		thread.start_new_thread(label_image, (fname,connection, c_addr, ))


def client_handler(connection):

	#TODO 11/15 ##### howto Receive string message(?) connection.recv() to string so idendify request string. #######
	##### implementation while loop for request parser ###########

	#this while loop necessary?

	try:
		
		req= str.decode(connection.recv(1024))
		print(req)
		req=req.split('_')
		rqn = req[0]

		if(rqn=='REGISTER'):
			thread.start_new_thread(register_,(connection, req))
			pass
		elif(rqn=='LOGIN'):
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

	



#create a tcp/ip socket.
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)


#bind the socket to the port
s_addr = (Sadr, int(Port))
print('start up on %s port %s' %s_addr)
sock.bind(s_addr)
sock.listen(1)

while True:
	print('wating for connection')
	connection, c_addr = sock.accept()
	print('connection from %s', c_addr)

	thread.start_new_thread(client_handler,(connection, ))