package com.khubla.olmreader.olm;

import java.io.InputStream;

import com.google.common.io.LittleEndianDataInputStream;

public class OLMDataDescriptor {
   /**
    * file signature
    */
   private static final int SIGNATURE = 0x04034b50;

   /**
    * read an OLM file
    */
   public static OLMDataDescriptor read(InputStream inputStream) throws Exception {
      try {
         final OLMDataDescriptor ret = new OLMDataDescriptor();
         final LittleEndianDataInputStream is = new LittleEndianDataInputStream(inputStream);
         /*
          * file crc
          */
         ret.crc = is.readInt();
         /*
          * file compressed size
          */
         ret.compressedSize = is.readInt();
         /*
          * file uncompressed size
          */
         ret.uncompressedSize = is.readInt();
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   /**
    * file crc
    */
   private int crc;
   /**
    * file compressed size
    */
   private int compressedSize;
   /**
    * file uncompressed size
    */
   private int uncompressedSize;

   public int getCrc() {
      return crc;
   }

   public int getCompressedSize() {
      return compressedSize;
   }

   public int getUncompressedSize() {
      return uncompressedSize;
   }
}
