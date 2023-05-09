package com.example.cafesocial.service;

import com.example.cafesocial.model.Store;
import com.example.cafesocial.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static User user;
    private static String token;
    private static List<Store> stores = new ArrayList<>();

    public static List<Store> getStores() {
        return stores;
    }

    public static void addStore(Store store) {
        for(Store s: stores) {
            if(s.getId().equals(store.getId())) {
                return;
            }
        }
        stores.add(store);
    }

    public static void removeStore(Store store) {
        for(int i=0;i<stores.size();i++) {
            if(stores.get(i).getId().equals(store.getId())) {
                stores.remove(i);
            }
        }
    }


    public static void setAuth(User user, String token) {
        UserService.user = user;
        UserService.token = token;
    }

    public static void clearAuth() {
        UserService.user = null;
        UserService.token = "";
    }

    public static Boolean isAuthen() {
        System.out.println("user nef");
        System.out.println( UserService.user);
        if(UserService.user != null) return true;
        return false;
    }

    public static User getUser() {
        return UserService.user;
    }

    public static String getToken() {
        return UserService.token;
    }
}
