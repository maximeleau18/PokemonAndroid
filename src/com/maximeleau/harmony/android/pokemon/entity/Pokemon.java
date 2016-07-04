package com.maximeleau.harmony.android.pokemon.entity;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.ManyToOne;

@Entity
public class Pokemon {

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String surnom;

	@Column(type = Type.INTEGER)
	private int niveau;
	
	@Column(type = Type.DATETIME, nullable = false)
	private DateTime captureLe;
	
	@ManyToOne(targetEntity="Attaque")
	@Column(nullable = true)
	private Attaque attaque1;

	@ManyToOne(targetEntity="Attaque")
	@Column(nullable = true)
	private Attaque attaque2;

	@ManyToOne(targetEntity="Attaque")
	@Column(nullable = true)
	private Attaque attaque3;

	@ManyToOne(targetEntity="Attaque")
	@Column(nullable = true)
	private Attaque attaque4;
	
	@ManyToOne(targetEntity="TypeDePokemon", inversedBy="pokemons")
	@Column(nullable = false)
	private TypeDePokemon typeDePokemon;
	
	@ManyToOne(targetEntity="PersonnageNonJoueur", inversedBy="pokemons")
	@Column(nullable=true)
	private PersonnageNonJoueur personnageNonJoueur;
	
}
