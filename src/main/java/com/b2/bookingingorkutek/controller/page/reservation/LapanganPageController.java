package com.b2.bookingingorkutek.controller.page.reservation;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.lapangan.OperasionalLapangan;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.OperasionalLapanganService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/lapangan-page")
@RequiredArgsConstructor
public class LapanganPageController {

    @Autowired
    private final AuthorizationService authorizationService;
    static final String ADMIN = "ADMIN";
    static final String REDIRECT_TO_LOGIN = "redirect:/auth-page/login";


    @GetMapping("/create")
    public String createLapangan(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || !user.getRole().equals(ADMIN))
            return REDIRECT_TO_LOGIN;
        model.addAttribute("user", user);
        return "create_lapangan";
    }
}
