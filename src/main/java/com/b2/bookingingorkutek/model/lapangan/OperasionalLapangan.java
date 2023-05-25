package com.b2.bookingingorkutek.model.lapangan;

import lombok.*;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class OperasionalLapangan {
    private Integer id;
    private Integer idLapangan;
    private Date tanggalLibur;
}
