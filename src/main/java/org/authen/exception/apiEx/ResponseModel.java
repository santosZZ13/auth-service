package org.authen.exception.apiEx;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseModel {
	private Boolean success;
	private Object data;

	@lombok.Data
	@AllArgsConstructor
	@NoArgsConstructor
	@Builder
	public static class Data {
		private String name;
		private String message;
		private Integer code;
		private Integer status;
	}
}
