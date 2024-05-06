package service.user;

import service.model.UserModel;

public interface UserLogicService {
	UserModel getUserModelByUsername(String username);
	void saveUser(UserModel userModel);
}
