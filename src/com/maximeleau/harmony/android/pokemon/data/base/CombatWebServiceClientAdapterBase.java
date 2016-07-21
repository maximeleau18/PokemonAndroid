/**************************************************************************
 * CombatWebServiceClientAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.CombatContract;

import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit CombatWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class CombatWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Combat> {
    /** CombatWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "CombatWSClientAdapter";

    /** JSON Object Combat pattern. */
    protected static String JSON_OBJECT_COMBAT = "Combat";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_LANCELE attributes. */
    protected static String JSON_LANCELE = "lanceLe";
    /** JSON_DUREE attributes. */
    protected static String JSON_DUREE = "duree";
    /** JSON_POKEMON1 attributes. */
    protected static String JSON_POKEMON1 = "pokemon1";
    /** JSON_POKEMON2 attributes. */
    protected static String JSON_POKEMON2 = "pokemon2";
    /** JSON_DRESSEUR1 attributes. */
    protected static String JSON_DRESSEUR1 = "dresseur1";
    /** JSON_DRESSEUR2 attributes. */
    protected static String JSON_DRESSEUR2 = "dresseur2";
    /** JSON_DRESSEUR1VAINQUEUR attributes. */
    protected static String JSON_DRESSEUR1VAINQUEUR = "dresseur1Vainqueur";
    /** JSON_DRESSEUR2VAINQUEUR attributes. */
    protected static String JSON_DRESSEUR2VAINQUEUR = "dresseur2Vainqueur";
    /** JSON_POKEMON1VAINQUEUR attributes. */
    protected static String JSON_POKEMON1VAINQUEUR = "pokemon1Vainqueur";
    /** JSON_POKEMON2VAINQUEUR attributes. */
    protected static String JSON_POKEMON2VAINQUEUR = "pokemon2Vainqueur";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Combat REST Columns. */
    public static String[] REST_COLS = new String[]{
            CombatContract.COL_ID,
            CombatContract.COL_LANCELE,
            CombatContract.COL_DUREE,
            CombatContract.COL_POKEMON1_ID,
            CombatContract.COL_POKEMON2_ID,
            CombatContract.COL_DRESSEUR1_ID,
            CombatContract.COL_DRESSEUR2_ID,
            CombatContract.COL_DRESSEUR1VAINQUEUR,
            CombatContract.COL_DRESSEUR2VAINQUEUR,
            CombatContract.COL_POKEMON1VAINQUEUR,
            CombatContract.COL_POKEMON2VAINQUEUR
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public CombatWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public CombatWebServiceClientAdapterBase(Context context,
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
    public CombatWebServiceClientAdapterBase(Context context,
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
    public CombatWebServiceClientAdapterBase(Context context,
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
    public CombatWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Combats in the given list. Uses the route : Combat.
     * @param combats : The list in which the Combats will be returned
     * @return The number of Combats returned
     */
    public int getAll(List<Combat> combats) {
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
                result = extractItems(json, "Combats", combats);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                combats = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "combat";
    }

    /**
     * Retrieve one Combat. Uses the route : Combat/%id%.
     * @param combat : The Combat to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Combat combat) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        combat.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, combat)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                combat = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Combat. Uses the route : Combat/%id%.
     * @param combat : The Combat to retrieve (set the  ID)
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
     * Update a Combat. Uses the route : Combat/%id%.
     * @param combat : The Combat to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Combat combat) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        combat.getId(),
                        REST_FORMAT),
                    itemToJson(combat));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, combat);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Combat. Uses the route : Combat/%id%.
     * @param combat : The Combat to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Combat combat) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        combat.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Combats associated with a Pokemon. Uses the route : pokemon/%Pokemon_id%/combat.
     * @param combats : The list in which the Combats will be returned
     * @param pokemon : The associated pokemon
     * @return The number of Combats returned
     */
    public int getByPokemon1(List<Combat> combats, Pokemon pokemon) {
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
                result = this.extractItems(json, "Combats", combats);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                combats = null;
            }
        }

        return result;
    }

    /**
     * Get the Combats associated with a Pokemon. Uses the route : pokemon/%Pokemon_id%/combat.
     * @param combats : The list in which the Combats will be returned
     * @param pokemon : The associated pokemon
     * @return The number of Combats returned
     */
    public int getByPokemon2(List<Combat> combats, Pokemon pokemon) {
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
                result = this.extractItems(json, "Combats", combats);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                combats = null;
            }
        }

        return result;
    }

    /**
     * Get the Combats associated with a Dresseur. Uses the route : dresseur/%Dresseur_id%/combat.
     * @param combats : The list in which the Combats will be returned
     * @param dresseur : The associated dresseur
     * @return The number of Combats returned
     */
    public int getByDresseur1(List<Combat> combats, Dresseur dresseur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        dresseur.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Combats", combats);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                combats = null;
            }
        }

        return result;
    }

    /**
     * Get the Combats associated with a Dresseur. Uses the route : dresseur/%Dresseur_id%/combat.
     * @param combats : The list in which the Combats will be returned
     * @param dresseur : The associated dresseur
     * @return The number of Combats returned
     */
    public int getByDresseur2(List<Combat> combats, Dresseur dresseur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        dresseur.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Combats", combats);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                combats = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Combat Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(CombatWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Combat from a JSONObject describing a Combat.
     * @param json The JSONObject describing the Combat
     * @param combat The returned Combat
     * @return true if a Combat was found. false if not
     */
    public boolean extract(JSONObject json, Combat combat) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(CombatWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_ID)) {
                    combat.setId(
                            json.getInt(CombatWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(CombatWebServiceClientAdapter.JSON_LANCELE)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_LANCELE)) {
                    DateTimeFormatter lanceLeFormatter = DateTimeFormat.forPattern(
                            CombatWebServiceClientAdapter.REST_UPDATE_DATE_FORMAT);
                    try {
                        combat.setLanceLe(
                                lanceLeFormatter.withOffsetParsed().parseDateTime(
                                        json.getString(
                                        CombatWebServiceClientAdapter.JSON_LANCELE)));
                    } catch (IllegalArgumentException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(CombatWebServiceClientAdapter.JSON_DUREE)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_DUREE)) {
                    combat.setDuree(
                            json.getInt(CombatWebServiceClientAdapter.JSON_DUREE));
                }

                if (json.has(CombatWebServiceClientAdapter.JSON_POKEMON1)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_POKEMON1)) {

                    try {
                        PokemonWebServiceClientAdapter pokemon1Adapter =
                                new PokemonWebServiceClientAdapter(this.context);
                        Pokemon pokemon1 =
                                new Pokemon();

                        if (pokemon1Adapter.extract(
                                json.optJSONObject(
                                        CombatWebServiceClientAdapter.JSON_POKEMON1),
                                        pokemon1)) {
                            combat.setPokemon1(pokemon1);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Pokemon data");
                    }
                }

                if (json.has(CombatWebServiceClientAdapter.JSON_POKEMON2)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_POKEMON2)) {

                    try {
                        PokemonWebServiceClientAdapter pokemon2Adapter =
                                new PokemonWebServiceClientAdapter(this.context);
                        Pokemon pokemon2 =
                                new Pokemon();

                        if (pokemon2Adapter.extract(
                                json.optJSONObject(
                                        CombatWebServiceClientAdapter.JSON_POKEMON2),
                                        pokemon2)) {
                            combat.setPokemon2(pokemon2);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Pokemon data");
                    }
                }

                if (json.has(CombatWebServiceClientAdapter.JSON_DRESSEUR1)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_DRESSEUR1)) {

                    try {
                        DresseurWebServiceClientAdapter dresseur1Adapter =
                                new DresseurWebServiceClientAdapter(this.context);
                        Dresseur dresseur1 =
                                new Dresseur();

                        if (dresseur1Adapter.extract(
                                json.optJSONObject(
                                        CombatWebServiceClientAdapter.JSON_DRESSEUR1),
                                        dresseur1)) {
                            combat.setDresseur1(dresseur1);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Dresseur data");
                    }
                }

                if (json.has(CombatWebServiceClientAdapter.JSON_DRESSEUR2)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_DRESSEUR2)) {

                    try {
                        DresseurWebServiceClientAdapter dresseur2Adapter =
                                new DresseurWebServiceClientAdapter(this.context);
                        Dresseur dresseur2 =
                                new Dresseur();

                        if (dresseur2Adapter.extract(
                                json.optJSONObject(
                                        CombatWebServiceClientAdapter.JSON_DRESSEUR2),
                                        dresseur2)) {
                            combat.setDresseur2(dresseur2);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Dresseur data");
                    }
                }

                if (json.has(CombatWebServiceClientAdapter.JSON_DRESSEUR1VAINQUEUR)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_DRESSEUR1VAINQUEUR)) {
                    combat.setDresseur1Vainqueur(
                            json.getBoolean(CombatWebServiceClientAdapter.JSON_DRESSEUR1VAINQUEUR));
                }

                if (json.has(CombatWebServiceClientAdapter.JSON_DRESSEUR2VAINQUEUR)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_DRESSEUR2VAINQUEUR)) {
                    combat.setDresseur2Vainqueur(
                            json.getBoolean(CombatWebServiceClientAdapter.JSON_DRESSEUR2VAINQUEUR));
                }

                if (json.has(CombatWebServiceClientAdapter.JSON_POKEMON1VAINQUEUR)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_POKEMON1VAINQUEUR)) {
                    combat.setPokemon1Vainqueur(
                            json.getBoolean(CombatWebServiceClientAdapter.JSON_POKEMON1VAINQUEUR));
                }

                if (json.has(CombatWebServiceClientAdapter.JSON_POKEMON2VAINQUEUR)
                        && !json.isNull(CombatWebServiceClientAdapter.JSON_POKEMON2VAINQUEUR)) {
                    combat.setPokemon2Vainqueur(
                            json.getBoolean(CombatWebServiceClientAdapter.JSON_POKEMON2VAINQUEUR));
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
        String id = json.optString(CombatWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[11];
                if (json.has(CombatWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(CombatWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(CombatWebServiceClientAdapter.JSON_LANCELE)) {
                    row[1] = json.getString(CombatWebServiceClientAdapter.JSON_LANCELE);
                }
                if (json.has(CombatWebServiceClientAdapter.JSON_DUREE)) {
                    row[2] = json.getString(CombatWebServiceClientAdapter.JSON_DUREE);
                }
                if (json.has(CombatWebServiceClientAdapter.JSON_POKEMON1)) {
                    JSONObject pokemon1Json = json.getJSONObject(
                            CombatWebServiceClientAdapter.JSON_POKEMON1);
                    row[3] = pokemon1Json.getString(
                            PokemonWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(CombatWebServiceClientAdapter.JSON_POKEMON2)) {
                    JSONObject pokemon2Json = json.getJSONObject(
                            CombatWebServiceClientAdapter.JSON_POKEMON2);
                    row[4] = pokemon2Json.getString(
                            PokemonWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(CombatWebServiceClientAdapter.JSON_DRESSEUR1)) {
                    JSONObject dresseur1Json = json.getJSONObject(
                            CombatWebServiceClientAdapter.JSON_DRESSEUR1);
                    row[5] = dresseur1Json.getString(
                            DresseurWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(CombatWebServiceClientAdapter.JSON_DRESSEUR2)) {
                    JSONObject dresseur2Json = json.getJSONObject(
                            CombatWebServiceClientAdapter.JSON_DRESSEUR2);
                    row[6] = dresseur2Json.getString(
                            DresseurWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(CombatWebServiceClientAdapter.JSON_DRESSEUR1VAINQUEUR)) {
                    row[7] = json.getString(CombatWebServiceClientAdapter.JSON_DRESSEUR1VAINQUEUR);
                }
                if (json.has(CombatWebServiceClientAdapter.JSON_DRESSEUR2VAINQUEUR)) {
                    row[8] = json.getString(CombatWebServiceClientAdapter.JSON_DRESSEUR2VAINQUEUR);
                }
                if (json.has(CombatWebServiceClientAdapter.JSON_POKEMON1VAINQUEUR)) {
                    row[9] = json.getString(CombatWebServiceClientAdapter.JSON_POKEMON1VAINQUEUR);
                }
                if (json.has(CombatWebServiceClientAdapter.JSON_POKEMON2VAINQUEUR)) {
                    row[10] = json.getString(CombatWebServiceClientAdapter.JSON_POKEMON2VAINQUEUR);
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
     * Convert a Combat to a JSONObject.
     * @param combat The Combat to convert
     * @return The converted Combat
     */
    public JSONObject itemToJson(Combat combat) {
        JSONObject params = new JSONObject();
        try {
            params.put(CombatWebServiceClientAdapter.JSON_ID,
                    combat.getId());

            if (combat.getLanceLe() != null) {
                params.put(CombatWebServiceClientAdapter.JSON_LANCELE,
                        combat.getLanceLe().toString(REST_UPDATE_DATE_FORMAT));
            }
            params.put(CombatWebServiceClientAdapter.JSON_DUREE,
                    combat.getDuree());

            if (combat.getPokemon1() != null) {
                PokemonWebServiceClientAdapter pokemon1Adapter =
                        new PokemonWebServiceClientAdapter(this.context);

                params.put(CombatWebServiceClientAdapter.JSON_POKEMON1,
                        pokemon1Adapter.itemIdToJson(combat.getPokemon1()));
            }

            if (combat.getPokemon2() != null) {
                PokemonWebServiceClientAdapter pokemon2Adapter =
                        new PokemonWebServiceClientAdapter(this.context);

                params.put(CombatWebServiceClientAdapter.JSON_POKEMON2,
                        pokemon2Adapter.itemIdToJson(combat.getPokemon2()));
            }

            if (combat.getDresseur1() != null) {
                DresseurWebServiceClientAdapter dresseur1Adapter =
                        new DresseurWebServiceClientAdapter(this.context);

                params.put(CombatWebServiceClientAdapter.JSON_DRESSEUR1,
                        dresseur1Adapter.itemIdToJson(combat.getDresseur1()));
            }

            if (combat.getDresseur2() != null) {
                DresseurWebServiceClientAdapter dresseur2Adapter =
                        new DresseurWebServiceClientAdapter(this.context);

                params.put(CombatWebServiceClientAdapter.JSON_DRESSEUR2,
                        dresseur2Adapter.itemIdToJson(combat.getDresseur2()));
            }
            params.put(CombatWebServiceClientAdapter.JSON_DRESSEUR1VAINQUEUR,
                    combat.isDresseur1Vainqueur());
            params.put(CombatWebServiceClientAdapter.JSON_DRESSEUR2VAINQUEUR,
                    combat.isDresseur2Vainqueur());
            params.put(CombatWebServiceClientAdapter.JSON_POKEMON1VAINQUEUR,
                    combat.isPokemon1Vainqueur());
            params.put(CombatWebServiceClientAdapter.JSON_POKEMON2VAINQUEUR,
                    combat.isPokemon2Vainqueur());
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
    public JSONObject itemIdToJson(Combat item) {
        JSONObject params = new JSONObject();
        try {
            params.put(CombatWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Combat to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(CombatWebServiceClientAdapter.JSON_ID,
                    values.get(CombatContract.COL_ID));
            params.put(CombatWebServiceClientAdapter.JSON_LANCELE,
                    new DateTime(values.get(
                            CombatContract.COL_LANCELE)).toString(REST_UPDATE_DATE_FORMAT));
            params.put(CombatWebServiceClientAdapter.JSON_DUREE,
                    values.get(CombatContract.COL_DUREE));
            PokemonWebServiceClientAdapter pokemon1Adapter =
                    new PokemonWebServiceClientAdapter(this.context);

            params.put(CombatWebServiceClientAdapter.JSON_POKEMON1,
                    pokemon1Adapter.contentValuesToJson(values));
            PokemonWebServiceClientAdapter pokemon2Adapter =
                    new PokemonWebServiceClientAdapter(this.context);

            params.put(CombatWebServiceClientAdapter.JSON_POKEMON2,
                    pokemon2Adapter.contentValuesToJson(values));
            DresseurWebServiceClientAdapter dresseur1Adapter =
                    new DresseurWebServiceClientAdapter(this.context);

            params.put(CombatWebServiceClientAdapter.JSON_DRESSEUR1,
                    dresseur1Adapter.contentValuesToJson(values));
            DresseurWebServiceClientAdapter dresseur2Adapter =
                    new DresseurWebServiceClientAdapter(this.context);

            params.put(CombatWebServiceClientAdapter.JSON_DRESSEUR2,
                    dresseur2Adapter.contentValuesToJson(values));
            params.put(CombatWebServiceClientAdapter.JSON_DRESSEUR1VAINQUEUR,
                    values.get(CombatContract.COL_DRESSEUR1VAINQUEUR));
            params.put(CombatWebServiceClientAdapter.JSON_DRESSEUR2VAINQUEUR,
                    values.get(CombatContract.COL_DRESSEUR2VAINQUEUR));
            params.put(CombatWebServiceClientAdapter.JSON_POKEMON1VAINQUEUR,
                    values.get(CombatContract.COL_POKEMON1VAINQUEUR));
            params.put(CombatWebServiceClientAdapter.JSON_POKEMON2VAINQUEUR,
                    values.get(CombatContract.COL_POKEMON2VAINQUEUR));
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
            List<Combat> items,
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
                Combat item = new Combat();
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
            List<Combat> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
