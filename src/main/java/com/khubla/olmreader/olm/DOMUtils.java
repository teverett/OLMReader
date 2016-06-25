package com.khubla.olmreader.olm;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DOMUtils {
   public static String safeGetElement(Document document, String name) {
      final NodeList nodeList = document.getElementsByTagName(name);
      if ((null != nodeList) && (nodeList.getLength() > 0)) {
         return nodeList.item(0).getTextContent();
      }
      return null;
   }

   public static String safeGetAttribute(Node node, String name) {
      Node n = node.getAttributes().getNamedItem(name);
      if (n != null) {
         return n.getTextContent();
      }
      return null;
   }
}
