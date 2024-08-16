package com.maids.library_system.borrowing_record.models.response;

import com.maids.library_system.book.models.response.BookResModel;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BorrowingRecordResModel implements Serializable {
	private String comment;
	private int rating;
	private LocalDateTime reviewDate;
	private BookResModel story;
}
