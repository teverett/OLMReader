package com.khubla.olmreader.olm;

import com.khubla.olmreader.olm.generated.Appointments.Appointment;
import com.khubla.olmreader.olm.generated.Categories;
import com.khubla.olmreader.olm.generated.Contacts.Contact;
import com.khubla.olmreader.olm.generated.Emails.Email;
import com.khubla.olmreader.olm.generated.Groups.Group;
import com.khubla.olmreader.olm.generated.Notes.Note;
import com.khubla.olmreader.olm.generated.Tasks.Task;

public interface OLMMessageCallback {
   void appointment(Appointment appointment);

   void categories(Categories categories);

   void contact(Contact contact);

   void email(Email email);

   void group(Group group);

   void note(Note note);

   void task(Task task);
}
