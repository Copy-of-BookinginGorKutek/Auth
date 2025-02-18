package com.b2.bookingingorkutek.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KuponRequest {
    private String name;
    private Integer percentageDiscounted;
}
