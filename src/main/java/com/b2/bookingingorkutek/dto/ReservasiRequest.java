package com.b2.bookingingorkutek.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservasiRequest {
    private String emailUser;
    private String statusPembayaran;
    private String buktiTransfer;
    private String waktuMulai;
    private String waktuBerakhir;
    private Map<String, Integer> tambahanQuantity;
}
