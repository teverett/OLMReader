/*
 * Copyright (C) khubla.com - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Tom Everett <tom@khubla.com>, 2018
 */
package com.khubla.olmreader;

import org.junit.*;

import com.khubla.olmreader.olm.*;
import com.khubla.olmreader.olm.generated.*;
import com.khubla.olmreader.olm.generated.Appointments.*;
import com.khubla.olmreader.olm.generated.Categories.*;
import com.khubla.olmreader.olm.generated.Contacts.*;
import com.khubla.olmreader.olm.generated.Emails.*;
import com.khubla.olmreader.olm.generated.Groups.*;
import com.khubla.olmreader.olm.generated.Notes.*;
import com.khubla.olmreader.olm.generated.Tasks.*;

public class TestOutlook16_bill_rapp implements OLMMessageCallback, OLMRawMessageCallback {
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
	public void contact(Contact contact) {
		System.out.println("Contact: " + contact.getOPFContactCopyDisplayName().getValue());
	}

	@Override
	public void email(Email email) {
		System.out.println("Email: " + email.getOPFMessageCopyMessageID().getValue());
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

	@Override
	public void task(Task task) {
		System.out.println("Task: " + task.getOPFTaskCopyName().getValue());
	}

	@Test
	public void test_bill_rapp() {
		try {
			final OLMFile olmFile = new OLMFile("src/test/resources/Outlook16/bill_rapp.olm");
			olmFile.readOLMFile(this, this);
		} catch (final Exception e) {
			e.printStackTrace();
			Assert.fail();
		}
	}
}
