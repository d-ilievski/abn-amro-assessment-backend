package com.daniel.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {

	@Autowired
	private RecipeRepository recipeRepository;
	
	@Autowired
	private ImagesRestController imagesRestController;
	
	public Recipe save(Recipe recipe) {
		return recipeRepository.save(recipe);
	}
	
	public boolean delete(long id) {
		
		Recipe r = findById(id);
		
		if(r != null) {
			if(r.isHasImage())
				imagesRestController.remove(Long.toString(id) + ".jpg");
			recipeRepository.deleteById(id);
			return true;
		}
		
		return false;
	}
	
	public Recipe findById(long id) {
		return recipeRepository.findById(id).orElse(null);
	}
	
	public Page<Recipe> findAll(Pageable pageable, String query) {
		
		return recipeRepository.searchResult(pageable, query);
	}
	
	public void updateHasImage(long id) {
		recipeRepository.updateHasImage(id);
	}
}
