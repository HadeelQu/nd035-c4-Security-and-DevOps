package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.CreateUserRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserControllerTest {
    private UserController userController;
    private UserRepository userRepository = mock(UserRepository.class);
    private CartRepository cartRepository = mock(CartRepository.class);
    private BCryptPasswordEncoder encoder = mock(BCryptPasswordEncoder.class);
    @Before
    public void setup(){
        this.userController = new UserController();
        TestUtils.injectObjects(userController,"userRepository",userRepository);
        TestUtils.injectObjects(userController,"cartRepository",cartRepository);
        TestUtils.injectObjects(userController,"bCryptPasswordEncoder",encoder);
    }

    @Test
    public void testCreateUser(){
        when(encoder.encode("test12345")).thenReturn("thisIsHashed");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("test12345");
        request.setConfirmPassword("test12345");
        ResponseEntity<User> response =  this.userController.createUser(request);
        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        User actualUser = response.getBody();
        assertNotNull(actualUser);
        assertEquals(0,actualUser.getId());
        assertEquals("test" , actualUser.getUsername());
        assertEquals("thisIsHashed", actualUser.getPassword());
    }
    @Test
    public void testFindById(){
        when(encoder.encode("test12345")).thenReturn("thisIsHashed");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("test12345");
        request.setConfirmPassword("test12345");
        ResponseEntity<User> response =  this.userController.createUser(request);
        User u = response.getBody();
        when(userRepository.findById(0L)).thenReturn(java.util.Optional.ofNullable(u));
        ResponseEntity<User> response2 = this.userController.findById(u.getId());
        assertNotNull(response2);
        assertEquals("test",response2.getBody().getUsername());
        assertEquals("thisIsHashed",response2.getBody().getPassword());

    }
    @Test
    public void findByUserName(){
        when(encoder.encode("test12345")).thenReturn("thisIsHashed");
        CreateUserRequest request = new CreateUserRequest();
        request.setUsername("test");
        request.setPassword("test12345");
        request.setConfirmPassword("test12345");
        ResponseEntity<User> response =  this.userController.createUser(request);
        User u = response.getBody();
        when(userRepository.findByUsername("test")).thenReturn(u);
        ResponseEntity<User> response2 = this.userController.findByUserName(u.getUsername());
        assertNotNull(response2);
        assertEquals("test",response2.getBody().getUsername());
        assertEquals("thisIsHashed",response2.getBody().getPassword());

    }

}
