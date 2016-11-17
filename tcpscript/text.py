import sys
import socket
import os
import thread

import imghdr
import ConfigParser
import argparse
import subprocess

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
	#make img file name.

	try:
		print('connection from %s', c_addr)

		while True:
			data = connection.recv(1024)

			if data:
				print(data)
			else:
				print('receive done ')
				break
				
	finally:
		pass