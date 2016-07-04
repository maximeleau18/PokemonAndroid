package com.maximeleau.harmony.android.pokemon.entity;

import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.OneToMany;

@Entity
public class TypeDePokemon {

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.INTEGER)
	private int attaque;
	
	@Column(type = Type.INTEGER)
	private int defense;
	
	@Column(type = Type.INTEGER)
	private int pv;
	
	@Column(type = Type.INTEGER)
	private int numPokedex;
	
	@OneToMany(targetEntity="Pokemon", mappedBy="typeDePokemon")
	@Column(nullable = true)
	private ArrayList<Pokemon> pokemons;
	
}
