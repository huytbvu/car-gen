from lib import BasicGenerator
from lib import SumoVTypeParser
from lib import TapasGenerator
from lib import TapasGeneratorJSON
from lib import OSMMongoLoader
from lib import TapasMongoLoader
import argparse
import sys
import yaml

USAGE="car-data-generator [-h] /path/to/config.yaml [basic|prepare-tapas|tapas|populate-gis-db|purge-gis-db]\n"
USAGE += "yaml file syntax:\n"
USAGE += "  basic:                  # Options for basic mode\n"
USAGE += "    host:                 # Ip or hostname of the receiver\n"
USAGE += "    port:                 # TCP port number of the receiver\n"
USAGE += "  tapas:                  # Options when using tapas mode\n"
USAGE += "    tapasfile:            # Full path to the simulation output file\n"
USAGE += "    host:                 # Ip or hostname of the receiver\n"
USAGE += "    port:                 # TCP port number of the receiver\n"
USAGE += "    slippery_probability: # Probability of having one slippery event\n"
USAGE += "                          # generated per second\n"
USAGE += "    slippery_bootstrap:   # When set to true, will increase\n"
USAGE += "                          # slippery_probability to 0.5 until one\n"
USAGE += "                          # slippery event is generated\n"
USAGE += "    output_format: json   # Output format the data [text|json]\n"
USAGE += "  gis_db:                 # Options for experimental mode gis_db\n"
USAGE += "    host:                 # mongoDB IP or hostname\n"
USAGE += "    port:                 # mongoDB TCP port\n"
USAGE += "    osmfile:              # Full path to the osm data file\n"
USAGE += "    tapasfile:            # Full path to the simulation output file\n"


if __name__ == '__main__':
  parser = argparse.ArgumentParser(usage=USAGE)
  parser.add_argument("config", help="Path to the yaml configuration file")
  parser.add_argument("action", help="Action to be performed [basic|prepare-tapas|tapas|populate-gis-db|purge-gis-db]")
  args = parser.parse_args()

  stream = open(args.config)
  config = yaml.load(stream)

  if args.action == "basic":
    conf = config["basic"]
    gen = BasicGenerator(conf["host"], conf["port"], 100000, 24)
    gen.run()
  #elif args.action == "prepare-tapas": ##TODO: Check this mode with yaml config
  #  vtype_parser = SumoVTypeParser(config.vtypefile, config.outputpath)
  #  vtype_parser.load()
  elif args.action == "tapas": #TODO: Use a factory?
    conf = config["tapas"]
    if conf["output_format"] == "text":
      gen = TapasGenerator(conf["host"], conf["port"], conf["tapasfile"], conf["slippery_probability"], conf["slippery_bootstrap"])
    elif conf["output_format"] == "json":
      gen = TapasGeneratorJSON(conf["host"], conf["port"], conf["tapasfile"], conf["slippery_probability"], conf["slippery_bootstrap"])
    else:
      print "[ERROR]: Unrecognized output format error."
      sys.exit()
    gen.run()
  elif args.action == "populate-gis-db":
    conf = config["gis_db"]
    #osmLoader = OSMLoader(conf["host"], conf["port"], conf["osmfile"])
    #osmLoader.loadData()
    tapasLoader = TapasMongoLoader(conf["host"], conf["port"], conf["tapasfile"])
    tapasLoader.loadData()
  elif args.action == "purge-gis-db":
    conf = config["gis_db"]
    osmLoader = OSMMongoLoader(conf["host"], conf["port"], conf["osmfile"])
    osmLoader.deleteData()
  else:
    print "[ERROR] unrecognized action {a}".format(a=args.action)
    print "Usage: {u}".format(u=USAGE)
