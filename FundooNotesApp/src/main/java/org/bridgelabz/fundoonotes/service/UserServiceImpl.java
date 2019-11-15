package org.bridgelabz.fundoonotes.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.bridgelabz.fundoonotes.Utility.DateTime;
import org.bridgelabz.fundoonotes.Utility.TokenImpl;
import org.bridgelabz.fundoonotes.configuration.UserConfiguration;
import org.bridgelabz.fundoonotes.controller.UserController;
import org.bridgelabz.fundoonotes.dto.ForgotPassword;
import org.bridgelabz.fundoonotes.dto.ResetPassword;
import org.bridgelabz.fundoonotes.dto.UserDTO;
import org.bridgelabz.fundoonotes.exception.UserException;
import org.bridgelabz.fundoonotes.model.Login;
import org.bridgelabz.fundoonotes.model.User;
import org.bridgelabz.fundoonotes.repository.UserRepository;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Service
@Slf4j
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserConfiguration userConfiguration;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	RabbitMQSender rabbitMQSender;

	private static final Logger LOGGER = Logger.getLogger(UserController.class.getName());

	/*
	 * @Autowired private SimpleMailMessage msg;
	 */

//To save the user details in the DATABASE
	// Change method name registration
	public Boolean userRegistration(UserDTO userdto) {

		if (!isUserPresent(userdto.getEmail())) {

	
				throw new UserException("User is already present in our existing Data");
		
		} else {
			log.info(" entered into registration service ");
			ModelMapper modelMapper = userConfiguration.getModelMapper();
			User user = modelMapper.map(userdto, User.class);
			user.setPassword(userConfiguration.bCryptPasswordEncoder().encode(user.getPassword()));
			user.setDate(DateTime.today());
			userRepository.registerInDB(user);

			int currentUserId = getId(userRepository.getListOfUsers());
			log.info("user id  " + currentUserId);
			String token = TokenImpl.jwtToken(currentUserId);
			String url = "http://localhost:8085/user/verify?token=" + token;
			SimpleMailMessage msg = new SimpleMailMessage();
			msg.setTo(user.getEmail());
			msg.setSubject("Testing from Spring Boot");
			msg.setText("click this url to verify Your email  " + url);
			javaMailSender.send(msg);
			
			rabbitMQSender.send(user);
			log.info("msg from rabbit mq ");
			return true;
		}

	}

	private int getId(List<User> listOfUsers) {
		int id = 0;
		for (User user : listOfUsers) {
			id = user.getUserId();
		}
		return id;
	}

	private boolean isUserPresent(String email) {
		List<User> listofusers = userRepository.getListOfUsers();
		List<User> users = listofusers.stream().filter(User -> User.getEmail().equals(email))
				.collect(Collectors.toList());
		if (users.isEmpty()) {
			return true;
		} else {
			return false;
		}
	}

	// To check the User login credentials
	public String checkLoginDetails(Login loginDTO) {
		log.info("entered in to service layer");
		List<User> listofusers = userRepository.getListOfUsers();
		BCryptPasswordEncoder encoder = userConfiguration.bCryptPasswordEncoder();
		String token = null;
		if (listofusers.size() > 0) {
			for (User user : listofusers) {
				if (loginDTO.getEmail().equals(user.getEmail())
						&& encoder.matches(loginDTO.getPassword(), user.getPassword())) 
				{
					token = TokenImpl.jwtToken(user.getUserId());
				}
			}
		}
		return token;

		/*
		 * return listofusers.stream().filter(user->{ return user!=null; }).map(user->{
		 * if(loginDTO.getUsername().equals(user.getEmail()) &&
		 * encoder.matches(loginDTO.getPassword(), user.getPassword())) { String token =
		 * TokenImpl.jwtToken(user.getUserId()); return token; } });
		 */

	}

//To upadate the User is verified
	public boolean verifyuser(String token) {
		int id = TokenImpl.parseJWT(token);
		userRepository.updateVerify(id);
		System.out.println(id + " id  values as");
		return false;

	}

	// To update the User is verified
	@Override
	public boolean verifyemail(ForgotPassword forgotPassword) {
		String email=forgotPassword.getEmail();
		int currentUserId = getId(userRepository.getUserDetails(email));
		String token = "http://localhost:3000/reset/"+TokenImpl.jwtToken(currentUserId);
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(email);
		msg.setSubject("Testing from Spring Boot");
		msg.setText("click this url to verify Your email  " + token);
		javaMailSender.send(msg);
		return false;
	}

	// To upadate the User is verified
	@Override
	public void updateUserPassword(String token,ResetPassword resetPassword) {
		log.info("entered in to service layer");
		int id = TokenImpl.parseJWT(token);
		String password = userConfiguration.bCryptPasswordEncoder().encode(resetPassword.getPassword());
		userRepository.updatePassword(id, password);

	}

	@Override
	public List<User> getUserData(String email) {
		LOGGER.info("entered into service to fetch the data based on email");
		return userRepository.getUserDetails(email);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.getUsersData();
	}

}
