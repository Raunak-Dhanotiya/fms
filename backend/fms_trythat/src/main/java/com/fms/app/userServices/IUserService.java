package com.fms.app.userServices;

import com.fms.app.userModels.User;

public interface IUserService {

	User getUser(String userName);

	boolean userExists(String userName);

	User getUser(Integer userId);
}
