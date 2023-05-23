package com.b2.bookingingorkutek.controller;

import com.b2.bookingingorkutek.model.notification.Notification;
import com.b2.bookingingorkutek.service.JwtService;
import com.b2.bookingingorkutek.service.UserNotificationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(UserNotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class UserNotificationControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private UserNotificationService userNotificationService;

    @Test
    void testAccessingGetSelfNotificationMethod() throws Exception{
        List<Notification> notificationList = new ArrayList<>();
        notificationList.add(Notification
                .builder()
                .id(1)
                .emailUser("user@test.com")
                .timestamp(LocalDateTime.of(2023, 4,30,12, 12, 12))
                .build());
        when(userNotificationService.getSelf(anyString(), anyString())).thenReturn(notificationList);
        mvc.perform(get("/user-notification/get/user@test.com"))
                .andExpect(handler().methodName("getSelfNotification"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().json("[{\"emailUser\": \"user@test.com\", \"id\": 1, \"timestamp\": \"2023-04-30T12:12:12\"}]"));
    }
    @Test
    void testAccessingDeleteNotificationMethod() throws Exception{
        when(userNotificationService.getSelf(anyString(), anyString())).thenReturn(new ArrayList<>());
        mvc.perform(delete("/user-notification/delete/1"))
                .andExpect(handler().methodName("deleteNotification"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string("\"Deleted notification\""));
    }

}
