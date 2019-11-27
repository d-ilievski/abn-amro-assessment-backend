package com.daniel.food;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class ImagesManagementServiceTest {

	@InjectMocks
	ImageManagementService imageManagementService;

	@Mock
	private RecipeRepository recipeRepository;

	@Mock
	private RecipeService recipeService;

	@Test
	void shouldSuccessfullyStoreImage() {

		// given
		long id = 1;
		MockMultipartFile file = new MockMultipartFile("1.jpg", "TestFile".getBytes());

		// when
		imageManagementService.store(file, id);

		// then
		recipeRepository.updateHasImage(id);
	}

}
