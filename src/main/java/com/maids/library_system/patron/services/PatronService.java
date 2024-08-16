package com.maids.library_system.patron.services;

import java.util.List;

import com.maids.library_system.patron.models.request.PatronReqModel;
import com.maids.library_system.patron.models.response.PatronResModel;

public interface PatronService {
	long createPatron(PatronReqModel patronReqModel);
	long updatePatron(long patronId,PatronReqModel patronReqModel);
	PatronResModel getPatronById(long patronId);
	List<PatronResModel> getAllPatrons();
	void deletePatronById(long patronId);
}
