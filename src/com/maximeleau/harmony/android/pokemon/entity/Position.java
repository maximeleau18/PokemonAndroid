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

@Entity
public class Position  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


   	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@Column(type = Type.INTEGER)
	private int x;	

	@Column(type = Type.INTEGER)
	private int y;
	
	@ManyToOne(targetEntity="Zone")
	@Column(nullable = false)
	private Zone zone;


    /**
     * Default constructor.
     */
    public Position() {

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
     * Get the X.
     * @return the x
     */
    public int getX() {
         return this.x;
    }
     /**
     * Set the X.
     * @param value the x to set
     */
    public void setX(final int value) {
         this.x = value;
    }
     /**
     * Get the Y.
     * @return the y
     */
    public int getY() {
         return this.y;
    }
     /**
     * Set the Y.
     * @param value the y to set
     */
    public void setY(final int value) {
         this.y = value;
    }
     /**
     * Get the Zone.
     * @return the zone
     */
    public Zone getZone() {
         return this.zone;
    }
     /**
     * Set the Zone.
     * @param value the zone to set
     */
    public void setZone(final Zone value) {
         this.zone = value;
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
        dest.writeInt(this.getX());
        dest.writeInt(this.getY());
        if (this.getZone() != null
                    && !this.parcelableParents.contains(this.getZone())) {
            this.getZone().writeToParcel(this.parcelableParents, dest, flags);
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
        this.setX(parc.readInt());
        this.setY(parc.readInt());
        this.setZone((Zone) parc.readParcelable(Zone.class.getClassLoader()));
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public Position(Parcel parc) {
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
    public static final Parcelable.Creator<Position> CREATOR
        = new Parcelable.Creator<Position>() {
        public Position createFromParcel(Parcel in) {
            return new Position(in);
        }
        
        public Position[] newArray(int size) {
            return new Position[size];
        }
    };

}
