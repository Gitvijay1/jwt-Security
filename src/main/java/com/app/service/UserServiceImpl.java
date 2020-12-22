package com.app.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.model.User1;
import com.app.repo.UserRepo;
@Service
public class UserServiceImpl implements UserService,UserDetailsService{
	@Autowired
   private UserRepo repo;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Override
	public Integer saveUser(User1 user) {
		String password = user.getPassword();
		String encode = encoder.encode(password);
		user.setPassword(encode);
           Integer id = repo.save(user).getId();
		return id;
	}
	@Override
	public List<User1> fatchUser() {
          List<User1> list = repo.findAll();
		return list;
	}
	
	//get one user by username
	@Override
	public Optional<User1> findByUserName(String username) {
		Optional<User1> name = repo.findByUserName(username);
		return name;
	}
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		   Optional<User1> op = findByUserName(username);
		   
		     if(op.isEmpty()) {
		    	 throw new UsernameNotFoundException("suer name not found");
		     }
		     User1 user = op.get();
		return new User(
				username,
				user.getPassword(), 
				user.getRole().stream().map(role->new SimpleGrantedAuthority(role))
				.collect(Collectors.toList()));
	}

}
