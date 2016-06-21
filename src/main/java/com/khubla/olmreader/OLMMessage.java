package com.khubla.olmreader;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

public class OLMMessage {
   private Document document;

   public static OLMMessage read(InputStream inputStream) {
      try {
         OLMMessage ret = new OLMMessage();
         DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         DocumentBuilder builder = factory.newDocumentBuilder();
         ret.document = builder.parse(inputStream);
         return ret;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public Document getDocument() {
      return document;
   }
}
