package com.khubla.olmreader;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

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

   public String getTo() {
      NodeList nodeList = document.getElementsByTagName("emailAddress");
      return null;
   }

   public String getFrom() {
      NodeList nodeList = document.getElementsByTagName("OPFMessageCopySenderAddress");
      System.out.println(nodeList.getLength());
      return null;
   }

   public boolean isHTML() {
      NodeList nodeList = document.getElementsByTagName("OPFMessageGetHasHTML");
      if ((null != nodeList) && (nodeList.item(0).getTextContent().compareTo("1") == 0)) {
         return true;
      }
      return false;
   }

   public String getBody() {
      NodeList nodeList = document.getElementsByTagName("OPFMessageCopyBody");
      if ((null != nodeList) && (nodeList.getLength() > 0)) {
         return nodeList.item(0).getTextContent();
      }
      return null;
   }

   public String getHTMLBody() {
      NodeList nodeList = document.getElementsByTagName("OPFMessageCopyHTMLBody");
      if ((null != nodeList) && (nodeList.getLength() > 0)) {
         return nodeList.item(0).getTextContent();
      }
      return null;
   }
}
