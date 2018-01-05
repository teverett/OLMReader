package com.khubla.olmreader.olm;

import com.khubla.olmreader.olm.generated.Categories;
import com.khubla.olmreader.olm.generated.Emails;
import com.khubla.olmreader.olm.generated.Contacts;

public interface OLMMessageCallback {
   void categories(Categories categories);

   void message(Emails.Email email);

   void contact(Contacts.Contact contact);
}
