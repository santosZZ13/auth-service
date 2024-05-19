package org.authen.level.persistent.enums;

import lombok.Getter;
import org.jetbrains.annotations.Nullable;

@Getter
public enum Provider {

	LOCAL("local"), GOOGLE("google"), FACEBOOK("facebook"), GITHUB("github"), TWITTER("tw"), LINKEDIN("lk");

	private final String name;

	Provider(String name) {
		this.name = name;
	}

	public @Nullable Provider getProvider(String name) {
		for (Provider provider : Provider.values()) {
			if (provider.getName().equals(name)) {
				return provider;
			}
		}
		return null;
	}


}
