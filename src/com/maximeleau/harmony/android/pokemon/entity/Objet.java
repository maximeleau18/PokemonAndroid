package com.maximeleau.harmony.android.pokemon.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
import com.tactfactory.harmony.annotation.Column;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.Entity;
import com.tactfactory.harmony.annotation.GeneratedValue;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.annotation.Id;
import com.tactfactory.harmony.annotation.ManyToOne;

@Entity
public class Objet  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


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


    /**
     * Default constructor.
     */
    public Objet() {

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
     * Get the Quantite.
     * @return the quantite
     */
    public int getQuantite() {
         return this.quantite;
    }
     /**
     * Set the Quantite.
     * @param value the quantite to set
     */
    public void setQuantite(final int value) {
         this.quantite = value;
    }
     /**
     * Get the TypeObjet.
     * @return the typeObjet
     */
    public TypeObjet getTypeObjet() {
         return this.typeObjet;
    }
     /**
     * Set the TypeObjet.
     * @param value the typeObjet to set
     */
    public void setTypeObjet(final TypeObjet value) {
         this.typeObjet = value;
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
        if (this.getNom() != null) {
            dest.writeInt(1);
            dest.writeString(this.getNom());
        } else {
            dest.writeInt(0);
        }
        dest.writeInt(this.getQuantite());
        if (this.getTypeObjet() != null
                    && !this.parcelableParents.contains(this.getTypeObjet())) {
            this.getTypeObjet().writeToParcel(this.parcelableParents, dest, flags);
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
        int nomBool = parc.readInt();
        if (nomBool == 1) {
            this.setNom(parc.readString());
        }
        this.setQuantite(parc.readInt());
        this.setTypeObjet((TypeObjet) parc.readParcelable(TypeObjet.class.getClassLoader()));
        this.setPersonnageNonJoueur((PersonnageNonJoueur) parc.readParcelable(PersonnageNonJoueur.class.getClassLoader()));
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Objet(Parcel parc) {
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
    public static final Parcelable.Creator<Objet> CREATOR
        = new Parcelable.Creator<Objet>() {
        public Objet createFromParcel(Parcel in) {
            return new Objet(in);
        }
        
        public Objet[] newArray(int size) {
            return new Objet[size];
        }
    };

}

