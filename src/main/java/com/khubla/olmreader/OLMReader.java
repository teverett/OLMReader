package com.khubla.olmreader;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.khubla.olmreader.olm.OLMFileCallback;

public class OLMReader implements OLMFileCallback {
   private static String FN = "/Users/tom/Desktop/Outlook for Mac Archive.olm";

   public static void main(String[] args) throws IOException {
      readOLMFile();
   }

   private static void readOLMFile() {
      try {
         ZipFile zipfile = new ZipFile(FN);
         for (Enumeration<? extends ZipEntry> e = zipfile.entries(); e.hasMoreElements();) {
            ZipEntry zipEntry = e.nextElement();
            System.out.println(zipEntry.getName());
         }
         // File file = new File(FN);
         // if (file.exists()) {
         // final OLMReader olmReader = new OLMReader();
         // OLMPackage.read(file, olmReader);
         // }
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   public void file(String filename, byte[] data) {
      System.out.println(filename);
   }
}
