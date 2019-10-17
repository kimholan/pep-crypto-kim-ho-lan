PEP decryption component
------------------------

- [Documentation](pep-crypto-documentation/README.md)
  - [References](pep-crypto-documentation/10-Reference)
  - [Architecture](pep-crypto-documentation/30-Architecture)
  - [Schema](pep-crypto-documentation/35-Schema)
  - [Design](pep-crypto-documentation/40-Design)
  - [Development](pep-crypto-documentation/50-Development)
  - [Implementation](pep-crypto-documentation/55-Implementation)
  - [Testing](pep-crypto-documentation/60-Test/)


Quick start
-----------

_Build the project_

```bash
$ mvn clean install
```

_Start the Microprofile webapplication in a Thorntail-server_

```bash  
mvn -f ./pep-crypto-thorntail/pom.xml thorntail:run
```


_Running the tests (requires installing Gauge)_

Excludes the thorntail configuration tests which requires
multiple thorntail instances.

```
mvn clean install -Pgauge -pl ':pep-crypto-test'  -Dtags='!thorntail'
```

---
