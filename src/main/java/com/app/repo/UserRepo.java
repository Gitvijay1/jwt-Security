package com.app.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.model.User1;
import java.lang.String;
import java.util.List;

public interface UserRepo extends JpaRepository<User1, Integer>{
	Optional<User1> findByUserName(String username);

}
