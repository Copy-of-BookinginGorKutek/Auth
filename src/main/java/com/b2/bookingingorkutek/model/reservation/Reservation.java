package com.b2.bookingingorkutek.model.reservation;

import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class Reservation {
    private Integer id;
    private String emailUser;
    private String statusPembayaran;
    private String buktiTransfer;
    private Integer harga;
    private Integer idLapangan;
    private LocalDateTime waktuMulai;
    private LocalDateTime waktuBerakhir;
    private List<Tambahan> tambahanList;
    private Integer idKupon;
}
