package com.maximeleau.harmony.android.pokemon.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToOne;

@Entity
public class Arene {

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@ManyToOne(targetEntity="PersonnageNonJoueur", inversedBy="arenes")
	@Column(nullable = false)
	private PersonnageNonJoueur maitre;
	
	@OneToOne(targetEntity="Badge")
	@Column(nullable = false)
	private Badge badge;
	
	@OneToOne(targetEntity="Position")
	@Column(nullable = false)
	private Position position;
}

