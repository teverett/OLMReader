# OLMReader

A Java library for reading MS Outlook for Mac [OLM](https://support.office.com/en-us/article/Export-or-manually-archive-Outlook-items-281a62bf-cc42-46b1-9ad5-6bda80ca3106) archives

Schema for OLM XML can be found [here](https://github.com/teverett/OLMReader/blob/master/src/main/resources/olm.xsd)

Travis Status
---------

<a href="https://travis-ci.org/teverett/OLMReader"><img src="https://api.travis-ci.org/teverett/OLMReader.png"></a>

Coverity Status

<a href="https://scan.coverity.com/projects/teverett-olmreader"> <img alt="Coverity Scan Build Status" src="https://scan.coverity.com/projects/13283/badge.svg"/></a>

Maven Coordinates
-------------------

```
<groupId>com.khubla.olmreader</groupId>
<artifactId>olmreader</artifactId>
<version>1.0</version>
<packaging>jar</packaging>
```

Example Code
-------------------

To use OLMReader, provide a class which implements [OLMMessageCallback](https://github.com/teverett/OLMReader/blob/master/src/main/java/com/khubla/olmreader/olm/OLMMessageCallback.java), [OLMRawMessageCallback](https://github.com/teverett/OLMReader/blob/master/src/main/java/com/khubla/olmreader/olm/OLMRawMessageCallback.java) and pass those implementations to [OLMReader](https://github.com/teverett/OLMReader/blob/master/src/main/java/com/khubla/olmreader/olm/OLMFile.java).readOLMFile.



