import sys
import socket
import time
import errno
import os

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s_addr = ('localhost', 306)
sock.connect(s_addr)

sock.setblocking(0)

#send image to server.
fname = '/home/swkim306/Downloads/isthischick.jpg'
flen = os.path.getsize(fname)
sock.send(str(flen))
print(flen)

f = open(fname, 'rb')
img = f.read(1024)
while(img):
	sock.send(img)
	img = f.read(1024)
print('img sending done')

f.close()


print('waiting for result')
res=''
while not res:
	try : 
		rec = sock.recv(1)
		while rec:
			res+=rec
			rec = sock.recv(1)
		print(rec)
	except IOError as e:
		if e.errno == errno.EWOULDBLOCK:
                	pass
	finally:
		pass

print(res)
