package com.b2.bookingingorkutek.model;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;
    @BeforeEach
    void setup(){
        user = User.builder()
                .id(1)
                .firstname("testuser")
                .lastname("testuser")
                .email("testuser@email.com")
                .password("password")
                .active(true)
                .role("USER")
                .build();

    }
    @Test
    void getId(){ assertEquals(1, user.getId()); }

    @Test
    void getFirstName(){ assertEquals("testuser", user.getFirstname());}

    @Test
    void getLastName(){ assertEquals("testuser", user.getLastname());}
    @Test
    void getPassword(){ assertEquals("password", user.getPassword());}
    @Test
    void getRole(){ assertEquals("USER", user.getRole());}
    @Test
    void getUserActiveStatus(){ assertDoesNotThrow(()->user.isActive());}

    @Test
    void setFirstName(){
        user.setFirstname("testuser1");
        assertEquals("testuser1", user.getFirstname());
    }
    @Test
    void setLastName(){
        user.setLastname("testuser1");
        assertEquals("testuser1", user.getLastname());
    }
    @Test
    void setPassword(){
        user.setPassword("pass1");
        assertEquals("pass1", user.getPassword());
    }
    @Test
    void setRole(){
        user.setRole("ADMIN");
        assertEquals("ADMIN", user.getRole());
    }
    @Test
    void setActiveStatus(){
        user.setActive(false);
        assertFalse(user.isActive());
    }

}
