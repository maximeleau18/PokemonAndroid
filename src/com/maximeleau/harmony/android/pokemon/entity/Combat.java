package com.maximeleau.harmony.android.pokemon.entity;

import org.joda.time.format.ISODateTimeFormat;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;

import org.joda.time.DateTime;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Rest
@Entity
public class Combat  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;

	
	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(nullable = false)
	private DateTime lanceLe;
	
	@Column(nullable = true)
	private int duree;
	
	@ManyToOne(targetEntity="Pokemon")
	@Column(nullable = false)
	private Pokemon pokemon1;

	@ManyToOne(targetEntity="Pokemon")
	@Column(nullable = false)
	private Pokemon pokemon2;
	
	@ManyToOne(targetEntity="Dresseur")
	@Column(nullable = false)
	private Dresseur dresseur1;

	@ManyToOne(targetEntity="Dresseur")
	@Column(nullable = false)
	private Dresseur dresseur2;

	@Column(nullable = false)
	private boolean dresseur1Vainqueur;
	
	@Column(nullable = false)
	private boolean dresseur2Vainqueur;

	@Column(nullable = false)
	private boolean pokemon1Vainqueur;
	
	@Column(nullable = false)
	private boolean pokemon2Vainqueur;


    /**
     * Default constructor.
     */
    public Combat() {

    }

     /**
     * Get the Id.
     * @return the id
     */
    public int getId() {
         return this.id;
    }
     /**
     * Set the Id.
     * @param value the id to set
     */
    public void setId(final int value) {
         this.id = value;
    }
     /**
     * Get the LanceLe.
     * @return the lanceLe
     */
    public DateTime getLanceLe() {
         return this.lanceLe;
    }
     /**
     * Set the LanceLe.
     * @param value the lanceLe to set
     */
    public void setLanceLe(final DateTime value) {
         this.lanceLe = value;
    }
     /**
     * Get the Duree.
     * @return the duree
     */
    public int getDuree() {
         return this.duree;
    }
     /**
     * Set the Duree.
     * @param value the duree to set
     */
    public void setDuree(final int value) {
         this.duree = value;
    }
     /**
     * Get the Pokemon1.
     * @return the pokemon1
     */
    public Pokemon getPokemon1() {
         return this.pokemon1;
    }
     /**
     * Set the Pokemon1.
     * @param value the pokemon1 to set
     */
    public void setPokemon1(final Pokemon value) {
         this.pokemon1 = value;
    }
     /**
     * Get the Pokemon2.
     * @return the pokemon2
     */
    public Pokemon getPokemon2() {
         return this.pokemon2;
    }
     /**
     * Set the Pokemon2.
     * @param value the pokemon2 to set
     */
    public void setPokemon2(final Pokemon value) {
         this.pokemon2 = value;
    }
     /**
     * Get the Dresseur1.
     * @return the dresseur1
     */
    public Dresseur getDresseur1() {
         return this.dresseur1;
    }
     /**
     * Set the Dresseur1.
     * @param value the dresseur1 to set
     */
    public void setDresseur1(final Dresseur value) {
         this.dresseur1 = value;
    }
     /**
     * Get the Dresseur2.
     * @return the dresseur2
     */
    public Dresseur getDresseur2() {
         return this.dresseur2;
    }
     /**
     * Set the Dresseur2.
     * @param value the dresseur2 to set
     */
    public void setDresseur2(final Dresseur value) {
         this.dresseur2 = value;
    }
     /**
     * Get the Dresseur1Vainqueur.
     * @return the dresseur1Vainqueur
     */
    public boolean isDresseur1Vainqueur() {
         return this.dresseur1Vainqueur;
    }
     /**
     * Set the Dresseur1Vainqueur.
     * @param value the dresseur1Vainqueur to set
     */
    public void setDresseur1Vainqueur(final boolean value) {
         this.dresseur1Vainqueur = value;
    }
     /**
     * Get the Dresseur2Vainqueur.
     * @return the dresseur2Vainqueur
     */
    public boolean isDresseur2Vainqueur() {
         return this.dresseur2Vainqueur;
    }
     /**
     * Set the Dresseur2Vainqueur.
     * @param value the dresseur2Vainqueur to set
     */
    public void setDresseur2Vainqueur(final boolean value) {
         this.dresseur2Vainqueur = value;
    }
     /**
     * Get the Pokemon1Vainqueur.
     * @return the pokemon1Vainqueur
     */
    public boolean isPokemon1Vainqueur() {
         return this.pokemon1Vainqueur;
    }
     /**
     * Set the Pokemon1Vainqueur.
     * @param value the pokemon1Vainqueur to set
     */
    public void setPokemon1Vainqueur(final boolean value) {
         this.pokemon1Vainqueur = value;
    }
     /**
     * Get the Pokemon2Vainqueur.
     * @return the pokemon2Vainqueur
     */
    public boolean isPokemon2Vainqueur() {
         return this.pokemon2Vainqueur;
    }
     /**
     * Set the Pokemon2Vainqueur.
     * @param value the pokemon2Vainqueur to set
     */
    public void setPokemon2Vainqueur(final boolean value) {
         this.pokemon2Vainqueur = value;
    }
    /**
     * This stub of code is regenerated. DO NOT MODIFY.
     * 
     * @param dest Destination parcel
     * @param flags flags
     */
    public void writeToParcelRegen(Parcel dest, int flags) {
        if (this.parcelableParents == null) {
            this.parcelableParents = new ArrayList<Parcelable>();
        }
        if (!this.parcelableParents.contains(this)) {
            this.parcelableParents.add(this);
        }
        dest.writeInt(this.getId());
        if (this.getLanceLe() != null) {
            dest.writeInt(1);
            dest.writeString(ISODateTimeFormat.dateTime().print(
                    this.getLanceLe()));
        } else {
            dest.writeInt(0);
        }
        dest.writeInt(this.getDuree());
        if (this.getPokemon1() != null
                    && !this.parcelableParents.contains(this.getPokemon1())) {
            this.getPokemon1().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getPokemon2() != null
                    && !this.parcelableParents.contains(this.getPokemon2())) {
            this.getPokemon2().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getDresseur1() != null
                    && !this.parcelableParents.contains(this.getDresseur1())) {
            this.getDresseur1().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getDresseur2() != null
                    && !this.parcelableParents.contains(this.getDresseur2())) {
            this.getDresseur2().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.isDresseur1Vainqueur()) {
            dest.writeInt(1);
        } else {
            dest.writeInt(0);
        }
        if (this.isDresseur2Vainqueur()) {
            dest.writeInt(1);
        } else {
            dest.writeInt(0);
        }
        if (this.isPokemon1Vainqueur()) {
            dest.writeInt(1);
        } else {
            dest.writeInt(0);
        }
        if (this.isPokemon2Vainqueur()) {
            dest.writeInt(1);
        } else {
            dest.writeInt(0);
        }
    }

    /**
     * Regenerated Parcel Constructor. 
     *
     * This stub of code is regenerated. DO NOT MODIFY THIS METHOD.
     *
     * @param parc The parcel to read from
     */
    public void readFromParcel(Parcel parc) {
        this.setId(parc.readInt());
        if (parc.readInt() == 1) {
            this.setLanceLe(
                    ISODateTimeFormat.dateTimeParser()
                            .withOffsetParsed().parseDateTime(
                                    parc.readString()));
        }
        this.setDuree(parc.readInt());
        this.setPokemon1((Pokemon) parc.readParcelable(Pokemon.class.getClassLoader()));
        this.setPokemon2((Pokemon) parc.readParcelable(Pokemon.class.getClassLoader()));
        this.setDresseur1((Dresseur) parc.readParcelable(Dresseur.class.getClassLoader()));
        this.setDresseur2((Dresseur) parc.readParcelable(Dresseur.class.getClassLoader()));
        this.setDresseur1Vainqueur(parc.readInt() == 1);
        this.setDresseur2Vainqueur(parc.readInt() == 1);
        this.setPokemon1Vainqueur(parc.readInt() == 1);
        this.setPokemon2Vainqueur(parc.readInt() == 1);
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Combat(Parcel parc) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.readFromParcel(parc);

        // You can  implement your own parcel mechanics here.

    }

    /* This method is not regenerated. You can implement your own parcel mechanics here. */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // You can chose not to use harmony's generated parcel.
        // To do this, remove this line.
        this.writeToParcelRegen(dest, flags);
        // You can  implement your own parcel mechanics here.
    }

    /**
     * Use this method to write this entity to a parcel from another entity.
     * (Useful for relations)
     *
     * @param parent The entity being parcelled that need to parcel this one
     * @param dest The destination parcel
     * @param flags The flags
     */
    public synchronized void writeToParcel(List<Parcelable> parents, Parcel dest, int flags) {
        this.parcelableParents = new ArrayList<Parcelable>(parents);
        dest.writeParcelable(this, flags);
        this.parcelableParents = null;
    }

    @Override
    public int describeContents() {
        // This should return 0 
        // or CONTENTS_FILE_DESCRIPTOR if your entity is a FileDescriptor.
        return 0;
    }

    /**
     * Parcelable creator.
     */
    public static final Parcelable.Creator<Combat> CREATOR
        = new Parcelable.Creator<Combat>() {
        public Combat createFromParcel(Parcel in) {
            return new Combat(in);
        }
        
        public Combat[] newArray(int size) {
            return new Combat[size];
        }
    };

}
