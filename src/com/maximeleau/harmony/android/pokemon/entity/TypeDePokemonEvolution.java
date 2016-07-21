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
import com.tactfactory.harmony.annotation.ManyToOne;
import com.tactfactory.harmony.annotation.Column.Type;
import com.tactfactory.harmony.annotation.GeneratedValue.Strategy;
import com.tactfactory.harmony.bundles.rest.annotation.Rest;

@Rest
@Entity
public class TypeDePokemonEvolution  implements Serializable , Parcelable {

    /** Parent parcelable for parcellisation purposes. */
    protected List<Parcelable> parcelableParents;


	@Id
    @Column(type = Type.INTEGER, hidden = true)
    @GeneratedValue(strategy = Strategy.MODE_IDENTITY)
	private int id;
	
	@ManyToOne(targetEntity="TypeDePokemon")
	@Column(nullable = false)
	private TypeDePokemon evolueEn;

	@ManyToOne(targetEntity="TypeDePokemon")
	@Column(nullable = false)
	private TypeDePokemon estEvolueEn;
	

    /**
     * Default constructor.
     */
    public TypeDePokemonEvolution() {

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
     * Get the EvolueEn.
     * @return the evolueEn
     */
    public TypeDePokemon getEvolueEn() {
         return this.evolueEn;
    }
     /**
     * Set the EvolueEn.
     * @param value the evolueEn to set
     */
    public void setEvolueEn(final TypeDePokemon value) {
         this.evolueEn = value;
    }
     /**
     * Get the EstEvolueEn.
     * @return the estEvolueEn
     */
    public TypeDePokemon getEstEvolueEn() {
         return this.estEvolueEn;
    }
     /**
     * Set the EstEvolueEn.
     * @param value the estEvolueEn to set
     */
    public void setEstEvolueEn(final TypeDePokemon value) {
         this.estEvolueEn = value;
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
        if (this.getEvolueEn() != null
                    && !this.parcelableParents.contains(this.getEvolueEn())) {
            this.getEvolueEn().writeToParcel(this.parcelableParents, dest, flags);
        } else {
            dest.writeParcelable(null, flags);
        }
        if (this.getEstEvolueEn() != null
                    && !this.parcelableParents.contains(this.getEstEvolueEn())) {
            this.getEstEvolueEn().writeToParcel(this.parcelableParents, dest, flags);
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
        this.setEvolueEn((TypeDePokemon) parc.readParcelable(TypeDePokemon.class.getClassLoader()));
        this.setEstEvolueEn((TypeDePokemon) parc.readParcelable(TypeDePokemon.class.getClassLoader()));
    }

    /**
     * Parcel Constructor.
     *
     * @param parc The parcel to read from
     */
    public TypeDePokemonEvolution(Parcel parc) {
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
    public static final Parcelable.Creator<TypeDePokemonEvolution> CREATOR
        = new Parcelable.Creator<TypeDePokemonEvolution>() {
        public TypeDePokemonEvolution createFromParcel(Parcel in) {
            return new TypeDePokemonEvolution(in);
        }
        
        public TypeDePokemonEvolution[] newArray(int size) {
            return new TypeDePokemonEvolution[size];
        }
    };

}
