package com.khubla.olmreader.olm;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMUtils {
   public static Integer safeGetAttributeInteger(Node node, String name) {
      final Node n = node.getAttributes().getNamedItem(name);
      if (n != null) {
         return Integer.parseInt(n.getTextContent());
      }
      return null;
   }

   public static String safeGetAttributeString(Node node, String name) {
      final Node n = node.getAttributes().getNamedItem(name);
      if (n != null) {
         return n.getTextContent();
      }
      return null;
   }

   public static Node safeGetElement(Node node, String name) {
      final NodeList nodeList = node.getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
         final Node n = nodeList.item(i);
         if (n.getNodeName().compareTo(name) == 0) {
            return n;
         }
      }
      return null;
   }

   public static Boolean safeGetElementBoolean(Node node, String name) {
      final NodeList nodeList = node.getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
         final Node n = nodeList.item(i);
         if (n.getNodeName().compareTo(name) == 0) {
            final String s = n.getTextContent();
            if ((null != s) && (s.compareTo("1") == 0)) {
               return true;
            } else {
               return false;
            }
         }
      }
      return null;
   }

   public static Integer safeGetElementInteger(Node node, String name) {
      final NodeList nodeList = node.getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
         final Node n = nodeList.item(i);
         if (n.getNodeName().compareTo(name) == 0) {
            final String s = n.getTextContent();
            return Integer.parseInt(s);
         }
      }
      return null;
   }

   public static String safeGetElementString(Node node, String name) {
      final NodeList nodeList = node.getChildNodes();
      for (int i = 0; i < nodeList.getLength(); i++) {
         final Node n = nodeList.item(i);
         if (n.getNodeName().compareTo(name) == 0) {
            return n.getTextContent();
         }
      }
      return null;
   }
}
