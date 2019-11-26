package com.daniel.food;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
@RequestMapping(path = "/files")
@CrossOrigin
public class ImagesRestController {

	Path uploadLocation;
	
	@Autowired
	private RecipeService recipeService;

	@PostConstruct
	public void init() {
		this.uploadLocation = Paths.get("./tmp");
//		try {
//			Files.createDirectories(uploadLocation);
//		} catch (IOException e) {
//			throw new RuntimeException("Could not initialize storage", e);
//		}
	}

	@GetMapping("/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
		Resource file = this.loadAsResource(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public void handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("id") long id,
			RedirectAttributes redirectAttributes) {
		this.store(file, id);
		redirectAttributes.addFlashAttribute("message",
				"You successfully uploaded " + file.getOriginalFilename() + "!");
		// return "redirect:/";
	}
	
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

			// This is a security check
			if (filename.contains("..")) {
				throw new RuntimeException(
						"Cannot store file with relative path outside current directory " + filename);
			}

			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, this.uploadLocation.resolve(filename), StandardCopyOption.REPLACE_EXISTING);
				
				recipeService.updateHasImage(id);
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to store file " + filename, e);
		}
	}
}
