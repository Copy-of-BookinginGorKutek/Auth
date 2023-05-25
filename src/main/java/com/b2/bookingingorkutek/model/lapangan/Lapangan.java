package com.b2.bookingingorkutek.model.lapangan;

import jakarta.persistence.*;
import lombok.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class Lapangan {
    private static Integer cost = 50000;

    private Integer id;

    public static Integer getCost(){ return cost; }
    public static void setCost(Integer harga){ cost = harga; }
}
