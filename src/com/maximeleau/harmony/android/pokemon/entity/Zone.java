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
import com.tactfactory.harmony.annotation.OneToMany;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Rest
@Entity
public class Zone  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


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
	

    /**
     * Default constructor.
     */
    public Zone() {

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
     * Get the Badges.
     * @return the badges
     */
    public ArrayList<Badge> getBadges() {
         return this.badges;
    }
     /**
     * Set the Badges.
     * @param value the badges to set
     */
    public void setBadges(final ArrayList<Badge> value) {
         this.badges = value;
    }
     /**
     * Get the Positions.
     * @return the positions
     */
    public ArrayList<Position> getPositions() {
         return this.positions;
    }
     /**
     * Set the Positions.
     * @param value the positions to set
     */
    public void setPositions(final ArrayList<Position> value) {
         this.positions = value;
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

        if (this.getBadges() != null) {
            dest.writeInt(this.getBadges().size());
            for (Badge item : this.getBadges()) {
                if (!this.parcelableParents.contains(item)) {
                    item.writeToParcel(this.parcelableParents, dest, flags);
                } else {
                    dest.writeParcelable(null, flags);
                }
            }
        } else {
            dest.writeInt(-1);
        }

        if (this.getPositions() != null) {
            dest.writeInt(this.getPositions().size());
            for (Position item : this.getPositions()) {
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

        int nbBadges = parc.readInt();
        if (nbBadges > -1) {
            ArrayList<Badge> items =
                new ArrayList<Badge>();
            for (int i = 0; i < nbBadges; i++) {
                items.add((Badge) parc.readParcelable(
                        Badge.class.getClassLoader()));
            }
            this.setBadges(items);
        }

        int nbPositions = parc.readInt();
        if (nbPositions > -1) {
            ArrayList<Position> items =
                new ArrayList<Position>();
            for (int i = 0; i < nbPositions; i++) {
                items.add((Position) parc.readParcelable(
                        Position.class.getClassLoader()));
            }
            this.setPositions(items);
        }
    }


    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Zone(Parcel parc) {
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
    public static final Parcelable.Creator<Zone> CREATOR
        = new Parcelable.Creator<Zone>() {
        public Zone createFromParcel(Parcel in) {
            return new Zone(in);
        }
        
        public Zone[] newArray(int size) {
            return new Zone[size];
        }
    };

}
