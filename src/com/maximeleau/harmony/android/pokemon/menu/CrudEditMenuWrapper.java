/**************************************************************************
 * CrudEditMenuWrapper.java, pokemon Android
 *
 * Copyright 2016
 * Description : 
 * Author(s)   : Harmony
 * Licence     : 
 * Last update : Jul 9, 2016
 *
 **************************************************************************/
package com.maximeleau.harmony.android.pokemon.menu;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.MenuItemCompat;
import android.view.Menu;
import android.view.MenuItem;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.menu.base.MenuWrapperBase;

/**
 * Crud Menu wrapper for edit and delete actions.
 */
public class CrudEditMenuWrapper implements MenuWrapperBase {
    /** Edit menu item. */
    private MenuItem editItem;
    /** Menu Visibility. */
    private boolean visible = true;
    
    @Override
    public void initializeMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        if ((fragment != null
                && fragment instanceof CrudEditMenuInterface)
                || activity instanceof CrudEditMenuInterface) {            
            this.editItem     = menu.add(
                    PokemonMenu.CRUDEDIT,
                    1,
                    Menu.NONE,
                    R.string.menu_item_edit);
                    
            MenuItemCompat.setShowAsAction(this.editItem,
                    MenuItemCompat.SHOW_AS_ACTION_IF_ROOM
                    | MenuItemCompat.SHOW_AS_ACTION_WITH_TEXT);
                    
            this.editItem.setVisible(false);
        }
    }

    @Override
    public void updateMenu(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        if ((fragment != null 
                && fragment instanceof CrudEditMenuInterface)
                || ctx instanceof CrudEditMenuInterface) {
            menu.setGroupVisible(
                    PokemonMenu.CRUDEDIT, this.visible);
        }
    }

    @Override
    public boolean dispatch(MenuItem item, android.content.Context ctx, Fragment fragment) {
        boolean result = false;
        if ((fragment != null 
                && fragment instanceof CrudEditMenuInterface)
                || ctx instanceof CrudEditMenuInterface) {
            if (item.equals(this.editItem)) {
                ((CrudEditMenuInterface) fragment).onClickEdit();
                result = true;
            }
        }
        return result;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
            Intent data, android.content.Context ctx, Fragment fragment) {
        // We don't need this.
    }

    @Override
    public void clear(Menu menu, FragmentActivity activity,
            Fragment fragment, android.content.Context ctx) {
        
        if (fragment != null 
                && fragment instanceof CrudEditMenuInterface) {
            menu.removeGroup(PokemonMenu.CRUDEDIT);
        }
    }

    @Override
    public void hide(Menu menu, FragmentActivity activity, Fragment fragment,
            android.content.Context ctx) {
        this.visible = false;
    }

    @Override
    public void show(Menu menu, FragmentActivity activity, Fragment fragment,
            android.content.Context ctx) {
        this.visible = true;
    }

    /**
     * Implement this interface in your activity or fragment
     * to have edit and delete menu buttons.
     */
    public interface CrudEditMenuInterface {
        /** On click edit. */
        void onClickEdit();
    }
}


