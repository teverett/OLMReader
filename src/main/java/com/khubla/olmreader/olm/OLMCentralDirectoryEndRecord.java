package com.khubla.olmreader.olm;

import java.io.RandomAccessFile;

import org.apache.commons.io.EndianUtils;

public class OLMCentralDirectoryEndRecord {
   /**
    * central directory signature
    */
   public static final int SIGNATURE = 0x06054b50;

   /**
    * read a central directory
    */
   public static OLMCentralDirectoryEndRecord read(RandomAccessFile randomAccessFile) throws Exception {
      try {
         final OLMCentralDirectoryEndRecord ret = new OLMCentralDirectoryEndRecord();
         /*
          * signature
          */
         ret.signature = EndianUtils.swapInteger(randomAccessFile.readInt());
         if (ret.signature != SIGNATURE) {
            throw new Exception("Central Directory signature is incorrect");
         }
         /*
          * diskNumber
          */
         ret.diskNumber = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * diskNumberWCD
          */
         ret.diskNumberWCD = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * diskEntries
          */
         ret.diskEntries = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * totalEntries
          */
         ret.totalEntries = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * centralDirectorySize
          */
         ret.centralDirectorySize = EndianUtils.swapInteger(randomAccessFile.readInt());
         /*
          * OffSetOfCD
          */
         ret.OffSetOfCD = EndianUtils.swapInteger(randomAccessFile.readInt());
         /*
          * commentLen
          */
         ret.commentLen = EndianUtils.swapShort(randomAccessFile.readShort());
         /*
          * comment
          */
         if (ret.commentLen > 0) {
            byte[] buffer = new byte[ret.commentLen];
            randomAccessFile.read(buffer, 0, ret.commentLen);
            ret.comment = new String(buffer);
         }
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   /**
    * signature
    */
   private int signature;
   /**
    * diskNumber
    */
   private short diskNumber;
   /**
    * diskNumberWCD
    */
   private short diskNumberWCD;
   /**
    * diskEntries
    */
   private short diskEntries;
   /**
    * totalEntries
    */
   private short totalEntries;
   /**
    * centralDirectorySize
    */
   private int centralDirectorySize;
   /**
    * OffSetOfCD
    */
   private int OffSetOfCD;
   /**
    * commentLen
    */
   private short commentLen;
   /**
    * comment
    */
   private String comment;
}
