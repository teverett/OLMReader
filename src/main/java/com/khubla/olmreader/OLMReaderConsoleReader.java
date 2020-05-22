package com.khubla.olmreader;

import java.io.*;
import java.util.*;

import com.khubla.olmreader.olm.*;
import com.khubla.olmreader.olm.generated.*;
import com.khubla.olmreader.olm.generated.Appointments.*;
import com.khubla.olmreader.olm.generated.Categories.*;
import com.khubla.olmreader.olm.generated.Groups.*;
import com.khubla.olmreader.olm.generated.Notes.*;
import com.khubla.olmreader.olm.generated.Tasks.*;

public class OLMReaderConsoleReader implements OLMMessageCallback, OLMRawMessageCallback {
	private OLMFile olmFile;

	@Override
	public void appointment(Appointment appointment) {
		System.out.println("Appointment: " + appointment.getOPFCalendarEventCopyUUID().getValue());
	}

	@Override
	public void categories(Categories categories) {
		for (final Category category : categories.getCategory()) {
			System.out.println("Category: " + category.getOPFCategoryCopyName());
		}
	}

	@Override
	public void contact(Contacts.Contact contact) {
		System.out.println("Contact: " + contact.getOPFContactCopyDisplayName().getValue());
	}

	@Override
	public void email(Emails.Email email, HashMap<String, byte[]> attachments) {
		try {
			System.out.println("EMail: " + email.getOPFMessageCopyMessageID());
		} catch (final Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void group(Group group) {
		System.out.println("Group: " + group.getOPFGroupCopyDisplayName().getValue());
	}

	@Override
	public void note(Note note) {
		System.out.println("Note: " + note.getOPFNoteCopyTitle().getValue());
	}

	@Override
	public void rawMessage(String olmMessage) {
		// System.out.println(olmMessage);
	}

	public void read(String filename) throws IOException {
		olmFile = new OLMFile(filename);
		olmFile.readOLMFile(this, this);
	}

	@Override
	public void task(Task task) {
		System.out.println("Task: " + task.getOPFTaskCopyName().getValue());
	}
}
