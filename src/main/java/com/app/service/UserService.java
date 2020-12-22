package com.app.service;

import java.util.List;
import java.util.Optional;

import com.app.model.User1;

public interface UserService {
	public Integer saveUser(User1 user);
    public List<User1> fatchUser();
    Optional<User1> findByUserName(String username);

}
