package com.khubla.olmreader.olm;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipException;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.IOUtils;
import org.xml.sax.SAXException;

import com.khubla.olmreader.olm.generated.Categories;
import com.khubla.olmreader.olm.generated.Emails;
import com.khubla.olmreader.olm.generated.Emails.Email;
import com.khubla.olmreader.util.GenericJAXBMarshaller;

public class OLMFile {
   /**
    * XML extension
    */
   private static final String XML = ".xml";
   /**
    * schemas
    */
   private static final String OLM_SCHEMA = "/olm.xsd";
   private static final String XML_SCHEMA = "/xml.xsd";
   private static final String CATEGORIES_SCHEMA = "/categories.xsd";
   /**
    * date format
    */
   private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
   /**
    * the zip
    */
   private final ZipFile zipfile;
   /**
    * categories.xml
    */
   private final String CATEGORIES_XML = "Categories.xml";

   /**
    * ctor
    */
   public OLMFile(String filename) throws IOException {
      zipfile = new ZipFile(filename);
   }

   /**
    * parse the date format the OLM uses
    */
   public Date parseOLMDate(String date) throws ParseException {
      return simpleDateFormat.parse(date);
   }

   /**
    * read an attachment
    */
   public byte[] readAttachment(Emails.Email.OPFMessageCopyAttachmentList.MessageAttachment messageAttachment) throws ZipException, IOException {
      final ZipArchiveEntry zipEntry = zipfile.getEntry(messageAttachment.getOPFAttachmentURL());
      if (null != zipEntry) {
         final ByteArrayOutputStream boas = new ByteArrayOutputStream();
         IOUtils.copy(zipfile.getInputStream(zipEntry), boas);
         return boas.toByteArray();
      }
      return null;
   }

   private void readCategories(ZipArchiveEntry zipEntry, OLMMessageCallback olmMessageCallback) throws ZipException, IOException, SAXException {
      /*
       * message callback
       */
      if (null != olmMessageCallback) {
         final InputStream zipInputStream = zipfile.getInputStream(zipEntry);
         final InputStream categoriesSchemaInputStream = OLMFile.class.getResourceAsStream(CATEGORIES_SCHEMA);
         final InputStream xmlSchemaInputStream = OLMFile.class.getResourceAsStream(XML_SCHEMA);
         final Source[] sources = new StreamSource[2];
         /*
          * order is important here. JaxB needs to see xml.xsd before olm.xsd
          */
         sources[0] = new StreamSource(xmlSchemaInputStream);
         sources[1] = new StreamSource(categoriesSchemaInputStream);
         final GenericJAXBMarshaller<Categories> marshaller = new GenericJAXBMarshaller<Categories>(Categories.class, sources);
         Categories categories = null;
         try {
            categories = marshaller.unmarshall(zipInputStream);
         } catch (final Exception ex) {
            ex.printStackTrace();
         }
         if (null != categories) {
            olmMessageCallback.categories(categories);
         }
      }
   }

   private void readEmail(ZipArchiveEntry zipEntry, OLMMessageCallback olmMessageCallback) throws ZipException, IOException, SAXException {
      /*
       * message callback
       */
      if (null != olmMessageCallback) {
         final InputStream zipInputStream = zipfile.getInputStream(zipEntry);
         final InputStream olmSchemaInputStream = OLMFile.class.getResourceAsStream(OLM_SCHEMA);
         final InputStream xmlSchemaInputStream = OLMFile.class.getResourceAsStream(XML_SCHEMA);
         final Source[] sources = new StreamSource[2];
         /*
          * order is important here. JaxB needs to see xml.xsd before olm.xsd
          */
         sources[0] = new StreamSource(xmlSchemaInputStream);
         sources[1] = new StreamSource(olmSchemaInputStream);
         final GenericJAXBMarshaller<Emails> marshaller = new GenericJAXBMarshaller<Emails>(Emails.class, sources);
         Emails emails = null;
         try {
            emails = marshaller.unmarshall(zipInputStream);
         } catch (final Exception ex) {
            ex.printStackTrace();
         }
         if ((null != emails) && (null != emails.getEmail())) {
            for (int i = 0; i < emails.getEmail().size(); i++) {
               final Email email = emails.getEmail().get(i);
               olmMessageCallback.message(email);
            }
         }
      }
   }

   /**
    * read OLM file
    */
   public void readOLMFile(OLMMessageCallback olmMessageCallback, OLMRawMessageCallback olmRawMessageCallback) {
      try {
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
                     olmRawMessageCallback.rawMessage(baos.toString());
                  }
                  if (zipEntry.getName().compareTo(CATEGORIES_XML) != 0) {
                     readEmail(zipEntry, olmMessageCallback);
                  } else {
                     readCategories(zipEntry, olmMessageCallback);
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
