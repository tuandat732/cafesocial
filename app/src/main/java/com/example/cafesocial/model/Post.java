package com.example.cafesocial.model;

import com.example.cafesocial.core.model.Model;

import java.util.List;

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
public class Post extends Model {
    private Long userId;
    private User user;

    private float rateLocation;
    private float rateSpace;
    private float rateMenu;
    private float rateService;
    private float ratePrice;
    private float totalRate;
    private String rateContent;
    private List<ImagePost> images;
    private int totalLike;
    private int totalComment;

    private Long storeId;
    private Store store;

    private List<Comment> comments;
    private List<PostLike> postLikes;
}
