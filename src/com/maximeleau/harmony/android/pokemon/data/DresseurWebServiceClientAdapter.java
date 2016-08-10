/**************************************************************************
 * DresseurWebServiceClientAdapter.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.data.base.DresseurWebServiceClientAdapterBase;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Rest class for {@link Dresseur} WebServiceClient adapters.
 */
public class DresseurWebServiceClientAdapter
        extends DresseurWebServiceClientAdapterBase {

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public DresseurWebServiceClientAdapter(Context context) {
        super(context);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public DresseurWebServiceClientAdapter(Context context,
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
    public DresseurWebServiceClientAdapter(Context context,
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
    public DresseurWebServiceClientAdapter(Context context,
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
    public DresseurWebServiceClientAdapter(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);
    }

    /**
     * Retrieve one Dresseur. Uses the route : Dresseur/connexion.
     * @param dresseur : The Dresseur to retrieve (set the login and password)
     * @return -1 if an error has occurred. 0 if not.
     */
    public int connectDresseur(Dresseur dresseur) {
        int result = -1;
        String response = this.invokeRequest(
                RestClient.Verb.POST,
                String.format(
                        this.getUri() + "/connexion",
                        dresseur.getLogin(),
                        dresseur.getMotDePasse(),
                        REST_FORMAT),
                itemToJson(dresseur));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, dresseur)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                dresseur = null;
            }
        }

        return result;
    }

}
