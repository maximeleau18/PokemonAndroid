/**************************************************************************
 * AreneWebServiceClientAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.Arene;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.AreneContract;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Badge;
import com.maximeleau.harmony.android.pokemon.entity.Position;
import com.maximeleau.harmony.android.pokemon.entity.Zone;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit AreneWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class AreneWebServiceClientAdapterBase
        extends WebServiceClientAdapter<Arene> {
    /** AreneWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "AreneWSClientAdapter";

    /** JSON Object Arene pattern. */
    protected static String JSON_OBJECT_ARENE = "Arene";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_NOM attributes. */
    protected static String JSON_NOM = "nom";
    /** JSON_MAITRE attributes. */
    protected static String JSON_MAITRE = "maitre";
    /** JSON_BADGE attributes. */
    protected static String JSON_BADGE = "badge";
    /** JSON_POSITION attributes. */
    protected static String JSON_POSITION = "position";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** Arene REST Columns. */
    public static String[] REST_COLS = new String[]{
            AreneContract.COL_ID,
            AreneContract.COL_NOM,
            AreneContract.COL_MAITRE_ID,
            AreneContract.COL_BADGE_ID,
            AreneContract.COL_POSITION_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public AreneWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public AreneWebServiceClientAdapterBase(Context context,
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
    public AreneWebServiceClientAdapterBase(Context context,
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
    public AreneWebServiceClientAdapterBase(Context context,
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
    public AreneWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the Arenes in the given list. Uses the route : Arene.
     * @param arenes : The list in which the Arenes will be returned
     * @return The number of Arenes returned
     */
    public int getAll(List<Arene> arenes) {
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
                result = extractItems(json, "Arenes", arenes);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                arenes = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "arene";
    }

    /**
     * Retrieve one Arene. Uses the route : Arene/%id%.
     * @param arene : The Arene to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(Arene arene) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        arene.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, arene)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                arene = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one Arene. Uses the route : Arene/%id%.
     * @param arene : The Arene to retrieve (set the  ID)
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
     * Update a Arene. Uses the route : Arene/%id%.
     * @param arene : The Arene to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(Arene arene) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        arene.getId(),
                        REST_FORMAT),
                    itemToJson(arene));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, arene);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a Arene. Uses the route : Arene/%id%.
     * @param arene : The Arene to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(Arene arene) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        arene.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the Arenes associated with a PersonnageNonJoueur. Uses the route : personnagenonjoueur/%PersonnageNonJoueur_id%/arene.
     * @param arenes : The list in which the Arenes will be returned
     * @param personnagenonjoueur : The associated personnagenonjoueur
     * @return The number of Arenes returned
     */
    public int getByMaitre(List<Arene> arenes, PersonnageNonJoueur personnageNonJoueur) {
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
                result = this.extractItems(json, "Arenes", arenes);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                arenes = null;
            }
        }

        return result;
    }

    /**
     * Get the Arene associated with a Badge. Uses the route : badge/%Badge_id%/arene.
     * @param arene : The Arene that will be returned
     * @param badge : The associated badge
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByBadge(Arene arene, Badge badge) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        badge.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, arene);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                arene = null;
            }
        }

        return result;
    }

    /**
     * Get the Arene associated with a Position. Uses the route : position/%Position_id%/arene.
     * @param arene : The Arene that will be returned
     * @param position : The associated position
     * @return -1 if an error has occurred. 0 if not.
     */
    public int getByPosition(Arene arene, Position position) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        position.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, arene);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                arene = null;
            }
        }

        return result;
    }

    /**
     * Get the Arenes associated with a Zone. Uses the route : zone/%Zone_id%/arene.
     * @param arenes : The list in which the Arenes will be returned
     * @param zone : The associated zone
     * @return The number of Arenes returned
     */
    public int getByZonearenesInternal(List<Arene> arenes, Zone zone) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        zone.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                result = this.extractItems(json, "Arenes", arenes);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                arenes = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid Arene Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(AreneWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a Arene from a JSONObject describing a Arene.
     * @param json The JSONObject describing the Arene
     * @param arene The returned Arene
     * @return true if a Arene was found. false if not
     */
    public boolean extract(JSONObject json, Arene arene) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(AreneWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(AreneWebServiceClientAdapter.JSON_ID)) {
                    arene.setId(
                            json.getInt(AreneWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(AreneWebServiceClientAdapter.JSON_NOM)
                        && !json.isNull(AreneWebServiceClientAdapter.JSON_NOM)) {
                    arene.setNom(
                            json.getString(AreneWebServiceClientAdapter.JSON_NOM));
                }

                if (json.has(AreneWebServiceClientAdapter.JSON_MAITRE)
                        && !json.isNull(AreneWebServiceClientAdapter.JSON_MAITRE)) {

                    try {
                        PersonnageNonJoueurWebServiceClientAdapter maitreAdapter =
                                new PersonnageNonJoueurWebServiceClientAdapter(this.context);
                        PersonnageNonJoueur maitre =
                                new PersonnageNonJoueur();

                        if (maitreAdapter.extract(
                                json.optJSONObject(
                                        AreneWebServiceClientAdapter.JSON_MAITRE),
                                        maitre)) {
                            arene.setMaitre(maitre);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PersonnageNonJoueur data");
                    }
                }

                if (json.has(AreneWebServiceClientAdapter.JSON_BADGE)
                        && !json.isNull(AreneWebServiceClientAdapter.JSON_BADGE)) {

                    try {
                        BadgeWebServiceClientAdapter badgeAdapter =
                                new BadgeWebServiceClientAdapter(this.context);
                        Badge badge =
                                new Badge();

                        if (badgeAdapter.extract(
                                json.optJSONObject(
                                        AreneWebServiceClientAdapter.JSON_BADGE),
                                        badge)) {
                            arene.setBadge(badge);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Badge data");
                    }
                }

                if (json.has(AreneWebServiceClientAdapter.JSON_POSITION)
                        && !json.isNull(AreneWebServiceClientAdapter.JSON_POSITION)) {

                    try {
                        PositionWebServiceClientAdapter positionAdapter =
                                new PositionWebServiceClientAdapter(this.context);
                        Position position =
                                new Position();

                        if (positionAdapter.extract(
                                json.optJSONObject(
                                        AreneWebServiceClientAdapter.JSON_POSITION),
                                        position)) {
                            arene.setPosition(position);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Position data");
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
        String id = json.optString(AreneWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[5];
                if (json.has(AreneWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(AreneWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(AreneWebServiceClientAdapter.JSON_NOM)) {
                    row[1] = json.getString(AreneWebServiceClientAdapter.JSON_NOM);
                }
                if (json.has(AreneWebServiceClientAdapter.JSON_MAITRE)) {
                    JSONObject maitreJson = json.getJSONObject(
                            AreneWebServiceClientAdapter.JSON_MAITRE);
                    row[2] = maitreJson.getString(
                            PersonnageNonJoueurWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(AreneWebServiceClientAdapter.JSON_BADGE)) {
                    JSONObject badgeJson = json.getJSONObject(
                            AreneWebServiceClientAdapter.JSON_BADGE);
                    row[3] = badgeJson.getString(
                            BadgeWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(AreneWebServiceClientAdapter.JSON_POSITION)) {
                    JSONObject positionJson = json.getJSONObject(
                            AreneWebServiceClientAdapter.JSON_POSITION);
                    row[4] = positionJson.getString(
                            PositionWebServiceClientAdapter.JSON_ID);
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
     * Convert a Arene to a JSONObject.
     * @param arene The Arene to convert
     * @return The converted Arene
     */
    public JSONObject itemToJson(Arene arene) {
        JSONObject params = new JSONObject();
        try {
            params.put(AreneWebServiceClientAdapter.JSON_ID,
                    arene.getId());
            params.put(AreneWebServiceClientAdapter.JSON_NOM,
                    arene.getNom());

            if (arene.getMaitre() != null) {
                PersonnageNonJoueurWebServiceClientAdapter maitreAdapter =
                        new PersonnageNonJoueurWebServiceClientAdapter(this.context);

                params.put(AreneWebServiceClientAdapter.JSON_MAITRE,
                        maitreAdapter.itemIdToJson(arene.getMaitre()));
            }

            if (arene.getBadge() != null) {
                BadgeWebServiceClientAdapter badgeAdapter =
                        new BadgeWebServiceClientAdapter(this.context);

                params.put(AreneWebServiceClientAdapter.JSON_BADGE,
                        badgeAdapter.itemIdToJson(arene.getBadge()));
            }

            if (arene.getPosition() != null) {
                PositionWebServiceClientAdapter positionAdapter =
                        new PositionWebServiceClientAdapter(this.context);

                params.put(AreneWebServiceClientAdapter.JSON_POSITION,
                        positionAdapter.itemIdToJson(arene.getPosition()));
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
    public JSONObject itemIdToJson(Arene item) {
        JSONObject params = new JSONObject();
        try {
            params.put(AreneWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a Arene to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(AreneWebServiceClientAdapter.JSON_ID,
                    values.get(AreneContract.COL_ID));
            params.put(AreneWebServiceClientAdapter.JSON_NOM,
                    values.get(AreneContract.COL_NOM));
            PersonnageNonJoueurWebServiceClientAdapter maitreAdapter =
                    new PersonnageNonJoueurWebServiceClientAdapter(this.context);

            params.put(AreneWebServiceClientAdapter.JSON_MAITRE,
                    maitreAdapter.contentValuesToJson(values));
            BadgeWebServiceClientAdapter badgeAdapter =
                    new BadgeWebServiceClientAdapter(this.context);

            params.put(AreneWebServiceClientAdapter.JSON_BADGE,
                    badgeAdapter.contentValuesToJson(values));
            PositionWebServiceClientAdapter positionAdapter =
                    new PositionWebServiceClientAdapter(this.context);

            params.put(AreneWebServiceClientAdapter.JSON_POSITION,
                    positionAdapter.contentValuesToJson(values));
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
            List<Arene> items,
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
                Arene item = new Arene();
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
            List<Arene> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
