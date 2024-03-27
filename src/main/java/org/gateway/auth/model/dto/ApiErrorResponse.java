package org.gateway.auth.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {
	int errorCode;
	String description;
}
