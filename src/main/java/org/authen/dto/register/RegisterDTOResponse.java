package org.authen.dto.register;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RegisterDTOResponse {
	private RegisterConfig registerConfig;
}


