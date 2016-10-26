import sys
import socket

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s_addr = ('localhost', 306)

sock.connect(s_addr)

f = open('/home/swkim306/Downloads/index.jpeg', 'rb')

img = f.read(1024)

while(img):
	sock.send(img)
	img = f.read(1024)

print('img sending done')

