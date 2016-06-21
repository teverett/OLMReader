package com.khubla.olmreader.olm;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;

import org.apache.commons.io.EndianUtils;

public class OLMCentralDirectory {
   /**
    * central directory signature
    */
   private static final byte[] START_BYTE_SIGNATURE = { 0x50, 0x4b, 0x01, 0x02 };
   private static final int CHUNKSIZE = 1000;
   private static final int EXTRA = 10;
   /**
    * file headers
    */
   private final ArrayList<OLMCentralDirectoryFileHeader> fileHeaders = new ArrayList<OLMCentralDirectoryFileHeader>();
   /**
    * end record
    */
   private OLMCentralDirectoryEndRecord olmCentralDirectoryEndRecord;

   /**
    * read a central directory
    */
   public static OLMCentralDirectory read(RandomAccessFile randomAccessFile) throws Exception {
      try {
         long idx = scanForHeader(randomAccessFile);
         if (-1 != idx) {
            final OLMCentralDirectory ret = new OLMCentralDirectory();
            /*
             * seek to the start of the central directory
             */
            randomAccessFile.seek(idx);
            /*
             * read file headers
             */
            boolean more = true;
            while (more) {
               /*
                * read
                */
               OLMCentralDirectoryFileHeader olmCentralDirectoryFileHeader = OLMCentralDirectoryFileHeader.read(randomAccessFile);
               if (null != olmCentralDirectoryFileHeader) {
                  ret.fileHeaders.add(olmCentralDirectoryFileHeader);
                  System.out.println(olmCentralDirectoryFileHeader.getFilename());
               }
               /*
                * check
                */
               int endSig = EndianUtils.swapInteger(randomAccessFile.readInt());
               randomAccessFile.seek(randomAccessFile.getFilePointer() - 4);
               if (endSig == OLMCentralDirectoryEndRecord.SIGNATURE) {
                  ret.olmCentralDirectoryEndRecord = OLMCentralDirectoryEndRecord.read(randomAccessFile);
                  more = false;
               }
            }
            return ret;
         }
         return null;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      }
   }

   /**
    * scan for the header
    */
   private static long scanForHeader(RandomAccessFile randomAccessFile) throws IOException {
      byte[] chunk = new byte[CHUNKSIZE];
      long idx = randomAccessFile.length() - CHUNKSIZE;
      try {
         randomAccessFile.seek(idx);
         /*
          * read bytes, backward
          */
         randomAccessFile.readFully(chunk, 0, CHUNKSIZE);
         /*
          * find
          */
         for (int i = 0; i < CHUNKSIZE; i++) {
            if ((chunk[i + 0] == START_BYTE_SIGNATURE[0]) && (chunk[i + 1] == START_BYTE_SIGNATURE[1]) && (chunk[i + 2] == START_BYTE_SIGNATURE[2]) && (chunk[i + 3] == START_BYTE_SIGNATURE[3])) {
               return idx + i;
            }
         }
         /*
          * next idx
          */
         if (idx < CHUNKSIZE) {
            idx = 0;
         } else {
            idx = idx - (CHUNKSIZE - EXTRA);
         }
         return -1;
      } catch (Exception e) {
         e.printStackTrace();
         return -1;
      }
   }
}
