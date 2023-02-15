### pom.xml changes
* add dependency micrometer-registry-prometheus

### application.properties changes
* add these properties - management.endpoints.web.exposure.include, management.endpoint.health.show-details

### new file added
* prometheus.yml

### setting up prometheus on local
* start docker in virtualbox and run this command
* docker pull prom/prometheus
* docker run -p 8000:9090 -v "E:\Program Files\EclipseWorkspace\spring-grafana-monitoring\src\main\resources\prometheus.yml" prom/prometheus (9090 is prometheus port and 8000 is port given by us 'public')
* prometheus console url - http://127.0.0.1:8000/ (make sure 8000 port is configured in port forwarding in virtualbox)

### setting up grafana on local
* docker pull grafana/grafana
* docker run -p 9000:3000 grafana/grafana (3000 is grafana port and 9000 is port given by us 'public')
* grafana console url - http://127.0.0.1:9000/ (make sure 8000 port is configured in port forwarding in virtualbox)
* username/password - admin/admin