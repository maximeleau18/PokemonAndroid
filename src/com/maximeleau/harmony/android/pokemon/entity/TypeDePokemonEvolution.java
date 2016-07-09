package com.maximeleau.harmony.android.pokemon.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;

@Entity
public class TypeDePokemonEvolution {
		
	@Id
	@ManyToOne(targetEntity="TypeDePokemon")
	@Column(nullable = false)
	private TypeDePokemon evolueEn;
	
	@Id
	@ManyToOne(targetEntity="TypeDePokemon")
	@Column(nullable = false)
	private TypeDePokemon estEvolueEn;
	
}
