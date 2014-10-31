import time
import datetime
import socket
import random

class TapasGenerator:

  def __init__(self, ip, port, tapasfile, slippery_probability, slippery_bootstart):
    self.proxy_ip = ip
    self.proxy_port = port
    self.filepath = tapasfile
    self.slippery_probability = slippery_probability
    self.bootstart_slippery_event = slippery_bootstart

  def run(self):
    print "[INFO] running tapas generator"
    i = 1
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((self.proxy_ip, self.proxy_port))
    f = open(self.filepath, "r")
    self.data = []
    current_ts = 0
    start_time = time.time()
    slippery_prob = self.slippery_probability
    bootstart_is_active = False
    if self.bootstart_slippery_event:
      slippery_prob = 0.5
      bootstart_is_active = True
    for line in f:
      ts = line.split(' ')[0]
      if ts != current_ts:
        current_ts = ts
        prob = float(random.randrange(1000))/1000
        if (prob < slippery_prob) and (len(self.data) > 0):
          print "[INFO] injecting 1 slippery events (prob={p})".format(p=prob)
          event_id = random.randrange(len(self.data))
          #self.data[event_id] = "{data} s\n".format(data=self.data[event_id].rstrip())
          self.makeSlippery(event_id)
          print "[INFO] event injected: {data}".format(data=self.data[event_id].rstrip())
          if bootstart_is_active:
            slippery_prob = self.slippery_probability
            bootstart_is_active = False
        delay = start_time + i - time.time()
        if delay > 0:
          time.sleep(start_time + i - time.time())
          #print "[INFO] sending data"
        for d in self.data:
          s.send(d.replace("\"{ts}\"", str(int(round(time.time() * 1000))))) 
        self.data = []
        i=i+1
      self.appendData(line)

  def appendData(self, line):
    self.data.append(line)

  def makeSlippery(self, event_id):
    self.data[event_id] = "{data} s\n".format(data=self.data[event_id].rstrip())
