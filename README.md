# OrientDB-Metadata-Extract

## Required Tools:
    1. Java JDK 1.8
    2. Maven build tool

## How to run the project:

Step 1: Start OrientDB Database

Step 2: goto project directory

Step 3: Create Executable Jar

    $ mvn package

Step 4: Execute Jar

    $ java -jar target/main-1.0-SNAPSHOT.jar -h localhost -u admin -p admin -d OpenBeer -e myfile.csv

Here,

    -h is Hostname
    -u is database username
    -p is database password
    -d is database name
    -e is CSV export Path