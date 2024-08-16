package com.maids.library_system.patron.models.request;

import java.io.Serializable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PatronReqModel implements Serializable {
	@NotNull
	@Email
	@Size(max = 50)
	@Pattern(regexp = "^$|^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$")
	private String email;
	@NotNull
	private String name;
	@NotNull
	@Size(max=11)
	@Pattern(regexp = "^$|^01[0-9]{9}$")
	private String mobile;
}
