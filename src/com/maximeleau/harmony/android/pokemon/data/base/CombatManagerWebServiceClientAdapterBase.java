package com.maximeleau.harmony.android.pokemon.data.base;

import android.app.Activity;
import android.content.Context;
import android.database.MatrixCursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.maximeleau.harmony.android.pokemon.PokemonApplication;
import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.data.AttaqueWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.CombatManagerWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.CombatWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.PokemonWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.RestClient;
import com.maximeleau.harmony.android.pokemon.entity.Attaque;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public abstract class CombatManagerWebServiceClientAdapterBase {
    /** AreneWebServiceClientAdapterBase TAG. */
    protected static final String TAG = "CombatManagerWSClientAdapter";

    /** JSON Object Arene pattern. */
    protected static String JSON_OBJECT_COMBAT_MANAGER = "CombatManager";
    /** JSON_ID attributes. */
    protected static String JSON_COMBAT = "combat";
    /** JSON_MAITRE attributes. */
        protected static String JSON_ATTAQUE = "attaque";
    /** JSON_BADGE attributes. */
    protected static String JSON_POKEMON = "pokemon";
    /** JSON_BADGE attributes. */
    protected static String JSON_ACTUAL_PV = "actualPv";
    /** JSON_BADGE attributes. */
    protected static String JSON_POKEMON_ACTUAL_TURN_ID = "pokemonActualTurnId";

    /** Rest Date Format pattern. */
    public static final String REST_UPDATE_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ";

    /** Time pattern.*/
    public static final String TIME_FORMAT = "HH:mm:ss";

    /** JSON RSS xml or empty (for html). */
    protected static String REST_FORMAT = ".json";

    /** headers List of {@link Header}. */
    protected List<Header> headers = new ArrayList<Header>();
    /** restClient {@link RestClient}. */
    protected RestClient restClient;
    /** context {@link Context}. */
    protected Context context;
    /** statusCode Http result code. */
    protected int statusCode;
    /** errorCode Http error code. */
    protected int errorCode;
    /** error Http error value. */
    protected String error;

    /** host of URI. */
    protected String host;
    /** prefix of URI. */
    protected String prefix;
    /** port of URI. */
    protected Integer port;
    /** scheme of URI. */
    protected String scheme;

    /**
     * Constructor with overriden port and host.
     *
     * @param context The context
     */
    public CombatManagerWebServiceClientAdapterBase(Context context) {
        this(context, null);
    }

    /**
     * Constructor with overriden port.
     *
     * @param context The context
     * @param port The overriden port
     */
    public CombatManagerWebServiceClientAdapterBase(Context context,
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
    public CombatManagerWebServiceClientAdapterBase(Context context,
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
    public CombatManagerWebServiceClientAdapterBase(Context context,
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
    public CombatManagerWebServiceClientAdapterBase(Context context,
                                            String host, Integer port, String scheme, String prefix) {
        this.headers.add(new BasicHeader("Content-Type","application/json; charset=utf-8"));
        this.headers.add(new BasicHeader("Accept","application/json"));

        this.context = context;

        Uri configUri;

        if (PokemonApplication.DEBUG) {
            configUri = Uri.parse(
                    this.context.getString(R.string.rest_url_dev));
        } else {
            configUri = Uri.parse(
                    this.context.getString(R.string.rest_url_prod));
        }

        if (scheme == null) {
            this.scheme = configUri.getScheme();
        } else {
            this.scheme = scheme;
        }

        if (host == null) {
            this.host = configUri.getHost();
        } else {
            this.host = host;
        }

        if (port == null) {
            this.port = configUri.getPort();
        } else {
            this.port = port;
        }

        // If port was not set in config string,
        // deduct it from scheme.
        if (this.port == null || this.port < 0) {
            if (this.scheme.equalsIgnoreCase("https")) {
                this.port = 443;
            } else {
                this.port = 80;
            }
        }

        if (prefix == null) {
            this.prefix = configUri.getPath();
        } else {
            this.prefix = prefix;
        }
    }

    /**
     * @return the URI.
     */
    public String getUri() {
        return "combatmanager";
    }


    /**
     * Send JSONObject on WebService.
     * @param verb {@link RestClient.Verb}
     * @param request Request to send on WebService
     * @param params JSON parameter {@link JSONObject}
     * @return response WebService response
     */
    protected synchronized String invokeRequest(RestClient.Verb verb, String request, JSONObject params) {
        String response = "";
        if (this.isOnline()) {
            this.restClient = this.getRestClient();

            StringBuilder error = new StringBuilder();

            try {
                synchronized (this.restClient) {
                    response = this.restClient.invoke(
                            verb, this.prefix + request, params, this.headers);
                    this.statusCode = this.restClient.getStatusCode();

                    if (isValidResponse(response)) {
                        this.errorCode = this.appendError(response,error);
                        this.error = error.toString();
                    }
                }

            } catch (Exception e) {
                this.displayOups();

                if (PokemonApplication.DEBUG) {
                    String message = String.format(
                            "Error in invokeRequest, statusCode = %s; uri = %s",
                            this.restClient.getStatusCode(),
                            this.prefix + request);
                    Log.e(TAG, message);

                    if (e.getMessage() != null) {
                        Log.e(TAG, e.getMessage());
                    }
                }
            }
        } else {
            this.displayOups(this.context.getString(R.string.no_network_error));
        }

        return response;
    }

    protected RestClient getRestClient() {
        return new RestClient(this.host, this.port, this.scheme);
    }

    public void shutdownHttpClientManager() {
        this.restClient.shutdownHttpClient();
    }

    /**
     * Get error.
     * @return the error
     */
    public String getError() {
        return error;
    }


    /**
     * Append WebService Error.
     * @param response WebService response
     * @param error StringBuilder to append
     * @return result mapped value
     * @throws JSONException {@link JSONException}
     */
    protected int appendError(String response, StringBuilder error) throws JSONException {
        int result = 0;
        StringBuilder builder = new StringBuilder();

        JSONObject json = new JSONObject(response);
        JSONObject jsonErr = json.optJSONObject("Err");

        if (jsonErr != null) {
            result = jsonErr.optInt("cd");

            JSONArray arrayErrors = json.optJSONArray("msg");
            if (arrayErrors != null) {
                int count = arrayErrors.length();

                for (int i = 0; i < count; i++) {
                    error.append(arrayErrors.optString(i, null));
                    if (!TextUtils.isEmpty(error.toString()))
                        builder.append(error + "; ");
                }

                error.append(builder.toString());
            } else {
                error.append(jsonErr.optString("msg", ""));
            }

            if (TextUtils.isEmpty(error.toString())) {
                error = null;
            }
        }

        return result;
    }

    /**
     * Set network error message.
     */
    protected void displayOups() {
        this.displayOups(this.context.getString(R.string.common_network_error));
    }

    /**
     * Display error message on Toast.
     * @param message Message to show on toast
     */
    protected void displayOups(final String message) {
        if (this.context != null
                && !TextUtils.isEmpty(message)
                && this.context instanceof Activity) {

            ((Activity) this.context).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(
                            context,
                            message,//message,
                            Toast.LENGTH_SHORT)
                            .show();

                }
            });
        }
    }

    /**
     * Check if request has return valid code.
     *
     * @return false if request return error code
     */
    protected boolean isValidRequest() {
        return (
                this.statusCode >= 200 &&
                        this.statusCode < 300 &&
                        this.errorCode == 0);
    }

    /**
     * Check if response is not null and valid.
     *
     * @param response Response to check
     *
     * @return Return false if request return error code
     */
    protected boolean isValidResponse(String response) {
        return (
                response != null &&
                        !response.trim().equals("") &&
                        response.startsWith("{"));
    }

    /**
     * Extract a CombatManager from a JSONObject describing a CombatManager.
     * @param json The JSONObject describing the CombatManager
     * @param combatManager The returned CombatManager
     * @return true if a CombatManager was found. false if not
     */
    public boolean extract(JSONObject json, CombatManager combatManager) {
        boolean result = true;
        if (result) {
            try {

                if (json.has(CombatManagerWebServiceClientAdapter.JSON_COMBAT)
                        && !json.isNull(CombatManagerWebServiceClientAdapter.JSON_COMBAT)) {

                    try {
                        CombatWebServiceClientAdapter combatAdapter =
                                new CombatWebServiceClientAdapter(this.context);
                        Combat combat =
                                new Combat();

                        if (combatAdapter.extract(
                                json.optJSONObject(
                                        CombatManagerWebServiceClientAdapter.JSON_COMBAT),
                                combat)) {
                            combatManager.setCombat(combat);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains PersonnageNonJoueur data");
                    }
                }

                if (json.has(CombatManagerWebServiceClientAdapter.JSON_ATTAQUE)
                        && !json.isNull(CombatManagerWebServiceClientAdapter.JSON_ATTAQUE)) {

                    try {
                        AttaqueWebServiceClientAdapter attaqueAdapter =
                                new AttaqueWebServiceClientAdapter(this.context);
                        Attaque attaque =
                                new Attaque();

                        if (attaqueAdapter.extract(
                                json.optJSONObject(
                                        CombatManagerWebServiceClientAdapter.JSON_ATTAQUE),
                                attaque)) {
                            combatManager.setAttaque(attaque);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Badge data");
                    }
                }

                if (json.has(CombatManagerWebServiceClientAdapter.JSON_POKEMON)
                        && !json.isNull(CombatManagerWebServiceClientAdapter.JSON_POKEMON)) {

                    try {
                        PokemonWebServiceClientAdapter pokemonAdapter =
                                new PokemonWebServiceClientAdapter(this.context);
                        Pokemon pokemon =
                                new Pokemon();

                        if (pokemonAdapter.extract(
                                json.optJSONObject(
                                        CombatManagerWebServiceClientAdapter.JSON_POKEMON),
                                pokemon)) {
                            combatManager.setPokemon(pokemon);
                        }
                    } catch (Exception e) {
                        Log.e(TAG, "Json doesn't contains Position data");
                    }
                }

                if (json.has(CombatManagerWebServiceClientAdapter.JSON_ACTUAL_PV)
                        && !json.isNull(CombatManagerWebServiceClientAdapter.JSON_ACTUAL_PV)) {
                    combatManager.setActualPv(
                            json.getInt(CombatManagerWebServiceClientAdapter.JSON_ACTUAL_PV));
                }

                if (json.has(CombatManagerWebServiceClientAdapter.JSON_POKEMON_ACTUAL_TURN_ID)
                        && !json.isNull(CombatManagerWebServiceClientAdapter.JSON_POKEMON_ACTUAL_TURN_ID)) {
                    combatManager.setPokemonActualTurnId(
                            json.getInt(CombatManagerWebServiceClientAdapter.JSON_POKEMON_ACTUAL_TURN_ID));
                }

            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }
        }

        return result;
    }

    public boolean extractCursor(JSONObject json, MatrixCursor cursor) {
        boolean result = false;
            try {
                String[] row = new String[5];

                if (json.has(CombatManagerWebServiceClientAdapter.JSON_COMBAT)) {
                    JSONObject combatJson = json.getJSONObject(
                            CombatManagerWebServiceClientAdapter.JSON_COMBAT);
                    row[0] = combatJson.getString(
                            CombatWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(CombatManagerWebServiceClientAdapter.JSON_ATTAQUE)) {
                    JSONObject attaqueJson = json.getJSONObject(
                            CombatManagerWebServiceClientAdapter.JSON_ATTAQUE);
                    row[1] = attaqueJson.getString(
                            AttaqueWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(CombatManagerWebServiceClientAdapter.JSON_POKEMON)) {
                    JSONObject pokemonJson = json.getJSONObject(
                            CombatManagerWebServiceClientAdapter.JSON_POKEMON);
                    row[2] = pokemonJson.getString(
                            PokemonWebServiceClientAdapter.JSON_ID);
                }
                if (json.has(CombatManagerWebServiceClientAdapter.JSON_ACTUAL_PV)) {
                    row[3] = json.getString(CombatManagerWebServiceClientAdapter.JSON_ACTUAL_PV);
                }
                if (json.has(CombatManagerWebServiceClientAdapter.JSON_POKEMON_ACTUAL_TURN_ID)) {
                    row[4] = json.getString(CombatManagerWebServiceClientAdapter.JSON_POKEMON_ACTUAL_TURN_ID);
                }

                cursor.addRow(row);
                result = true;
            } catch (JSONException e) {
                Log.e(TAG, e.getMessage());
            }

        return result;
    }

    /**
     * Convert a CombatManager to a JSONObject.
     * @param combatManager The CombatManager to convert
     * @return The converted CombatManager
     */
    public JSONObject itemToJson(CombatManager combatManager) {
        JSONObject params = new JSONObject();
        try {

            if (combatManager.getCombat() != null) {
                CombatWebServiceClientAdapter combatAdapter =
                        new CombatWebServiceClientAdapter(this.context);

                params.put(CombatManagerWebServiceClientAdapter.JSON_COMBAT,
                        combatAdapter.itemToJson(combatManager.getCombat()));
            }

            if (combatManager.getAttaque() != null) {
                AttaqueWebServiceClientAdapter attaqueAdapter =
                        new AttaqueWebServiceClientAdapter(this.context);

                params.put(CombatManagerWebServiceClientAdapter.JSON_ATTAQUE,
                        attaqueAdapter.itemToJson(combatManager.getAttaque()));
            }

            if (combatManager.getPokemon() != null) {
                PokemonWebServiceClientAdapter pokemonAdapter =
                        new PokemonWebServiceClientAdapter(this.context);

                params.put(CombatManagerWebServiceClientAdapter.JSON_POKEMON,
                        pokemonAdapter.itemToJson(combatManager.getPokemon()));
            }
            params.put(CombatManagerWebServiceClientAdapter.JSON_ACTUAL_PV,
                    combatManager.getActualPv());
            params.put(CombatManagerWebServiceClientAdapter.JSON_POKEMON_ACTUAL_TURN_ID,
                    combatManager.getPokemonActualTurnId());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage());
        }

        return params;
    }

    /**
     * Checks network connection.
     * @return true if available
     */
    public boolean isOnline() {
        boolean result = false;
        ConnectivityManager cm = (ConnectivityManager)
                this.context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (cm != null) {
            NetworkInfo netInfo = cm.getActiveNetworkInfo();

            if (netInfo != null && netInfo.isConnectedOrConnecting()) {
                result = true;
            }
        }

        return result;
    }


}
