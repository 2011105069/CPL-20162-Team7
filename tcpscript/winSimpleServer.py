import sys
import socket
import os


#create a tcp/ip socket.
sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

#bind the socket to the port
s_addr = ('localhost', 306)
print('start up on %s port %s' %s_addr)
sock.bind(s_addr)
sock.listen(1)


while True:
	print('wating for connection')
	connection, c_addr = sock.accept()
	#make img file name.

	try:
		print('connection from %s %s' %c_addr)

		while True:
			data = connection.recv(20)

			if data:
				print(data)
			else:
				print('receive done ')
				break
				
	finally:
		pass
