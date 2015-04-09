#!/usr/bin/env python
# Author: Archer Reilly
# File: RTSPClient.py
# Date: 09/Apr/2015
# Desc: RTSP Client Lib
#
# Produced By CSRGXTU
import socket
import sys

class RTSPClient(object):
  host = None
  port = None
  socket = None
  RTSPUrl = None

  def __init__(self, host, port):
    self.host = host
    self.port = port
    self.RTSPUrl = 'rtsp://' + host + ':' + str(port)

    self.socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    self.socket.settimeout(5)
    res = self.socket.connect_ex((self.host, self.port))
    if res != 0:
      sys.exit(1)

  def options(self):
    options = 'OPTIONS ' + self.RTSPUrl + ' RTSP/1.0\nCSeq: 1\n\n'
    print options

    if self.socket.send(options) != len(options):
      print 'ERROR: send error'

    res = self.socket.recv(8192)
    print res
    return res

  def describe(self):
    describe = 'DESCRIBE ' + self.RTSPUrl + ' RTSP/1.0\nCSeq: 2\n\n'
    print describe

    if self.socket.send(describe) != len(describe):
        print 'ERROR: send error'

    res = self.socket.recv(16384)
    print res
    return res

  def setup(self):
    setup = 'SETUP ' + self.RTSPUrl + '/trackID=1 RTSP/1.0\n'\
      + 'CSeq: 3\nTransport: RTP/AVP;unicast;client_port=8000-8001'\
      + '\n\n'
    print setup

    if self.socket.send(setup) != len(setup):
      print 'ERROR: send errror'

    res = self.socket.recv(16384)
    print res
    return res

  def play(self):
    play = 'PLAY ' + self.RTSPUrl + ' RTSP/1.0\n'\
      + 'CSeq: 4\nSession: 1185d20035702ca\n\n'
    print play

    if self.socket.send(play) != len(play):
      print 'ERROR: send error'

    res = self.socket.recv(8192)
    print res
    return res

  def pause(self):
    pause = 'PAUSE ' + self.RTSPUrl + ' RTSP/1.0\n'\
      + 'CSeq: 5\nSession: 1185d20035702ca\n\n'
    print pause

    if self.socket.send(pause) != len(pause):
      print 'ERROR: send error'

    res = self.socket.recv(8192)
    print res
    return res

  def record(self):
    record = 'RECORD ' + self.RTSPUrl + ' RTSP/1.0\n'\
      + 'CSeq: 6\nSession: 1185d20035702ca\n\n'
    print record

    if self.socket.send(record) != len(record):
      print 'ERROR: send error'

    res = self.socket.recv(8192)
    print res
    return res

  def announce(self):
    pass

  def teardown(self):
    teardown = 'TEARDOWN ' + self.RTSPUrl + ' RTSP/1.0\n'\
      + 'CSeq: 7\nSession: 1185d20035702ca\n\n'
    print teardown

    if self.socket.send(teardown) != len(teardown):
        print 'ERROR: send error'

    res = self.socket.recv(8192)
    print res
    return res

  def getParameter(self):
    pass

  def setParameter(self):
    pass

  def redirect(self):
    pass

  def getHost():
    return self.host

  def getPort():
    return self.port

  def setHost(host):
    self.host = host

  def setPort(port):
    self.port = port
