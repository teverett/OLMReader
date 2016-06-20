package com.khubla.olmreader.zip;

import java.io.InputStream;

/**
 * https://users.cs.jmu.edu/buchhofp/forensics/formats/pkzip.html
 * 
 * @author tom
 */
public class OLMFile {
   private OLMLocalFileHeader olmLocalFileHeader = null;

   public OLMLocalFileHeader getOlmLocalFileHeader() {
      return olmLocalFileHeader;
   }

   public OLMCryptoHeader getOlmCryptoHeader() {
      return olmCryptoHeader;
   }

   public OLMDataDescriptor getOlmDataDescriptor() {
      return olmDataDescriptor;
   }

   private OLMCryptoHeader olmCryptoHeader = null;
   private OLMDataDescriptor olmDataDescriptor = null;

   /**
    * read an OLM file
    */
   public static OLMFile read(InputStream inputStream) throws Exception {
      try {
         final OLMFile ret = new OLMFile();
         /*
          * header
          */
         ret.olmLocalFileHeader = OLMLocalFileHeader.read(inputStream);
         /*
          * encryption header
          */
         boolean isEncrypted = ret.olmLocalFileHeader.isEncrypted();
         if (isEncrypted) {
            ret.olmCryptoHeader = OLMCryptoHeader.read(inputStream);
         }
         /*
          * data descriptor
          */
         boolean hasDataDescriptor = ret.olmLocalFileHeader.hasDataDescriptor();
         if (hasDataDescriptor) {
            ret.olmDataDescriptor = OLMDataDescriptor.read(inputStream);
         }
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }
}
