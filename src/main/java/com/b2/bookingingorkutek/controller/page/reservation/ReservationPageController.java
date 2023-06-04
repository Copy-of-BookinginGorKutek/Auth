package com.b2.bookingingorkutek.controller.page.reservation;

import com.b2.bookingingorkutek.dto.ModelUserDto;
import com.b2.bookingingorkutek.model.lapangan.OperasionalLapangan;
import com.b2.bookingingorkutek.model.reservation.Reservation;
import com.b2.bookingingorkutek.service.AuthorizationService;
import com.b2.bookingingorkutek.service.OperasionalLapanganService;
import com.b2.bookingingorkutek.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller
@RequestMapping("/user-reservation-page")
public class ReservationPageController {
    @Autowired
    ReservationService reservationService;
    @Autowired
    OperasionalLapanganService operasionalLapanganService;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    AuthorizationService authorizationService;
    static final String ADMIN = "ADMIN";
    static final String REDIRECT_TO_LOGIN = "redirect:/auth-page/login";

    @GetMapping("/get-self")
    public String getUserReservationList(@CookieValue(name = "token", defaultValue = "") String token, Model model){
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals(ADMIN)) {
            return REDIRECT_TO_LOGIN;
        }
        List<Reservation> userReservationList = reservationService.getSelf(user.getEmailUser(), token);
        model.addAttribute("noReservation", userReservationList.isEmpty());
        model.addAttribute("reservasiList", userReservationList);
        model.addAttribute("user", user);
        return "user_reservation_list";
    }

    @GetMapping("/get-all-reservation")
    public String getAllReservationsAndCourtAvailability(@CookieValue(name = "token", defaultValue = "") String token, Model model) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals(ADMIN)) {
            return REDIRECT_TO_LOGIN;
        }

        CompletableFuture<List<Reservation>> reservationListAsync = CompletableFuture.supplyAsync(() ->
                reservationService.getAllReservasi(token)
        );

        CompletableFuture<List<OperasionalLapangan>> operasionalLapanganListAsync = CompletableFuture.supplyAsync(() ->
                operasionalLapanganService.getAllOperasionalLapangan(token)
        );

        model.addAttribute("notEmptyReservation", !reservationListAsync.get().isEmpty());
        model.addAttribute("noClosedLapangan", operasionalLapanganListAsync.get().isEmpty());
        model.addAttribute("reservationList", reservationListAsync.get());
        model.addAttribute("operasionalLapanganList", operasionalLapanganListAsync.get());
        return "all_reservation_court_availability";
    }

    @GetMapping("/get-all-reservation/{date}")
    public String getAllReservationsAndCourtAvailabilityByDate(@CookieValue(name = "token", defaultValue = "") String token, Model model, @PathVariable String date) throws ExecutionException, InterruptedException {
        ModelUserDto user = authorizationService.requestCurrentUser(token);
        if(user == null || user.getRole().equals(ADMIN)) {
            return REDIRECT_TO_LOGIN;
        }

        CompletableFuture<List<Reservation>> reservationListAsync = CompletableFuture.supplyAsync(() ->
                reservationService.getReservasiByDate(date, token)
        );

        CompletableFuture<List<OperasionalLapangan>> operasionalLapanganListAsync = CompletableFuture.supplyAsync(() ->
                operasionalLapanganService.getOperasionalLapanganByDate(date, token)
        );

        model.addAttribute("notEmptyReservation", !reservationListAsync.get().isEmpty());
        model.addAttribute("noClosedLapangan", operasionalLapanganListAsync.get().isEmpty());
        model.addAttribute("reservationList", reservationListAsync.get());
        model.addAttribute("operasionalLapanganList", operasionalLapanganListAsync.get());
        return "all_reservation_court_availability";
    }
}