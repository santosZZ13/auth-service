package org.authen.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import javax.servlet.http.HttpServletRequest;

@Data
@Builder
public class Request {
	private HttpServletRequest request;
}
