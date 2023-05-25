package com.b2.bookingingorkutek.dto;

import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class ReservasiRequest {
    private String emailUser;
    private String statusPembayaran;
    private String buktiTransfer;
    private String waktuMulai;
    private String waktuBerakhir;
    private Map<String, Integer> tambahanQuantity;
    private Integer kuponId;
}
