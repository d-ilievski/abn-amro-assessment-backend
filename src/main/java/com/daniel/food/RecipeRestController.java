package com.daniel.food;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/recipes")
@CrossOrigin
public class RecipeRestController {

	@Autowired
	private RecipeService recipeService;

	@PostMapping(produces = "application/json")
	public Recipe save(@RequestBody Recipe recipe) {
		return recipeService.save(recipe);
	}

	@PutMapping(produces = "application/json")
	public Recipe update(@RequestBody Recipe recipe) {
		return recipeService.save(recipe);
	}

	@DeleteMapping(path = "/{id}", produces = "application/json")
	public boolean delete(@PathVariable("id") long id) {
		return recipeService.delete(id);
	}

	@GetMapping(path = "/{id}", produces = "application/json")
	public Recipe findById(@PathVariable("id") Long id) {
		return recipeService.findById(id);
	}

	@GetMapping(produces = "application/json")
	public Page<Recipe> getRecipes(Pageable pageable, String query) {
		return recipeService.findAll(pageable, query);
	}
}
