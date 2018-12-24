package com.example.mechsrit.bakingapp.adapterclasses;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mechsrit.bakingapp.ItemListActivity;
import com.example.mechsrit.bakingapp.R;
import com.example.mechsrit.bakingapp.modelclasses.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.MyViewHolder> {
    Context ct;
    List<Ingredient> ingredients;
    boolean mTwoPane;
    public IngredientsAdapter(ItemListActivity itemListActivity, List<Ingredient> ingredients, boolean mTwoPane) {
        this.ct=itemListActivity;
        this.ingredients=ingredients;
        this.mTwoPane=mTwoPane;
    }

    @NonNull
    @Override
    public IngredientsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(ct).inflate(R.layout.ingredientslist,viewGroup,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.tv1.setText(ingredients.get(i).getIngredient());
        myViewHolder.tv2.setText(""+ingredients.get(i).getQuantity());
        myViewHolder.tv3.setText(ingredients.get(i).getMeasure());

    }

    @Override
    public int getItemCount() {
        if (ingredients!=null) {
            return ingredients.size();
        }
        else{
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv1,tv2,tv3;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv1=itemView.findViewById(R.id.ingname);
            tv2=itemView.findViewById(R.id.ingquantity);
            tv3=itemView.findViewById(R.id.ingmeasure);
        }
    }
}

