package com.maximeleau.harmony.android.pokemon.entity;

import java.io.Serializable;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class CombatManager implements Serializable {
    private Combat combat;

    private Attaque attaque;

    private Pokemon pokemon;

    private int actualPv;

    private int pokemonActualTurnId;

    /**
     * Default constructor.
     */
    public CombatManager() {

    }

    public Combat getCombat() {
        return combat;
    }

    public void setCombat(Combat combat) {
        this.combat = combat;
    }

    public Attaque getAttaque() {
        return attaque;
    }

    public void setAttaque(Attaque attaque) {
        this.attaque = attaque;
    }

    public Pokemon getPokemon() {
        return pokemon;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    public int getActualPv() {
        return actualPv;
    }

    public void setActualPv(int actualPv) {
        this.actualPv = actualPv;
    }

    public int getPokemonActualTurnId() {
        return pokemonActualTurnId;
    }

    public void setPokemonActualTurnId(int pokemonActualTurnId) {
        this.pokemonActualTurnId = pokemonActualTurnId;
    }

}
