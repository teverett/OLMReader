package com.khubla.olmreader.olm;

import com.khubla.olmreader.olm.generated.Emails;

public interface OLMMessageCallback {
   void message(Emails.Email email);
}
