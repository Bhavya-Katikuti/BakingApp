package com.example.mechsrit.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.example.mechsrit.bakingapp.modelclasses.Step;

import java.io.Serializable;
import java.util.List;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity {
    public static final String POSITIONKEY="pos";
    public static final String STEP="step";
    public static final String STEPSKEY="stepsList";
    public static final String BKEY="pos";
    List<Step> steps;
    int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        if (savedInstanceState == null)
        {
            Intent intent=getIntent();
            Bundle bundle=intent.getExtras();
            steps=bundle.getParcelableArrayList(STEPSKEY);
            pos=bundle.getInt(BKEY,0);
            bundle.putSerializable(STEP, (Serializable) steps);
            bundle.putInt(POSITIONKEY,pos);
            ItemDetailFragment fragment=new ItemDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container,fragment).commit();

        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
