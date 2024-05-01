package org.authen.enums;

public enum RoleConstants {

	ADMIN("ADMIN"),
	USER("USER");

	private final String role;

	RoleConstants(String role) {
		this.role = role;
	}

	public static RoleConstants fromString(String role) {
		for (RoleConstants roleConstant : RoleConstants.values()) {
			if (roleConstant.name().equalsIgnoreCase(role)) {
				return roleConstant;
			}
		}
		throw new IllegalArgumentException("Invalid role: " + role);
	}

	public String getRole() {
		return this.role;
	}

	public String getName() {
		return this.name();
	}

	// return list of roles
	public static String[] getRoles() {
		RoleConstants[] roles = RoleConstants.values();
		String[] roleNames = new String[roles.length];
		for (int i = 0; i < roles.length; i++) {
			roleNames[i] = roles[i].getRole();
		}
		return roleNames;
	}
}
