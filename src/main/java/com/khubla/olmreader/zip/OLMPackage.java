package com.khubla.olmreader.zip;

import java.io.InputStream;

public class OLMPackage {
   /**
    * read an OLM package
    */
   public static OLMPackage read(InputStream inputStream) throws Exception {
      try {
         final OLMPackage ret = new OLMPackage();
         OLMFile.read(inputStream);
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }
}
