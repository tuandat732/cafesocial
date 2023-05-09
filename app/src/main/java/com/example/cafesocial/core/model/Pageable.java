package com.example.cafesocial.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Pageable {
    private int page;
    private int pageSize;
    private int totalPage;
    private int totalItem;
}
