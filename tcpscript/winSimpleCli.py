import sys
import socket

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s_addr = ('localhost', 306)
sock.connect(s_addr)

#sock.setblocking(0)

#send tex to server.
tex ='hi im seven\n'
sock.send(str.encode(tex))
print('sending done')

sock.close()
