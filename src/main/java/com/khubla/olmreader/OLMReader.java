package com.khubla.olmreader;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class OLMReader {
   private static String FN = "/Users/tom/Desktop/Outlook for Mac Archive.olm";

   public static void main(String[] args) throws IOException {
      readOLMFile();
   }

   private static void readOLMFile() {
      try {
         ZipFile zipfile = new ZipFile(FN);
         for (Enumeration<? extends ZipEntry> e = zipfile.entries(); e.hasMoreElements();) {
            ZipEntry zipEntry = e.nextElement();
            processFile(zipEntry);
         }
         zipfile.close();
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   private static void processFile(ZipEntry zipEntry) {
      System.out.println(zipEntry.getName());
   }
}
