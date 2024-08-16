package com.maids.library_system.patron.models.response;

import java.io.Serializable;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatronResModel implements Serializable {

	private int id;
	private String name;
	private String email;
	private String mobile;
}
