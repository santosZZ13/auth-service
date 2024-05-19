package org.authen.level.service.user;

import org.authen.level.service.model.UserModel;

public interface UserLogicService {
	UserModel getUserModelByUsername(String username);
	void saveUser(UserModel userModel);
	void updateEnabledByUsername(Boolean aBoolean, String username);
	UserModel getUserModelByEmail(String email);
	UserModel updateUserModel(UserModel userModelByEmail);
	UserModel saveUserModel(UserModel userModel);

}
