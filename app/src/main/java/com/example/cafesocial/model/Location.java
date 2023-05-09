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
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Location extends Model {
    private String name;
    private String image;
    private List<Store> stores;

    @Override
    public String toString() {
        return name;
    }
}
