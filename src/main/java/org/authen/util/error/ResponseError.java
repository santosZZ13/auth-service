package org.authen.util.error;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseError {
	private String code;
	private String message;
	private String shortDesc;
}
