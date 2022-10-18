package com.arusnac.weddingplanner.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.*;

@Entity
@Getter
@Setter @NoArgsConstructor
public class Photographer extends Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private ArrayList<Integer> galleries;

    public void setGalleries(ArrayList<Integer> galleries){
        this.galleries = galleries;
    }

    public ArrayList<Integer> getGalleries() {
        return galleries;
    }

    public void addGalleryId(Integer id){
        galleries.add(id);
    }
}
