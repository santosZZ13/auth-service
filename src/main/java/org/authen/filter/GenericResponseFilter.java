package org.authen.filter;

import lombok.extern.log4j.Log4j2;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

@Log4j2
public class GenericResponseFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		Filter.super.init(filterConfig);
	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse res = (HttpServletResponse) servletResponse;


		// Create a wrapper response
		GenericResponseWrapper wrapperResponse = new GenericResponseWrapper(res);


		// Continue the filter chain
		filterChain.doFilter(req, wrapperResponse);

		// After the execution of the request, get the response data
		String responseData = wrapperResponse.getData();

		log.info("responseData -> " + responseData);

//		ContentCachingResponseWrapper ccrw= new ContentCachingResponseWrapper(res);
//		String content=new String(ccrw.getContentAsByteArray(), "utf-8");

//		// After the execution of the request, wrap the response in a GenericResponse
//		GenericResponseWapper genericResponse = GenericResponseWapper.builder()
//				.success(Boolean.TRUE)
//				.data(wrapperResponse.getData())
//				.build();
//
//
//		// Write the GenericResponse to the original response
//		res.getOutputStream().write(genericResponse.toString().getBytes());

	}

	@Override
	public void destroy() {
		Filter.super.destroy();
	}

	public static class GenericResponseWrapper extends HttpServletResponseWrapper {
		private final CharArrayWriter output;

		public GenericResponseWrapper(HttpServletResponse response) {
			super(response);
			output = new CharArrayWriter();
		}

		@Override
		public PrintWriter getWriter() {
			return new PrintWriter(output);
		}

		public String getData() {
			return output.toString();
		}
	}
}
