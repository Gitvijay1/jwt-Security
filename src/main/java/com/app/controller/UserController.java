package com.app.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.model.User1;
import com.app.model.UserRequest;
import com.app.model.UserResponse;
import com.app.service.UserService;
import com.app.util.JwtUtil;

@Controller
public class UserController {
	@Autowired
    private UserService service;
	
	@Autowired
	private JwtUtil util;
	
   @Autowired
   private AuthenticationManager authenticationManager;
	
	
	//save data into table
   @PostMapping("/save")
   public ResponseEntity<String> saveUser1(@RequestBody User1 user){
	     Integer id = service.saveUser(user);
	     String msg=id+":User save";
	     //ResponseEntity<String> resp=new ResponseEntity<String>(msg, HttpStatus.OK);
	   //return new ResponseEntity<String>(msg,HttpStatus.OK);
	     return ResponseEntity.ok(msg);
   }
   
   
   //fatch all data from table
   @GetMapping("/all")
   public ResponseEntity<List<User1>> fatchU(){
	   List<User1> list = service.fatchUser();
	   return ResponseEntity.ok(list);
   }
   
   //validate user and generate token(login)
   
   @PostMapping("/login")
   public ResponseEntity<UserResponse> loginUser(
		   @RequestBody UserRequest request){
	   //validate uname/pwd with database
	   authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
			   request.getUserName(),
			   request.getPassword()));
	      String token = util.genereteToken(request.getUserName());
	   
	   return ResponseEntity.ok(new UserResponse(token,"success"));
   }
   
   
   //get user
   @PostMapping("/welcome")
   public ResponseEntity<String> accessData(Principal p){
	   return ResponseEntity.ok("hello user! "+p.getName());
   }
}
