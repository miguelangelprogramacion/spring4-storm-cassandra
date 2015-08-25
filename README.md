# spring4-storm-cassandra

spring4-storm-cassandra is a proof of concept, which  combines "Spring DI" (4) to instanciate apache-storm topologies.

The injection proceses has been split in two ComponentScan:

  - The first one is used for the general topology components (Bolts, Spouts, Factories,...)
  - The second one is used to instanciate internal Spring elements (in this sample, cassandra spring-data components)


### Tech

spring4-storm-cassandra uses a pair of source projects as a reference:

* [storm-spring](https://github.com/granthenke/storm-spring)
* [storm-cassandra-cql](https://github.com/hmsonline/storm-cassandra-cql)
