####Kafka Setup Commands

directory - F:\soft\kafka_2.13-2.4.1\bin

* F:\soft\kafka_2.13-2.4.1\bin\windows>zookeeper-server-start.bat ..\..\config\zookeeper.properties
* old - zookeeper-server-start.bat ..\..\config\zookeeper.properties

***
[2023-06-02 11:47:49,803] INFO binding to port 0.0.0.0/0.0.0.0:2181 (org.apache.zookeeper.server.NIOServerCnxnFactory)
[2023-06-02 11:47:49,883] INFO zookeeper.snapshotSizeFactor = 0.33 (org.apache.zookeeper.server.ZKDatabase)
[2023-06-02 11:47:49,999] INFO Reading snapshot \tmp\zookeeper\version-2\snapshot.f0 (org.apache.zookeeper.server.persistenc
[2023-06-02 11:47:50,161] INFO Snapshotting: 0x103 to \tmp\zookeeper\version-2\snapshot.103 (org.apache.zookeeper.server.per
[2023-06-02 11:47:50,234] INFO Using checkIntervalMs=60000 maxPerMinute=10000 (org.apache.zookeeper.server.ContainerManager)
***

kafka-server-start.bat ..\..\config\server.properties

kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic spring-boot-topic

kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic spring-boot-topic