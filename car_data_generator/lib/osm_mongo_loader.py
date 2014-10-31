from xml.etree import ElementTree
import pymongo

class OSMMongoLoader:

  def __init__(self, host, port, osmfile):
    self.host = host
    self.port = port
    self.osmfile = osmfile

  def _connect_to_mongodb(self):
    print "[INFO] connecting to mongodb {h}:{p}".format(h=self.host, p=self.port)
    client = pymongo.MongoClient(self.host, self.port)
    return client
    

  def loadData(self):
    client = self._connect_to_mongodb()
    db = client.gisdata 
    #self._loadNodesData(db)
    self._loadWaysData(db)

  def _loadNodesData(self, db):
    col = db.nodes
    parser = ElementTree.iterparse(self.osmfile, events=("start", "end"))
    node_count = 0
    data = []
    for event, element in parser:
      if event == "start" and element.tag == "node":
        node_count = node_count + 1
        node = {"id":  element.attrib["id"],
                "lat": element.attrib["lat"],
                "lon": element.attrib["lon"]}
        data.append(node)
      if (event == "end" and element.tag == "osm") or (node_count % 1000 == 0 and node_count > 0):
        print "[TRACE] inserting nodes {n} new records".format(n=len(data))
        col.insert(data)
        data = []
        node_count = 0

  def _loadWaysData(self, db):
    col = db.ways
    parser = ElementTree.iterparse(self.osmfile, events=("start", "end"))
    way_count = 0
    data = []
    for event, element in parser:
      if event == "start" and element.tag == "way":
        way_count = way_count + 1
        nodes = []
        for child in filter(lambda el: el.tag == "nd", list(element)):
          nodes.append(child.attrib["ref"])
        way = {"id":    element.attrib["id"],
               "nodes": nodes}
        data.append(way)
      if (event == "end" and element.tag == "osm") or (way_count % 1000 == 0 and way_count > 0):
        print "[TRACE] inserting ways {n} new records".format(n=len(data))
        col.insert(data)
        data = []
        way_count = 0
  

  def deleteData(self):
    client = self._connect_to_mongodb()
    db = client.gisdata
    for dbName in ["ways"]: #["nodes", "ways"]:
      col = db[dbName]
      col.remove({})
