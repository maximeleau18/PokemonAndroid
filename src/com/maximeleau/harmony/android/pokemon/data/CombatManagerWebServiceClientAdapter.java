package com.maximeleau.harmony.android.pokemon.data;

import android.content.Context;
import android.util.Log;

import com.maximeleau.harmony.android.pokemon.data.base.CombatManagerWebServiceClientAdapterBase;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class CombatManagerWebServiceClientAdapter extends CombatManagerWebServiceClientAdapterBase {

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public CombatManagerWebServiceClientAdapter(Context context) {
        super(context);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public CombatManagerWebServiceClientAdapter(Context context,
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
    public CombatManagerWebServiceClientAdapter(Context context,
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
    public CombatManagerWebServiceClientAdapter(Context context,
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
    public CombatManagerWebServiceClientAdapter(Context context,
                                                String host, Integer port, String scheme, String prefix) {
        super(context, host, port, scheme, prefix);
    }

    /**
     * Retrieve one CombatManager. Uses the route : CombatManager/launchattack.
     * @param combatManager : The CombatManager to retrieve
     * @return -1 if an error has occurred. 0 if not.
     */
    public int launchAttack(CombatManager combatManager) {
        int result = -1;
        String response = this.invokeRequest(
                RestClient.Verb.POST,
                String.format(
                        this.getUri() + "/launchattack",
                        itemToJsonSend(combatManager),
                        REST_FORMAT),
                itemToJsonSend(combatManager));

        if (this.isValidResponse(response) && this.isValidRequest()) {
            try {
                JSONObject json = new JSONObject(response);
                if (extract(json, combatManager)) {
                    result = 0;
                }
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
                combatManager = null;
            }
        }

        return result;
    }
}
