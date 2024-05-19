package org.authen.service.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.authen.exception.OAuth2AuthenticationProcessingException;
import org.authen.level.persistent.enums.Provider;
import org.authen.level.service.model.UserModel;
import org.authen.oauth2.user.OAuth2UserInfo;
import org.authen.oauth2.user.OAuth2UserWrapper;
import org.authen.util.OAuth2UserInfoFactory;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Objects;


@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceImpl extends DefaultOAuth2UserService {

	private final UserService userService;

	/**
	 * if add openId
	 *
	 * @param oAuth2UserRequest the user request
	 * @return
	 * @throws OAuth2AuthenticationException
	 */
	@Override
	public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
		try {
			return this.processOAuth2User(oAuth2UserRequest, oAuth2User);
		} catch (AuthenticationException ex) {
			throw ex;
		} catch (Exception ex) {
			// Throwing an instance of AuthenticationException will trigger the OAuth2AuthenticationFailureHandler
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {

		String registrationId = oAuth2UserRequest.getClientRegistration().getRegistrationId();
		Map<String, Object> attributes = oAuth2User.getAttributes();

		OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(registrationId, attributes);

//		if(StringUtils.isEmpty(oAuth2UserInfo.getEmail())) {
//			throw new OAuth2AuthenticationProcessingException("Email not found from OAuth2 provider");
//		}

		UserModel userModelByEmail = userService.getUSerModelByEmail(oAuth2UserInfo.getEmail());

		if (Objects.nonNull(userModelByEmail)) {
			// In case the user is already registered but trying to sign up again using the same email
			if (!Objects.equals(userModelByEmail.getProvider().getName(), registrationId)) {
				throw new OAuth2AuthenticationProcessingException("Looks like you're signed up with " +
						userModelByEmail.getProvider() + " account. Please use your " + userModelByEmail.getProvider() +
						" account to login.");
			}
			userModelByEmail = updateExistingUser(userModelByEmail, oAuth2UserInfo);
		} else {
			userModelByEmail = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
		}
		assert userModelByEmail != null;
		return OAuth2UserWrapper.create(userModelByEmail);
	}

	private UserModel registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {
		UserModel userModel = UserModel.builder()
				.email(oAuth2UserInfo.getEmail())
				.provider(Provider.GOOGLE)
				.providerId(oAuth2UserInfo.getId())
				.firstName(oAuth2UserInfo.getGivenName())
				.lastName(oAuth2UserInfo.getFamilyName())
				.imageUrl(oAuth2UserInfo.getImageUrl())
				.build();
		return userService.saveUserModel(userModel);
	}

	private UserModel updateExistingUser(UserModel userModelByEmail, OAuth2UserInfo oAuth2UserInfo) {
		final String imageUrl = oAuth2UserInfo.getImageUrl();
		final String name = oAuth2UserInfo.getName();
		final String familyName = oAuth2UserInfo.getFamilyName();
		final String givenName = oAuth2UserInfo.getGivenName();

		if (!Objects.equals(userModelByEmail.getImageUrl(), imageUrl)
				&& !Objects.equals(userModelByEmail.getFirstName(), givenName)
				&& !Objects.equals(userModelByEmail.getLastName(), familyName)
		) {
			userModelByEmail.setImageUrl(imageUrl);
			userModelByEmail.setFirstName(givenName);
			userModelByEmail.setLastName(familyName);
			return userService.updateUserModel(userModelByEmail);
		} else {
			return userModelByEmail;
		}
	}
}
