/**************************************************************************
 * PokemonProviderBase.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Aug 5, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.provider.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import android.content.ContentProvider;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentValues;
import android.content.Context;
import android.content.OperationApplicationException;
import android.content.UriMatcher;
import android.database.ContentObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProvider;
import com.maximeleau.harmony.android.pokemon.provider.ObjetProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.AreneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PositionProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.ZoneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.TypeAttaqueProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.BadgeProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonEvolutionProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.DresseurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.TypeObjetProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurBadgeProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.ProfessionProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.AttaqueProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.CombatProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonZoneProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PersonnageNonJoueurProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.PokemonProviderAdapter;

/**
 * PokemonProviderBase.
 */
public class PokemonProviderBase extends ContentProvider {
    /** TAG for debug purpose. */
    protected static String TAG = "PokemonProvider";
    /** Uri not supported. */
    protected static String URI_NOT_SUPPORTED;

    /** INITIALIZE_DATABASE. */
    private static final String INITIALIZE_DATABASE = "INITIALIZE_DATABASE";

    /* Tools / Common. */
    /** com.maximeleau.harmony.android.pokemon.provider authority. */
    public static String authority = "com.maximeleau.harmony.android.pokemon.provider";
    /** URI Matcher. */
    protected static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /** List of all the provider adapters. */
    protected ArrayList<ProviderAdapterBase<?>> providerAdapters;

    /** Database. */
    protected SQLiteDatabase db;

    /** Is this provider currently in a batch operation ? */
    private boolean isBatch = false;

    /** True if the adapters is already loaded. */
    private boolean isAdaptersLoaded = false;

    /** android.content.Context. */
    protected android.content.Context mContext;

    /**
     * Hashmap containing the uris to notify at the end of a batch and their
     * associated ContentObservers.
     */
    protected Map<Uri, ContentObserver> urisToNotify = new HashMap<Uri, ContentObserver>();

    /**
     * Called when the contentProvider is first created.
     * @return true if everything goes well, false otherwise
     */
    @Override
    public boolean onCreate() {
        boolean result = true;

        this.mContext = getContext();
        URI_NOT_SUPPORTED = this.getContext().getString(R.string.uri_not_supported);

        // Load adapters if database creation is not deferred
        if (!PokemonProvider.CREATE_DATABASE_DEFERRED) {
            this.loadAdapters();
        }

        return result;
    }

    /**
     * Load all the adapters.
     */
    public void loadAdapters() {
        this.providerAdapters = new ArrayList<ProviderAdapterBase<?>>();
        ObjetProviderAdapter objetProviderAdapter = new ObjetProviderAdapter(this);
        this.db = objetProviderAdapter.getDb();
        this.providerAdapters.add(objetProviderAdapter);
        this.providerAdapters.add(new AreneProviderAdapter(this));
        this.providerAdapters.add(new PositionProviderAdapter(this));
        this.providerAdapters.add(new ZoneProviderAdapter(this));
        this.providerAdapters.add(new TypeAttaqueProviderAdapter(this));
        this.providerAdapters.add(new TypeDePokemonProviderAdapter(this));
        this.providerAdapters.add(new BadgeProviderAdapter(this));
        this.providerAdapters.add(new TypeDePokemonEvolutionProviderAdapter(this));
        this.providerAdapters.add(new DresseurProviderAdapter(this));
        this.providerAdapters.add(new TypeObjetProviderAdapter(this));
        this.providerAdapters.add(new PersonnageNonJoueurBadgeProviderAdapter(this));
        this.providerAdapters.add(new ProfessionProviderAdapter(this));
        this.providerAdapters.add(new AttaqueProviderAdapter(this));
        this.providerAdapters.add(new CombatProviderAdapter(this));
        this.providerAdapters.add(new TypeDePokemonZoneProviderAdapter(this));
        this.providerAdapters.add(new PersonnageNonJoueurProviderAdapter(this));
        this.providerAdapters.add(new PokemonProviderAdapter(this));

        this.isAdaptersLoaded = true;
    }

    /**
     * Get the entity from the URI.
     * @param uri URI
     * @return A String representing the entity name
     */
    @Override
    public String getType(final Uri uri) {
        String result = null;
        boolean matched = false;

        for (ProviderAdapterBase<?> adapter : this.providerAdapters) {
            if (adapter.match(uri)) {
                result = adapter.getType(uri);
                matched = true;
                break;
            }
        }

        if (!matched) {
            throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
        } else {
            return result;
        }
    }

    /**
     * Delete the entities matching with uri from the DB.
     * @param uri URI
     * @param selection SELECT clause for SQL
     * @param selectionArgs SELECT arguments for SQL
     * @return how many token deleted
     */
    @Override
    public int delete(final Uri uri, final String selection,
            final String[] selectionArgs) {
        int result = 0;
        boolean matched = false;
        boolean alreadyInTransaction = this.db.inTransaction();

        if (!alreadyInTransaction) {
            this.db.beginTransaction();
        }

        for (ProviderAdapterBase<?> adapter : this.providerAdapters) {
            if (adapter.match(uri)) {
                result = adapter.delete(uri, selection, selectionArgs);
                matched = true;
                break;
            }
        }

        if (!alreadyInTransaction) {
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
        }

        if (!matched) {
            throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
        } else {
            if (result > 0) {
                this.getContext().getContentResolver().notifyChange(uri, null);
            }
            return result;
        }
    }

    /**
     * Insert the entities matching with uri from the DB.
     * @param uri URI
     * @param values ContentValues to insert
     * @return how many token inserted
     */
    @Override
    public Uri insert(final Uri uri, final ContentValues values) {
        Uri result = null;
        boolean matched = false;
        boolean alreadyInTransaction = this.db.inTransaction();

        if (!alreadyInTransaction) {
            this.db.beginTransaction();
        }

        for (ProviderAdapterBase<?> adapter : this.providerAdapters) {
            if (adapter.match(uri)) {
                result = adapter.insert(uri, values);
                matched = true;
                break;
            }
        }

        if (!alreadyInTransaction) {
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
        }

        if (!matched) {
            throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
        } else {
            if (result != null) {
                this.getContext().getContentResolver().notifyChange(uri, null);
            }

            return result;
        }
    }

    /**
     * Send a query to the DB.
     * @param uri URI
     * @param projection Columns to work with
     * @param selection SELECT clause for SQL
     * @param selectionArgs SELECT arguments for SQL
     * @param sortOrder ORDER BY clause
     * @return A cursor pointing to the result of the query
     */
    @Override
    public android.database.Cursor query(final Uri uri, final String[] projection,
            final String selection, final String[] selectionArgs,
            final String sortOrder) {
        android.database.Cursor result = null;
        boolean matched = false;
        boolean alreadyInTransaction = this.db.inTransaction();

        if (!alreadyInTransaction) {
            this.db.beginTransaction();
        }

        for (ProviderAdapterBase<?> adapter : this.providerAdapters) {
            if (adapter.match(uri)) {
                result = adapter.query(uri,
                            projection,
                            selection,
                            selectionArgs,
                            sortOrder);

                matched = true;
                break;
            }
        }

        if (!alreadyInTransaction) {
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
        }

        if (!matched) {
            throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
        } else {
            return result;
        }
    }

    /**
     * Update the entities matching with uri from the DB.
     * @param uri URI
     * @param values ContentValues to update
     * @param selection SELECT clause for SQL
     * @param selectionArgs SELECT arguments for SQL
     * @return how many token update
     */
    @Override
    public int update(final Uri uri, final ContentValues values,
                      final String selection, final String[] selectionArgs) {
        int result = 0;
        boolean matched = false;
        boolean alreadyInTransaction = this.db.inTransaction();

        if (!alreadyInTransaction) {
            this.db.beginTransaction();
        }

        for (ProviderAdapterBase<?> adapter : this.providerAdapters) {
            if (adapter.match(uri)) {
                result = adapter.update(uri,
                            values,
                            selection,
                            selectionArgs);

                matched = true;
                break;
            }
        }

        if (!alreadyInTransaction) {
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
        }

        if (!matched) {
            throw new IllegalArgumentException(URI_NOT_SUPPORTED + uri);
        } else {
            if (result > 0) {
                this.getContext().getContentResolver().notifyChange(uri, null);
            }

            return result;
        }
    }

    //-------------------------------------------------------------------------

    /** Utils function.
     * @param typePath Path to type
     * @return generated URI
     */
    public static final Uri generateUri(final String typePath) {
        return Uri.parse("content://" + authority + "/" + typePath);
    }

    /** Utils function.
     * @return generated URI
     */
    public static final Uri generateUri() {
        return Uri.parse("content://" + authority);
    }

    /**
     * Get URI Matcher.
     * @return the uriMatcher
     */
    public static UriMatcher getUriMatcher() {
        return uriMatcher;
    }

    @Override
    public ContentProviderResult[] applyBatch(
            ArrayList<ContentProviderOperation> operations)
            throws OperationApplicationException {
        ContentProviderResult[] result;
        this.isBatch = true;
        this.db.beginTransaction();

        try {
            result = super.applyBatch(operations);
            this.db.setTransactionSuccessful();
            this.db.endTransaction();
            this.isBatch = false;

            return result;
        } catch (OperationApplicationException e) {
            this.db.endTransaction();
            this.isBatch = false;
            throw e;
        }
    }

    /**
     * Ask the provider to notify an Uri. This method is useful
     * for not notifying the same Uri a lot of times when we're in case of a
     * batch. (It will delay all the uri changes notification at the end of the
     * batch.)
     *
     * @param uri The uri to notify
     * @param observer The observer that originated the change.
     */
    public void notifyUri(Uri uri, ContentObserver observer) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (this.isBatch) {
            if (!this.urisToNotify.containsKey(uri)) {
                this.urisToNotify.put(uri, observer);
            }
        } else {
            this.getContext().getContentResolver().notifyChange(uri, observer);
        }
    }

    /**
     * Notify all stored uris in case we're in a batch.
     */
    protected void notifyAllUrisNow() {
        for (Uri uri : this.urisToNotify.keySet()) {
            this.getContext().getContentResolver().notifyChange(uri, this.urisToNotify.get(uri));
        }

        this.urisToNotify.clear();
    }

    /**
     * Returns the sqlite database object attached to this provider.
     *
     * @return The sqlite database
     */
    public SQLiteDatabase getDatabase() {
        return this.db;
    }

    @Override
    public Bundle call(String method, String arg, Bundle extras) {
        Bundle result = null;

        if (INITIALIZE_DATABASE.equals(method) && !isAdaptersLoaded) {
            this.loadAdapters();
        } else {
            result = super.call(method, arg, extras);
        }

        return result;
    }

    /**
     * Initialize the database.
     * @param context {@link Context}
     */
    public static void initializeDatabase(Context context) {
        context.getContentResolver().call(
                PokemonProviderBase.generateUri(),
                PokemonProviderBase.INITIALIZE_DATABASE,
                null,
                null);
    }
}
