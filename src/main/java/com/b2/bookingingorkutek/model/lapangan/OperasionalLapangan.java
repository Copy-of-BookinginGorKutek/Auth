package com.b2.bookingingorkutek.model.lapangan;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OperasionalLapangan {
    private Integer id;
    private Integer idLapangan;
    private Date tanggalLibur;
}
