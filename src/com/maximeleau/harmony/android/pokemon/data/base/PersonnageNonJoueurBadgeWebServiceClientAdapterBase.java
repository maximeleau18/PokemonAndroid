/**************************************************************************
 * PersonnageNonJoueurBadgeWebServiceClientAdapterBase.java, pokemon Android
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
import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueurBadge;
import com.maximeleau.harmony.android.pokemon.data.RestClient.Verb;
import com.maximeleau.harmony.android.pokemon.provider.contract.PersonnageNonJoueurBadgeContract;

import com.maximeleau.harmony.android.pokemon.entity.PersonnageNonJoueur;
import com.maximeleau.harmony.android.pokemon.entity.Badge;


/**
 *
 * <b><i>This class will be overwrited whenever you regenerate the project with Harmony.
 * You should edit PersonnageNonJoueurBadgeWebServiceClientAdapter class instead of this one or you will lose all your modifications.</i></b>
 *
 */
public abstract class PersonnageNonJoueurBadgeWebServiceClientAdapterBase
        extends WebServiceClientAdapter<PersonnageNonJoueurBadge> {
    /** PersonnageNonJoueurBadgeWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "PersonnageNonJoueurBadgeWSClientAdapter";

    /** JSON Object PersonnageNonJoueurBadge pattern. */
    protected static String JSON_OBJECT_PERSONNAGENONJOUEURBADGE = "PersonnageNonJoueurBadge";
    /** JSON_ID attributes. */
    protected static String JSON_ID = "id";
    /** JSON_PERSONNAGENONJOUEUR attributes. */
    protected static String JSON_PERSONNAGENONJOUEUR = "personnageNonJoueur";
    /** JSON_BADGE attributes. */
    protected static String JSON_BADGE = "badge";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** PersonnageNonJoueurBadge REST Columns. */
    public static String[] REST_COLS = new String[]{
            PersonnageNonJoueurBadgeContract.COL_ID,
            PersonnageNonJoueurBadgeContract.COL_PERSONNAGENONJOUEUR_ID,
            PersonnageNonJoueurBadgeContract.COL_BADGE_ID
        };

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public PersonnageNonJoueurBadgeWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public PersonnageNonJoueurBadgeWebServiceClientAdapterBase(Context context,
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
    public PersonnageNonJoueurBadgeWebServiceClientAdapterBase(Context context,
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
    public PersonnageNonJoueurBadgeWebServiceClientAdapterBase(Context context,
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
    public PersonnageNonJoueurBadgeWebServiceClientAdapterBase(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);

        
    }

    /**
     * Retrieve all the PersonnageNonJoueurBadges in the given list. Uses the route : PersonnageNonJoueurBadge.
     * @param personnageNonJoueurBadges : The list in which the PersonnageNonJoueurBadges will be returned
     * @return The number of PersonnageNonJoueurBadges returned
     */
    public int getAll(List<PersonnageNonJoueurBadge> personnageNonJoueurBadges) {
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
                result = extractItems(json, "PersonnageNonJoueurBadges", personnageNonJoueurBadges);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                personnageNonJoueurBadges = null;
            }
        }

        return result;
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "personnagenonjoueurbadge";
    }

    /**
     * Retrieve one PersonnageNonJoueurBadge. Uses the route : PersonnageNonJoueurBadge/%id%.
     * @param personnageNonJoueurBadge : The PersonnageNonJoueurBadge to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int get(PersonnageNonJoueurBadge personnageNonJoueurBadge) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.GET,
                    String.format(
                        this.getUri() + "/%s%s",
                        personnageNonJoueurBadge.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, personnageNonJoueurBadge)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                personnageNonJoueurBadge = null;
            }
        }

        return result;
    }

    /**
     * Retrieve one PersonnageNonJoueurBadge. Uses the route : PersonnageNonJoueurBadge/%id%.
     * @param personnageNonJoueurBadge : The PersonnageNonJoueurBadge to retrieve (set the  ID)
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
     * Update a PersonnageNonJoueurBadge. Uses the route : PersonnageNonJoueurBadge/%id%.
     * @param personnageNonJoueurBadge : The PersonnageNonJoueurBadge to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int update(PersonnageNonJoueurBadge personnageNonJoueurBadge) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.PUT,
                    String.format(
                        this.getUri() + "/%s%s",
                        personnageNonJoueurBadge.getId(),
                        REST_FORMAT),
                    itemToJson(personnageNonJoueurBadge));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, personnageNonJoueurBadge);
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    /**
     * Delete a PersonnageNonJoueurBadge. Uses the route : PersonnageNonJoueurBadge/%id%.
     * @param personnageNonJoueurBadge : The PersonnageNonJoueurBadge to delete (only the id is necessary)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int delete(PersonnageNonJoueurBadge personnageNonJoueurBadge) {
        int result = -1;
        String response = this.invokeRequest(
                    Verb.DELETE,
                    String.format(
                        this.getUri() + "/%s%s",
                        personnageNonJoueurBadge.getId(),
                        REST_FORMAT),
                    null);

        if (this.isValidResponse(response) && this.isValidRequest()) {
            result = 0;
        }

        return result;
    }

    /**
     * Get the PersonnageNonJoueurBadges associated with a PersonnageNonJoueur. Uses the route : personnagenonjoueur/%PersonnageNonJoueur_id%/personnagenonjoueurbadge.
     * @param personnageNonJoueurBadges : The list in which the PersonnageNonJoueurBadges will be returned
     * @param personnagenonjoueur : The associated personnagenonjoueur
     * @return The number of PersonnageNonJoueurBadges returned
     */
    public int getByPersonnageNonJoueur(List<PersonnageNonJoueurBadge> personnageNonJoueurBadges, PersonnageNonJoueur personnageNonJoueur) {
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
                result = this.extractItems(json, "PersonnageNonJoueurBadges", personnageNonJoueurBadges);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                personnageNonJoueurBadges = null;
            }
        }

        return result;
    }

    /**
     * Get the PersonnageNonJoueurBadges associated with a Badge. Uses the route : badge/%Badge_id%/personnagenonjoueurbadge.
     * @param personnageNonJoueurBadges : The list in which the PersonnageNonJoueurBadges will be returned
     * @param badge : The associated badge
     * @return The number of PersonnageNonJoueurBadges returned
     */
    public int getByBadge(List<PersonnageNonJoueurBadge> personnageNonJoueurBadges, Badge badge) {
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
                result = this.extractItems(json, "PersonnageNonJoueurBadges", personnageNonJoueurBadges);
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                personnageNonJoueurBadges = null;
            }
        }

        return result;
    }


    /**
     * Tests if the json is a valid PersonnageNonJoueurBadge Object.
     *
     * @param json The json
     *
     * @return True if valid
     */
    public boolean isValidJSON(JSONObject json) {
        boolean result = true;
        result = result && json.has(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_ID);

        return result;
    }

    /**
     * Extract a PersonnageNonJoueurBadge from a JSONObject describing a PersonnageNonJoueurBadge.
     * @param json The JSONObject describing the PersonnageNonJoueurBadge
     * @param personnageNonJoueurBadge The returned PersonnageNonJoueurBadge
     * @return true if a PersonnageNonJoueurBadge was found. false if not
     */
    public boolean extract(JSONObject json, PersonnageNonJoueurBadge personnageNonJoueurBadge) {
        boolean result = this.isValidJSON(json);
        if (result) {
            try {

                if (json.has(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_ID)
                        && !json.isNull(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_ID)) {
                    personnageNonJoueurBadge.setId(
                            json.getInt(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_ID));
                }

                if (json.has(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)
                        && !json.isNull(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)) {

                    try {
                        PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                                new PersonnageNonJoueurWebServiceClientAdapter(this.context);
                        PersonnageNonJoueur personnageNonJoueur =
                                new PersonnageNonJoueur();

                        if (personnageNonJoueurAdapter.extract(
                                json.optJSONObject(
                                        PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR),
                                        personnageNonJoueur)) {
                            personnageNonJoueurBadge.setPersonnageNonJoueur(personnageNonJoueur);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PersonnageNonJoueur data");
                    }
                }

                if (json.has(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_BADGE)
                        && !json.isNull(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_BADGE)) {

                    try {
                        BadgeWebServiceClientAdapter badgeAdapter =
                                new BadgeWebServiceClientAdapter(this.context);
                        Badge badge =
                                new Badge();

                        if (badgeAdapter.extract(
                                json.optJSONObject(
                                        PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_BADGE),
                                        badge)) {
                            personnageNonJoueurBadge.setBadge(badge);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Badge data");
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
        String id = json.optString(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_ID, null);
        if (id != null) {
            try {
                String[] row = new String[3];
                if (json.has(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_ID)) {
                    row[0] = json.getString(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR)) {
                    JSONObject personnageNonJoueurJson = json.getJSONObject(
                            PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR);
                    row[1] = personnageNonJoueurJson.getString(
                            PersonnageNonJoueurWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_BADGE)) {
                    JSONObject badgeJson = json.getJSONObject(
                            PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_BADGE);
                    row[2] = badgeJson.getString(
                            BadgeWebServiceClientAdapter.JSON_ID);
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
     * Convert a PersonnageNonJoueurBadge to a JSONObject.
     * @param personnageNonJoueurBadge The PersonnageNonJoueurBadge to convert
     * @return The converted PersonnageNonJoueurBadge
     */
    public JSONObject itemToJson(PersonnageNonJoueurBadge personnageNonJoueurBadge) {
        JSONObject params = new JSONObject();
        try {
            params.put(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_ID,
                    personnageNonJoueurBadge.getId());

            if (personnageNonJoueurBadge.getPersonnageNonJoueur() != null) {
                PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                        new PersonnageNonJoueurWebServiceClientAdapter(this.context);

                params.put(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR,
                        personnageNonJoueurAdapter.itemIdToJson(personnageNonJoueurBadge.getPersonnageNonJoueur()));
            }

            if (personnageNonJoueurBadge.getBadge() != null) {
                BadgeWebServiceClientAdapter badgeAdapter =
                        new BadgeWebServiceClientAdapter(this.context);

                params.put(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_BADGE,
                        badgeAdapter.itemIdToJson(personnageNonJoueurBadge.getBadge()));
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
    public JSONObject itemIdToJson(PersonnageNonJoueurBadge item) {
        JSONObject params = new JSONObject();
        try {
            params.put(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_ID, item.getId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }


    /**
     * Converts a content value reprensenting a PersonnageNonJoueurBadge to a JSONObject.
     * @param The content values
     * @return The JSONObject
     */
    public JSONObject contentValuesToJson(ContentValues values) {
        JSONObject params = new JSONObject();

        try {
            params.put(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_ID,
                    values.get(PersonnageNonJoueurBadgeContract.COL_ID));
            PersonnageNonJoueurWebServiceClientAdapter personnageNonJoueurAdapter =
                    new PersonnageNonJoueurWebServiceClientAdapter(this.context);

            params.put(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_PERSONNAGENONJOUEUR,
                    personnageNonJoueurAdapter.contentValuesToJson(values));
            BadgeWebServiceClientAdapter badgeAdapter =
                    new BadgeWebServiceClientAdapter(this.context);

            params.put(PersonnageNonJoueurBadgeWebServiceClientAdapter.JSON_BADGE,
                    badgeAdapter.contentValuesToJson(values));
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
            List<PersonnageNonJoueurBadge> items,
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
                PersonnageNonJoueurBadge item = new PersonnageNonJoueurBadge();
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
            List<PersonnageNonJoueurBadge> items) throws JSONException {

        return this.extractItems(json, paramName, items, 0);
    }

}
