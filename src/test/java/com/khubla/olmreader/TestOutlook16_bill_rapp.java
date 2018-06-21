/*
 * Copyright (C) khubla.com - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 * Written by Tom Everett <tom@khubla.com>, 2018
 */
package com.khubla.olmreader;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.khubla.olmreader.olm.OLMFile;
import com.khubla.olmreader.olm.OLMMessageCallback;
import com.khubla.olmreader.olm.OLMRawMessageCallback;
import com.khubla.olmreader.olm.generated.Appointments.Appointment;
import com.khubla.olmreader.olm.generated.Categories;
import com.khubla.olmreader.olm.generated.Categories.Category;
import com.khubla.olmreader.olm.generated.Contacts.Contact;
import com.khubla.olmreader.olm.generated.Emails.Email;
import com.khubla.olmreader.olm.generated.Notes.Note;
import com.khubla.olmreader.olm.generated.Tasks.Task;

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

   @Test(enabled = true)
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
