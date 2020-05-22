package com.khubla.olmreader.olm;

import java.io.*;
import java.nio.file.*;
import java.text.*;
import java.util.*;
import java.util.zip.*;

import org.apache.commons.compress.archivers.zip.*;
import org.apache.commons.compress.archivers.zip.ZipFile;
import org.apache.commons.compress.utils.*;
import org.apache.logging.log4j.*;
import org.xml.sax.*;

import com.khubla.olmreader.olm.generated.*;
import com.khubla.olmreader.olm.generated.Appointments.*;
import com.khubla.olmreader.olm.generated.Contacts.*;
import com.khubla.olmreader.olm.generated.Emails.*;
import com.khubla.olmreader.olm.generated.Emails.Email.OPFMessageCopyAttachmentList.*;
import com.khubla.olmreader.olm.generated.Groups.*;
import com.khubla.olmreader.olm.generated.Notes.*;
import com.khubla.olmreader.olm.generated.Tasks.*;
import com.khubla.olmreader.util.*;

public class OLMFile {
	/**
	 * logger
	 */
	private static final Logger logger = LogManager.getLogger(OLMFile.class);
	/**
	 * XML extension
	 */
	private static final String XML = ".xml";
	/**
	 * schemas
	 */
	private static final String EMAILS_SCHEMA = "/schema/emails.xsd";
	private static final String XML_SCHEMA = "/schema/xml.xsd";
	private static final String CATEGORIES_SCHEMA = "/schema/categories.xsd";
	private static final String CONTACTS_SCHEMA = "/schema/contacts.xsd";
	private static final String TASKS_SCHEMA = "/schema/tasks.xsd";
	private static final String NOTES_SCHEMA = "/schema/notes.xsd";
	private static final String APPOINTMENTS_SCHEMA = "/schema/appointments.xsd";
	private static final String GROUPS_SCHEMA = "/schema/groups.xsd";
	/**
	 * date format
	 */
	private static final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
	/**
	 * the zip
	 */
	private final ZipFile zipfile;
	/**
	 * OLM XML
	 */
	private final String CATEGORIES_XML = "Categories.xml";
	private final String CONTACTS_XML = "Contacts.xml";
	private final String CALENDAR_XML = "Calendar.xml";
	private final String TASKS_XML = "Tasks.xml";
	private final String NOTES_XML = "Notes.xml";
	private final String GROUPS_XML = "Groups.xml";

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
			final GenericJAXBMarshaller<Appointments> marshaller = new GenericJAXBMarshaller<Appointments>(Appointments.class, new String[] { XML_SCHEMA, APPOINTMENTS_SCHEMA });
			Appointments appointments = null;
			try {
				appointments = marshaller.unmarshall(zipInputStream);
			} catch (final Exception e) {
				logger.error("Error in readAppointments in file '" + zipEntry.getName() + "'", e);
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
			final GenericJAXBMarshaller<Categories> marshaller = new GenericJAXBMarshaller<Categories>(Categories.class, new String[] { XML_SCHEMA, CATEGORIES_SCHEMA });
			Categories categories = null;
			try {
				categories = marshaller.unmarshall(zipInputStream);
			} catch (final Exception e) {
				logger.error("Error in readCategories in file '" + zipEntry.getName() + "'", e);
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
			final GenericJAXBMarshaller<Contacts> marshaller = new GenericJAXBMarshaller<Contacts>(Contacts.class, new String[] { XML_SCHEMA, CONTACTS_SCHEMA });
			Contacts contacts = null;
			try {
				contacts = marshaller.unmarshall(zipInputStream);
			} catch (final Exception e) {
				logger.error("Error in readContacts in file '" + zipEntry.getName() + "'", e);
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
			final GenericJAXBMarshaller<Emails> marshaller = new GenericJAXBMarshaller<Emails>(Emails.class, new String[] { XML_SCHEMA, EMAILS_SCHEMA });
			Emails emails = null;
			try {
				emails = marshaller.unmarshall(zipInputStream);
			} catch (final Exception e) {
				logger.error("Error in readEmails in file '" + zipEntry.getName() + "'", e);
				e.printStackTrace();
			}
			if ((null != emails) && (null != emails.getEmail())) {
				for (int i = 0; i < emails.getEmail().size(); i++) {
					final Email email = emails.getEmail().get(i);
					HashMap<String, byte[]> attachments = null;
					/*
					 * attachments
					 */
					if (null != email.getOPFMessageCopyAttachmentList()) {
						attachments = new HashMap<String, byte[]>();
						for (final MessageAttachment messageAttachment : email.getOPFMessageCopyAttachmentList().getMessageAttachment()) {
							final byte[] data = readAttachment(messageAttachment);
							attachments.put(messageAttachment.getOPFAttachmentName(), data);
						}
					}
					/*
					 * ok
					 */
					olmMessageCallback.email(email, attachments);
				}
			}
		}
	}

	private void readGroups(ZipArchiveEntry zipEntry, OLMMessageCallback olmMessageCallback) throws ZipException, IOException, SAXException {
		/*
		 * callback
		 */
		if (null != olmMessageCallback) {
			final InputStream zipInputStream = zipfile.getInputStream(zipEntry);
			final GenericJAXBMarshaller<Groups> marshaller = new GenericJAXBMarshaller<Groups>(Groups.class, new String[] { XML_SCHEMA, GROUPS_SCHEMA });
			Groups groups = null;
			try {
				groups = marshaller.unmarshall(zipInputStream);
			} catch (final Exception e) {
				logger.error("Error in readGroups in file '" + zipEntry.getName() + "'", e);
				e.printStackTrace();
			}
			if ((null != groups) && (null != groups.getGroup())) {
				for (int i = 0; i < groups.getGroup().size(); i++) {
					final Group group = groups.getGroup().get(i);
					olmMessageCallback.group(group);
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
			final GenericJAXBMarshaller<Notes> marshaller = new GenericJAXBMarshaller<Notes>(Notes.class, new String[] { XML_SCHEMA, NOTES_SCHEMA });
			Notes notes = null;
			try {
				notes = marshaller.unmarshall(zipInputStream);
			} catch (final Exception e) {
				logger.error("Error in readNotes in file '" + zipEntry.getName() + "'", e);
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
						 * get the actual file name
						 */
						String fileName = zipEntry.getName();
						fileName = Paths.get(fileName).getFileName().toString();
						/*
						 * switch on the file type
						 */
						if (fileName.equals(CATEGORIES_XML)) {
							readCategories(zipEntry, olmMessageCallback);
						} else if (fileName.equals(CONTACTS_XML)) {
							readContacts(zipEntry, olmMessageCallback);
						} else if (fileName.equals(CALENDAR_XML)) {
							readAppointments(zipEntry, olmMessageCallback);
						} else if (fileName.equals(TASKS_XML)) {
							readTasks(zipEntry, olmMessageCallback);
						} else if (fileName.equals(NOTES_XML)) {
							readNotes(zipEntry, olmMessageCallback);
						} else if (fileName.equals(GROUPS_XML)) {
							readGroups(zipEntry, olmMessageCallback);
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
			final GenericJAXBMarshaller<Tasks> marshaller = new GenericJAXBMarshaller<Tasks>(Tasks.class, new String[] { XML_SCHEMA, TASKS_SCHEMA });
			Tasks tasks = null;
			try {
				tasks = marshaller.unmarshall(zipInputStream);
			} catch (final Exception e) {
				logger.error("Error in readTasks in file '" + zipEntry.getName() + "'", e);
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
