package com.khubla.olmreader.olm;

import java.io.InputStream;

public class OLMCryptoHeader {
   /**
    * read an OLM file
    */
   public static OLMCryptoHeader read(InputStream inputStream) throws Exception {
      try {
         final OLMCryptoHeader ret = new OLMCryptoHeader();
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }
}
