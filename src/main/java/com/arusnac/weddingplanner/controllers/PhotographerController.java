package com.arusnac.weddingplanner.controllers;
import com.arusnac.weddingplanner.models.Photographer;
import com.arusnac.weddingplanner.repository.PhotographerRepo;
import com.arusnac.weddingplanner.services.PhotographerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000/")
@Controller
@RequestMapping(path = "/photography")
public class PhotographerController {
    @Autowired
    private PhotographerRepo photographerRepo;
    @Autowired
    private final PhotographerService photographerService;

    public PhotographerController(PhotographerService photographerService) {
        this.photographerService = photographerService;
    }

    @PostMapping(path="/add")
    public @ResponseBody String addNewPhotographer (@RequestParam String name, @RequestParam String bio, @RequestParam String email, @RequestParam String city, @RequestParam String state,
                                                    @RequestParam String website, @RequestParam String featuredImage, @RequestParam ArrayList<Integer> galleryId ){
        //1515626553181-0f218cb03f14
        Photographer newPhotographer = new Photographer();
        newPhotographer.setName(name);
        newPhotographer.setBio(bio);
        newPhotographer.setEmail(email);
        newPhotographer.setCity(city);
        newPhotographer.setState(state);
        newPhotographer.setCategory("Photography");
        newPhotographer.setWebsite(website);
        newPhotographer.setGalleries(galleryId);
        newPhotographer.setFeaturedImage(featuredImage);
        photographerRepo.save(newPhotographer);
        return "SAVED";
    }

    //Return all photographers
    @GetMapping(path="/all")
    public @ResponseBody Iterable<Photographer> getAllPhotographers(){
        return photographerRepo.findAll();
    }


    //Delete this vendor by id
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

    //Update this vendors data
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

    //Add image to the photographers sample gallery
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

    //Upload a featured image
    @Async
    @PostMapping(
            path = "/upload/{id}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public @ResponseBody ResponseEntity<HttpStatus> uploadFeaturedImage(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file){
        System.out.println(file);
        photographerService.uploadFeaturedImage(id, file);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //Download the featured image for this vendor
    @GetMapping(value= "/download/{id}")
    @Async
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable("id") Integer id){
        final byte[] data = photographerService.downloadPhotographerFeaturedImage(id);
        final ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity
                .ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                //.header("Content-disposition", "attachment; filename =\"" + data.)
                .body(resource);
    }

}
