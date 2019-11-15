package org.bridgelabz.fundoonotes.repository;

import java.util.List;
import org.bridgelabz.fundoonotes.model.User;

public interface UserRepository {

	List<User> getListOfUsers();

	void updateVerify(int id);

	void updatePassword(int id, String password);

	List<User> getUserDetails(String email);

	void registerInDB(User user);

	List<User> getUsersData();

}
