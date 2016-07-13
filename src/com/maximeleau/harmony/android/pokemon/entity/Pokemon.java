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
public class Pokemon  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


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
	

    /**
     * Default constructor.
     */
    public Pokemon() {

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
     * Get the Surnom.
     * @return the surnom
     */
    public String getSurnom() {
         return this.surnom;
    }
     /**
     * Set the Surnom.
     * @param value the surnom to set
     */
    public void setSurnom(final String value) {
         this.surnom = value;
    }
     /**
     * Get the Niveau.
     * @return the niveau
     */
    public int getNiveau() {
         return this.niveau;
    }
     /**
     * Set the Niveau.
     * @param value the niveau to set
     */
    public void setNiveau(final int value) {
         this.niveau = value;
    }
     /**
     * Get the CaptureLe.
     * @return the captureLe
     */
    public DateTime getCaptureLe() {
         return this.captureLe;
    }
     /**
     * Set the CaptureLe.
     * @param value the captureLe to set
     */
    public void setCaptureLe(final DateTime value) {
         this.captureLe = value;
    }
     /**
     * Get the Attaque1.
     * @return the attaque1
     */
    public Attaque getAttaque1() {
         return this.attaque1;
    }
     /**
     * Set the Attaque1.
     * @param value the attaque1 to set
     */
    public void setAttaque1(final Attaque value) {
         this.attaque1 = value;
    }
     /**
     * Get the Attaque2.
     * @return the attaque2
     */
    public Attaque getAttaque2() {
         return this.attaque2;
    }
     /**
     * Set the Attaque2.
     * @param value the attaque2 to set
     */
    public void setAttaque2(final Attaque value) {
         this.attaque2 = value;
    }
     /**
     * Get the Attaque3.
     * @return the attaque3
     */
    public Attaque getAttaque3() {
         return this.attaque3;
    }
     /**
     * Set the Attaque3.
     * @param value the attaque3 to set
     */
    public void setAttaque3(final Attaque value) {
         this.attaque3 = value;
    }
     /**
     * Get the Attaque4.
     * @return the attaque4
     */
    public Attaque getAttaque4() {
         return this.attaque4;
    }
     /**
     * Set the Attaque4.
     * @param value the attaque4 to set
     */
    public void setAttaque4(final Attaque value) {
         this.attaque4 = value;
    }
     /**
     * Get the TypeDePokemon.
     * @return the typeDePokemon
     */
    public TypeDePokemon getTypeDePokemon() {
         return this.typeDePokemon;
    }
     /**
     * Set the TypeDePokemon.
     * @param value the typeDePokemon to set
     */
    public void setTypeDePokemon(final TypeDePokemon value) {
         this.typeDePokemon = value;
    }
     /**
     * Get the PersonnageNonJoueur.
     * @return the personnageNonJoueur
     */
    public PersonnageNonJoueur getPersonnageNonJoueur() {
         return this.personnageNonJoueur;
    }
     /**
     * Set the PersonnageNonJoueur.
     * @param value the personnageNonJoueur to set
     */
    public void setPersonnageNonJoueur(final PersonnageNonJoueur value) {
         this.personnageNonJoueur = value;
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
        if (this.getSurnom() != null) {
            dest.writeInt(1);
            dest.writeString(this.getSurnom());
        } else {
            dest.writeInt(0);
        }
        dest.writeInt(this.getNiveau());
        if (this.getCaptureLe() != null) {
            dest.writeInt(1);
            dest.writeString(ISODateTimeFormat.dateTime().print(
                    this.getCaptureLe()));
        } else {
            dest.writeInt(0);
        }
        if (this.getAttaque1() != null
                    && !this.parcelableParents.contains(this.getAttaque1())) {
            this.getAttaque1().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getAttaque2() != null
                    && !this.parcelableParents.contains(this.getAttaque2())) {
            this.getAttaque2().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getAttaque3() != null
                    && !this.parcelableParents.contains(this.getAttaque3())) {
            this.getAttaque3().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getAttaque4() != null
                    && !this.parcelableParents.contains(this.getAttaque4())) {
            this.getAttaque4().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getTypeDePokemon() != null
                    && !this.parcelableParents.contains(this.getTypeDePokemon())) {
            this.getTypeDePokemon().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getPersonnageNonJoueur() != null
                    && !this.parcelableParents.contains(this.getPersonnageNonJoueur())) {
            this.getPersonnageNonJoueur().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
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
        int surnomBool = parc.readInt();
        if (surnomBool == 1) {
            this.setSurnom(parc.readString());
        }
        this.setNiveau(parc.readInt());
        if (parc.readInt() == 1) {
            this.setCaptureLe(
                    ISODateTimeFormat.dateTimeParser()
                            .withOffsetParsed().parseDateTime(
                                    parc.readString()));
        }
        this.setAttaque1((Attaque) parc.readParcelable(Attaque.class.getClassLoader()));
        this.setAttaque2((Attaque) parc.readParcelable(Attaque.class.getClassLoader()));
        this.setAttaque3((Attaque) parc.readParcelable(Attaque.class.getClassLoader()));
        this.setAttaque4((Attaque) parc.readParcelable(Attaque.class.getClassLoader()));
        this.setTypeDePokemon((TypeDePokemon) parc.readParcelable(TypeDePokemon.class.getClassLoader()));
        this.setPersonnageNonJoueur((PersonnageNonJoueur) parc.readParcelable(PersonnageNonJoueur.class.getClassLoader()));
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Pokemon(Parcel parc) {
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
    public static final Parcelable.Creator<Pokemon> CREATOR
        = new Parcelable.Creator<Pokemon>() {
        public Pokemon createFromParcel(Parcel in) {
            return new Pokemon(in);
        }
        
        public Pokemon[] newArray(int size) {
            return new Pokemon[size];
        }
    };

}
