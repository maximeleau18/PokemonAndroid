package com.maximeleau.harmony.android.pokemon.entity;

import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.OneToOne;

@Entity
public class PersonnageNonJoueur {

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;

	@Column(type = Type.STRING)
	private String description;
	
	@ManyToOne(targetEntity="Profession")
	@Column(nullable = false)
	private Profession profession;
	
	@OneToMany(targetEntity="Objet", mappedBy="personnageNonJoueur")
	@Column(nullable = true)
	private ArrayList<Objet> objets;
	
	@OneToOne(targetEntity="Dreseur", inversedBy="personnageNonJoueur")
	@Column(nullable = true)
	private Dresseur dresseur;
	
	@OneToMany(targetEntity="Arene", mappedBy="maitre")
	@Column(nullable = true)
	private ArrayList<Arene> arenes;

	@OneToMany(targetEntity="Pokemon", mappedBy="personnageNonJoueur")
	@Column(nullable = true)
	private ArrayList<Pokemon> pokemons;

}

