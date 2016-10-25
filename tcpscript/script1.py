import ConfigParser
import argparse
import sys
import socket

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

#Create a TCP/IP socket
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
#Bind the socket to the port
s_addr = ('localhost', int(Port))
print('start up on %s port %s' %s_adr)
sock.bind


