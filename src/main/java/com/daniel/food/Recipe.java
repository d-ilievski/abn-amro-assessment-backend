package com.daniel.food;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "recipes")
@Entity
public class Recipe implements Serializable {
	
	private static final long serialVersionUID = 3024208420163848977L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private boolean isVegetarian;
	private int servings;
	
	private String instructions;
	private boolean hasImage;
	private Date createdOn;
	private Date updatedOn;
	private String ingredients;
	
}
