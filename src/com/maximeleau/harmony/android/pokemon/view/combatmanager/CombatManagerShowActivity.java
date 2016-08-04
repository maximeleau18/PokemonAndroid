package com.maximeleau.harmony.android.pokemon.view.combatmanager;

import android.content.Intent;
import android.os.Bundle;
import com.maximeleau.harmony.android.pokemon.R;
import com.maximeleau.harmony.android.pokemon.entity.Combat;
import com.maximeleau.harmony.android.pokemon.entity.CombatManager;
import com.maximeleau.harmony.android.pokemon.entity.Pokemon;
import com.microsoft.azure.engagement.activity.EngagementFragmentActivity;

/**
 * Created by Maxime Léau on 03/08/2016.
 */
public class CombatManagerShowActivity extends EngagementFragmentActivity {

    private CombatManager combatManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        this.combatManager = (CombatManager) intent.getSerializableExtra("combatManager");

        setContentView(R.layout.activity_combat_manager_show);
    }

    public CombatManager getData(){
        return this.combatManager;
    }
}
