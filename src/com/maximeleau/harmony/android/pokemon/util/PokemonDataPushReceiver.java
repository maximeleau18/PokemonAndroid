package com.maximeleau.harmony.android.pokemon.util;

import android.content.Context;
import android.util.Log;

import com.microsoft.azure.engagement.reach.EngagementReachDataPushReceiver;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class PokemonDataPushReceiver extends EngagementReachDataPushReceiver {
    @Override
    protected Boolean onDataPushStringReceived(Context context, String category, String body)
    {
        Log.d("PokemonAndroidML", "String data push message received: " + body);
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
