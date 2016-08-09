/**************************************************************************
 * CombatWebServiceClientAdapter.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 21, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.data;

import android.content.Context;
import android.util.Log;

import com.maximeleau.harmony.android.pokemon.data.base.CombatWebServiceClientAdapterBase;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Rest class for {@link Combat} WebServiceClient adapters.
 */
public class CombatWebServiceClientAdapter
        extends CombatWebServiceClientAdapterBase {

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public CombatWebServiceClientAdapter(Context context) {
        super(context);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public CombatWebServiceClientAdapter(Context context,
            Integer port) {
        super(context, port);
    }

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     */
    public CombatWebServiceClientAdapter(Context context,
            String host, Integer port) {
        super(context, host, port);
    }
    

    /**
     * Constructor with overriden port, host and scheme.
     *
     * @param context The context
     * @param host The overriden host
     * @param port The overriden port
     * @param scheme The overriden scheme
     */
    public CombatWebServiceClientAdapter(Context context,
            String host, Integer port, String scheme) {
        super(context, host, port, scheme);
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
    public CombatWebServiceClientAdapter(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);


    }

    /**
     * Retrieve one Combat. Uses the route : Combat/searchemptycombat.
     * @param combat : The Combat to retrieve (set pokemon2 and dresseur2)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int searchEmptyFight(Combat combat) {
        int result = -1;
        String response = this.invokeRequest(
                RestClient.Verb.POST,
                String.format(
                        this.getUri() + "/searchemptycombat",
                        itemToJsonSend(combat),
                        REST_FORMAT),
                itemToJsonSend(combat));

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
     * Create Combat. Uses the route : Combat/.
     * @param combat : The Combat to create
     * @return -1 if an error has occurred. 0 if not.
     */
    public int createFight(Combat combat) {
        int result = -1;
        String response = this.invokeRequest(
                RestClient.Verb.POST,
                        this.getUri(),
                itemToJsonSend(combat));

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
     * @param combat : The Combat to retrieve (set the ID)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int waitingForDresseur2(Combat combat) {
        int result = -1;

        String response = this.invokeRequest(
                RestClient.Verb.GET,
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
     * Update a Combat. Uses the route : Combat/%id%.
     * @param combatManager : The CombatManager with combat to update
     * @return -1 if an error has occurred. 0 if not.
     */
    public int updateFromCombatManager(CombatManager combatManager) {
        int result = -1;
        CombatManagerWebServiceClientAdapter combatManagerWebServiceClientAdapter = new CombatManagerWebServiceClientAdapter(this.context);
        String response = this.invokeRequest(
                RestClient.Verb.PUT,
                String.format(
                        this.getUri() + "/%s%s",
                        combatManager.getCombat().getId(),
                        REST_FORMAT),
                combatManagerWebServiceClientAdapter.itemToJsonSend(combatManager));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                this.extract(json, combatManager.getCombat());
                result = 0;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }
}
