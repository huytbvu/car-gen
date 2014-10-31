car-data-generator

https://openalm.lmera.ericsson.se/plugins/mediawiki/wiki/cloud-analytics/index.php/Car-data-generator

usage: car-data-generator [-h] /path/to/config.yaml [basic|prepare-tapas|tapas|populate-gis-db|purge-gis-db]
yaml file syntax:
  basic:                  # Options for basic mode
    host:                 # Ip or hostname of the receiver
    port:                 # TCP port number of the receiver
  tapas:                  # Options when using tapas mode
    tapasfile:            # Full path to the simulation output file
    host:                 # Ip or hostname of the receiver
    port:                 # TCP port number of the receiver
    slippery_probability: # Probability of having one slippery event
                          # generated per second
    slippery_bootstrap:   # When set to true, will increase
                          # slippery_probability to 0.5 until one
                          # slippery event is generated
    output_format: json   # Output format the data [text|json]
  gis_db:                 # Options for experimental mode gis_db
    host:                 # mongoDB IP or hostname
    port:                 # mongoDB TCP port
    osmfile:              # Full path to the osm data file
    tapasfile:            # Full path to the simulation output file

positional arguments:
  config      Path to the yaml configuration file
  action      Action to be performed [basic|prepare-tapas|tapas|populate-gis-
              db|purge-gis-db]

optional arguments:
  -h, --help  show this help message and exit
