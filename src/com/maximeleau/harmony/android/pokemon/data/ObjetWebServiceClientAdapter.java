/**************************************************************************
 * ObjetWebServiceClientAdapter.java, pokemon Android
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

import com.maximeleau.harmony.android.pokemon.data.base.ObjetWebServiceClientAdapterBase;
import com.maximeleau.harmony.android.pokemon.entity.Objet;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Rest class for {@link Objet} WebServiceClient adapters.
 */
public class ObjetWebServiceClientAdapter
        extends ObjetWebServiceClientAdapterBase {

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public ObjetWebServiceClientAdapter(Context context) {
        super(context);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public ObjetWebServiceClientAdapter(Context context,
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
    public ObjetWebServiceClientAdapter(Context context,
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
    public ObjetWebServiceClientAdapter(Context context,
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
    public ObjetWebServiceClientAdapter(Context context,
            String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);
    }

}
