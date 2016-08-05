/**************************************************************************
 * PersonnageNonJoueurWebServiceClientAdapterBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
 *
 **************************************************************************/

package com.maximeleau.harmony.android.pokemon.data.base;

import java.util.List;
import java.util.ArrayList;

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
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurContract;

import com.maximeleau.harmony.android.pokemon.entity.Profession;
import com.maximeleau.harmony.android.pokemon.entity.Objet;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PersonnageNonJoueurWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PersonnageNonJoueurWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PersonnageNonJoueur> {
    /** PersonnageNonJoueurWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PersonnageNonJoueurWSClientAdapter";

    /** JSON Object PersonnageNonJoueur pattern. */
    protected static String JSON_OBJECT_PERSONNAGENONJOUEUR = "PersonnageNonJoueur";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_DESCRIPTION attributes. */
    protected static String JSON_DESCRIPTION = "description";
    /** JSON_URLIMAGE attributes. */
    protected static String JSON_URLIMAGE = "urlImage";
    /** JSON_PROFESSION attributes. */
    protected static String JSON_PROFESSION = "profession";
    /** JSON_OBJETS attributes. */
    protected static String JSON_OBJETS = "objets";
    /** JSON_DRESSEURS attributes. */
    protected static String JSON_DRESSEURS = "dresseurs";
    /** JSON_ARENES attributes. */
    protected static String JSON_ARENES = "arenes";
    /** JSON_POKEMONS attributes. */
    protected static String JSON_POKEMONS = "pokemons";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PersonnageNonJoueur REST Columns. */
    public static String[] REST_COLS = new String[]{
            PersonnageNonJoueurContract.COL_ID,
            PersonnageNonJoueurContract.COL_NOM,
            PersonnageNonJoueurContract.COL_DESCRIPTION,
            PersonnageNonJoueurContract.COL_URLIMAGE,
            PersonnageNonJoueurContract.COL_PROFESSION_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PersonnageNonJoueurWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PersonnageNonJoueurWebServiceClientAdapterBase(Context context,
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
    public PersonnageNonJoueurWebServiceClientAdapterBase(Context context,
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
    public PersonnageNonJoueurWebServiceClientAdapterBase(Context context,
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
    public PersonnageNonJoueurWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PersonnageNonJoueurs in the given list. Uses the route : PersonnageNonJoueur.
     * @param personnageNonJoueurs : The list in which the PersonnageNonJoueurs will be returned
     * @return The number of PersonnageNonJoueurs returned
     */
    public int getAll(List<PersonnageNonJoueur> personnageNonJoueurs) {
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
                result = extractItems(json, "PersonnageNonJoueurs", personnageNonJoueurs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                personnageNonJoueurs = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "personnagenonjoueur";
    }

    /**
     * Retrieve one PersonnageNonJoueur. Uses the route : PersonnageNonJoueur/%id%.
     * @param personnageNonJoueur : The PersonnageNonJoueur to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PersonnageNonJoueur personnageNonJoueur) {
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
                if (extract(json, personnageNonJoueur)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                personnageNonJoueur = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PersonnageNonJoueur. Uses the route : PersonnageNonJoueur/%id%.
     * @param personnageNonJoueur : The PersonnageNonJoueur to retrieve (set the  ID)
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
     * Update a PersonnageNonJoueur. Uses the route : PersonnageNonJoueur/%id%.
     * @param personnageNonJoueur : The PersonnageNonJoueur to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PersonnageNonJoueur personnageNonJoueur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        personnageNonJoueur.getId(),
                        REST_FORMAT),
                    itemToJson(personnageNonJoueur));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, personnageNonJoueur);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PersonnageNonJoueur. Uses the route : PersonnageNonJoueur/%id%.
     * @param personnageNonJoueur : The PersonnageNonJoueur to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PersonnageNonJoueur personnageNonJoueur) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        personnageNonJoueur.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the PersonnageNonJoueurs associated with a Profession. Uses the route : profession/%Profession_id%/personnagenonjoueur.
     * @param personnageNonJoueurs : The list in which the PersonnageNonJoueurs will be returned
     * @param profession : The associated profession
     * @return The number of PersonnageNonJoueurs returned
     */
    public int getByProfession(List<PersonnageNonJoueur> personnageNonJoueurs, Profession profession) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        profession.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "PersonnageNonJoueurs", personnageNonJoueurs);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                personnageNonJoueurs = null;
            }
        }

        return result;
    }






    /**
     * Tests if the json is a valid PersonnageNonJoueur Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PersonnageNonJoueur from a JSONObject describing a PersonnageNonJoueur.
     * @param json The JSONObject describing the PersonnageNonJoueur
     * @param personnageNonJoueur The returned PersonnageNonJoueur
     * @return true if a PersonnageNonJoueur was found. false if not
     */
    public boolean extract(JSONObject json, PersonnageNonJoueur personnageNonJoueur) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PersonnageNonJoueurWebServiceClientAdapter.JSON_ID)) {
                    personnageNonJoueur.setId(
                            json.getInt(PersonnageNonJoueurWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(PersonnageNonJoueurWebServiceClientAdapter.JSON_NOM)) {
                    personnageNonJoueur.setNom(
                            json.getString(PersonnageNonJoueurWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_DESCRIPTION)
                        && !json.isNull(PersonnageNonJoueurWebServiceClientAdapter.JSON_DESCRIPTION)) {
                    personnageNonJoueur.setDescription(
                            json.getString(PersonnageNonJoueurWebServiceClientAdapter.JSON_DESCRIPTION));
                }

                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_URLIMAGE)
                        && !json.isNull(PersonnageNonJoueurWebServiceClientAdapter.JSON_URLIMAGE)) {
                    personnageNonJoueur.setUrlImage(
                            json.getString(PersonnageNonJoueurWebServiceClientAdapter.JSON_URLIMAGE));
                }

                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_PROFESSION)
                        && !json.isNull(PersonnageNonJoueurWebServiceClientAdapter.JSON_PROFESSION)) {

                    try {
                        ProfessionWebServiceClientAdapter professionAdapter =
                                new ProfessionWebServiceClientAdapter(this.context);
                        Profession profession =
                                new Profession();

                        if (professionAdapter.extract(
                                json.optJSONObject(
                                        PersonnageNonJoueurWebServiceClientAdapter.JSON_PROFESSION),
                                        profession)) {
                            personnageNonJoueur.setProfession(profession);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Profession data");
                    }
                }

                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_OBJETS)
                        && !json.isNull(PersonnageNonJoueurWebServiceClientAdapter.JSON_OBJETS)) {
                    ArrayList<Objet> objets =
                            new ArrayList<Objet>();
                    ObjetWebServiceClientAdapter objetsAdapter =
                            new ObjetWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PersonnageNonJoueurWebServiceClientAdapter.JSON_OBJETS);
                        objetsAdapter.extractItems(
                                json, PersonnageNonJoueurWebServiceClientAdapter.JSON_OBJETS,
                                objets);
                        personnageNonJoueur.setObjets(objets);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_DRESSEURS)
                        && !json.isNull(PersonnageNonJoueurWebServiceClientAdapter.JSON_DRESSEURS)) {
                    ArrayList<Dresseur> dresseurs =
                            new ArrayList<Dresseur>();
                    DresseurWebServiceClientAdapter dresseursAdapter =
                            new DresseurWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PersonnageNonJoueurWebServiceClientAdapter.JSON_DRESSEURS);
                        dresseursAdapter.extractItems(
                                json, PersonnageNonJoueurWebServiceClientAdapter.JSON_DRESSEURS,
                                dresseurs);
                        personnageNonJoueur.setDresseurs(dresseurs);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_ARENES)
                        && !json.isNull(PersonnageNonJoueurWebServiceClientAdapter.JSON_ARENES)) {
                    ArrayList<Arene> arenes =
                            new ArrayList<Arene>();
                    AreneWebServiceClientAdapter arenesAdapter =
                            new AreneWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PersonnageNonJoueurWebServiceClientAdapter.JSON_ARENES);
                        arenesAdapter.extractItems(
                                json, PersonnageNonJoueurWebServiceClientAdapter.JSON_ARENES,
                                arenes);
                        personnageNonJoueur.setArenes(arenes);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_POKEMONS)
                        && !json.isNull(PersonnageNonJoueurWebServiceClientAdapter.JSON_POKEMONS)) {
                    ArrayList<Pokemon> pokemons =
                            new ArrayList<Pokemon>();
                    PokemonWebServiceClientAdapter pokemonsAdapter =
                            new PokemonWebServiceClientAdapter(this.context);

                    try {
                        //.optJSONObject(PersonnageNonJoueurWebServiceClientAdapter.JSON_POKEMONS);
                        pokemonsAdapter.extractItems(
                                json, PersonnageNonJoueurWebServiceClientAdapter.JSON_POKEMONS,
                                pokemons);
                        personnageNonJoueur.setPokemons(pokemons);
                    } catch (JSONException e) {
                        Log.e(TAG, e.getMessage());
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
        String id = json.optString(PersonnageNonJoueurWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[5];
                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PersonnageNonJoueurWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(PersonnageNonJoueurWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_DESCRIPTION)) {
                    row[2] = json.getString(PersonnageNonJoueurWebServiceClientAdapter.JSON_DESCRIPTION);
                }
                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_URLIMAGE)) {
                    row[3] = json.getString(PersonnageNonJoueurWebServiceClientAdapter.JSON_URLIMAGE);
                }
                if (json.has(PersonnageNonJoueurWebServiceClientAdapter.JSON_PROFESSION)) {
                    JSONObject professionJson = json.getJSONObject(
                            PersonnageNonJoueurWebServiceClientAdapter.JSON_PROFESSION);
                    row[4] = professionJson.getString(
                            ProfessionWebServiceClientAdapter.JSON_ID);
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
     * Convert a PersonnageNonJoueur to a JSONObject.
     * @param personnageNonJoueur The PersonnageNonJoueur to convert
     * @return The converted PersonnageNonJoueur
     */
    public JSONObject itemToJson(PersonnageNonJoueur personnageNonJoueur) {
        JSONObject params = new JSONObject();
        try {
            params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_ID,
                    personnageNonJoueur.getId());
            params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_NOM,
                    personnageNonJoueur.getNom());
            params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_DESCRIPTION,
                    personnageNonJoueur.getDescription());
            params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_URLIMAGE,
                    personnageNonJoueur.getUrlImage());

            if (personnageNonJoueur.getProfession() != null) {
                ProfessionWebServiceClientAdapter professionAdapter =
                        new ProfessionWebServiceClientAdapter(this.context);

                params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_PROFESSION,
                        professionAdapter.itemIdToJson(personnageNonJoueur.getProfession()));
            }

            if (personnageNonJoueur.getObjets() != null) {
                ObjetWebServiceClientAdapter objetsAdapter =
                        new ObjetWebServiceClientAdapter(this.context);

                params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_OBJETS,
                        objetsAdapter.itemsIdToJson(personnageNonJoueur.getObjets()));
            }

            if (personnageNonJoueur.getDresseurs() != null) {
                DresseurWebServiceClientAdapter dresseursAdapter =
                        new DresseurWebServiceClientAdapter(this.context);

                params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_DRESSEURS,
                        dresseursAdapter.itemsIdToJson(personnageNonJoueur.getDresseurs()));
            }

            if (personnageNonJoueur.getArenes() != null) {
                AreneWebServiceClientAdapter arenesAdapter =
                        new AreneWebServiceClientAdapter(this.context);

                params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_ARENES,
                        arenesAdapter.itemsIdToJson(personnageNonJoueur.getArenes()));
            }

            if (personnageNonJoueur.getPokemons() != null) {
                PokemonWebServiceClientAdapter pokemonsAdapter =
                        new PokemonWebServiceClientAdapter(this.context);

                params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_POKEMONS,
                        pokemonsAdapter.itemsIdToJson(personnageNonJoueur.getPokemons()));
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
    public JSONObject itemIdToJson(PersonnageNonJoueur item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PersonnageNonJoueur to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_ID,
                    values.get(PersonnageNonJoueurContract.COL_ID));
            params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_NOM,
                    values.get(PersonnageNonJoueurContract.COL_NOM));
            params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_DESCRIPTION,
                    values.get(PersonnageNonJoueurContract.COL_DESCRIPTION));
            params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_URLIMAGE,
                    values.get(PersonnageNonJoueurContract.COL_URLIMAGE));
            ProfessionWebServiceClientAdapter professionAdapter =
                    new ProfessionWebServiceClientAdapter(this.context);

            params.put(PersonnageNonJoueurWebServiceClientAdapter.JSON_PROFESSION,
                    professionAdapter.contentValuesToJson(values));
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
            List<PersonnageNonJoueur> items,
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
                PersonnageNonJoueur item = new PersonnageNonJoueur();
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
            List<PersonnageNonJoueur> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
