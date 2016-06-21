package com.khubla.olmreader;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class OLMMessage {
   public static OLMMessage read(InputStream inputStream) {
      try {
         OLMMessage ret = new OLMMessage();
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         IOUtils.copy(inputStream, baos);
         System.out.println(baos.toString());
         // DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
         // DocumentBuilder builder = factory.newDocumentBuilder();
         // Document doc = builder.parse(inputStream);
         return ret;
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }
}
