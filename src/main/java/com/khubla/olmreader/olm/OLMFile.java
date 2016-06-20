package com.khubla.olmreader.olm;

import java.io.InputStream;

/**
 * https://users.cs.jmu.edu/buchhofp/forensics/formats/pkzip.html
 *
 * @author tom
 */
public class OLMFile {
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
         final boolean isEncrypted = ret.olmLocalFileHeader.isEncrypted();
         if (isEncrypted) {
            ret.olmCryptoHeader = OLMCryptoHeader.read(inputStream);
         }
         /*
          * data descriptor
          */
         final boolean hasDataDescriptor = ret.olmLocalFileHeader.hasDataDescriptor();
         if (hasDataDescriptor) {
            ret.olmDataDescriptor = OLMDataDescriptor.read(inputStream);
         }
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   private OLMLocalFileHeader olmLocalFileHeader = null;
   private OLMCryptoHeader olmCryptoHeader = null;
   private OLMDataDescriptor olmDataDescriptor = null;

   public OLMCryptoHeader getOlmCryptoHeader() {
      return olmCryptoHeader;
   }

   public OLMDataDescriptor getOlmDataDescriptor() {
      return olmDataDescriptor;
   }

   public OLMLocalFileHeader getOlmLocalFileHeader() {
      return olmLocalFileHeader;
   }
}
