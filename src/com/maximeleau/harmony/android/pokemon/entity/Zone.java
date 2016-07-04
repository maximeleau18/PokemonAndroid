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
public class Zone {

	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@OneToMany(targetEntity="Arene")
	@Column(nullable = true)
	private ArrayList<Arene> arenes;
	
	@OneToMany(targetEntity="Badge")
	@Column(nullable = true)
	private ArrayList<Badge> badges;
	
	@OneToMany(targetEntity="Position")
	@Column(nullable = true)
	private ArrayList<Position> positions;
	
}
