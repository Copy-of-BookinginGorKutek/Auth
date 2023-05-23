package com.b2.bookingingorkutek.model.reservation;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class Tambahan {
    private Integer tambahanId;
    private Reservation reservasi;
    private String category;
    private Integer quantity;
}
