package com.library.librarymanagementsystem.service;

import com.library.librarymanagementsystem.entity.User;
import com.library.librarymanagementsystem.user.WebUser;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

	public User findByUserName(String userName);

	void save(WebUser webUser);

}
