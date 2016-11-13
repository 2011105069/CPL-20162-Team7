import sys
import socket
import time
import errno
import os

sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s_addr = ('192.168.200.128', 306)
sock.connect(s_addr)

#sock.setblocking(0)

#send image to server.


img ='hi im seven\n'
sock.send(img)

sock.close()
