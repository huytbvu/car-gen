import pymongo

class TapasMongoLoader:

  def __init__(self, host, port, tapaspath):
    self.host = host
    self.port = port
    self.tapaspath = tapaspath

  def _connect_to_mongodb(self):
    print "[INFO] connecting to mongodb {h}:{p}".format(h=self.host, p=self.port)
    client = pymongo.MongoClient(self.host, self.port)
    return client

  def loadData(self):
    client = self._connect_to_mongodb()
    db = client.gisdata
    col = db["cached_coordinates"]
    f = open(self.tapaspath, 'r')
    line_count = 0
    data = []
    col.remove({})
    for line in f:
      fields = line.split(" ")
      entry = { "loc": { "type": "Point", "coordinates": [float(fields[3]), float(fields[2])] }, 
                "way_id": long(fields[4].split("#")[0].lstrip("-").split("-")[0])}
      data.append(entry)
      line_count = line_count + 1
      if line_count % 1000 == 0 and len(data) > 0:
        print "[TRACE] inserting cached coordinates {n} new records".format(n=len(data))
        col.insert(data)
        data = []
    if len(data) > 0:
      print "[TRACE] inserting cached coordinates {n} new records".format(n=len(data))
      col.insert(data)
    col.ensure_index([("loc", pymongo.GEOSPHERE)])

      

