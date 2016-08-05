package com.maximeleau.harmony.android.pokemon.entity;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;
import java.util.ArrayList;
import java.io.Serializable;
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
public class Dresseur  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@Column(type = Type.STRING)
	private String prenom;
	
	@Column(type = Type.STRING)
	private String login;
	
	@Column(type = Type.STRING)
	private String motDePasse;
	
	@ManyToOne(targetEntity = "PersonnageNonJoueur", inversedBy="dresseurs")
	@Column(nullable = false)
	private PersonnageNonJoueur personnageNonJoueur;
	

    /**
     * Default constructor.
     */
    public Dresseur() {

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
     * Get the Prenom.
     * @return the prenom
     */
    public String getPrenom() {
         return this.prenom;
    }
     /**
     * Set the Prenom.
     * @param value the prenom to set
     */
    public void setPrenom(final String value) {
         this.prenom = value;
    }
     /**
     * Get the Login.
     * @return the login
     */
    public String getLogin() {
         return this.login;
    }
     /**
     * Set the Login.
     * @param value the login to set
     */
    public void setLogin(final String value) {
         this.login = value;
    }
     /**
     * Get the MotDePasse.
     * @return the motDePasse
     */
    public String getMotDePasse() {
         return this.motDePasse;
    }
     /**
     * Set the MotDePasse.
     * @param value the motDePasse to set
     */
    public void setMotDePasse(final String value) {
         this.motDePasse = value;
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
        if (this.getPrenom() != null) {
            dest.writeInt(1);
            dest.writeString(this.getPrenom());
        } else {
            dest.writeInt(0);
        }
        if (this.getLogin() != null) {
            dest.writeInt(1);
            dest.writeString(this.getLogin());
        } else {
            dest.writeInt(0);
        }
        if (this.getMotDePasse() != null) {
            dest.writeInt(1);
            dest.writeString(this.getMotDePasse());
        } else {
            dest.writeInt(0);
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
        int prenomBool = parc.readInt();
        if (prenomBool == 1) {
            this.setPrenom(parc.readString());
        }
        int loginBool = parc.readInt();
        if (loginBool == 1) {
            this.setLogin(parc.readString());
        }
        int motDePasseBool = parc.readInt();
        if (motDePasseBool == 1) {
            this.setMotDePasse(parc.readString());
        }
        this.setPersonnageNonJoueur((PersonnageNonJoueur) parc.readParcelable(PersonnageNonJoueur.class.getClassLoader()));
    }


    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Dresseur(Parcel parc) {
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
    public static final Parcelable.Creator<Dresseur> CREATOR
        = new Parcelable.Creator<Dresseur>() {
        public Dresseur createFromParcel(Parcel in) {
            return new Dresseur(in);
        }
        
        public Dresseur[] newArray(int size) {
            return new Dresseur[size];
        }
    };

}
