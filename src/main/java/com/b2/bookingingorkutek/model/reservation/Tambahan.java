package com.b2.bookingingorkutek.model.reservation;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tambahan {
    private Integer tambahanId;
    private Reservation reservasi;
    private String category;
    private Integer quantity;
}
