package com.khubla.olmreader.olm;

import com.khubla.olmreader.olm.generated.Email;

public interface OLMMessageCallback {
   void message(Email email);
}
