package com.khubla.olmreader.olm;

import com.khubla.olmreader.olm.generated.Categories;
import com.khubla.olmreader.olm.generated.Contacts;
import com.khubla.olmreader.olm.generated.Emails;
import com.khubla.olmreader.olm.generated.Tasks.Task;

public interface OLMMessageCallback {
   void categories(Categories categories);

   void contact(Contacts.Contact contact);

   void message(Emails.Email email);

   void task(Task task);
}
