package com.b2.bookingingorkutek.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Generated
public class OperasionalLapanganRequest {
    private Integer idLapangan;
    @JsonFormat(pattern="yyyy-MM-dd")
    private Date tanggalLibur;
}
