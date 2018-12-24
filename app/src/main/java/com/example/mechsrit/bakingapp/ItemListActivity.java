package com.example.mechsrit.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.example.mechsrit.bakingapp.adapterclasses.IngredientsAdapter;
import com.example.mechsrit.bakingapp.adapterclasses.StepsAdapter;
import com.example.mechsrit.bakingapp.modelclasses.Ingredient;
import com.example.mechsrit.bakingapp.modelclasses.Step;

import java.util.ArrayList;
import java.util.List;

import static com.example.mechsrit.bakingapp.adapterclasses.RecepieAdapter.INGREDIENTS;
import static com.example.mechsrit.bakingapp.adapterclasses.RecepieAdapter.STEPS;


public class ItemListActivity extends AppCompatActivity {
    List<Step> steps=new ArrayList<>();
    List<Ingredient> ingredients=new ArrayList<>();

    public static final String SHP_KEY="Bhav";
    public static final String PREF_KEY="KEY";

    private boolean mTwoPane;
    RecyclerView stepsRecycler,ingredientsRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);
        stepsRecycler = findViewById(R.id.stepsrecycler);
        ingredientsRecycler = findViewById(R.id.ingrecycler);
        Intent intent = getIntent();
        steps = intent.getParcelableArrayListExtra(STEPS);
        ingredients = intent.getParcelableArrayListExtra(INGREDIENTS);

        if (findViewById(R.id.item_detail_container) != null) {
            mTwoPane = true;
        }
        stepsRecycler.setLayoutManager(new LinearLayoutManager(this));
        ingredientsRecycler.setLayoutManager(new LinearLayoutManager(this));
        stepsRecycler.setAdapter(new StepsAdapter(this, steps, mTwoPane,ingredients));
        ingredientsRecycler.setAdapter(new IngredientsAdapter(this, ingredients, mTwoPane));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        updateWidget();
    }

    private void updateWidget() {
        SharedPreferences sharedPreferences=getSharedPreferences(SHP_KEY,MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        StringBuilder stringBuilder=new StringBuilder();
        for (int i=0;i<ingredients.size();i++)
        {
            int j=i+1;
            stringBuilder.append(j+"."+ingredients.get(i).getIngredient()+" ");
        }
        String totalString=stringBuilder.toString();
        editor.putString(PREF_KEY,getTitle().toString());
        editor.putString(INGREDIENTS,totalString);
        editor.apply();
        Intent intent=new Intent(this,BakingWidget.class);
        sharedPreferences.edit().clear();
        intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        int[] ints=AppWidgetManager.getInstance(getApplicationContext()).getAppWidgetIds(new ComponentName(getApplication(),BakingWidget.class));
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS,ints);
        sendBroadcast(intent);


    }
}
