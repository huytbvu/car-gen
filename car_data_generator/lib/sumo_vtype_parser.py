from xml.etree import ElementTree

class SumoVTypeParser:

  def __init__(self, vtype_path, output_path):
    self.vtype_path = vtype_path
    self.output_path = output_path

  def load(self):
    output_filename = self.output_path + "/tapas.out"
    f = open(output_filename, 'w')
    parser = ElementTree.iterparse(self.vtype_path, events=("start", "end"))
    i=0
    for event, element in parser:
      if event == 'start' and element.tag == 'timestep':
        timedata = []
        current_time = element.attrib["time"]
      if event == 'end' and element.tag == 'timestep':
        if int(float(current_time)) % 60 == 0:
          print "[TRACE] writing data for second {s}".format(s=current_time)
        for item in timedata:
          f.write("{t} {i} {lat} {lon} {s} {l}\n".format(t=int(float(current_time)), i=item["id"], lat=item["lat"], 
                                                         lon=item["lon"], s=item["segment"], l=item["lane"]))
        i = i + 1
      if event == 'start' and element.tag == 'vehicle':
        vehicle_data = {'id': element.attrib["id"],
                        'lat': element.attrib["lat"],
                        'lon': element.attrib["lon"],
                        'segment': element.attrib["lane"].split("_")[0],
                        'lane': element.attrib["lane"].split("_")[1]}
        timedata.append(vehicle_data)
      #if i == 600:
      #  break
      element.clear()
