package com.maximeleau.harmony.android.pokemon.entity;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;

@Entity
public class PersonnageNonJoueurBadge {

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@ManyToOne(targetEntity="PersonnageNonJoueur")
	@Column(nullable = false)
	private PersonnageNonJoueur personnageNonJoueur;
	
	@ManyToOne(targetEntity="Badge")
	@Column(nullable = false)
	private Badge badge;

}
