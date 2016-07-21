/**************************************************************************
 * PersonnageNonJoueurProviderUtilsBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.utils.base;

import java.util.ArrayList;

import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;

import android.content.ContentResolver;
import android.content.ContentValues;

import android.content.OperationApplicationException;
import android.net.Uri;
import android.os.RemoteException;


import com.maximeleau.harmony.android.pokemon.provider.utils.ProviderUtils;
import com.maximeleau.harmony.android.pokemon.criterias.base.Criterion;
import com.maximeleau.harmony.android.pokemon.criterias.base.Criterion.Type;
import com.maximeleau.harmony.android.pokemon.criterias.base.value.ArrayValue;
import com.maximeleau.harmony.android.pokemon.criterias.base.CriteriaExpression;
import com.maximeleau.harmony.android.pokemon.criterias.base.CriteriaExpression.GroupType;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Profession;
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;

import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.ProfessionProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.ObjetProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.DresseurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.AreneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ProfessionContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.ObjetContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.DresseurContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;

/**
 * PersonnageNonJoueur Provider Utils Base.
 *
 * DO NOT MODIFY THIS CLASS AS IT IS REGENERATED
 *
 * This class is a utility class helpful for complex provider calls.
 * ex : inserting an entity and its relations alltogether, etc.
 */
public abstract class PersonnageNonJoueurProviderUtilsBase
            extends ProviderUtils<PersonnageNonJoueur> {
    /**
     * Tag for debug messages.
     */
    public static final String TAG = "PersonnageNonJoueurProviderUtilBase";

    /**
     * Constructor.
     * @param context Context
     */
    public PersonnageNonJoueurProviderUtilsBase(android.content.Context context) {
        super(context);
    }

    @Override
    public Uri insert(final PersonnageNonJoueur item) {
        Uri result = null;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();


        ContentValues itemValues = PersonnageNonJoueurContract.itemToContentValues(item);
        itemValues.remove(PersonnageNonJoueurContract.COL_ID);

        operations.add(ContentProviderOperation.newInsert(
                PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI)
                        .withValues(itemValues)
                        .build());

        if (item.getObjets() != null && item.getObjets().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(ObjetContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getObjets().size(); i++) {
                inValue.addValue(String.valueOf(item.getObjets().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(ObjetProviderAdapter.OBJET_URI)
                    .withValueBackReference(
                            ObjetContract
                                    .COL_PERSONNAGENONJOUEUR_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getDresseurs() != null && item.getDresseurs().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(DresseurContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getDresseurs().size(); i++) {
                inValue.addValue(String.valueOf(item.getDresseurs().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(DresseurProviderAdapter.DRESSEUR_URI)
                    .withValueBackReference(
                            DresseurContract
                                    .COL_PERSONNAGENONJOUEUR_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getArenes() != null && item.getArenes().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(AreneContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getArenes().size(); i++) {
                inValue.addValue(String.valueOf(item.getArenes().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(AreneProviderAdapter.ARENE_URI)
                    .withValueBackReference(
                            AreneContract
                                    .COL_MAITRE_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }
        if (item.getPokemons() != null && item.getPokemons().size() > 0) {
            CriteriaExpression crit = new CriteriaExpression(GroupType.AND);
            Criterion inCrit = new Criterion();
            crit.add(inCrit);
            
            inCrit.setKey(PokemonContract.COL_ID);
            inCrit.setType(Type.IN);
            ArrayValue inValue = new ArrayValue();
            inCrit.addValue(inValue);

            for (int i = 0; i < item.getPokemons().size(); i++) {
                inValue.addValue(String.valueOf(item.getPokemons().get(i).getId()));
            }

            operations.add(ContentProviderOperation.newUpdate(PokemonProviderAdapter.POKEMON_URI)
                    .withValueBackReference(
                            PokemonContract
                                    .COL_PERSONNAGENONJOUEUR_ID,
                            0)
                    .withSelection(
                            crit.toSQLiteSelection(),
                            crit.toSQLiteSelectionArgs())
                    .build());
        }

        try {
            ContentProviderResult[] results =
                    prov.applyBatch(PokemonProvider.authority, operations);
            if (results[0] != null) {
                result = results[0].uri;
                item.setId(Integer.parseInt(result.getPathSegments().get(1)));
            }
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }


    /**
     * Delete from DB.
     * @param item PersonnageNonJoueur
     * @return number of row affected
     */
    public int delete(final PersonnageNonJoueur item) {
        int result = -1;
        ContentResolver prov = this.getContext().getContentResolver();

        Uri uri = PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));

        result = prov.delete(uri,
            null,
            null);

        return result;
    }


    /**
     * Query the DB.
     * @param item The item with its ids set
     * @return PersonnageNonJoueur
     */
    public PersonnageNonJoueur query(final PersonnageNonJoueur item) {
        return this.query(item.getId());
    }

    /**
     * Query the DB.
     *
     * @param id id
     *
     * @return PersonnageNonJoueur
     */
    public PersonnageNonJoueur query(final int id) {
        PersonnageNonJoueur result = null;
        ContentResolver prov = this.getContext().getContentResolver();

        CriteriaExpression crits = new CriteriaExpression(GroupType.AND);
        crits.add(PersonnageNonJoueurContract.ALIASED_COL_ID,
                    String.valueOf(id));

        android.database.Cursor cursor = prov.query(
            PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI,
            PersonnageNonJoueurContract.ALIASED_COLS,
            crits.toSQLiteSelection(),
            crits.toSQLiteSelectionArgs(),
            null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            result = PersonnageNonJoueurContract.cursorToItem(cursor);

            if (result.getProfession() != null) {
                result.setProfession(
                    this.getAssociateProfession(result));
            }
            result.setObjets(
                this.getAssociateObjets(result));
            result.setDresseurs(
                this.getAssociateDresseurs(result));
            result.setArenes(
                this.getAssociateArenes(result));
            result.setPokemons(
                this.getAssociatePokemons(result));
        }
        cursor.close();
        
        return result;
    }

    /**
     * Query the DB to get all entities.
     * @return ArrayList<PersonnageNonJoueur>
     */
    public ArrayList<PersonnageNonJoueur> queryAll() {
        ArrayList<PersonnageNonJoueur> result =
                    new ArrayList<PersonnageNonJoueur>();
        ContentResolver prov =
                    this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI,
                PersonnageNonJoueurContract.ALIASED_COLS,
                null,
                null,
                null);

        result = PersonnageNonJoueurContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Query the DB to get the entities filtered by criteria.
     * @param expression The criteria expression defining the selection and selection args
     * @return ArrayList<PersonnageNonJoueur>
     */
    public ArrayList<PersonnageNonJoueur> query(CriteriaExpression expression) {
        ArrayList<PersonnageNonJoueur> result =
                    new ArrayList<PersonnageNonJoueur>();
        ContentResolver prov = this.getContext().getContentResolver();

        android.database.Cursor cursor = prov.query(
                PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI,
                PersonnageNonJoueurContract.ALIASED_COLS,
                expression.toSQLiteSelection(),
                expression.toSQLiteSelectionArgs(),
                null);

        result = PersonnageNonJoueurContract.cursorToItems(cursor);

        cursor.close();

        return result;
    }

    /**
     * Updates the DB.
     * @param item PersonnageNonJoueur
     
     * @return number of rows updated
     */
    public int update(final PersonnageNonJoueur item) {
        int result = -1;
        ArrayList<ContentProviderOperation> operations =
                new ArrayList<ContentProviderOperation>();
        ContentResolver prov = this.getContext().getContentResolver();
        ContentValues itemValues = PersonnageNonJoueurContract.itemToContentValues(
                item);

        Uri uri = PersonnageNonJoueurProviderAdapter.PERSONNAGENONJOUEUR_URI;
        uri = Uri.withAppendedPath(uri, String.valueOf(item.getId()));


        operations.add(ContentProviderOperation.newUpdate(uri)
                .withValues(itemValues)
                .build());


        if (item.getObjets() != null && item.getObjets().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new objets for PersonnageNonJoueur
            CriteriaExpression objetsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(ObjetContract.COL_ID);
            crit.addValue(values);
            objetsCrit.add(crit);


            for (Objet objets : item.getObjets()) {
                values.addValue(
                    String.valueOf(objets.getId()));
            }
            selection = objetsCrit.toSQLiteSelection();
            selectionArgs = objetsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    ObjetProviderAdapter.OBJET_URI)
                    .withValue(
                            ObjetContract.COL_PERSONNAGENONJOUEUR_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated objets
            crit.setType(Type.NOT_IN);
            objetsCrit.add(ObjetContract.COL_PERSONNAGENONJOUEUR_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    ObjetProviderAdapter.OBJET_URI)
                    .withValue(
                            ObjetContract.COL_PERSONNAGENONJOUEUR_ID,
                            null)
                    .withSelection(
                            objetsCrit.toSQLiteSelection(),
                            objetsCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getDresseurs() != null && item.getDresseurs().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new dresseurs for PersonnageNonJoueur
            CriteriaExpression dresseursCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(DresseurContract.COL_ID);
            crit.addValue(values);
            dresseursCrit.add(crit);


            for (Dresseur dresseurs : item.getDresseurs()) {
                values.addValue(
                    String.valueOf(dresseurs.getId()));
            }
            selection = dresseursCrit.toSQLiteSelection();
            selectionArgs = dresseursCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    DresseurProviderAdapter.DRESSEUR_URI)
                    .withValue(
                            DresseurContract.COL_PERSONNAGENONJOUEUR_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated dresseurs
            crit.setType(Type.NOT_IN);
            dresseursCrit.add(DresseurContract.COL_PERSONNAGENONJOUEUR_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    DresseurProviderAdapter.DRESSEUR_URI)
                    .withValue(
                            DresseurContract.COL_PERSONNAGENONJOUEUR_ID,
                            null)
                    .withSelection(
                            dresseursCrit.toSQLiteSelection(),
                            dresseursCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getArenes() != null && item.getArenes().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new arenes for PersonnageNonJoueur
            CriteriaExpression arenesCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(AreneContract.COL_ID);
            crit.addValue(values);
            arenesCrit.add(crit);


            for (Arene arenes : item.getArenes()) {
                values.addValue(
                    String.valueOf(arenes.getId()));
            }
            selection = arenesCrit.toSQLiteSelection();
            selectionArgs = arenesCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    AreneProviderAdapter.ARENE_URI)
                    .withValue(
                            AreneContract.COL_MAITRE_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated arenes
            crit.setType(Type.NOT_IN);
            arenesCrit.add(AreneContract.COL_MAITRE_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    AreneProviderAdapter.ARENE_URI)
                    .withValue(
                            AreneContract.COL_MAITRE_ID,
                            null)
                    .withSelection(
                            arenesCrit.toSQLiteSelection(),
                            arenesCrit.toSQLiteSelectionArgs())
                    .build());
        }

        if (item.getPokemons() != null && item.getPokemons().size() > 0) {
            String selection;
            String[] selectionArgs;
            // Set new pokemons for PersonnageNonJoueur
            CriteriaExpression pokemonsCrit =
                        new CriteriaExpression(GroupType.AND);
            Criterion crit = new Criterion();
            ArrayValue values = new ArrayValue();
            crit.setType(Type.IN);
            crit.setKey(PokemonContract.COL_ID);
            crit.addValue(values);
            pokemonsCrit.add(crit);


            for (Pokemon pokemons : item.getPokemons()) {
                values.addValue(
                    String.valueOf(pokemons.getId()));
            }
            selection = pokemonsCrit.toSQLiteSelection();
            selectionArgs = pokemonsCrit.toSQLiteSelectionArgs();

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonProviderAdapter.POKEMON_URI)
                    .withValue(
                            PokemonContract.COL_PERSONNAGENONJOUEUR_ID,
                            item.getId())
                    .withSelection(
                            selection,
                            selectionArgs)
                    .build());

            // Remove old associated pokemons
            crit.setType(Type.NOT_IN);
            pokemonsCrit.add(PokemonContract.COL_PERSONNAGENONJOUEUR_ID,
                    String.valueOf(item.getId()),
                    Type.EQUALS);
            

            operations.add(ContentProviderOperation.newUpdate(
                    PokemonProviderAdapter.POKEMON_URI)
                    .withValue(
                            PokemonContract.COL_PERSONNAGENONJOUEUR_ID,
                            null)
                    .withSelection(
                            pokemonsCrit.toSQLiteSelection(),
                            pokemonsCrit.toSQLiteSelectionArgs())
                    .build());
        }


        try {
            ContentProviderResult[] results = prov.applyBatch(PokemonProvider.authority, operations);
            result = results[0].count;
        } catch (RemoteException e) {
            android.util.Log.e(TAG, e.getMessage());
        } catch (OperationApplicationException e) {
            android.util.Log.e(TAG, e.getMessage());
        }

        return result;
    }

    /** Relations operations. */
    /**
     * Get associate Profession.
     * @param item PersonnageNonJoueur
     * @return Profession
     */
    public Profession getAssociateProfession(
            final PersonnageNonJoueur item) {
        Profession result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor professionCursor = prov.query(
                ProfessionProviderAdapter.PROFESSION_URI,
                ProfessionContract.ALIASED_COLS,
                ProfessionContract.ALIASED_COL_ID + "= ?",
                new String[]{String.valueOf(item.getProfession().getId())},
                null);

        if (professionCursor.getCount() > 0) {
            professionCursor.moveToFirst();
            result = ProfessionContract.cursorToItem(professionCursor);
        } else {
            result = null;
        }
        professionCursor.close();

        return result;
    }

    /**
     * Get associate Objets.
     * @param item PersonnageNonJoueur
     * @return Objet
     */
    public ArrayList<Objet> getAssociateObjets(
            final PersonnageNonJoueur item) {
        ArrayList<Objet> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor objetCursor = prov.query(
                ObjetProviderAdapter.OBJET_URI,
                ObjetContract.ALIASED_COLS,
                ObjetContract.ALIASED_COL_PERSONNAGENONJOUEUR_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = ObjetContract.cursorToItems(
                        objetCursor);
        objetCursor.close();

        return result;
    }

    /**
     * Get associate Dresseurs.
     * @param item PersonnageNonJoueur
     * @return Dresseur
     */
    public ArrayList<Dresseur> getAssociateDresseurs(
            final PersonnageNonJoueur item) {
        ArrayList<Dresseur> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor dresseurCursor = prov.query(
                DresseurProviderAdapter.DRESSEUR_URI,
                DresseurContract.ALIASED_COLS,
                DresseurContract.ALIASED_COL_PERSONNAGENONJOUEUR_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = DresseurContract.cursorToItems(
                        dresseurCursor);
        dresseurCursor.close();

        return result;
    }

    /**
     * Get associate Arenes.
     * @param item PersonnageNonJoueur
     * @return Arene
     */
    public ArrayList<Arene> getAssociateArenes(
            final PersonnageNonJoueur item) {
        ArrayList<Arene> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor areneCursor = prov.query(
                AreneProviderAdapter.ARENE_URI,
                AreneContract.ALIASED_COLS,
                AreneContract.ALIASED_COL_MAITRE_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = AreneContract.cursorToItems(
                        areneCursor);
        areneCursor.close();

        return result;
    }

    /**
     * Get associate Pokemons.
     * @param item PersonnageNonJoueur
     * @return Pokemon
     */
    public ArrayList<Pokemon> getAssociatePokemons(
            final PersonnageNonJoueur item) {
        ArrayList<Pokemon> result;
        ContentResolver prov = this.getContext().getContentResolver();
        android.database.Cursor pokemonCursor = prov.query(
                PokemonProviderAdapter.POKEMON_URI,
                PokemonContract.ALIASED_COLS,
                PokemonContract.ALIASED_COL_PERSONNAGENONJOUEUR_ID
                        + "= ?",
                new String[]{String.valueOf(item.getId())},
                null);

        result = PokemonContract.cursorToItems(
                        pokemonCursor);
        pokemonCursor.close();

        return result;
    }

}
