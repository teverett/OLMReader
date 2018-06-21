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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.khubla.olmreader.olm.generated.Appointments;
import com.khubla.olmreader.olm.generated.Appointments.Appointment;
import com.khubla.olmreader.olm.generated.Categories;
import com.khubla.olmreader.olm.generated.Contacts;
import com.khubla.olmreader.olm.generated.Contacts.Contact;
import com.khubla.olmreader.olm.generated.Emails;
import com.khubla.olmreader.olm.generated.Emails.Email;
import com.khubla.olmreader.olm.generated.Notes;
import com.khubla.olmreader.olm.generated.Notes.Note;
import com.khubla.olmreader.olm.generated.Tasks;
import com.khubla.olmreader.olm.generated.Tasks.Task;
import com.khubla.olmreader.util.GenericJAXBMarshaller;

public class OLMFile {
   /**
    * logger
    */
   private static final Logger logger = LoggerFactory.getLogger(OLMFile.class);
   /**
    * XML extension
    */
   private static final String XML = ".xml";
   /**
    * schemas
    */
   private static final String EMAILS_SCHEMA = "/emails.xsd";
   private static final String XML_SCHEMA = "/xml.xsd";
   private static final String CATEGORIES_SCHEMA = "/categories.xsd";
   private static final String CONTACTS_SCHEMA = "/contacts.xsd";
   private static final String TASKS_SCHEMA = "/tasks.xsd";
   private static final String NOTES_SCHEMA = "/notes.xsd";
   private static final String APPOINTMENTS_SCHEMA = "/appointments.xsd";
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
   private final String CONTACTS_XML = "Local/Address Book/Contacts.xml";
   private final String CALENDAR_XML = "Local/Calendar/Calendar.xml";
   private final String TASKS_XML = "Local/Tasks/Tasks.xml";
   private final String NOTES_XML = "Local/Notes/Notes.xml";

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

   private void readAppointments(ZipArchiveEntry zipEntry, OLMMessageCallback olmMessageCallback) throws ZipException, IOException, SAXException {
      /*
       * callback
       */
      if (null != olmMessageCallback) {
         final InputStream zipInputStream = zipfile.getInputStream(zipEntry);
         final InputStream appointmentsSchemaInputStream = OLMFile.class.getResourceAsStream(APPOINTMENTS_SCHEMA);
         final InputStream xmlSchemaInputStream = OLMFile.class.getResourceAsStream(XML_SCHEMA);
         final Source[] sources = new StreamSource[2];
         /*
          * order is important here. JaxB needs to see xml.xsd before olm.xsd
          */
         sources[0] = new StreamSource(xmlSchemaInputStream);
         sources[1] = new StreamSource(appointmentsSchemaInputStream);
         final GenericJAXBMarshaller<Appointments> marshaller = new GenericJAXBMarshaller<Appointments>(Appointments.class, sources);
         Appointments appointments = null;
         try {
            appointments = marshaller.unmarshall(zipInputStream);
         } catch (final Exception e) {
            logger.error("Error in readAppointments", e);
            e.printStackTrace();
         }
         if ((null != appointments) && (null != appointments.getAppointment())) {
            for (int i = 0; i < appointments.getAppointment().size(); i++) {
               final Appointment appointment = appointments.getAppointment().get(i);
               olmMessageCallback.appointment(appointment);
            }
         }
      }
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
       * callback
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
         } catch (final Exception e) {
            logger.error("Error in readCategories", e);
            e.printStackTrace();
         }
         if (null != categories) {
            olmMessageCallback.categories(categories);
         }
      }
   }

   private void readContacts(ZipArchiveEntry zipEntry, OLMMessageCallback olmMessageCallback) throws ZipException, IOException, SAXException {
      /*
       * callback
       */
      if (null != olmMessageCallback) {
         final InputStream zipInputStream = zipfile.getInputStream(zipEntry);
         final InputStream contactchemaInputStream = OLMFile.class.getResourceAsStream(CONTACTS_SCHEMA);
         final InputStream xmlSchemaInputStream = OLMFile.class.getResourceAsStream(XML_SCHEMA);
         final Source[] sources = new StreamSource[2];
         /*
          * order is important here. JaxB needs to see xml.xsd before olm.xsd
          */
         sources[0] = new StreamSource(xmlSchemaInputStream);
         sources[1] = new StreamSource(contactchemaInputStream);
         final GenericJAXBMarshaller<Contacts> marshaller = new GenericJAXBMarshaller<Contacts>(Contacts.class, sources);
         Contacts contacts = null;
         try {
            contacts = marshaller.unmarshall(zipInputStream);
         } catch (final Exception e) {
            logger.error("Error in readContact", e);
            e.printStackTrace();
         }
         if ((null != contacts) && (null != contacts.getContact())) {
            for (int i = 0; i < contacts.getContact().size(); i++) {
               final Contact contact = contacts.getContact().get(i);
               olmMessageCallback.contact(contact);
            }
         }
      }
   }

   private void readEmails(ZipArchiveEntry zipEntry, OLMMessageCallback olmMessageCallback) throws ZipException, IOException, SAXException {
      /*
       * message callback
       */
      if (null != olmMessageCallback) {
         final InputStream zipInputStream = zipfile.getInputStream(zipEntry);
         final InputStream emailsSchemaInputStream = OLMFile.class.getResourceAsStream(EMAILS_SCHEMA);
         final InputStream xmlSchemaInputStream = OLMFile.class.getResourceAsStream(XML_SCHEMA);
         final Source[] sources = new StreamSource[2];
         /*
          * order is important here. JaxB needs to see xml.xsd before olm.xsd
          */
         sources[0] = new StreamSource(xmlSchemaInputStream);
         sources[1] = new StreamSource(emailsSchemaInputStream);
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
               olmMessageCallback.email(email);
            }
         }
      }
   }

   private void readNotes(ZipArchiveEntry zipEntry, OLMMessageCallback olmMessageCallback) throws ZipException, IOException, SAXException {
      /*
       * callback
       */
      if (null != olmMessageCallback) {
         final InputStream zipInputStream = zipfile.getInputStream(zipEntry);
         final InputStream notesSchemaInputStream = OLMFile.class.getResourceAsStream(NOTES_SCHEMA);
         final InputStream xmlSchemaInputStream = OLMFile.class.getResourceAsStream(XML_SCHEMA);
         final Source[] sources = new StreamSource[2];
         /*
          * order is important here. JaxB needs to see xml.xsd before olm.xsd
          */
         sources[0] = new StreamSource(xmlSchemaInputStream);
         sources[1] = new StreamSource(notesSchemaInputStream);
         final GenericJAXBMarshaller<Notes> marshaller = new GenericJAXBMarshaller<Notes>(Notes.class, sources);
         Notes notes = null;
         try {
            notes = marshaller.unmarshall(zipInputStream);
         } catch (final Exception e) {
            logger.error("Error in readNotes", e);
            e.printStackTrace();
         }
         if ((null != notes) && (null != notes.getNote())) {
            for (int i = 0; i < notes.getNote().size(); i++) {
               final Note note = notes.getNote().get(i);
               olmMessageCallback.note(note);
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
            if (zipEntry.isDirectory() == false) {
               logger.info(zipEntry.getName());
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
                  /*
                   * switch on the file type
                   */
                  if (zipEntry.getName().equals(CATEGORIES_XML)) {
                     readCategories(zipEntry, olmMessageCallback);
                  } else if (zipEntry.getName().equals(CONTACTS_XML)) {
                     readContacts(zipEntry, olmMessageCallback);
                  } else if (zipEntry.getName().equals(CALENDAR_XML)) {
                     readAppointments(zipEntry, olmMessageCallback);
                  } else if (zipEntry.getName().equals(TASKS_XML)) {
                     readTasks(zipEntry, olmMessageCallback);
                  } else if (zipEntry.getName().equals(NOTES_XML)) {
                     readNotes(zipEntry, olmMessageCallback);
                  } else {
                     readEmails(zipEntry, olmMessageCallback);
                  }
               }
            }
         }
         zipfile.close();
      } catch (final Exception e) {
         logger.error("Error in readOLMFile", e);
         e.printStackTrace();
      }
   }

   private void readTasks(ZipArchiveEntry zipEntry, OLMMessageCallback olmMessageCallback) throws ZipException, IOException, SAXException {
      /*
       * callback
       */
      if (null != olmMessageCallback) {
         final InputStream zipInputStream = zipfile.getInputStream(zipEntry);
         final InputStream tasksSchemaInputStream = OLMFile.class.getResourceAsStream(TASKS_SCHEMA);
         final InputStream xmlSchemaInputStream = OLMFile.class.getResourceAsStream(XML_SCHEMA);
         final Source[] sources = new StreamSource[2];
         /*
          * order is important here. JaxB needs to see xml.xsd before olm.xsd
          */
         sources[0] = new StreamSource(xmlSchemaInputStream);
         sources[1] = new StreamSource(tasksSchemaInputStream);
         final GenericJAXBMarshaller<Tasks> marshaller = new GenericJAXBMarshaller<Tasks>(Tasks.class, sources);
         Tasks tasks = null;
         try {
            tasks = marshaller.unmarshall(zipInputStream);
         } catch (final Exception e) {
            logger.error("Error in readTasks", e);
            e.printStackTrace();
         }
         if ((null != tasks) && (null != tasks.getTask())) {
            for (int i = 0; i < tasks.getTask().size(); i++) {
               final Task task = tasks.getTask().get(i);
               olmMessageCallback.task(task);
            }
         }
      }
   }
}
