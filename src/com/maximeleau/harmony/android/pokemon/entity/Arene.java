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
import com.tactfactory.harmony.annotation.OneToOne;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Rest
@Entity
public class Arene  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.STRING)
	private String nom;
	
	@ManyToOne(targetEntity="PersonnageNonJoueur", inversedBy="arenes")
	@Column(nullable = false)
	private PersonnageNonJoueur maitre;
	
	@OneToOne(targetEntity="Badge")
	@Column(nullable = false)
	private Badge badge;
	
	@OneToOne(targetEntity="Position")
	@Column(nullable = false)
	private Position position;

    /**
     * Default constructor.
     */
    public Arene() {

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
     * Get the Maitre.
     * @return the maitre
     */
    public PersonnageNonJoueur getMaitre() {
         return this.maitre;
    }
     /**
     * Set the Maitre.
     * @param value the maitre to set
     */
    public void setMaitre(final PersonnageNonJoueur value) {
         this.maitre = value;
    }
     /**
     * Get the Badge.
     * @return the badge
     */
    public Badge getBadge() {
         return this.badge;
    }
     /**
     * Set the Badge.
     * @param value the badge to set
     */
    public void setBadge(final Badge value) {
         this.badge = value;
    }
     /**
     * Get the Position.
     * @return the position
     */
    public Position getPosition() {
         return this.position;
    }
     /**
     * Set the Position.
     * @param value the position to set
     */
    public void setPosition(final Position value) {
         this.position = value;
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
        if (this.getMaitre() != null
                    && !this.parcelableParents.contains(this.getMaitre())) {
            this.getMaitre().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getBadge() != null
                    && !this.parcelableParents.contains(this.getBadge())) {
            this.getBadge().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getPosition() != null
                    && !this.parcelableParents.contains(this.getPosition())) {
            this.getPosition().writeToParcel(this.parcelableParents, dest, flags);
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
        this.setMaitre((PersonnageNonJoueur) parc.readParcelable(PersonnageNonJoueur.class.getClassLoader()));
        this.setBadge((Badge) parc.readParcelable(Badge.class.getClassLoader()));
        this.setPosition((Position) parc.readParcelable(Position.class.getClassLoader()));
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Arene(Parcel parc) {
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
    public static final Parcelable.Creator<Arene> CREATOR
        = new Parcelable.Creator<Arene>() {
        public Arene createFromParcel(Parcel in) {
            return new Arene(in);
        }
        
        public Arene[] newArray(int size) {
            return new Arene[size];
        }
    };

}

