package org.authen.codes;

public class RegisterCode {
	public static final String INVALID_HTTP_METHOD_MSG = "HTTP method [GET] is not supported for action [createUser].";
	public static final String INVALID_HTTP_METHOD_CODE = "C10100E0";

	public static final String AUTHORIZATION_FAILED_MSG = "Authorization failed.";
	public static final String AUTHORIZATION_FAILED_CODE = "C1010001";

	public static final String INVALID_AUTHENTICATION_MSG = "Invalid Authentication";
	public static final String INVALID_AUTHENTICATION_CODE = "C10400E9";

	public static final String INVALID_JSON_REQUEST_MSG = "Invalid JSON Request";
	public static final String INVALID_JSON_REQUEST_CODE = "C1010002";

	/**
	 * Mandatory parameter {username} is not specified
	 */
	public static final String MANDATORY_PARAMETER_USERNAME_MSG = "Mandatory parameter {username} is not specified";
	public static final String MANDATORY_PARAMETER_USERNAME_CODE = "C1010003";

	/**
	 * Invalid value specified for parameter {username}.
	 */
	public static final String INVALID_USERNAME_MSG = "Invalid value specified for parameter {username}.";
	public static final String INVALID_USERNAME_CODE = "C1010004";

	/**
	 * Mandatory parameter {password} is not specified.
	 */
	public static final String MANDATORY_PARAMETER_PASSWORD_MSG = "Mandatory parameter {password} is not specified.";
	public static final String MANDATORY_PARAMETER_PASSWORD_CODE = "C1010003";

	/**
	 * Invalid value specified for parameter {password}.
	 */
	public static final String INVALID_PASSWORD_MSG = "Invalid value specified for parameter {password}.";
	public static final String INVALID_PASSWORD_CODE = "C1010004";

	/**
	 * Mandatory parameter {email} is not specified.
	 */
	public static final String MANDATORY_PARAMETER_FIRSTNAME_MSG = "Mandatory parameter {firstname} is not specified";
	public static final String MANDATORY_PARAMETER_FIRSTNAME_CODE = "C1010003";

	/**
	 * Invalid value specified for parameter {firstname}.
	 */
	public static final String INVALID_FIRSTNAME_MSG = "Invalid value specified for parameter {firstname}.";
	public static final String INVALID_FIRSTNAME_CODE = "C1010004";

	/**
	 * Mandatory parameter {lastname} is not specified.
	 */
	public static final String MANDATORY_PARAMETER_LASTNAME_MSG = "Mandatory parameter {lastname} is not specified.";
	public static final String MANDATORY_PARAMETER_LASTNAME_CODE = "C1010003";

	/**
	 * Invalid value specified for parameter {lastname}.
	 */
	public static final String INVALID_LASTNAME_MSG = "Invalid value specified for parameter {lastname}.";
	public static final String INVALID_LASTNAME_CODE = "C1010004";

	/**
	 * Mandatory parameter {email} is not specified.
	 */
	public static final String MANDATORY_PARAMETER_EMAIL_MSG = "Mandatory parameter {email} is not specified.";
	public static final String MANDATORY_PARAMETER_EMAIL_CODE = "C1010003";

	/**
	 * Invalid value specified for parameter {email}.
	 */
	public static final String INVALID_EMAIL_MSG = "Invalid value specified for parameter {email}.";
	public static final String INVALID_EMAIL_CODE = "C1010004";

	/**
	 * Mandatory parameter {type} is not specified.
	 */
	public static final String INVALID_TYPE_MSG = "Invalid value specified for parameter {type}.";
	public static final String INVALID_TYPE_CODE = "C1010004";

	/**
	 * Mandatory parameter {role} is not specified.
	 */
	public static final String MANDATORY_PARAMETER_ROLE_MSG = "Mandatory parameter {role} is not specified.";
	public static final String MANDATORY_PARAMETER_ROLE_CODE = "C1010003";

	/**
	 * Invalid value specified for parameter {role}.
	 */
	public static final String INVALID_ROLE_MSG = "Invalid value specified for parameter {role} specified.";
	public static final String INVALID_ROLE_CODE = "C1010004";

	/**
	 * Mandatory parameter {locale} is not specified.
	 */
	public static final String INVALID_LOCALE_MSG = "Invalid value specified for parameter {locale}.";
	public static final String INVALID_LOCALE_CODE = "C1010004";

	/**
	 * User with {username} (admin role) already exists on the server.
	 */
	public static final String USER_EXISTS_MSG = "User with {username} (admin role) already exists on the server.";
	public static final String USER_EXISTS_CODE = "C1040005";



}
