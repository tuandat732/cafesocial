package com.example.cafesocial.model;

import com.example.cafesocial.core.model.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Comment extends Model {
    private Long userId;
    private User user;
    private String content;
    private Long storeId;
    private Store store;
    private int totalLike;
}
