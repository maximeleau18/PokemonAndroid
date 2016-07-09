/**************************************************************************
 * TypeDePokemonEvolutionListFragment.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.view.typedepokemonevolution;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.maximeleau.harmony.android.pokemon.criterias.base.CriteriaExpression;
import com.maximeleau.harmony.android.pokemon.menu.CrudCreateMenuWrapper.CrudCreateMenuInterface;
import com.maximeleau.harmony.android.pokemon.provider.TypeDePokemonEvolutionProviderAdapter;
import com.maximeleau.harmony.android.pokemon.provider.contract.TypeDePokemonEvolutionContract;
import com.maximeleau.harmony.android.pokemon.harmony.view.HarmonyListFragment;
import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.TypeDePokemonEvolution;


/** TypeDePokemonEvolution list fragment.
 *
 * This fragment gives you an interface to list all your TypeDePokemonEvolutions.
 *
 * @see android.app.Fragment
 */
public class TypeDePokemonEvolutionListFragment
        extends HarmonyListFragment<TypeDePokemonEvolution>
        implements CrudCreateMenuInterface {

    /** The adapter which handles list population. */
    protected TypeDePokemonEvolutionListAdapter mAdapter;

    @Override
    public View onCreateView(
            LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState) {

        final View view =
                inflater.inflate(R.layout.fragment_typedepokemonevolution_list,
                        null);

        this.initializeHackCustomList(view,
                R.id.typedepokemonevolutionProgressLayout,
                R.id.typedepokemonevolutionListContainer);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Give some text to display if there is no data.  In a real
        // application this would come from a resource.
        this.setEmptyText(this.getString(
                R.string.typedepokemonevolution_empty_list));

        // Create an empty adapter we will use to display the loaded data.
        this.mAdapter = new TypeDePokemonEvolutionListAdapter(this.getActivity(), null);

        // Start out with a progress indicator.
        this.setListShown(false);

        // Prepare the loader.  Either re-connect with an existing one,
        // or start a new one.
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        /* Do click action inside your fragment here. */
    }

    @Override
    public Loader<android.database.Cursor> onCreateLoader(int id, Bundle bundle) {
        Loader<android.database.Cursor> result = null;
        CriteriaExpression crit = null;
        if (bundle != null) {
            crit = (CriteriaExpression) bundle.get(
                        CriteriaExpression.PARCELABLE);
        }

        if (crit != null) {
            result = new TypeDePokemonEvolutionListLoader(this.getActivity(),
                TypeDePokemonEvolutionProviderAdapter.TYPEDEPOKEMONEVOLUTION_URI,
                TypeDePokemonEvolutionContract.ALIASED_COLS,
                crit,
                null);
        } else {
            result = new TypeDePokemonEvolutionListLoader(this.getActivity(),
                TypeDePokemonEvolutionProviderAdapter.TYPEDEPOKEMONEVOLUTION_URI,
                TypeDePokemonEvolutionContract.ALIASED_COLS,
                null,
                null,
                null);
        }
        return result;
    }

    @Override
    public void onLoadFinished(
            Loader<android.database.Cursor> loader,
            android.database.Cursor data) {

        // Set the new data in the adapter.
        data.setNotificationUri(this.getActivity().getContentResolver(),
                TypeDePokemonEvolutionProviderAdapter.TYPEDEPOKEMONEVOLUTION_URI);

        this.mAdapter.swapCursor(data);

        if (this.getListAdapter() == null) {
            this.setListAdapter(this.mAdapter);
        }

        // The list should now be shown.
        if (this.isResumed()) {
            this.setListShown(true);
        } else {
            this.setListShownNoAnimation(true);
        }

        super.onLoadFinished(loader, data);
    }

    @Override
    public void onLoaderReset(Loader<android.database.Cursor> loader) {
        // Clear the data in the adapter.
        this.mAdapter.swapCursor(null);
    }
    
    @Override
    public void onClickAdd() {
        Intent intent = new Intent(this.getActivity(),
                    TypeDePokemonEvolutionCreateActivity.class);
        this.startActivity(intent);
    }

}
