package org.gateway.common.constant;

@Deprecated(since = "1.0.0", forRemoval = true)
public class SecurityEndpoint {
	public static final String[] USER_WHITELIST = {"/api/user/**"};
	public static final String[] PUBLIC_WHITELIST = {"/api/public/**"};
	public static final String[] PRIVATE_WHITELIST = {"/api/private/**"};
	public static final String[] AUTH_WHITELIST = {"/api/auth/**"};
	public static final String USER_ROLE_NAME = "User";

}
