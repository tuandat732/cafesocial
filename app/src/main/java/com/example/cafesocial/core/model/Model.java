package com.example.cafesocial.core.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Model implements Serializable {
    private Long id;
    private String createdAt;
    private String updatedAt;
    private Boolean isSelect;
}
