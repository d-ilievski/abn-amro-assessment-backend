package com.daniel.food;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
@RequiredArgsConstructor
public class ImageManagementService {

  private final RecipeRepository recipeRepository;
  private Path uploadLocation = Paths.get("./tmp");

  public void remove(String filename) {
    try {
      Path file = uploadLocation.resolve(filename);
      File f = new File(file.toUri());
      if(f.exists())
        f.delete();
      else
        throw new MalformedURLException("Could not find file:" + filename);

    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not read file: " + filename, e);
    }
  }

  public Resource loadAsResource(String filename) {
    try {
      Path file = uploadLocation.resolve(filename);
      Resource resource = new UrlResource(file.toUri());
      if (resource.exists() || resource.isReadable()) {
        return resource;
      } else {
        throw new RuntimeException("Could not read file: " + filename);
      }
    } catch (MalformedURLException e) {
      throw new RuntimeException("Could not read file: " + filename, e);
    }
  }

  public void store(MultipartFile file, long id) {

    String filename = id + "." + "jpg";

    try {
      if (file.isEmpty()) {
        throw new RuntimeException("Failed to store empty file " + filename);
      }

      try (InputStream inputStream = file.getInputStream()) {
        Files.copy(inputStream, this.uploadLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);

        recipeRepository.updateHasImage(id);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file " + filename, e);
    }
  }
}
