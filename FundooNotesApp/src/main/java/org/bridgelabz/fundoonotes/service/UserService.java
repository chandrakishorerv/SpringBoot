package org.bridgelabz.fundoonotes.service;

import java.util.List;

import org.bridgelabz.fundoonotes.dto.ForgotPassword;
import org.bridgelabz.fundoonotes.dto.ResetPassword;
import org.bridgelabz.fundoonotes.dto.UserDTO;
import org.bridgelabz.fundoonotes.model.Login;
import org.bridgelabz.fundoonotes.model.User;

public interface UserService {
	public Boolean userRegistration(UserDTO user);

	String checkLoginDetails(Login loginDTO);

	public boolean verifyuser(String token);

	public boolean verifyemail(ForgotPassword forgotPassword);

	public void updateUserPassword(String token, ResetPassword resetPassword);

	public List<User> getUserData(String email);

	public List<User> getAllUsers();
}
