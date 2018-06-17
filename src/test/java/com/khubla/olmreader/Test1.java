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
import com.khubla.olmreader.olm.generated.Categories;
import com.khubla.olmreader.olm.generated.Contacts.Contact;
import com.khubla.olmreader.olm.generated.Emails.Email;

public class Test1 implements OLMMessageCallback, OLMRawMessageCallback {
   @Test
   public void test1() {
      try {
         OLMFile olmFile = new OLMFile("src/test/resources/bill_rapp.olm");
         olmFile.readOLMFile(this, this);
      } catch (Exception e) {
         e.printStackTrace();
         Assert.fail();
      }
   }

   @Override
   public void rawMessage(String olmMessage) {
      // TODO Auto-generated method stub
   }

   @Override
   public void categories(Categories categories) {
      // TODO Auto-generated method stub
   }

   @Override
   public void contact(Contact contact) {
      // TODO Auto-generated method stub
   }

   @Override
   public void message(Email email) {
      // TODO Auto-generated method stub
   }
}
