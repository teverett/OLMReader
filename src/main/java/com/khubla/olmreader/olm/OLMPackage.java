package com.khubla.olmreader.olm;

import java.io.File;
import java.io.RandomAccessFile;

/**
 * https://users.cs.jmu.edu/buchhofp/forensics/formats/pkzip.html
 */
/**
 * http://fileanalysis.net/zip/
 */
/**
 * @author tom
 */
public class OLMPackage {
   /**
    * read an OLM package
    */
   public static OLMPackage read(File file, OLMFileCallback olmFileCallback) throws Exception {
      RandomAccessFile randomAccessFile = null;
      try {
         final OLMPackage ret = new OLMPackage();
         /*
          * read the central directory
          */
         randomAccessFile = new RandomAccessFile(file, "r");
         OLMCentralDirectory olmCentralDirectory = OLMCentralDirectory.read(randomAccessFile);
         /*
          * read a file
          */
         // final OLMFile olmFile = OLMFile.read(inputStream);
         // olmFileCallback.file(olmFile.getOlmLocalFileHeader().getFilename(), null);
         return ret;
      } catch (final Exception e) {
         e.printStackTrace();
         return null;
      } finally {
         if (null != randomAccessFile) {
            randomAccessFile.close();
         }
      }
   }
}
