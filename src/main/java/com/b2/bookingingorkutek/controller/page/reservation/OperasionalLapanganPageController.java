package com.b2.bookingingorkutek.controller.page.reservation;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/operasional-lapangan-page")
@RequiredArgsConstructor
public class OperasionalLapanganPageController {
    @Autowired
    AuthorizationService authorizationService;
    @GetMapping("/create")
    public String createOperasionalLapangan(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("USER"))
            return "redirect:/auth-page/login";
        return "operasional_lapangan_page";
    }
}
