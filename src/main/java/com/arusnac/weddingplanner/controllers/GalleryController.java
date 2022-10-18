package com.arusnac.weddingplanner.controllers;

import com.arusnac.weddingplanner.models.Gallery;
import com.arusnac.weddingplanner.models.Photographer;
import com.arusnac.weddingplanner.repository.GalleryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@Controller
@RequestMapping(path = "/gallery")
public class GalleryController {

    @Autowired
    private GalleryRepo galleryRepo;

    @PostMapping(path="/add")
    public @ResponseBody String addNewPhotographer (@RequestParam String title, @RequestParam String description, @RequestParam ArrayList<String> imageUrls ){
        Gallery newGallery = new Gallery();
        newGallery.setTitle(title);
        newGallery.setDescription(description);
        newGallery.setImageUrls(imageUrls);
        galleryRepo.save(newGallery);
        return "SAVED";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Gallery> getAllGalleries(){
        return galleryRepo.findAll();
    }
    @GetMapping(path="/{id}")
    public ResponseEntity<Gallery> getGalleryById(@PathVariable("id") Integer id) {
        Optional<Gallery> galleryData = galleryRepo.findById(id);

        if (galleryData.isPresent()) {
            return new ResponseEntity<>(galleryData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
