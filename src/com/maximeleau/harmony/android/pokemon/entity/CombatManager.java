package com.maximeleau.harmony.android.pokemon.entity;

import java.io.Serializable;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class CombatManager implements Serializable {
    private Combat combat;

    private Attaque attaque;

    private Dresseur dresseur;

    private int actualPv;

    private int dresseurActualTurnId;

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

    public Dresseur getDresseur() {
        return dresseur;
    }

    public void setDresseur(Dresseur dresseur) {
        this.dresseur = dresseur;
    }

    public int getActualPv() {
        return actualPv;
    }

    public void setActualPv(int actualPv) {
        this.actualPv = actualPv;
    }

    public int getDresseurActualTurnId() {
        return dresseurActualTurnId;
    }

    public void setDresseurActualTurnId(int dresseurActualTurnId) {
        this.dresseurActualTurnId = dresseurActualTurnId;
    }
}
