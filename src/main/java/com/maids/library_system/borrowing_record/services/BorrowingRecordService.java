package com.maids.library_system.borrowing_record.services;

public interface BorrowingRecordService {
	
	Long borrowABook (long bookId,long patronId);
	Long returnABook (long bookId,long patronId);
}
