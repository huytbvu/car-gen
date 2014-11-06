curl -X POST -H "Content-Type: application/json" -d@car-speed-monitoring-lax.json http://localhost:2121/api/services | python -m json.tool
curl -X POST -H "Content-Type: application/json" -d@car-speed-monitoring-jfk.json http://localhost:2121/api/services | python -m json.tool
curl -X POST -H "Content-Type: application/json" -d@car-speed-monitoring-mia.json http://localhost:2121/api/services | python -m json.tool
