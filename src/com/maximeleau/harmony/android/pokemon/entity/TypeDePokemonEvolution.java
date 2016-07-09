package com.maximeleau.harmony.android.pokemon.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;

@Entity
public class TypeDePokemonEvolution {
		
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@ManyToOne(targetEntity="TypeDePokemon")
	@Column(nullable = false)
	private TypeDePokemon evolueEn;

	@ManyToOne(targetEntity="TypeDePokemon")
	@Column(nullable = false)
	private TypeDePokemon estEvolueEn;
	
}
