package com.khubla.olmreader;

import java.io.IOException;
import java.util.List;

import com.khubla.olmreader.olm.OLMFile;
import com.khubla.olmreader.olm.OLMMessageCallback;
import com.khubla.olmreader.olm.OLMRawMessageCallback;
import com.khubla.olmreader.olm.generated.Categories;
import com.khubla.olmreader.olm.generated.Contacts;
import com.khubla.olmreader.olm.generated.Emails;

public class OLMReaderConsoleReader implements OLMMessageCallback, OLMRawMessageCallback {
   private OLMFile olmFile;

   @Override
   public void categories(Categories categories) {
      System.out.println(categories.getCategory().size());
   }

   @Override
   public void contact(Contacts.Contact contact) {
      // todo contact handler
      System.out.println(contact.getOPFContactCopyDisplayName().getValue());
   }

   @Override
   public void message(Emails.Email email) {
      try {
         if (null != email.getOPFMessageCopyAttachmentList()) {
            final List<Emails.Email.OPFMessageCopyAttachmentList.MessageAttachment> attachments = email.getOPFMessageCopyAttachmentList().getMessageAttachment();
            if (attachments != null) {
               for (int i = 0; i < attachments.size(); i++) {
                  olmFile.readAttachment(attachments.get(i));
               }
            }
         }
      } catch (final Exception e) {
         e.printStackTrace();
      }
   }

   @Override
   public void rawMessage(String olmMessage) {
      // System.out.println(olmMessage);
   }

   public void read(String filename) throws IOException {
      olmFile = new OLMFile(filename);
      olmFile.readOLMFile(this, this);
   }
}
