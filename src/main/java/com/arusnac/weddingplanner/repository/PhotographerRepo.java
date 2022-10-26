package com.arusnac.weddingplanner.repository;
import org.springframework.data.repository.CrudRepository;
import com.arusnac.weddingplanner.models.Photographer;
import org.springframework.stereotype.Repository;
import org.springframework.web.multipart.MultipartFile;


public interface PhotographerRepo extends CrudRepository<Photographer, Integer> {

    Photographer findByName(String name);
}
