package com.example.demo.controllers;

import com.example.demo.TestUtils;
import com.example.demo.model.persistence.Item;
import com.example.demo.model.persistence.repositories.ItemRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemControllerTest {
    private ItemRepository itemRepository = mock(ItemRepository.class);
    private ItemController itemController;
    @Before
    public void setup(){
        this.itemController = new ItemController();
        TestUtils.injectObjects(itemController,"itemRepository",itemRepository);


        Item item1 = new Item();
        item1.setId(1L);
        item1.setName("item1");
        item1.setDescription("the desc of item1");
        item1.setPrice(BigDecimal.valueOf(4.5));
        when(itemRepository.findById(1L)).thenReturn(java.util.Optional.of(item1));
        when(itemRepository.findByName("item1")).thenReturn(Collections.singletonList(item1));
    }
    @Test
    public void get_item_by_id_happy_path(){
        ResponseEntity<Item> response = itemController.getItemById(1L);
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals("item1",response.getBody().getName());
        assertEquals("the desc of item1",response.getBody().getDescription());
    }
    @Test
    public void get_item_by_name_happy_path(){
        ResponseEntity<List<Item>> response = itemController.getItemsByName("item1");
        assertNotNull(response);
        assertEquals(200,response.getStatusCodeValue());
        assertEquals(1,response.getBody().size());

    }

}
