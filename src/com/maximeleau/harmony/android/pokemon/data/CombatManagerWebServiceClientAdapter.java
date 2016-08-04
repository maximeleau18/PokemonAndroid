package com.maximeleau.harmony.android.pokemon.data;

import android.content.Context;

import com.maximeleau.harmony.android.pokemon.data.base.CombatManagerWebServiceClientAdapterBase;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;

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
}
