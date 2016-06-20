package com.khubla.olmreader.olm;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

import com.google.common.io.LittleEndianDataInputStream;

public class OLMCentralDirectory {
   /**
    * central directory signature
    */
   private static final int SIGNATURE = 0x02014b50;
   private static final String STRING_SIGNATURE = "504b0102";

   /**
    * read a central directory
    */
   public static OLMCentralDirectory read(InputStream inputStream) throws Exception {
      try {
         boolean foundHeader = scanForHeader(inputStream);
         final OLMCentralDirectory ret = new OLMCentralDirectory();
         final LittleEndianDataInputStream is = new LittleEndianDataInputStream(inputStream);
         /*
          * signature
          */
         ret.signature = is.readInt();
         if (ret.signature != SIGNATURE) {
            throw new Exception("Central Directory signature is incorrect");
         }
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   /**
    * scan for the header
    */
   private static boolean scanForHeader(InputStream inputStream) {
      Scanner streamScanner = null;
      try {
         streamScanner = new Scanner(new InputStreamReader(inputStream));
         if (streamScanner.findWithinHorizon(STRING_SIGNATURE, 0) != null) {
            return true;
         } else {
            return false;
         }
      } catch (Exception e) {
         e.printStackTrace();
         return false;
      } finally {
         if (null != streamScanner) {
            streamScanner.close();
         }
      }
   }

   /**
    * file signature
    */
   private int signature;
}
