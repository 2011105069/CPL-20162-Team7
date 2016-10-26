import ConfigParser
import argparse
import sys
import socket
import imghdr
import os

#Argument parsing
parser = argparse.ArgumentParser()
parser.add_argument("-c", "--conf", help="location of conf file", type=str)
args = parser.parse_args()

#Config file parsing
Config = ConfigParser.ConfigParser()
if args.conf:
	Config.read(args.conf)

else :
	print("no configure file")
	sys.exit()

Port = Config.get('Option', 'Port')
LabDir = Config.get('Option', 'Lab_script_Directory')
ImgSaveDir = Config.get('Option', 'Received_Image_Directory')
#ImgSaveDir= ImgSaveDir+'tmp'

#Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#Bind the socket to the port
s_addr = ('localhost', int(Port))
print('start up on %s port %s' %s_addr)
sock.bind(s_addr)

sock.listen(1)

while True:
	print('wating for connection')
	connection, c_addr = sock.accept()

	try:
		print('connection from %s', c_addr)

		f=open(ImgSaveDir+'tmp', 'wb+')

		while True:
			data = connection.recv(16)

			if data:
				f.write(data)
			else:
				print('img save done ')
				break
	finally:
		imgtype = imghdr.what(ImgSaveDir+'tmp')
		for filename in os.listdir(ImgSaveDir):
			if filename.startswith('tmp'):
				print('file name is ' + filename)
				os.rename(ImgSaveDir + filename, ImgSaveDir+filename+'.'+imgtype)
		f.close()
		connection.close()


