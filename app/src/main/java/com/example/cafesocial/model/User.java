package com.example.cafesocial.model;

import com.example.cafesocial.core.model.Model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import retrofit2.http.POST;

//@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class User {
   private Long id;
   private String createdAt;
   private String updatedAt;
   private String email;
   private String password;
   private String username;
   private String avatar;
   private List<Store> stores;
   private List<Post> posts;
   private List<Comment> comments;
   private List<PostLike> postLikes;
   private List<Follow> follows;
}
