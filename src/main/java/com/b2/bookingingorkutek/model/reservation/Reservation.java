package com.b2.bookingingorkutek.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    private Integer id;
    private String emailUser;
    private String statusPembayaran;
    private String buktiTransfer;
    private Integer harga;
    private Integer idLapangan;
    private LocalDateTime waktuMulai;
    private LocalDateTime waktuBerakhir;

}
