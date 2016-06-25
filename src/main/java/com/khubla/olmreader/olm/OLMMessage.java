package com.khubla.olmreader.olm;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class OLMMessage {
   public static OLMMessage read(InputStream inputStream) {
      try {
         final OLMMessage ret = new OLMMessage();
         final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         final DocumentBuilder builder = factory.newDocumentBuilder();
         final Document document = builder.parse(inputStream);
         if (null != document) {
            ret.emailAddress = safeGetElement(document, "emailAddress");
            ret.fromAddress = safeGetElement(document, "OPFMessageCopySenderAddress");
            final String isHTML = safeGetElement(document, "OPFMessageGetHasHTML");
            if ((null != isHTML) && (isHTML.compareTo("1") == 0)) {
               ret.isHTML = true;
            } else {
               ret.isHTML = false;
            }
            ret.body = safeGetElement(document, "OPFMessageCopyBody");
            ret.htmlBody = safeGetElement(document, "OPFMessageCopyHTMLBody");
         }
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   private static String safeGetElement(Document document, String name) {
      final NodeList nodeList = document.getElementsByTagName(name);
      if ((null != nodeList) && (nodeList.getLength() > 0)) {
         return nodeList.item(0).getTextContent();
      }
      return null;
   }

   private String emailAddress;
   private String fromAddress;
   private String body;
   private String htmlBody;
   private boolean isHTML;

   public String getBody() {
      return body;
   }

   public String getEmailAddress() {
      return emailAddress;
   }

   public String getFromAddress() {
      return fromAddress;
   }

   public String getHtmlBody() {
      return htmlBody;
   }

   public boolean isHTML() {
      return isHTML;
   }
}
