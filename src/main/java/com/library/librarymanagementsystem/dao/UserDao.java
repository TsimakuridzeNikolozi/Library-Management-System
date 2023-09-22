package com.library.librarymanagementsystem.dao;

import com.library.librarymanagementsystem.entity.User;

public interface UserDao {

    User findByUserName(String userName);

    void save(User theUser);
}
