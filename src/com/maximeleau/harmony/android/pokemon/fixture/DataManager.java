/**************************************************************************
 * DataManager.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 10, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.fixture;


import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import com.maximeleau.harmony.android.pokemon.data.base.SQLiteAdapterBase;
import com.maximeleau.harmony.android.pokemon.data.ObjetSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.data.AreneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.data.PositionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.data.ZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Zone;
import com.maximeleau.harmony.android.pokemon.data.TypeAttaqueSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.TypeAttaque;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.data.BadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonEvolutionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;
import com.maximeleau.harmony.android.pokemon.data.DresseurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.data.TypeObjetSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.TypeObjet;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurBadgeSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;
import com.maximeleau.harmony.android.pokemon.data.ProfessionSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Profession;
import com.maximeleau.harmony.android.pokemon.data.AttaqueSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.data.TypeDePokemonZoneSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonZone;
import com.maximeleau.harmony.android.pokemon.data.PersonnageNonJoueurSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.data.PokemonSQLiteAdapter;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;

/**
 * DataManager.
 * 
 * This class is an "orm-like" manager which simplifies insertion in database
 * with sqlite adapters.
 */
public class DataManager {
    /** HashMap to join Entity Name and its SQLiteAdapterBase. */
    protected Map<String, SQLiteAdapterBase<?>> adapters =
            new HashMap<String, SQLiteAdapterBase<?>>();
    /** is successfull. */
    protected boolean isSuccessfull = true;
    /** is in internal transaction. */
    protected boolean isInInternalTransaction = false;
    /** database. */
    protected SQLiteDatabase db;
    /** Objet name constant. */
    private static final String OBJET = "Objet";
    /** Arene name constant. */
    private static final String ARENE = "Arene";
    /** Position name constant. */
    private static final String POSITION = "Position";
    /** Zone name constant. */
    private static final String ZONE = "Zone";
    /** TypeAttaque name constant. */
    private static final String TYPEATTAQUE = "TypeAttaque";
    /** TypeDePokemon name constant. */
    private static final String TYPEDEPOKEMON = "TypeDePokemon";
    /** Badge name constant. */
    private static final String BADGE = "Badge";
    /** TypeDePokemonEvolution name constant. */
    private static final String TYPEDEPOKEMONEVOLUTION = "TypeDePokemonEvolution";
    /** Dresseur name constant. */
    private static final String DRESSEUR = "Dresseur";
    /** TypeObjet name constant. */
    private static final String TYPEOBJET = "TypeObjet";
    /** PersonnageNonJoueurBadge name constant. */
    private static final String PERSONNAGENONJOUEURBADGE = "PersonnageNonJoueurBadge";
    /** Profession name constant. */
    private static final String PROFESSION = "Profession";
    /** Attaque name constant. */
    private static final String ATTAQUE = "Attaque";
    /** TypeDePokemonZone name constant. */
    private static final String TYPEDEPOKEMONZONE = "TypeDePokemonZone";
    /** PersonnageNonJoueur name constant. */
    private static final String PERSONNAGENONJOUEUR = "PersonnageNonJoueur";
    /** Pokemon name constant. */
    private static final String POKEMON = "Pokemon";
    /**
     * Constructor.
     * @param ctx The context
     * @param db The DB to work in
     */
    public DataManager(final android.content.Context ctx, final SQLiteDatabase db) {
        this.db = db;
        this.adapters.put(OBJET,
                new ObjetSQLiteAdapter(ctx));
        this.adapters.get(OBJET).open(this.db);
        this.adapters.put(ARENE,
                new AreneSQLiteAdapter(ctx));
        this.adapters.get(ARENE).open(this.db);
        this.adapters.put(POSITION,
                new PositionSQLiteAdapter(ctx));
        this.adapters.get(POSITION).open(this.db);
        this.adapters.put(ZONE,
                new ZoneSQLiteAdapter(ctx));
        this.adapters.get(ZONE).open(this.db);
        this.adapters.put(TYPEATTAQUE,
                new TypeAttaqueSQLiteAdapter(ctx));
        this.adapters.get(TYPEATTAQUE).open(this.db);
        this.adapters.put(TYPEDEPOKEMON,
                new TypeDePokemonSQLiteAdapter(ctx));
        this.adapters.get(TYPEDEPOKEMON).open(this.db);
        this.adapters.put(BADGE,
                new BadgeSQLiteAdapter(ctx));
        this.adapters.get(BADGE).open(this.db);
        this.adapters.put(TYPEDEPOKEMONEVOLUTION,
                new TypeDePokemonEvolutionSQLiteAdapter(ctx));
        this.adapters.get(TYPEDEPOKEMONEVOLUTION).open(this.db);
        this.adapters.put(DRESSEUR,
                new DresseurSQLiteAdapter(ctx));
        this.adapters.get(DRESSEUR).open(this.db);
        this.adapters.put(TYPEOBJET,
                new TypeObjetSQLiteAdapter(ctx));
        this.adapters.get(TYPEOBJET).open(this.db);
        this.adapters.put(PERSONNAGENONJOUEURBADGE,
                new PersonnageNonJoueurBadgeSQLiteAdapter(ctx));
        this.adapters.get(PERSONNAGENONJOUEURBADGE).open(this.db);
        this.adapters.put(PROFESSION,
                new ProfessionSQLiteAdapter(ctx));
        this.adapters.get(PROFESSION).open(this.db);
        this.adapters.put(ATTAQUE,
                new AttaqueSQLiteAdapter(ctx));
        this.adapters.get(ATTAQUE).open(this.db);
        this.adapters.put(TYPEDEPOKEMONZONE,
                new TypeDePokemonZoneSQLiteAdapter(ctx));
        this.adapters.get(TYPEDEPOKEMONZONE).open(this.db);
        this.adapters.put(PERSONNAGENONJOUEUR,
                new PersonnageNonJoueurSQLiteAdapter(ctx));
        this.adapters.get(PERSONNAGENONJOUEUR).open(this.db);
        this.adapters.put(POKEMON,
                new PokemonSQLiteAdapter(ctx));
        this.adapters.get(POKEMON).open(this.db);
    }

    /**
     * Tells the ObjectManager to make an instance managed and persistent.
     *
     * The object will be entered into the database as a result of the <br />
     * flush operation.
     *
     * NOTE: The persist operation always considers objects that are not<br />
     * yet known to this ObjectManager as NEW. Do not pass detached <br />
     * objects to the persist operation.
     *
     * @param object $object The instance to make managed and persistent.
     * @return Count of objects entered into the DB
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public int persist(final Object object) {
        int result;

        this.beginTransaction();
        try {
            final SQLiteAdapterBase adapter = this.getRepository(object);

            result = (int) adapter.insert(object);
        } catch (Exception ex) {
            ex.printStackTrace();
            this.isSuccessfull = false;
            result = 0;
        }

        return result;
    }

    /**
     * Removes an object instance.
     *
     * A removed object will be removed from the database as a result of <br />
     * the flush operation.
     *
     * @param object $object The object instance to remove.
     */
    public void remove(final Object object) {
        this.beginTransaction();
        try {
            if (object instanceof Objet) {
                ((ObjetSQLiteAdapter)
                        this.adapters.get(OBJET))
                            .remove(((Objet) object).getId());
            }
            if (object instanceof Arene) {
                ((AreneSQLiteAdapter)
                        this.adapters.get(ARENE))
                            .remove(((Arene) object).getId());
            }
            if (object instanceof Position) {
                ((PositionSQLiteAdapter)
                        this.adapters.get(POSITION))
                            .remove(((Position) object).getId());
            }
            if (object instanceof Zone) {
                ((ZoneSQLiteAdapter)
                        this.adapters.get(ZONE))
                            .remove(((Zone) object).getId());
            }
            if (object instanceof TypeAttaque) {
                ((TypeAttaqueSQLiteAdapter)
                        this.adapters.get(TYPEATTAQUE))
                            .remove(((TypeAttaque) object).getId());
            }
            if (object instanceof TypeDePokemon) {
                ((TypeDePokemonSQLiteAdapter)
                        this.adapters.get(TYPEDEPOKEMON))
                            .remove(((TypeDePokemon) object).getId());
            }
            if (object instanceof Badge) {
                ((BadgeSQLiteAdapter)
                        this.adapters.get(BADGE))
                            .remove(((Badge) object).getId());
            }
            if (object instanceof TypeDePokemonEvolution) {
                ((TypeDePokemonEvolutionSQLiteAdapter)
                        this.adapters.get(TYPEDEPOKEMONEVOLUTION))
                            .remove(((TypeDePokemonEvolution) object).getId());
            }
            if (object instanceof Dresseur) {
                ((DresseurSQLiteAdapter)
                        this.adapters.get(DRESSEUR))
                            .remove(((Dresseur) object).getId());
            }
            if (object instanceof TypeObjet) {
                ((TypeObjetSQLiteAdapter)
                        this.adapters.get(TYPEOBJET))
                            .remove(((TypeObjet) object).getId());
            }
            if (object instanceof PersonnageNonJoueurBadge) {
                ((PersonnageNonJoueurBadgeSQLiteAdapter)
                        this.adapters.get(PERSONNAGENONJOUEURBADGE))
                            .remove(((PersonnageNonJoueurBadge) object).getId());
            }
            if (object instanceof Profession) {
                ((ProfessionSQLiteAdapter)
                        this.adapters.get(PROFESSION))
                            .remove(((Profession) object).getId());
            }
            if (object instanceof Attaque) {
                ((AttaqueSQLiteAdapter)
                        this.adapters.get(ATTAQUE))
                            .remove(((Attaque) object).getId());
            }
            if (object instanceof TypeDePokemonZone) {
                ((TypeDePokemonZoneSQLiteAdapter)
                        this.adapters.get(TYPEDEPOKEMONZONE))
                            .remove(((TypeDePokemonZone) object).getId());
            }
            if (object instanceof PersonnageNonJoueur) {
                ((PersonnageNonJoueurSQLiteAdapter)
                        this.adapters.get(PERSONNAGENONJOUEUR))
                            .remove(((PersonnageNonJoueur) object).getId());
            }
            if (object instanceof Pokemon) {
                ((PokemonSQLiteAdapter)
                        this.adapters.get(POKEMON))
                            .remove(((Pokemon) object).getId());
            }
        } catch (Exception ex) {
            this.isSuccessfull = false;
        }
    }

//    /**
//     * Merges the state of a detached object into the persistence context
//     * of this ObjectManager and returns the managed copy of the object.
//     * The object passed to merge will not become associated/managed with
//       * this ObjectManager.
//     *
//     * @param object $object
//     */
//    public void merge(Object object) {
//
//    }
//
//    /**
//     * Clears the ObjectManager. All objects that are currently managed
//     * by this ObjectManager become detached.
//     *
//     * @param objectName $objectName if given, only objects of this type will
//     * get detached
//     */
//    public void clear(String objectName) {
//
//    }
//
//    /**
//     * Detaches an object from the ObjectManager, causing a managed object to
//     * become detached. Unflushed changes made to the object if any
//     * (including removal of the object), will not be synchronized to the
//     * database.
//     * Objects which previously referenced the detached object will continue
//     * to reference it.
//     *
//     * @param object $object The object to detach.
//     */
//    public void detach(Object object) {
//
//    }
//
//    /**
//     * Refreshes the persistent state of an object from the database,
//     * overriding any local changes that have not yet been persisted.
//     *
//     * @param object $object The object to refresh.
//     */
//    public void refresh(Object object) {
//
//    }

    /**
     * Flushes all changes to objects that have been queued up to now to <br />
     * the database. This effectively synchronizes the in-memory state of<br />
     * managed objects with the database.
     */
    public void flush() {
        if (this.isInInternalTransaction) {
            if (this.isSuccessfull) {
                this.db.setTransactionSuccessful();
            }
            this.db.endTransaction();
            this.isInInternalTransaction = false;
        }
    }

    /**
     * Gets the repository for a class.
     *
     * @param className $className
     * @return \Doctrine\Common\Persistence\ObjectRepository
     */
    public SQLiteAdapterBase<?> getRepository(final String className) {
        return this.adapters.get(className);
    }


    /**
     * Gets the repository for a given object.
     *
     * @param o object
     * @return \Doctrine\Common\Persistence\ObjectRepository
     */
    private SQLiteAdapterBase<?> getRepository(final Object o) {
        final String className = o.getClass().getSimpleName();

        return this.getRepository(className);
    }

//    /**
//     * Returns the ClassMetadata descriptor for a class.
//     *
//     * The class name must be the fully-qualified class name without a <br />
//     * leading backslash (as it is returned by get_class($obj)).
//     *
//     * @param className $className
//     * @return \Doctrine\Common\Persistence\Mapping\ClassMetadata
//     */
//    public ClassMetadata getClassMetadata(final String className) {
//        return null;
//    }

    /**
     * Check if the object is part of the current UnitOfWork and therefore
     * managed.
     *
     * @param object $object
     * @return bool
     */
    public boolean contains(final Object object) {
        return false;
    }

    /**
     * Called before any transaction to open the DB.
     */
    private void beginTransaction() {
        // If we are not already in a transaction, begin it
        if (!this.isInInternalTransaction) {
            this.db.beginTransaction();
            this.isSuccessfull = true;
            this.isInInternalTransaction = true;
        }
    }

}
