package com.arusnac.weddingplanner.controllers;
import com.arusnac.weddingplanner.models.Photographer;
import com.arusnac.weddingplanner.repository.PhotographerRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@Controller
@RequestMapping(path = "/photography")
public class PhotographerController {
    @Autowired
    private PhotographerRepo photographerRepo;

    @PostMapping(path="/add")
    public @ResponseBody String addNewPhotographer (@RequestParam String name, @RequestParam String email, @RequestParam String website, @RequestParam String location, @RequestParam ArrayList<Integer> galleryId ){
        Photographer newPhotographer = new Photographer();
        newPhotographer.setName(name);
        newPhotographer.setEmail(email);
        newPhotographer.setLocation(location);
        newPhotographer.setCategory("Photography");
        newPhotographer.setWebsite(website);
        newPhotographer.setGalleries(galleryId);
        photographerRepo.save(newPhotographer);
        return "SAVED";
    }

    @GetMapping(path="/all")
    public @ResponseBody Iterable<Photographer> getAllPhotographers(){
        return photographerRepo.findAll();
    }


    @DeleteMapping(path="/delete/{id}")
    public @ResponseBody ResponseEntity<HttpStatus> deleteById(@PathVariable("id") int id) {
        try {
            photographerRepo.deleteById(id);
            System.out.println(String.format("PHOTOGRAPH OF ID: %s was deleted", id));
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping(path="/update/{id}", consumes = {"application/json"})
    public ResponseEntity<Photographer> updatePhotographer(@PathVariable("id") int id,
                                                           @RequestBody Photographer photographer) {
        Optional<Photographer> photographerData = photographerRepo.findById(id);
        if(photographerData.isPresent()){
            Photographer _photographer = photographerData.get();
            _photographer.setName(photographer.getName());
            _photographer.setEmail(photographer.getEmail());
            _photographer.setWebsite(photographer.getWebsite());
            return new ResponseEntity<>(photographerRepo.save(_photographer), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping(path="/addGallery/{id}")
    public @ResponseBody ResponseEntity<Photographer> addToGallery(@PathVariable("id") Integer id, @RequestParam Integer galleryId){
        Optional<Photographer> photographerData = photographerRepo.findById(id);
        if(photographerData.isPresent()){
            Photographer _photographer = photographerData.get();
            _photographer.addGalleryId(galleryId);
            return new ResponseEntity<>(photographerRepo.save(_photographer), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
