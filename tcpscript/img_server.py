import sys
import socket
import os
import thread

import imghdr
import ConfigParser
import argparse
import subprocess

import pymysql


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


#TODO 1118: register, login, databse connection.
def register_(connection, req):
	pass

def login_(connection, req)


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
		flen = int(req.split('_')[0])
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
	while(True):
		try:
			
			req= str.decode(connection.recv(1024))
			print(req)
			rqn=req.split('_')[0]
			req=req.split('_')[1]

			if(rqn=='REGISTER'):
				pass
			elif(rqn=='LOGIN'):
				pass
			elif(rqn=='PICTURE'):
				print('picture start')
				thread.start_new_thread(picture_,(connection, req))
		except:
			e = sys.exc_info()[0]
			print(e)
			break;

		finally:
			pass

	

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