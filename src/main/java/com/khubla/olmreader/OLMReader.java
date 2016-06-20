package com.khubla.olmreader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.khubla.olmreader.zip.OLMPackage;

public class OLMReader {
   private static String FN = "/Users/tom/Desktop/Outlook for Mac Archive.olm";

   public static void main(String[] args) throws IOException {
      readOLMFile();
   }

   private static void readOLMFile() {
      try {
         final InputStream is = new FileInputStream(FN);
         OLMPackage.read(is);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
