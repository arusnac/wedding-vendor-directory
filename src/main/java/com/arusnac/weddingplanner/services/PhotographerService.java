package com.arusnac.weddingplanner.services;

import com.arusnac.weddingplanner.bucket.BucketName;
import com.arusnac.weddingplanner.fileStore.FileStore;
import com.arusnac.weddingplanner.models.Photographer;
import com.arusnac.weddingplanner.repository.PhotographerRepo;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Service
public class PhotographerService {
    @Autowired
    private PhotographerRepo photographerRepo;
//    @Autowired
    private final FileStore fileStore;

//    @Autowired
    public PhotographerService(FileStore fileStore) {
        this.fileStore = fileStore;
    }

    public void uploadFeaturedImage(Integer id, MultipartFile file){
        //System.out.println(ContentType.IMAGE_JPEG);
        if(file.isEmpty()){
            throw new IllegalStateException("Upload failed - empty file: " + file.getSize());
        }

        if(!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType()).contains(file.getContentType())) {
            throw new IllegalStateException("File must be an image");
        }

        Optional<Photographer> photographerData = photographerRepo.findById(id);
        if(photographerData.isPresent()) {
            Photographer photographer = photographerData.get();
            Map<String, String> metadata = new HashMap<>();
            metadata.put("Content-Type", file.getContentType());
            metadata.put("Content-Length", String.valueOf(file.getSize()));
            System.out.println(photographer.getName());
            String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), photographer.getId());
            String filename = String.format("%s-%s", UUID.randomUUID(),file.getOriginalFilename());
            System.out.println(filename);
            try {
                fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
                photographer.setFeaturedImage(filename);
                photographerRepo.save(photographer);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        } else {
            throw new IllegalStateException("User Not found with ID: " + id);
        }
    }

    public byte[] downloadPhotographerFeaturedImage(Integer id) {
        Optional<Photographer> photographerData = photographerRepo.findById(id);

        if(photographerData.isPresent()) {
            Photographer photographer = photographerData.get();
            String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), photographer.getId());
            return fileStore.download(path, photographer.getFeaturedImage());
//            return photographer.getFeaturedImage()
//                    .map(key -> fileStore.download(path, key))
//                    .orElse(new byte[0]);
            //return fileStore.download(path);
        }
        return new byte[0];
    }
}
