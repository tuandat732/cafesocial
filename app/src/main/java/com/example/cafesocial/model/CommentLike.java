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
public class CommentLike extends Model {
    private Long userId;
    private Long commentId;
    private User user;
}
