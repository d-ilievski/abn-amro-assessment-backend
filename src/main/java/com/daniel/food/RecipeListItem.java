package com.daniel.food;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecipeListItem implements Serializable {
	
	private static final long serialVersionUID = 3024208420163848977L;

	private Long id;	
	private String name;
	private boolean isVegetarian;
	private int servings;
	
}
