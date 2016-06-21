package com.khubla.olmreader;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;

public class OLMReader {
   private static String FN = "/Users/tom/Desktop/Outlook for Mac Archive.olm";

   public static void main(String[] args) throws IOException {
      readOLMFile();
   }

   private static void readOLMFile() {
      try {
         ZipFile zipfile = new ZipFile(FN);
         for (Enumeration<ZipArchiveEntry> e = zipfile.getEntries(); e.hasMoreElements();) {
            ZipArchiveEntry zipEntry = e.nextElement();
            System.out.println(zipEntry.getName());
            if (zipEntry.isDirectory() == false) {
               if (zipEntry.getName().trim().toLowerCase().endsWith(".xml")) {
                  InputStream inputStream = zipfile.getInputStream(zipEntry);
                  OLMMessage olmMessage = OLMMessage.read(inputStream);
                  System.out.println(olmMessage.getBody());
               }
            }
         }
         zipfile.close();
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
