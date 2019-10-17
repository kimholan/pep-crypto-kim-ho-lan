
Running the tests
=================

## Install Gauge

See https://docs.gauge.org/master/installation.html.

```bash
$ sudo apt-key adv --keyserver hkp://pool.sks-keyservers.net:80 --recv-keys 023EDB0B
Executing: /tmp/apt-key-gpghome.PVDfhO3mA6/gpg.1.sh --keyserver hkp://pool.sks-keyservers.net:80 --recv-keys 023EDB0B
gpg: key 25CCDC10023EDB0B: public key "gauge (gpg key for maven archetypes) <getgauge@thoughtworks.com>" imported
gpg: Total number processed: 1
gpg:               imported: 1
$ echo deb https://dl.bintray.com/gauge/gauge-deb nightly main | sudo tee -a /etc/apt/sources.list
deb https://dl.bintray.com/gauge/gauge-deb nightly main
$ sudo sed -e 's|nightly|stable|g' -i /etc/apt/sources.list
$ sudo apt-get update
Reading package lists... Done
$ sudo apt-get install gauge
Reading package lists... Done
Building dependency tree       
Reading state information... Done
The following NEW packages will be installed:
  gauge
0 upgraded, 1 newly installed, 0 to remove and 41 not upgraded.
Need to get 4,397 kB of archives.
After this operation, 19.1 MB of additional disk space will be used.
Get:1 https://dl.bintray.com/gauge/gauge-deb stable/main amd64 gauge amd64 1.0.4 [4,397 kB]
Fetched 4,397 kB in 0s (18.3 MB/s)
Selecting previously unselected package gauge.
(Reading database ... 198376 files and directories currently installed.)
Preparing to unpack .../archives/gauge_1.0.4_amd64.deb ...
Unpacking gauge (1.0.4) ...
Setting up gauge (1.0.4) ...

We are constantly looking to make Gauge better, and report usage statistics anonymously over time. If you do not want to participate please read instructions https://manpage.gauge.org/gauge_telemetry_off.html on how to turn it off.

$ gauge telemetry off
```
### Build the project

```bash
$ mvn clean install
```

### Start the app server

```bash
$ java -jar ./pep-crypto-thorntail/target/pep-crypto-thorntail-1.0-quarkus-thorntail.jar 
```

### Run testspecifications using Gauge

```bash
$ mvn -Pgauge -pl ':pep-crypto-test' install
``` 
