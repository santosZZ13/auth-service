package org.authen.model;

import lombok.Builder;
import lombok.Data;

import javax.servlet.http.HttpServletRequest;

@Data
@Builder
public class RequestWrapper {
	private HttpServletRequest request;
}
