package com.example.cafesocial.model;

import com.example.cafesocial.core.model.Model;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Store extends Model {
   private Long userId;
   private User user;
   private String name;
   private String address;
   private String present;
   private String timeOpen;
   private String timeClose;
   private float priceStart;
   private float priceEnd;
   private String wifi;
   private String wifiPass;
   private String phone;
   private String email;
   private String facebook;
   private String instagram;
   private String website;
   private List<ImageStore> images;
   private List<Menu> menus;
   private Long locationId;
   private Location location;
   private List<Benefit> benefits;
   private List<Purpose> purposes;
   private List<Type> types;
   private List<Sale> sales;
   private List<Post> posts;
   private int totalPost;
   private float totalRateCount;
   private float totalRateSpace;
   private  float totalRateMenu;
   private  float totalRateService;
   private  float totalRatePrice;
   private  float totalRateLocation;
   private double latitude;
   private double longitude;

   public String getRateString() {
      if(totalRateCount  < 1) return "Tệ";
      if(totalRateCount <= 2) return "Khá";
      if(totalRateCount <= 3) return "Ổn";
      if(totalRateCount <= 4) return "Tốt";
      if(totalRateCount <= 5) return "Tuyệt vời";
      return "";
   }

   public int getProgressPercen(float value, float total){
      return (int)((value/total)*100);
   }

   public String getStoreTypeTag() {
      List<String> tags = new ArrayList<>();
      types.forEach(type -> {
         tags.add(type.getName());
      });
      return String.join(",", tags);

   }
}
