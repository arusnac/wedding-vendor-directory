package com.arusnac.weddingplanner.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private String website;
    private String city;
    private String state;
    private String email;
    private String category;
    private String bio;
    private String featuredImage;

//    public String getName(){
//        return name;
//    }
//
//    public void setName(String name){
//        this.name = name;
//    }
}
