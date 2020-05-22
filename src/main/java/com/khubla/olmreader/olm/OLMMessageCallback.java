package com.khubla.olmreader.olm;

import java.util.*;

import com.khubla.olmreader.olm.generated.*;
import com.khubla.olmreader.olm.generated.Appointments.*;
import com.khubla.olmreader.olm.generated.Contacts.*;
import com.khubla.olmreader.olm.generated.Emails.*;
import com.khubla.olmreader.olm.generated.Groups.*;
import com.khubla.olmreader.olm.generated.Notes.*;
import com.khubla.olmreader.olm.generated.Tasks.*;

public interface OLMMessageCallback {
	void appointment(Appointment appointment);

	void categories(Categories categories);

	void contact(Contact contact);

	void email(Email email, HashMap<String, byte[]> attachments);

	void group(Group group);

	void note(Note note);

	void task(Task task);
}
