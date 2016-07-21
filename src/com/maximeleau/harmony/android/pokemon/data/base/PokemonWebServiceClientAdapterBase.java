/**************************************************************************
 * PokemonWebServiceClientAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/

package com.maximeleau.harmony.android.pokemon.data.base;

import java.util.List;


import org.joda.time.format.DateTimeFormatter;
import com.maximeleau.harmony.android.pokemon.harmony.util.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.joda.time.format.DateTimeFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;

import com.maximeleau.harmony.android.pokemon.data.*;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.PokemonContract;

import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemon;
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PokemonWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PokemonWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Pokemon> {
    /** PokemonWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PokemonWSClientAdapter";

    /** JSON Object Pokemon pattern. */
    protected static String JSON_OBJECT_POKEMON = "Pokemon";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_SURNOM attributes. */
    protected static String JSON_SURNOM = "surnom";
    /** JSON_NIVEAU attributes. */
    protected static String JSON_NIVEAU = "niveau";
    /** JSON_CAPTURELE attributes. */
    protected static String JSON_CAPTURELE = "captureLe";
    /** JSON_ATTAQUE1 attributes. */
    protected static String JSON_ATTAQUE1 = "attaque1";
    /** JSON_ATTAQUE2 attributes. */
    protected static String JSON_ATTAQUE2 = "attaque2";
    /** JSON_ATTAQUE3 attributes. */
    protected static String JSON_ATTAQUE3 = "attaque3";
    /** JSON_ATTAQUE4 attributes. */
    protected static String JSON_ATTAQUE4 = "attaque4";
    /** JSON_TYPEDEPOKEMON attributes. */
    protected static String JSON_TYPEDEPOKEMON = "typeDePokemon";
    /** JSON_PERSONNAGENONJOUEUR attributes. */
    protected static String JSON_PERSONNAGENONJOUEUR = "personnageNonJoueur";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Pokemon REST Columns. */
    public static String[] REST_COLS = new String[]{
            PokemonContract.COL_ID,
            PokemonContract.COL_SURNOM,
            PokemonContract.COL_NIVEAU,
            PokemonContract.COL_CAPTURELE,
            PokemonContract.COL_ATTAQUE1_ID,
            PokemonContract.COL_ATTAQUE2_ID,
            PokemonContract.COL_ATTAQUE3_ID,
            PokemonContract.COL_ATTAQUE4_ID,
            PokemonContract.COL_TYPEDEPOKEMON_ID,
            PokemonContract.COL_PERSONNAGENONJOUEUR_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PokemonWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PokemonWebServiceClientAdapterBase(Context context,
        Integer port) {
        this(context, null, port);
    }

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     */
    public PokemonWebServiceClientAdapterBase(Context context,
            String host, Integer port) {
        this(context, host, port, null);
    }

    /**
     * Constructor with overriden port, host and scheme.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     */
    public PokemonWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme) {
        this(context, host, port, scheme, null);
    }

    /**
     * Constructor with overriden port, host, scheme and prefix.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     * @param prefix The overriden prefix
     */
    public PokemonWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Pokemons in the given list. Uses the route : Pokemon.
     * @param pokemons : The list in which the Pokemons will be returned
     * @return The number of Pokemons returned
     */
    public int getAll(List<Pokemon> pokemons) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "%s",
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = extractItems(json, "Pokemons", pokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "pokemon";
    }

    /**
     * Retrieve one Pokemon. Uses the route : Pokemon/%id%.
     * @param pokemon : The Pokemon to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Pokemon pokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokemon.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, pokemon)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemon = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Pokemon. Uses the route : Pokemon/%id%.
     * @param pokemon : The Pokemon to retrieve (set the  ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public Cursor query(final int id) {
        MatrixCursor result = new MatrixCursor(REST_COLS);
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        id,
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extractCursor(json, result);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                result = null;
            }
        }

        return result;
    }

    /**
     * Update a Pokemon. Uses the route : Pokemon/%id%.
     * @param pokemon : The Pokemon to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Pokemon pokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokemon.getId(),
                        REST_FORMAT),
                    itemToJson(pokemon));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, pokemon);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Pokemon. Uses the route : Pokemon/%id%.
     * @param pokemon : The Pokemon to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Pokemon pokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        pokemon.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a Attaque. Uses the route : attaque/%Attaque_id%/pokemon.
     * @param pokemons : The list in which the Pokemons will be returned
     * @param attaque : The associated attaque
     * @return The number of Pokemons returned
     */
    public int getByAttaque1(List<Pokemon> pokemons, Attaque attaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Pokemons", pokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a Attaque. Uses the route : attaque/%Attaque_id%/pokemon.
     * @param pokemons : The list in which the Pokemons will be returned
     * @param attaque : The associated attaque
     * @return The number of Pokemons returned
     */
    public int getByAttaque2(List<Pokemon> pokemons, Attaque attaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Pokemons", pokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a Attaque. Uses the route : attaque/%Attaque_id%/pokemon.
     * @param pokemons : The list in which the Pokemons will be returned
     * @param attaque : The associated attaque
     * @return The number of Pokemons returned
     */
    public int getByAttaque3(List<Pokemon> pokemons, Attaque attaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Pokemons", pokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a Attaque. Uses the route : attaque/%Attaque_id%/pokemon.
     * @param pokemons : The list in which the Pokemons will be returned
     * @param attaque : The associated attaque
     * @return The number of Pokemons returned
     */
    public int getByAttaque4(List<Pokemon> pokemons, Attaque attaque) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        attaque.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Pokemons", pokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a TypeDePokemon. Uses the route : typedepokemon/%TypeDePokemon_id%/pokemon.
     * @param pokemons : The list in which the Pokemons will be returned
     * @param typedepokemon : The associated typedepokemon
     * @return The number of Pokemons returned
     */
    public int getByTypeDePokemon(List<Pokemon> pokemons, TypeDePokemon typeDePokemon) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        typeDePokemon.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Pokemons", pokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }

    /**
     * Get the Pokemons associated with a PersonnageNonJoueur. Uses the route : personnagenonjoueur/%PersonnageNonJoueur_id%/pokemon.
     * @param pokemons : The list in which the Pokemons will be returned
     * @param personnagenonjoueur : The associated personnagenonjoueur
     * @return The number of Pokemons returned
     */
    public int getByPersonnageNonJoueur(List<Pokemon> pokemons, PersonnageNonJoueur personnageNonJoueur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        personnageNonJoueur.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Pokemons", pokemons);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                pokemons = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Pokemon Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PokemonWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Pokemon from a JSONObject describing a Pokemon.
     * @param json The JSONObject describing the Pokemon
     * @param pokemon The returned Pokemon
     * @return true if a Pokemon was found. false if not
     */
    public boolean extract(JSONObject json, Pokemon pokemon) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PokemonWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PokemonWebServiceClientAdapter.JSON_ID)) {
                    pokemon.setId(
                            json.getInt(PokemonWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PokemonWebServiceClientAdapter.JSON_SURNOM)
                        && !json.isNull(PokemonWebServiceClientAdapter.JSON_SURNOM)) {
                    pokemon.setSurnom(
                            json.getString(PokemonWebServiceClientAdapter.JSON_SURNOM));
                }

                if (json.has(PokemonWebServiceClientAdapter.JSON_NIVEAU)
                        && !json.isNull(PokemonWebServiceClientAdapter.JSON_NIVEAU)) {
                    pokemon.setNiveau(
                            json.getInt(PokemonWebServiceClientAdapter.JSON_NIVEAU));
                }

                if (json.has(PokemonWebServiceClientAdapter.JSON_CAPTURELE)
                        && !json.isNull(PokemonWebServiceClientAdapter.JSON_CAPTURELE)) {
                    DateTimeFormatter captureLeFormatter = DateTimeFormat.forPattern(
                            PokemonWebServiceClientAdapter.REST_UPDATE_DATE_FORMAT);
                    try {
                        pokemon.setCaptureLe(
                                captureLeFormatter.withOffsetParsed().parseDateTime(
                                        json.getString(
                                        PokemonWebServiceClientAdapter.JSON_CAPTURELE)));
                    } catch (IllegalArgumentException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PokemonWebServiceClientAdapter.JSON_ATTAQUE1)
                        && !json.isNull(PokemonWebServiceClientAdapter.JSON_ATTAQUE1)) {

                    try {
                        AttaqueWebServiceClientAdapter attaque1Adapter =
                                new AttaqueWebServiceClientAdapter(this.context);
                        Attaque attaque1 =
                                new Attaque();

                        if (attaque1Adapter.extract(
                                json.optJSONObject(
                                        PokemonWebServiceClientAdapter.JSON_ATTAQUE1),
                                        attaque1)) {
                            pokemon.setAttaque1(attaque1);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Attaque data");
                    }
                }

                if (json.has(PokemonWebServiceClientAdapter.JSON_ATTAQUE2)
                        && !json.isNull(PokemonWebServiceClientAdapter.JSON_ATTAQUE2)) {

                    try {
                        AttaqueWebServiceClientAdapter attaque2Adapter =
                                new AttaqueWebServiceClientAdapter(this.context);
                        Attaque attaque2 =
                                new Attaque();

                        if (attaque2Adapter.extract(
                                json.optJSONObject(
                                        PokemonWebServiceClientAdapter.JSON_ATTAQUE2),
                                        attaque2)) {
                            pokemon.setAttaque2(attaque2);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Attaque data");
                    }
                }

                if (json.has(PokemonWebServiceClientAdapter.JSON_ATTAQUE3)
                        && !json.isNull(PokemonWebServiceClientAdapter.JSON_ATTAQUE3)) {

                    try {
                        AttaqueWebServiceClientAdapter attaque3Adapter =
                                new AttaqueWebServiceClientAdapter(this.context);
                        Attaque attaque3 =
                                new Attaque();

                        if (attaque3Adapter.extract(
                                json.optJSONObject(
                                        PokemonWebServiceClientAdapter.JSON_ATTAQUE3),
                                        attaque3)) {
                            pokemon.setAttaque3(attaque3);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Attaque data");
                    }
                }

                if (json.has(PokemonWebServiceClientAdapter.JSON_ATTAQUE4)
                        && !json.isNull(PokemonWebServiceClientAdapter.JSON_ATTAQUE4)) {

                    try {
                        AttaqueWebServiceClientAdapter attaque4Adapter =
                                new AttaqueWebServiceClientAdapter(this.context);
                        Attaque attaque4 =
                                new Attaque();

                        if (attaque4Adapter.extract(
                                json.optJSONObject(
                                        PokemonWebServiceClientAdapter.JSON_ATTAQUE4),
                                        attaque4)) {
                            pokemon.setAttaque4(attaque4);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Attaque data");
                    }
                }

                if (json.has(PokemonWebServiceClientAdapter.JSON_TYPEDEPOKEMON)
                        && !json.isNull(PokemonWebServiceClientAdapter.JSON_TYPEDEPOKEMON)) {

                    try {
                        TypeDePokemonWebServiceClientAdapter typeDePokemonAdapter =
                                new TypeDePokemonWebServiceClientAdapter(this.context);
                        TypeDePokemon typeDePokemon =
                                new TypeDePokemon();

                        if (typeDePokemonAdapter.extract(
                                json.optJSONObject(
                                        PokemonWebServiceClientAdapter.JSON_TYPEDEPOKEMON),
                                        typeDePokemon)) {
                            pokemon.setTypeDePokemon(typeDePokemon);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains TypeDePokemon data");
                    }
                }

                if (json.has(PokemonWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)
                        && !json.isNull(PokemonWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)) {

                    try {
                        PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                                new PersonnageNonJoueurWebServiceClientAdapter(this.context);
                        PersonnageNonJoueur personnageNonJoueur =
                                new PersonnageNonJoueur();

                        if (personnageNonJoueurAdapter.extract(
                                json.optJSONObject(
                                        PokemonWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR),
                                        personnageNonJoueur)) {
                            pokemon.setPersonnageNonJoueur(personnageNonJoueur);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PersonnageNonJoueur data");
                    }
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    @Override
    public boolean extractCursor(JSONObject json, MatrixCursor cursor) {
        boolean result = false;
        String id = json.optString(PokemonWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[10];
                if (json.has(PokemonWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PokemonWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonWebServiceClientAdapter.JSON_SURNOM)) {
                    row[1] = json.getString(PokemonWebServiceClientAdapter.JSON_SURNOM);
                }
                if (json.has(PokemonWebServiceClientAdapter.JSON_NIVEAU)) {
                    row[2] = json.getString(PokemonWebServiceClientAdapter.JSON_NIVEAU);
                }
                if (json.has(PokemonWebServiceClientAdapter.JSON_CAPTURELE)) {
                    row[3] = json.getString(PokemonWebServiceClientAdapter.JSON_CAPTURELE);
                }
                if (json.has(PokemonWebServiceClientAdapter.JSON_ATTAQUE1)) {
                    JSONObject attaque1Json = json.getJSONObject(
                            PokemonWebServiceClientAdapter.JSON_ATTAQUE1);
                    row[4] = attaque1Json.getString(
                            AttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonWebServiceClientAdapter.JSON_ATTAQUE2)) {
                    JSONObject attaque2Json = json.getJSONObject(
                            PokemonWebServiceClientAdapter.JSON_ATTAQUE2);
                    row[5] = attaque2Json.getString(
                            AttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonWebServiceClientAdapter.JSON_ATTAQUE3)) {
                    JSONObject attaque3Json = json.getJSONObject(
                            PokemonWebServiceClientAdapter.JSON_ATTAQUE3);
                    row[6] = attaque3Json.getString(
                            AttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonWebServiceClientAdapter.JSON_ATTAQUE4)) {
                    JSONObject attaque4Json = json.getJSONObject(
                            PokemonWebServiceClientAdapter.JSON_ATTAQUE4);
                    row[7] = attaque4Json.getString(
                            AttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonWebServiceClientAdapter.JSON_TYPEDEPOKEMON)) {
                    JSONObject typeDePokemonJson = json.getJSONObject(
                            PokemonWebServiceClientAdapter.JSON_TYPEDEPOKEMON);
                    row[8] = typeDePokemonJson.getString(
                            TypeDePokemonWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PokemonWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)) {
                    JSONObject personnageNonJoueurJson = json.getJSONObject(
                            PokemonWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR);
                    row[9] = personnageNonJoueurJson.getString(
                            PersonnageNonJoueurWebServiceClientAdapter.JSON_ID);
                }

                cursor.addRow(row);
                result = true;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Convert a Pokemon to a JSONObject.
     * @param pokemon The Pokemon to convert
     * @return The converted Pokemon
     */
    public JSONObject itemToJson(Pokemon pokemon) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokemonWebServiceClientAdapter.JSON_ID,
                    pokemon.getId());
            params.put(PokemonWebServiceClientAdapter.JSON_SURNOM,
                    pokemon.getSurnom());
            params.put(PokemonWebServiceClientAdapter.JSON_NIVEAU,
                    pokemon.getNiveau());

            if (pokemon.getCaptureLe() != null) {
                params.put(PokemonWebServiceClientAdapter.JSON_CAPTURELE,
                        pokemon.getCaptureLe().toString(REST_UPDATE_DATE_FORMAT));
            }

            if (pokemon.getAttaque1() != null) {
                AttaqueWebServiceClientAdapter attaque1Adapter =
                        new AttaqueWebServiceClientAdapter(this.context);

                params.put(PokemonWebServiceClientAdapter.JSON_ATTAQUE1,
                        attaque1Adapter.itemIdToJson(pokemon.getAttaque1()));
            }

            if (pokemon.getAttaque2() != null) {
                AttaqueWebServiceClientAdapter attaque2Adapter =
                        new AttaqueWebServiceClientAdapter(this.context);

                params.put(PokemonWebServiceClientAdapter.JSON_ATTAQUE2,
                        attaque2Adapter.itemIdToJson(pokemon.getAttaque2()));
            }

            if (pokemon.getAttaque3() != null) {
                AttaqueWebServiceClientAdapter attaque3Adapter =
                        new AttaqueWebServiceClientAdapter(this.context);

                params.put(PokemonWebServiceClientAdapter.JSON_ATTAQUE3,
                        attaque3Adapter.itemIdToJson(pokemon.getAttaque3()));
            }

            if (pokemon.getAttaque4() != null) {
                AttaqueWebServiceClientAdapter attaque4Adapter =
                        new AttaqueWebServiceClientAdapter(this.context);

                params.put(PokemonWebServiceClientAdapter.JSON_ATTAQUE4,
                        attaque4Adapter.itemIdToJson(pokemon.getAttaque4()));
            }

            if (pokemon.getTypeDePokemon() != null) {
                TypeDePokemonWebServiceClientAdapter typeDePokemonAdapter =
                        new TypeDePokemonWebServiceClientAdapter(this.context);

                params.put(PokemonWebServiceClientAdapter.JSON_TYPEDEPOKEMON,
                        typeDePokemonAdapter.itemIdToJson(pokemon.getTypeDePokemon()));
            }

            if (pokemon.getPersonnageNonJoueur() != null) {
                PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                        new PersonnageNonJoueurWebServiceClientAdapter(this.context);

                params.put(PokemonWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR,
                        personnageNonJoueurAdapter.itemIdToJson(pokemon.getPersonnageNonJoueur()));
            }
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Convert a <T> to a JSONObject.
     * @param item The <T> to convert
     * @return The converted <T>
     */
    public JSONObject itemIdToJson(Pokemon item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PokemonWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Pokemon to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PokemonWebServiceClientAdapter.JSON_ID,
                    values.get(PokemonContract.COL_ID));
            params.put(PokemonWebServiceClientAdapter.JSON_SURNOM,
                    values.get(PokemonContract.COL_SURNOM));
            params.put(PokemonWebServiceClientAdapter.JSON_NIVEAU,
                    values.get(PokemonContract.COL_NIVEAU));
            params.put(PokemonWebServiceClientAdapter.JSON_CAPTURELE,
                    new DateTime(values.get(
                            PokemonContract.COL_CAPTURELE)).toString(REST_UPDATE_DATE_FORMAT));
            AttaqueWebServiceClientAdapter attaque1Adapter =
                    new AttaqueWebServiceClientAdapter(this.context);

            params.put(PokemonWebServiceClientAdapter.JSON_ATTAQUE1,
                    attaque1Adapter.contentValuesToJson(values));
            AttaqueWebServiceClientAdapter attaque2Adapter =
                    new AttaqueWebServiceClientAdapter(this.context);

            params.put(PokemonWebServiceClientAdapter.JSON_ATTAQUE2,
                    attaque2Adapter.contentValuesToJson(values));
            AttaqueWebServiceClientAdapter attaque3Adapter =
                    new AttaqueWebServiceClientAdapter(this.context);

            params.put(PokemonWebServiceClientAdapter.JSON_ATTAQUE3,
                    attaque3Adapter.contentValuesToJson(values));
            AttaqueWebServiceClientAdapter attaque4Adapter =
                    new AttaqueWebServiceClientAdapter(this.context);

            params.put(PokemonWebServiceClientAdapter.JSON_ATTAQUE4,
                    attaque4Adapter.contentValuesToJson(values));
            TypeDePokemonWebServiceClientAdapter typeDePokemonAdapter =
                    new TypeDePokemonWebServiceClientAdapter(this.context);

            params.put(PokemonWebServiceClientAdapter.JSON_TYPEDEPOKEMON,
                    typeDePokemonAdapter.contentValuesToJson(values));
            PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                    new PersonnageNonJoueurWebServiceClientAdapter(this.context);

            params.put(PokemonWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR,
                    personnageNonJoueurAdapter.contentValuesToJson(values));
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Extract a list of <T> from a JSONObject describing an array of <T> given the array name.
     * @param json The JSONObject describing the array of <T>
     * @param items The returned list of <T>
     * @param paramName The name of the array
     * @param limit Limit the number of items to parse
     * @return The number of <T> found in the JSON
     */
    public int extractItems(JSONObject json,
            String paramName,
            List<Pokemon> items,
            int limit) throws JSONException {

        JSONArray itemArray = json.optJSONArray(paramName);

        int result = -1;

        if (itemArray != null) {
            int count = itemArray.length();

            if (limit > 0 && count > limit) {
                count = limit;
            }

            for (int i = 0; i < count; i++) {
                JSONObject jsonItem = itemArray.getJSONObject(i);
                Pokemon item = new Pokemon();
                this.extract(jsonItem, item);
                if (item!=null) {
                    synchronized (items) {
                        items.add(item);
                    }
                }
            }
        }

        if (!json.isNull("Meta")) {
            JSONObject meta = json.optJSONObject("Meta");
            result = meta.optInt("nbt",0);
        }

        return result;
    }

    /**
     * Extract a list of <T> from a JSONObject describing an array of <T> given the array name.
     * @param json The JSONObject describing the array of <T>
     * @param items The returned list of <T>
     * @param paramName The name of the array
     * @return The number of <T> found in the JSON
     */
    public int extractItems(JSONObject json,
            String paramName,
            List<Pokemon> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
