package org.authen.web.service;

import lombok.AllArgsConstructor;
import org.authen.wapper.model.GenericResponseSuccessWrapper;
import org.authen.level.service.model.UserModel;
import org.authen.util.error.ErrorCode;
import org.authen.web.dto.pw.UpdatePasswordDTO;
import org.authen.web.exception.InvalidOldPasswordException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.authen.level.service.user.UserLogicService;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

@Service
@AllArgsConstructor
public class PasswordServiceImpl implements PasswordService {

	private final PasswordEncoder passwordEncoder;
	private final UserLogicService userLogicService;


	@Override
	public ResponseEntity<GenericResponseSuccessWrapper> resetPassword(String email, HttpServletRequest request) {
		return null;
	}


	@Override
	public ResponseEntity<GenericResponseSuccessWrapper> changeUserPassword(UpdatePasswordDTO updatePasswordDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ErrorCode errorCode = new ErrorCode();
		final String oldPassword = updatePasswordDTO.getConfig().getOldPassword();
		final String newPassword = updatePasswordDTO.getConfig().getNewPassword();
		final String confirmPassword = updatePasswordDTO.getConfig().getConfirmPassword();

		if (Objects.isNull(authentication)) {
			// throw an exception
			throw new RuntimeException("User not authenticated");
		} else {
			if (!Objects.equals(newPassword, confirmPassword)) {
				throw new InvalidOldPasswordException("Old password and new password are not the same");
			}

			UserModel userModelByUsername = userLogicService.getUserModelByUsername(authentication.getName());
			if (!checkIfValidOldPassword(userModelByUsername, oldPassword)) {
				throw new InvalidOldPasswordException("Invalid old password");
			}
			changeUserPassword(userModelByUsername, newPassword);
			return ResponseEntity.ok(GenericResponseSuccessWrapper.builder()
					.success(Boolean.TRUE)
					.data("Password updated successfully")
					.build());
		}
	}

	private Boolean checkIfValidOldPassword(UserModel userModel, String oldPassword) {
		return  passwordEncoder.matches(oldPassword, userModel.getPassword());
	}

	private void changeUserPassword(UserModel userModel, String newPassword) {
		userModel.setPassword(passwordEncoder.encode(newPassword));
		userLogicService.saveUser(userModel);
	}
}
