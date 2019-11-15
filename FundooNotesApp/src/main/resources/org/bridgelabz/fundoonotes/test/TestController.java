package org.bridgelabz.fundoonotes.test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.bridgelabz.fundoonotes.model.Login;
import org.bridgelabz.fundoonotes.model.User;
import org.bridgelabz.fundoonotes.repository.UserRepositoryImpl;
import org.bridgelabz.fundoonotes.response.Response;
import org.bridgelabz.fundoonotes.service.UserServiceImpl;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.extern.slf4j.Slf4j;


@Slf4j
@RunWith(MockitoJUnitRunner.class)
public class TestController {
	@InjectMocks
	private UserServiceImpl userService;
	
	@Mock
	private UserRepositoryImpl userRepository;
	
	@Ignore
	@Test
	public void loginTest() {
		
	String email="chandrakishore314@gmail.com";
	
		List<User> listofusers=new ArrayList<User>();
		User user =new User();
		user.setEmail("chandrakishore314@gmail.com");
		listofusers.add(user);
		
		when(userRepository.getUserDetails(email)).thenReturn(listofusers);
		
		List<User> excepteddata=userService.getUserData(email);
		log.info(excepteddata.toString());
		assertEquals(excepteddata,listofusers);
		  
        assertEquals(1, excepteddata.size());
	}
	
	 @Ignore
    @Test
    public void testLoginUserSuccess() throws URISyntaxException
    {
        RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:+8085/user/login";
        URI uri = new URI(baseUrl);
        Login user = new Login( "chandrakishore314@email.com","1234");
         
//        HttpHeaders headers = new HttpHeaders();
//        headers.set("X-COM-PERSIST", "true");     
 
        HttpEntity<Login> request = new HttpEntity<>(user);
        try
        {
        ResponseEntity<Response> response = restTemplate.exchange(uri, HttpMethod.POST,request, Response.class);
        Assertions.assertEquals(201,response.getStatusCode());
        }
        catch(HttpClientErrorException ex)
        {
            //Verify bad request and missing header
            Assert.assertEquals(400, ex.getRawStatusCode());
            Assert.assertEquals(true, ex.getResponseBodyAsString().contains("Missing request header"));
        }
    }
}
