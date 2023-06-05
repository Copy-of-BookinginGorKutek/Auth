package com.b2.bookingingorkutek.controller.page;

import com.b2.bookingingorkutek.controller.page.reservation.AdminCouponPageController;
import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.kupon.Kupon;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.JwtService;
import com.b2.bookingingorkutek.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(AdminCouponPageController.class)
@AutoConfigureMockMvc(addFilters = false)
class AdminCouponPageControllerTest {
    @Autowired
    private MockMvc mvc;
    @MockBean
    private JwtService jwtService;
    @MockBean
    private AuthorizationService authorizationService;
    @MockBean
    private RestTemplate restTemplate;

    private Kupon[] kuponArr;
    private ModelUserDto admin;
    private ModelUserDto user;
    private ModelUserDto anonymous;

    @BeforeEach
    void setup(){
        this.user = ModelUserDto.builder()
                .firstname("User")
                .lastname("User")
                .role("USER")
                .emailUser("user@test.com")
                .build();
        this.admin = ModelUserDto.builder()
                .firstname("Admin")
                .lastname("Admin")
                .role("ADMIN")
                .emailUser("admin@test.com")
                .build();
        this.anonymous = null;
        Kupon kupon = Kupon.builder()
                .id(1)
                .name("dummy")
                .percentageDiscounted(50)
                .build();
        kuponArr = new Kupon[1];
        kuponArr[0] = kupon;
    }

    @Test
    void whenCreateCouponAndUserRoleIsNotAdminShouldRedirect() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/admin-coupon-page/create"))
                .andExpect(handler().methodName("createCoupon"))
                .andExpect(status().is3xxRedirection());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/admin-coupon-page/create"))
                .andExpect(handler().methodName("createCoupon"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testCreateCouponWithRoleUser() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        mvc.perform(get("/admin-coupon-page/create"))
                .andExpect(handler().methodName("createCoupon"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Coupon Name")))
                .andExpect(content().string(containsString("Discount Amount")))
                .andExpect(content().string(containsString("Add Coupon")));
    }

    @Test
    void whenGetAllCouponAndUserRoleIsNotAdminShouldRedirect() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(user);
        mvc.perform(get("/admin-coupon-page/get-all-coupon"))
                .andExpect(handler().methodName("getAllCoupon"))
                .andExpect(status().is3xxRedirection());
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(anonymous);
        mvc.perform(get("/admin-coupon-page/get-all-coupon"))
                .andExpect(handler().methodName("getAllCoupon"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void testGetAllCouponWhenCouponNotExist() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Kupon[].class))).thenReturn(new ResponseEntity<>(null, HttpStatusCode.valueOf(200)));
        mvc.perform(get("/admin-coupon-page/get-all-coupon"))
                .andExpect(handler().methodName("getAllCoupon"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("Coupon Name")))
                .andExpect(content().string(containsString("Discount Amount")));
    }

    @Test
    void testGetAllCouponWhenCouponExist() throws Exception{
        when(authorizationService.requestCurrentUser(anyString())).thenReturn(admin);
        when(restTemplate.exchange(anyString(), any(), any(), eq(Kupon[].class))).thenReturn(new ResponseEntity<>(kuponArr, HttpStatusCode.valueOf(200)));
        mvc.perform(get("/admin-coupon-page/get-all-coupon"))
                .andExpect(handler().methodName("getAllCoupon"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(content().string(containsString("dummy")))
                .andExpect(content().string(containsString("50")));
    }
}
