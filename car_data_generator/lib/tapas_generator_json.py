import time
import datetime
import socket
import random
import json
from tapas_generator import TapasGenerator

class TapasGeneratorJSON(TapasGenerator):

  def appendData(self, line):
    lineData = line.rstrip().split(" ")
    jsonData = {"type": "roadEvent",
                "timestamp": "{ts}", 
                "carFullId": lineData[1],
                "carId": int(lineData[1].split("_")[0]),
                "coordinates": { "latitude": float(lineData[2]),
                              "longitude": float(lineData[3])},
                "roadLocation": { "segmentId": lineData[4],
                                  "laneId": int(lineData[5])},
                "events": []}
    jsonString = json.dumps(jsonData)
    self.data.append(jsonString + "\n")

  def makeSlippery(self, event_id):
    jsonObject = json.loads(self.data[event_id])
    jsonObject["events"] = ["slippery"]
    self.data[event_id] = json.dumps(jsonObject) + "\n"
