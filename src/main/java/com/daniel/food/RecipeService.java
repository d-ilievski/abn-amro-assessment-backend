package com.daniel.food;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RecipeService {

	private final RecipeRepository recipeRepository;
	
	private final ImageManagementService imageManagementService;
	
	public Recipe save(Recipe recipe) {
		return recipeRepository.save(recipe);
	}
	
	public boolean delete(long id) {
		
		Recipe recipe = findById(id);
		
		if(recipe != null) {
			if(recipe.isHasImage())
				imageManagementService.remove(id + ".jpg");
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
