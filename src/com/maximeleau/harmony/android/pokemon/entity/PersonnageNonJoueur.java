package com.maximeleau.harmony.android.pokemon.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;

import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.annotation.OneToOne;

@Entity
public class PersonnageNonJoueur  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;

	@Column(type = Type.STRING)
	private String description;
	
	@ManyToOne(targetEntity="Profession")
	@Column(nullable = false)
	private Profession profession;
	
	@OneToMany(targetEntity="Objet", mappedBy="personnageNonJoueur")
	@Column(nullable = true)
	private ArrayList<Objet> objets;
	
	@OneToOne(targetEntity="Dreseur", inversedBy="personnageNonJoueur")
	@Column(nullable = true)
	private Dresseur dresseur;
	
	@OneToMany(targetEntity="Arene", mappedBy="maitre")
	@Column(nullable = true)
	private ArrayList<Arene> arenes;

	@OneToMany(targetEntity="Pokemon", mappedBy="personnageNonJoueur")
	@Column(nullable = true)
	private ArrayList<Pokemon> pokemons;


    /**
     * Default constructor.
     */
    public PersonnageNonJoueur() {

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
     * Get the Nom.
     * @return the nom
     */
    public String getNom() {
         return this.nom;
    }
     /**
     * Set the Nom.
     * @param value the nom to set
     */
    public void setNom(final String value) {
         this.nom = value;
    }
     /**
     * Get the Description.
     * @return the description
     */
    public String getDescription() {
         return this.description;
    }
     /**
     * Set the Description.
     * @param value the description to set
     */
    public void setDescription(final String value) {
         this.description = value;
    }
     /**
     * Get the Profession.
     * @return the profession
     */
    public Profession getProfession() {
         return this.profession;
    }
     /**
     * Set the Profession.
     * @param value the profession to set
     */
    public void setProfession(final Profession value) {
         this.profession = value;
    }
     /**
     * Get the Objets.
     * @return the objets
     */
    public ArrayList<Objet> getObjets() {
         return this.objets;
    }
     /**
     * Set the Objets.
     * @param value the objets to set
     */
    public void setObjets(final ArrayList<Objet> value) {
         this.objets = value;
    }
     /**
     * Get the Dresseur.
     * @return the dresseur
     */
    public Dresseur getDresseur() {
         return this.dresseur;
    }
     /**
     * Set the Dresseur.
     * @param value the dresseur to set
     */
    public void setDresseur(final Dresseur value) {
         this.dresseur = value;
    }
     /**
     * Get the Arenes.
     * @return the arenes
     */
    public ArrayList<Arene> getArenes() {
         return this.arenes;
    }
     /**
     * Set the Arenes.
     * @param value the arenes to set
     */
    public void setArenes(final ArrayList<Arene> value) {
         this.arenes = value;
    }
     /**
     * Get the Pokemons.
     * @return the pokemons
     */
    public ArrayList<Pokemon> getPokemons() {
         return this.pokemons;
    }
     /**
     * Set the Pokemons.
     * @param value the pokemons to set
     */
    public void setPokemons(final ArrayList<Pokemon> value) {
         this.pokemons = value;
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
        if (this.getNom() != null) {
            dest.writeInt(1);
            dest.writeString(this.getNom());
        } else {
            dest.writeInt(0);
        }
        if (this.getDescription() != null) {
            dest.writeInt(1);
            dest.writeString(this.getDescription());
        } else {
            dest.writeInt(0);
        }
        if (this.getProfession() != null
                    && !this.parcelableParents.contains(this.getProfession())) {
            this.getProfession().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }

        if (this.getObjets() != null) {
            dest.writeInt(this.getObjets().size());
            for (Objet item : this.getObjets()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }
        if (this.getDresseur() != null
                    && !this.parcelableParents.contains(this.getDresseur())) {
            this.getDresseur().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }

        if (this.getArenes() != null) {
            dest.writeInt(this.getArenes().size());
            for (Arene item : this.getArenes()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getPokemons() != null) {
            dest.writeInt(this.getPokemons().size());
            for (Pokemon item : this.getPokemons()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
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
        int nomBool = parc.readInt();
        if (nomBool == 1) {
            this.setNom(parc.readString());
        }
        int descriptionBool = parc.readInt();
        if (descriptionBool == 1) {
            this.setDescription(parc.readString());
        }
        this.setProfession((Profession) parc.readParcelable(Profession.class.getClassLoader()));

        int nbObjets = parc.readInt();
        if (nbObjets > -1) {
            ArrayList<Objet> items =
                new ArrayList<Objet>();
            for (int i = 0; i < nbObjets; i++) {
                items.add((Objet) parc.readParcelable(
                        Objet.class.getClassLoader()));
            }
            this.setObjets(items);
        }
        this.setDresseur((Dresseur) parc.readParcelable(Dresseur.class.getClassLoader()));

        int nbArenes = parc.readInt();
        if (nbArenes > -1) {
            ArrayList<Arene> items =
                new ArrayList<Arene>();
            for (int i = 0; i < nbArenes; i++) {
                items.add((Arene) parc.readParcelable(
                        Arene.class.getClassLoader()));
            }
            this.setArenes(items);
        }

        int nbPokemons = parc.readInt();
        if (nbPokemons > -1) {
            ArrayList<Pokemon> items =
                new ArrayList<Pokemon>();
            for (int i = 0; i < nbPokemons; i++) {
                items.add((Pokemon) parc.readParcelable(
                        Pokemon.class.getClassLoader()));
            }
            this.setPokemons(items);
        }
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public PersonnageNonJoueur(Parcel parc) {
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
    public static final Parcelable.Creator<PersonnageNonJoueur> CREATOR
        = new Parcelable.Creator<PersonnageNonJoueur>() {
        public PersonnageNonJoueur createFromParcel(Parcel in) {
            return new PersonnageNonJoueur(in);
        }
        
        public PersonnageNonJoueur[] newArray(int size) {
            return new PersonnageNonJoueur[size];
        }
    };

}

