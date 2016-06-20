package com.khubla.olmreader.olm;

import java.io.InputStream;

public class OLMPackage {
   /**
    * read an OLM package
    */
   public static OLMPackage read(InputStream inputStream, OLMFileCallback olmFileCallback) throws Exception {
      try {
         final OLMPackage ret = new OLMPackage();
         /*
          * read the central directory
          */
         OLMCentralDirectory olmCentralDirectory = OLMCentralDirectory.read(inputStream);
         /*
          * read a file
          */
         final OLMFile olmFile = OLMFile.read(inputStream);
         olmFileCallback.file(olmFile.getOlmLocalFileHeader().getFilename(), null);
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }
}
