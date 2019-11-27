package com.daniel.food;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.BDDAssertions.then;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RecipeServiceTest {

  @InjectMocks
  RecipeService recipeService;

  @Mock
  private RecipeRepository recipeRepository;

  @Mock
  private ImageManagementService imageManagementService;


  @Test
  void shouldSuccessfullyDeleteRecipeAndItsImage() {
    // given
    long recipeId = 1;
    Recipe recipe = createRecipe();
    given(recipeRepository.findById(recipeId)).willReturn(Optional.of(recipe));

    // when
    boolean isDeleted = recipeService.delete(recipeId);

    // then
    then(isDeleted).isTrue();
    BDDMockito.then(imageManagementService).should().remove(recipeId + ".jpg");
    BDDMockito.then(recipeRepository).should().deleteById(recipeId);
  }

  @Test
  void shouldSuccessfullyDeleteOnlyRecipe() {
    // given
    long recipeId = 1;
    Recipe recipe = createRecipe();
    recipe.setHasImage(false);
    given(recipeRepository.findById(recipeId)).willReturn(Optional.of(recipe));

    // when
    boolean isDeleted = recipeService.delete(recipeId);

    // then
    then(isDeleted).isTrue();
    BDDMockito.then(recipeRepository).should().deleteById(recipeId);
  }

  @Test
  void shouldNotDeleteRecipeWhenCantFind() {
    // given
    long recipeId = 1;
    given(recipeRepository.findById(recipeId)).willReturn(Optional.empty());

    // when
    boolean isDeleted = recipeService.delete(recipeId);

    // then
    then(isDeleted).isFalse();
  }

  private Recipe createRecipe() {
    Recipe recipe = new Recipe();
    recipe.setId(1L);
    recipe.setName("Recipe 1");
    recipe.setHasImage(true);
    return recipe;
  }
}
