package org.authen.oauth2.user;

import java.util.Map;

public class GoogleOAuth2UserInfo extends OAuth2UserInfo {

    public GoogleOAuth2UserInfo(Map<String, Object> attributes) {
        super(attributes);
    }

    @Override
    public String getId() {
        return (String) attributes.get("sub");
    }

    @Override
    public String getName() {
        return (String) attributes.get("name");
    }

    @Override
    public String getEmail() {
        return (String) attributes.get("email");
    }

    @Override
    public String getImageUrl() {
        return (String) attributes.get("picture");
    }

    @Override
    public String getGivenName() {
        return (String) attributes.get("given_name");
    }

    @Override
    public String getFamilyName() {
        return (String) attributes.get("family_name");
    }

    @Override
    public String getLocale() {
        return (String) attributes.get("locale");
    }

    @Override
    public String getSub() {
        return (String) attributes.get("sub");
    }
}
