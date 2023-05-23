package com.b2.bookingingorkutek.model.kupon;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class Kupon {
    private Integer id;
    private String name;
    private Integer percentageDiscounted;
}
