package com.b2.bookingingorkutek.controller.page.reservation;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.lapangan.OperasionalLapangan;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.OperasionalLapanganService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/operasional-lapangan-page")
@RequiredArgsConstructor
public class OperasionalLapanganPageController {
    @Autowired
    AuthorizationService authorizationService;

    @Autowired
    OperasionalLapanganService operasionalLapanganService;
    @GetMapping("/create")
    public String createOperasionalLapangan(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals("USER"))
            return "redirect:/auth-page/login";
        return "create_operasional_lapangan";
    }

    @GetMapping("/closed/all")
    @CrossOrigin
    public String allClosedLapangan(@CookieValue(name = "token", defaultValue = "") String token, Model model) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if (user == null || user.getRole().equals("USER")) {
            return "redirect:/auth-page/login";
        }

        CompletableFuture<List<OperasionalLapangan>> allOperasionalLapanganAsync = CompletableFuture.supplyAsync(() ->
                operasionalLapanganService.getAllOperasionalLapangan(token)
        );

        model.addAttribute("operasionalLapanganNotEmpty", !allOperasionalLapanganAsync.get().isEmpty());
        model.addAttribute("operasionalLapanganList", allOperasionalLapanganAsync.get());
        return "operasional_lapangan_admin";
    }
}
