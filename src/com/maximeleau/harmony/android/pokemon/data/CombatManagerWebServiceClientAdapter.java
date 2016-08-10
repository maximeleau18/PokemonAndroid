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

    /**
     * Convert a CombatManager to a JSONObject.
     * @param combatManager The CombatManager to convert
     * @return The converted CombatManager
     */
    public JSONObject itemToJsonSend(CombatManager combatManager) {
        JSONObject params = new JSONObject();
        try {

            if (combatManager.getCombat() != null) {

                params.put(CombatManagerWebServiceClientAdapter.JSON_COMBAT,
                        combatManager.getCombat().getId());
            }

            if (combatManager.getAttaque() != null) {

                params.put(CombatManagerWebServiceClientAdapter.JSON_ATTAQUE,
                        combatManager.getAttaque().getId());
            }

            if (combatManager.getDresseur() != null) {

                params.put(CombatManagerWebServiceClientAdapter.JSON_DRESSEUR,
                        combatManager.getDresseur().getId());
            }

            params.put(CombatManagerWebServiceClientAdapter.JSON_ACTUAL_PV,
                    combatManager.getActualPv());

            params.put(CombatManagerWebServiceClientAdapter.JSON_DRESSEUR_ACTUAL_TURN_ID,
                    combatManager.getDresseurActualTurnId());

            params.put(CombatManagerWebServiceClientAdapter.JSON_CONSOLE,
                    combatManager.getConsole());

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }

    /**
     * Convert a CombatManager to a JSONObject.
     * @param combatManager The CombatManager to convert
     * @return The converted CombatManager
     */
    public JSONObject itemToJsonFull(CombatManager combatManager) {
        JSONObject params = new JSONObject();
        try {

            if (combatManager.getCombat() != null) {
                CombatWebServiceClientAdapter combatAdapter =
                        new CombatWebServiceClientAdapter(this.context);

                params.put("Combat",
                        combatAdapter.itemToJsonSend(combatManager.getCombat()));
            }

            if (combatManager.getAttaque() != null) {

                params.put(CombatManagerWebServiceClientAdapter.JSON_ATTAQUE,
                        combatManager.getAttaque().getId());
            }

            if (combatManager.getDresseur() != null) {

                params.put(CombatManagerWebServiceClientAdapter.JSON_DRESSEUR,
                        combatManager.getDresseur().getId());
            }

            params.put(CombatManagerWebServiceClientAdapter.JSON_ACTUAL_PV,
                    combatManager.getActualPv());

            params.put(CombatManagerWebServiceClientAdapter.JSON_DRESSEUR_ACTUAL_TURN_ID,
                    combatManager.getDresseurActualTurnId());

            params.put(CombatManagerWebServiceClientAdapter.JSON_CONSOLE,
                    combatManager.getConsole());

        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }

}
