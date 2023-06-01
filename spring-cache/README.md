* endpoint to get data - http://localhost:8080/getValue?id=3

## actuator endpoints
* cache names  - http://localhost:8080/actuator/caches
* cache hits - http://localhost:8080/actuator/metrics/cache.gets?tag=result:hit
* cahce misses - http://localhost:8080/actuator/metrics/cache.gets?tag=result:miss