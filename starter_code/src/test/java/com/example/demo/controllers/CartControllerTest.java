package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Cart;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.User;
import com.example.demo.model.persistence.repositories.CartRepository;
import com.example.demo.model.persistence.repositories.ItemRepository;
import com.example.demo.model.persistence.repositories.UserRepository;
import com.example.demo.model.requests.ModifyCartRequest;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CartControllerTest {
    private CartController cartController;

    private UserRepository userRepository = mock(UserRepository.class);


    private CartRepository cartRepository = mock(CartRepository.class);


    private ItemRepository itemRepository = mock(ItemRepository.class);
    @Before
    public void setup(){
        this.cartController = new CartController();
        TestUtils.injectObjects(cartController, "userRepository", userRepository);
        TestUtils.injectObjects(cartController, "cartRepository", cartRepository);
        TestUtils.injectObjects(cartController,"itemRepository",itemRepository);

        User user = new User();
        Cart cart = new Cart();
        user.setUsername("test");
        user.setPassword("test12345");
        user.setCart(cart);
        when(userRepository.findByUsername("test")).thenReturn(user);

        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("item1");
        item1.setDescription("the desc of item1");
        item1.setPrice(BigDecimal.valueOf(4.5));
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));

    }
    @Test
    public void add_to_cart_happy_path(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        Cart c = response.getBody();
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(1,c.getItems().size());

    }
    @Test
    public void add_to_cart_not_found_user(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setUsername("test1");
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        Cart c = response.getBody();
        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());


    }
    @Test
    public void add_to_cart_not_found_item(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(2L);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.addTocart(modifyCartRequest);
        Cart c = response.getBody();
        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());


    }
    @Test
    public void remove_from_cart_happy_path(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        Cart c = response.getBody();
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(0,c.getItems().size());

    }
    @Test
    public void remove_from_cart_not_found_user(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(1L);
        modifyCartRequest.setUsername("test1");
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        Cart c = response.getBody();
        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());


    }
    @Test
    public void remove_from_cart_not_found_item(){
        ModifyCartRequest modifyCartRequest = new ModifyCartRequest();
        modifyCartRequest.setItemId(2L);
        modifyCartRequest.setUsername("test");
        modifyCartRequest.setQuantity(1);
        ResponseEntity<Cart> response = cartController.removeFromcart(modifyCartRequest);
        Cart c = response.getBody();
        assertNotNull(response);
        assertEquals(404,response.getStatusCodeValue());


    }





}
