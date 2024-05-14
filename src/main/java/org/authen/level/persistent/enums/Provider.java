package org.authen.level.persistent.enums;

import lombok.Getter;

@Getter
public enum Provider {

	LOCAL("L"), GOOGLE("G"), FACEBOOK("F"), GITHUB("GB"), TWITTER("TW"), LINKEDIN("LK");

	private final String name;

	Provider(String name) {
		this.name = name;
	}

	public Provider getProvider(String name) {
		for (Provider provider : Provider.values()) {
			if (provider.getName().equals(name)) {
				return provider;
			}
		}
		return null;
	}


}
