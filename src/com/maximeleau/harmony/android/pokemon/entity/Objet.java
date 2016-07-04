package com.maximeleau.harmony.android.pokemon.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;

@Entity
public class Objet {

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.INTEGER)
	private int quantite;
	
	@ManyToOne(targetEntity="TypeObjet")
	@Column(nullable = false)
	private TypeObjet typeObjet;
	
	@ManyToOne(targetEntity="PersonnageNonJoueur", inversedBy="objets")
	@Column(nullable = true)
	private PersonnageNonJoueur personnageNonJoueur;    

}

