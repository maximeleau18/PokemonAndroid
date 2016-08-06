package com.maximeleau.harmony.android.pokemon.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.maximeleau.harmony.android.pokemon.data.CombatManagerWebServiceClientAdapter;
import com.maximeleau.harmony.android.pokemon.data.base.CombatManagerWebServiceClientAdapterBase;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.view.combatmanager.CombatManagerShowActivity;
import com.microsoft.azure.engagement.reach.EngagementReachDataPushReceiver;

import org.apache.http.impl.entity.EntityDeserializer;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class PokemonDataPushReceiver extends EngagementReachDataPushReceiver {
    @Override
    protected Boolean onDataPushStringReceived(Context context, String category, String body)
    {
        Log.d("PokemonAndroidML", "String data push message received: " + body);

        Intent intent = new Intent("received_data_push");
        intent.putExtra("combatManagerJson", body);

        context.sendBroadcast(intent);

        return true;
    }

    @Override
    protected Boolean onDataPushBase64Received(Context context, String category, byte[] decodedBody, String encodedBody)
    {
        Log.d("PokemonAndroidML", "Base64 data push message received: " + encodedBody);
        // Do something useful with decodedBody like updating an image view
        return true;
    }
}
