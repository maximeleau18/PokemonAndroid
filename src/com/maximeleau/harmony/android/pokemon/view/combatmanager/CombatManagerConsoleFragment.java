package com.maximeleau.harmony.android.pokemon.view.combatmanager;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Dresseur;
import java.util.Locale;

/**
 * Created by Maxime Léau on 04/08/2016.
 */
public class CombatManagerConsoleFragment extends Fragment {
    private CombatManager combatManager;
    private Dresseur dresseurConnected;
    private TextView console;
    private ScrollView consoleSV;
    private Context ctx;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_combat_manager_console, container, false);

        initializeComponent(view);
        return view;
    }

    public void initializeComponent(View view) {
        // Get data from activity
        CombatManagerShowActivity parent = (CombatManagerShowActivity) this.getActivity();
        this.ctx = parent;
        this.combatManager = parent.getCombatManager();
        this.dresseurConnected = parent.getDresseurConnected();

        this.console = (TextView) view.findViewById(R.id.combat_manager_console);
        this.consoleSV = (ScrollView) view.findViewById(R.id.combat_manager_console_sv);
    }

    public TextView getConsole(){ return this.console; }

    public ScrollView getConsoleSV(){
        return this.consoleSV;
    }
}
