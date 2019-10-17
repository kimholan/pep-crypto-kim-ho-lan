Starting the thorntail server
=============================

The thorntail appserver can be run in debug mode by passing the -agentlib
parameter like the example below, assuming the thorntail project was built:


```bash 
$ java -agentlib:jdwp=transport=dt_socket,address=*:8787,server=y,suspend=n  -jar pep-crypto/pep-crypto-thorntail/target/pep-crypto-thorntail-1.0-quarkus-thorntail.jar
```

Then connect to the java processRequest using remote debugging from the IDE
as usual.
