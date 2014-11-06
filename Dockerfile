FROM ubuntu
ADD bin ~/cdg/
ADD car_data_generator ~/cdg/
ADD conf ~/cdg/
ADD bin/car_data_generator /usr/bin/
EXPOSE 4040
RUN export PATH=$PATH:~/cdg/bin
RUN apt-get update && apt-get install -y python-pymongo python-yaml
RUN mkdir /var/run/car_data_generator
RUN chmod +x ~/cdg/bin/car_data_generator

