package com.khubla.olmreader.olm;

import java.io.RandomAccessFile;

import org.apache.commons.io.EndianUtils;

public class OLMCentralDirectoryFileHeader {
   /**
    * central directory signature
    */
   private static final int SIGNATURE = 0x02014b50;

   /**
    * read a central directory
    */
   public static OLMCentralDirectoryFileHeader read(RandomAccessFile randomAccessFile) throws Exception {
      try {
         final OLMCentralDirectoryFileHeader ret = new OLMCentralDirectoryFileHeader();
         /*
          * signature
          */
         ret.signature = EndianUtils.swapInteger(randomAccessFile.readInt());
         if (ret.signature != SIGNATURE) {
            throw new Exception("Central Directory signature is incorrect");
         }
         /*
          * version
          */
         ret.version = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * version needed
          */
         ret.versionNeeded = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * flags
          */
         ret.flags = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * compression
          */
         ret.compression = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * modTime
          */
         ret.modTime = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * modDate
          */
         ret.modDate = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * crc32
          */
         ret.crc32 = EndianUtils.swapInteger(randomAccessFile.readInt());
         /*
          * compressedSize
          */
         ret.compressedSize = EndianUtils.swapInteger(randomAccessFile.readInt());
         /*
          * uncompressedSize
          */
         ret.uncompressedSize = EndianUtils.swapInteger(randomAccessFile.readInt());
         /*
          * filenameLength
          */
         ret.filenameLength = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * extraLength
          */
         ret.extraLength = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * fileCommLength
          */
         ret.fileCommLength = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * diskNoStart
          */
         ret.diskNoStart = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * internalAttr
          */
         ret.internalAttr = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * externalAttr
          */
         ret.externalAttr = EndianUtils.swapInteger(randomAccessFile.readInt());
         /*
          * offsetLocalHeader
          */
         ret.offsetLocalHeader = EndianUtils.swapInteger(randomAccessFile.readInt());
         /*
          * filename
          */
         if (ret.filenameLength > 0) {
            byte[] buffer = new byte[ret.filenameLength];
            randomAccessFile.read(buffer, 0, ret.filenameLength);
            ret.filename = new String(buffer);
         }
         /*
          * extra attr
          */
         if (ret.extraLength > 0) {
            ret.extraField = new byte[ret.extraLength];
            randomAccessFile.read(ret.extraField, 0, ret.extraLength);
         } else {
            ret.extraField = null;
         }
         /*
          * comment
          */
         if (ret.fileCommLength > 0) {
            byte[] buffer = new byte[ret.fileCommLength];
            randomAccessFile.read(buffer, 0, ret.fileCommLength);
            ret.comment = new String(buffer);
         }
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   public int getSignature() {
      return signature;
   }

   public short getVersion() {
      return version;
   }

   public short getVersionNeeded() {
      return versionNeeded;
   }

   public short getFlags() {
      return flags;
   }

   public short getCompression() {
      return compression;
   }

   public short getModTime() {
      return modTime;
   }

   public short getModDate() {
      return modDate;
   }

   public int getCrc32() {
      return crc32;
   }

   public int getCompressedSize() {
      return compressedSize;
   }

   public int getUncompressedSize() {
      return uncompressedSize;
   }

   public short getFilenameLength() {
      return filenameLength;
   }

   public short getExtraLength() {
      return extraLength;
   }

   public short getFileCommLength() {
      return fileCommLength;
   }

   public short getDiskNoStart() {
      return diskNoStart;
   }

   public int getExternalAttr() {
      return externalAttr;
   }

   public int getOffsetLocalHeader() {
      return offsetLocalHeader;
   }

   public String getFilename() {
      return filename;
   }

   public byte[] getExtraField() {
      return extraField;
   }

   public String getComment() {
      return comment;
   }

   /**
    * signature
    */
   private int signature;
   /**
    * version
    */
   private short version;
   /**
    * versionNeeded
    */
   private short versionNeeded;
   /**
    * flags
    */
   private short flags;
   /**
    * compression
    */
   private short compression;
   /**
    * modTime
    */
   private short modTime;
   /**
    * modDate
    */
   private short modDate;
   /**
    * crc32
    */
   private int crc32;
   /**
    * compressedSize
    */
   private int compressedSize;
   /**
    * uncompressedSize
    */
   private int uncompressedSize;
   /**
    * filenameLength
    */
   private short filenameLength;
   /**
    * extraLength
    */
   private short extraLength;
   /**
    * fileCommLength
    */
   private short fileCommLength;
   /**
    * diskNoStart
    */
   private short diskNoStart;
   /**
    * internalAttr
    */
   private short internalAttr;
   /**
    * externalAttr
    */
   private int externalAttr;
   /**
    * offsetLocalHeader
    */
   private int offsetLocalHeader;
   /**
    * filename
    */
   private String filename;
   /**
    * extrafield
    */
   private byte[] extraField;
   /**
    * comment
    */
   private String comment;
}
