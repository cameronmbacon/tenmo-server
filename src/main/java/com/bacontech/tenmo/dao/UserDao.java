package com.bacontech.tenmo.dao;

import com.bacontech.tenmo.model.RegisterUserDto;
import com.bacontech.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> listUsers();

    User findUserById(int id);

    User findUserByUsername(String username);

    User saveUser(RegisterUserDto user);
}
