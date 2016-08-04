package com.maximeleau.harmony.android.pokemon.entity;

import java.io.Serializable;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class CombatManager implements Serializable {
    private Combat combat;

    private Attaque attaque;

    private Pokemon pokemon;

    private int actual_pv;

    private int pokemon_actual_turn_id;

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

    public int getActual_pv() {
        return actual_pv;
    }

    public void setActual_pv(int actual_pv) {
        this.actual_pv = actual_pv;
    }

    public int getPokemon_actual_turn_id() {
        return pokemon_actual_turn_id;
    }

    public void setPokemon_actual_turn_id(int pokemon_actual_turn_id) {
        this.pokemon_actual_turn_id = pokemon_actual_turn_id;
    }
}
