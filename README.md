![CI](https://github.com/teverett/OLMReader/workflows/CI/badge.svg)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/7c1ef374e6fb42408e915a849c498798)](https://www.codacy.com/app/teverett/OLMReader?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=teverett/OLMReader&amp;utm_campaign=Badge_Grade)
[![DepShield Badge](https://depshield.sonatype.org/badges/teverett/OLMReader/depshield.svg)](https://depshield.github.io)

# OLMReader

A Java library for reading MS Outlook for Mac [OLM](https://support.office.com/en-us/article/Export-or-manually-archive-Outlook-items-281a62bf-cc42-46b1-9ad5-6bda80ca3106) archives

## Supported objects

* Email
* Contacts
* Contact Groups
* Appointments
* Categories
* Notes
* Tasks

## Schema

Schemas for OLM XML can be found [here](https://github.com/teverett/OLMReader/blob/master/src/main/resources/schema/)

# Maven Coordinates

```
<groupId>com.khubla.olmreader</groupId>
<artifactId>olmreader</artifactId>
<version>1.7.0</version>
<packaging>jar</packaging>
```

# Using OLMReader

To use OLMReader, provide a class which implements [OLMMessageCallback](https://github.com/teverett/OLMReader/blob/master/src/main/java/com/khubla/olmreader/olm/OLMMessageCallback.java), [OLMRawMessageCallback](https://github.com/teverett/OLMReader/blob/master/src/main/java/com/khubla/olmreader/olm/OLMRawMessageCallback.java) and pass those implementations to [OLMReader](https://github.com/teverett/OLMReader/blob/master/src/main/java/com/khubla/olmreader/olm/OLMFile.java).readOLMFile.

# Installation / Operation

To run this application you must first have the [Java Platform (JDK) 12](https://www.oracle.com/technetwork/java/javase/downloads/jdk12-downloads-5295953.html) and [Apache Maven](https://maven.apache.org/).

Ensure you set your `JAVA_HOME` as well as adding Maven to your `PATH`. [Instructions](https://maven.apache.org/install.html)

After that is done make sure to flag the `.sh` files as executable 

``` 
chmod +x run_example.sh
```

and then execute using `./run_example.sh`

# Troubleshooting

If you have issues with the build, try running `mvn clean install` in the git root directory for this application.
