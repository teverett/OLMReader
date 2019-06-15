[![Travis](https://travis-ci.org/teverett/OLMReader.svg?branch=master)](https://travis-ci.org/teverett/OLMReader)
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
<version>1.6</version>
<packaging>jar</packaging>
```

# Using OLMReader

To use OLMReader, provide a class which implements [OLMMessageCallback](https://github.com/teverett/OLMReader/blob/master/src/main/java/com/khubla/olmreader/olm/OLMMessageCallback.java), [OLMRawMessageCallback](https://github.com/teverett/OLMReader/blob/master/src/main/java/com/khubla/olmreader/olm/OLMRawMessageCallback.java) and pass those implementations to [OLMReader](https://github.com/teverett/OLMReader/blob/master/src/main/java/com/khubla/olmreader/olm/OLMFile.java).readOLMFile.



