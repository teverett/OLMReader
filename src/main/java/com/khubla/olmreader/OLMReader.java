package com.khubla.olmreader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import com.khubla.olmreader.olm.OLMFileCallback;
import com.khubla.olmreader.olm.OLMPackage;

public class OLMReader implements OLMFileCallback {
   private static String FN = "/Users/tom/Desktop/Outlook for Mac Archive.olm";

   public static void main(String[] args) throws IOException {
      readOLMFile();
   }

   private static void readOLMFile() {
      try {
         final InputStream is = new FileInputStream(FN);
         final OLMReader olmReader = new OLMReader();
         OLMPackage.read(is, olmReader);
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   public void file(String filename, byte[] data) {
      System.out.println(filename);
   }
}
