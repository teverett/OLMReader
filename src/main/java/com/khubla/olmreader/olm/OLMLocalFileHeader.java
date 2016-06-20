package com.khubla.olmreader.olm;

import java.io.InputStream;

import com.google.common.io.LittleEndianDataInputStream;

public class OLMLocalFileHeader {
   /**
    * file signature
    */
   private static final int SIGNATURE = 0x04034b50;

   /**
    * read an OLM file
    */
   public static OLMLocalFileHeader read(InputStream inputStream) throws Exception {
      try {
         final OLMLocalFileHeader ret = new OLMLocalFileHeader();
         final LittleEndianDataInputStream is = new LittleEndianDataInputStream(inputStream);
         /*
          * signature
          */
         ret.signature = is.readInt();
         if (ret.signature != SIGNATURE) {
            throw new Exception("Local file header signature is incorrect");
         }
         /*
          * version
          */
         ret.version = is.readShort();
         /*
          * bitflags
          */
         ret.bitflags = is.readShort();
         /*
          * compression type
          */
         ret.compressionType = is.readShort();
         /*
          * last mod
          */
         ret.lastModTime = is.readShort();
         ret.lastModDate = is.readShort();
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
         /*
          * filename length
          */
         ret.filenameLength = is.readShort();
         /*
          * extra field length
          */
         ret.extraFieldLength = is.readShort();
         /*
          * filename
          */
         final byte[] fn = new byte[ret.filenameLength];
         is.read(fn, 0, ret.filenameLength);
         ret.filename = new String(fn);
         /*
          * extra
          */
         final byte[] ex = new byte[ret.extraFieldLength];
         is.read(ex, 0, ret.extraFieldLength);
         ret.extraField = new String(ex);
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   /**
    * file signature
    */
   private int signature;
   /**
    * version
    */
   private short version;
   /**
    * bitflags
    */
   private short bitflags;
   /**
    * compression type
    */
   private short compressionType;
   /**
    * last mod date
    */
   private short lastModDate;
   /**
    * last mod time
    */
   private short lastModTime;
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
   /**
    * filename length
    */
   private short filenameLength;
   /**
    * extra field length
    */
   private short extraFieldLength;
   /**
    * filename
    */
   private String filename;
   /**
    * extraField
    */
   private String extraField;

   public short getBitflags() {
      return bitflags;
   }

   public int getCompressedSize() {
      return compressedSize;
   }

   public short getCompressionType() {
      return compressionType;
   }

   public int getCrc() {
      return crc;
   }

   public String getExtraField() {
      return extraField;
   }

   public short getExtraFieldLength() {
      return extraFieldLength;
   }

   public String getFilename() {
      return filename;
   }

   public short getFilenameLength() {
      return filenameLength;
   }

   public short getLastModDate() {
      return lastModDate;
   }

   public short getLastModTime() {
      return lastModTime;
   }

   public int getSignature() {
      return signature;
   }

   public int getUncompressedSize() {
      return uncompressedSize;
   }

   public short getVersion() {
      return version;
   }

   /**
    * check if there is a data descriptor
    */
   public boolean hasDataDescriptor() {
      return ((bitflags >> 3) & 1) != 0;
   }

   /**
    * check if encrypted
    */
   public boolean isEncrypted() {
      return ((bitflags >> 0) & 1) != 0;
   }
}
