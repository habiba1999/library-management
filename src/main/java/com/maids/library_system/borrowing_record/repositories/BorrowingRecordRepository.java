package com.maids.library_system.borrowing_record.repositories;

import org.springframework.data.repository.CrudRepository;

import com.maids.library_system.borrowing_record.entities.BorrowingRecord;

public interface BorrowingRecordRepository extends CrudRepository<BorrowingRecord, Long>{
	
	BorrowingRecord findByBookIdAndPatronId(long bookId,long patronId);
}
