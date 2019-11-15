//Package name 
package org.bridgelabz.fundoonotes.controller;

import java.util.List;

import org.bridgelabz.fundoonotes.dto.ForgotPassword;
import org.bridgelabz.fundoonotes.dto.ResetPassword;
import org.bridgelabz.fundoonotes.dto.UserDTO;
import org.bridgelabz.fundoonotes.model.Login;
import org.bridgelabz.fundoonotes.model.User;
import org.bridgelabz.fundoonotes.response.Response;
import org.bridgelabz.fundoonotes.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

//Add Swagger 2
//@RequestMapping("/user") add base url
@RestController
@RequestMapping("/user")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(allowedHeaders = "*", origins = "*", exposedHeaders = { "token" })
@Slf4j
public class UserController {
	@Autowired
	private UserService userService;
	
	 @Autowired
	    private RedisTemplate<String, String> redisTemplate;
	 
//	 public static final String KEY = "ITEM";

// To send the user details to the service class to save the data in the DATABASE
	@PostMapping("/registration")
	public ResponseEntity<Response> registerUser(@RequestBody UserDTO user) {
		log.info("entered into registration controller");
		boolean isUserPrensent = userService.userRegistration(user);
		if (isUserPrensent) {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("registration sucessful", HttpStatus.CREATED.value(), null));
		} else {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(new Response("User already present  ", HttpStatus.CREATED.value(), null));
		}
	}

// To send the loginDTO details to the service class to check the login details in the DATABASE
	@PostMapping("/login")
	public ResponseEntity<Response> loginUser(@RequestBody Login loginDTO) {
		String token = userService.checkLoginDetails(loginDTO);
		
		log.info("entered into login  controller");
		if (token==null) {
			return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION)
					.body(new Response("username or password incorrect ", HttpStatus.NON_AUTHORITATIVE_INFORMATION.value(), null));
		} else {
//			redisTemplate.opsForHash().put(KEY, "token", token);
			redisTemplate.opsForValue().set("token", token);
//			log.info("token  "+redisTemplate.opsForHash().get(KEY, "token"));
			log.info("token  "+redisTemplate.opsForValue().get("token"));
			return ResponseEntity.status(HttpStatus.ACCEPTED)
					.body(new Response("login sucessful", HttpStatus.ACCEPTED.value(), token));
		}
	}

//To verify the email 	
	@GetMapping("/verify")
	public ResponseEntity<Response> verifyUser(@RequestParam String token) {
		System.out.println("enterd in to conotroller");
		userService.verifyuser(token);
		return ResponseEntity.status(HttpStatus.ACCEPTED)
				.body(new Response(" verified the email in the DataBase", HttpStatus.ACCEPTED.value(), null));
	}

//To send the token to email for the forgot password.
	@PostMapping("/forgotpassword")
	public ResponseEntity<Response> forgotpassword(@RequestBody ForgotPassword forgotPassword) {
		userService.verifyemail(forgotPassword);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("mail sent to the email", HttpStatus.CREATED.value(), null));

	}
	
	// To update the forgot password.
	@PostMapping("/update/{token}")
	public ResponseEntity<Response> updatePassword(@PathVariable("token") String token, @RequestBody ResetPassword resetPassword) {
		
		userService.updateUserPassword(token, resetPassword);
		return ResponseEntity.status(HttpStatus.CREATED)
				.body(new Response("updated in the database", HttpStatus.CREATED.value(), null));
	}

	// To get the Data of User Based on Email
	@GetMapping("/getdata")
	public List<User> getUserDetails(@RequestParam String email) {
		return userService.getUserData(email);
	}

	// To get the Data of All Users
	@PostMapping("/getallusers")
	public List<User> getALLNote() {
		log.info("entered into  get all users controller");
		return userService.getAllUsers();
	}
}
