package com.khubla.olmreader.olm;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Enumeration;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;

public class OLMFile {
   private static final String XML = ".xml";

   public static void readOLMFile(String filename, OLMMessageCallback olmMessageCallback, OLMRawMessageCallback olmRawMessageCallback) {
      try {
         final ZipFile zipfile = new ZipFile(filename);
         for (final Enumeration<ZipArchiveEntry> e = zipfile.getEntries(); e.hasMoreElements();) {
            final ZipArchiveEntry zipEntry = e.nextElement();
            System.out.println(zipEntry.getName());
            if (zipEntry.isDirectory() == false) {
               if (zipEntry.getName().trim().toLowerCase().endsWith(XML)) {
                  /*
                   * raw callback
                   */
                  if (null != olmRawMessageCallback) {
                     final InputStream inputStream = zipfile.getInputStream(zipEntry);
                     final ByteArrayOutputStream baos = new ByteArrayOutputStream();
                     IOUtils.copy(inputStream, baos);
                     olmRawMessageCallback.message(baos.toString());
                  }
                  /*
                   * message callback
                   */
                  if (null != olmMessageCallback) {
                     final InputStream inputStream = zipfile.getInputStream(zipEntry);
                     final OLMMessage olmMessage = OLMMessage.read(inputStream);
                     olmMessageCallback.message(olmMessage);
                  }
               }
            }
         }
         zipfile.close();
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }
}
