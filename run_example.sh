#!/bin/sh

VERSION=$(grep -m1 'version' pom.xml | awk -F">" '{print $2}' | awk -F"<" '{print $1}')
java -jar target/olmreader-$VERSION.jar --file=src/test/resources/Outlook16/outlook_example.olm

