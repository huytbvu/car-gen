import time
import datetime
import socket
import random

POLYNOMIAL_COEFFICIENTS = [ 0.410169005, -4.95535E-05, 2.78385E-09, 7.61165E-14,
                           -7.26954E-18, 1.69372E-22, -1.65642E-27, 5.92843E-33]

class BasicGenerator:

  def __init__(self, ip, port, max_msg_per_second, time_delta):
    self.proxy_ip = ip
    self.proxy_port = port
    self.max_msg_per_second = max_msg_per_second
    self.time_delta = time_delta

  def _compute_polynomial_serie(self, coefficients, data_range):
    data = []
    for x in range(data_range):
      y = 0
      for j in range(len(coefficients)):
        y = y + coefficients[j] * x**j
      data.append(y)
    return data

  def _compute_random_coordinates(self, data_range):
    data = []
    for i in range(data_range):
      data.append((random.uniform(0,100), random.uniform(0,100)))
    return data

  def run(self):
    usage_data = self._compute_polynomial_serie(POLYNOMIAL_COEFFICIENTS, 3600*24)
    coord = self._compute_random_coordinates(200000)
    start_time = time.time()
    start_date = datetime.datetime.fromtimestamp(start_time)
    time_index = start_date.hour*3600 + start_date.minute*60 + start_date.second
    i = 1
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    s.connect((self.proxy_ip, self.proxy_port))

    while True:
      delay = start_time + i - time.time()
      if delay > 0:
        time.sleep(start_time + i - time.time())
      ts = int(time.time())
      start_coord = random.randrange(self.max_msg_per_second)
      data_points_count = int(usage_data[time_index]*self.max_msg_per_second)
      for j in range(data_points_count):
        s.send("{ts} {j} {lat} {lon}\n".format(ts=ts, j=j, lat=coord[start_coord+j][0], lon=coord[start_coord+j][1]).encode())
      #print "done {c}".format(c=data_points_count)
      i = i + 1
      time_index = time_index + self.time_delta
      if time_index >= 3600*24:
        time_index = 0
