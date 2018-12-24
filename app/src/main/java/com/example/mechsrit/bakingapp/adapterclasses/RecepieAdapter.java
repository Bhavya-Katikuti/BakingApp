package com.example.mechsrit.bakingapp.adapterclasses;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mechsrit.bakingapp.ItemListActivity;
import com.example.mechsrit.bakingapp.MainActivity;
import com.example.mechsrit.bakingapp.R;
import com.example.mechsrit.bakingapp.modelclasses.Ingredient;
import com.example.mechsrit.bakingapp.modelclasses.RecepiesList;
import com.example.mechsrit.bakingapp.modelclasses.Step;

import java.util.ArrayList;
import java.util.List;

public class RecepieAdapter extends RecyclerView.Adapter<RecepieAdapter.MyViewHolder> {
    List<RecepiesList> myrecepies;
    List<Ingredient> myingredients;
    List<Step> mySteps;

    Context context;
    public static final String NAME = "name";
    public static final String STEPS = "steps";
    public static final String POSITION = "position";
    public static final String SERVINGS = "servings";
    public static final String INGREDIENTS = "ingredients";
    public static final String ID = "id";

    public RecepieAdapter(MainActivity mainActivity, List<RecepiesList> itemList) {
        this.context = mainActivity;
        this.myrecepies=  itemList;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(context).inflate(R.layout.recepie,viewGroup,false);

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(myrecepies.get(i).getName());

    }

    @Override
    public int getItemCount() {
        return myrecepies.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.recepietext);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos=getAdapterPosition();
                    if (pos !=RecyclerView.NO_POSITION)
                    {
                        Intent intent=new Intent(context,ItemListActivity.class);
                        Bundle bundle=new Bundle();
                        bundle.putInt(POSITION,pos);
                        bundle.putInt(ID,myrecepies.get(pos).getId());
                        bundle.putInt(SERVINGS,myrecepies.get(pos).getServings());
                        myingredients=new ArrayList<>();
                        myingredients= myrecepies.get(pos).getIngredients();
                        mySteps=new ArrayList<>();
                        mySteps=myrecepies.get(pos).getSteps();
                        intent.putParcelableArrayListExtra(STEPS, (ArrayList<? extends Parcelable>) mySteps);
                        intent.putParcelableArrayListExtra(INGREDIENTS, (ArrayList<? extends Parcelable>) myingredients);
                        intent.putExtra(NAME,myrecepies.get(pos).getName());
                        context.startActivity(intent);
                    }

                }
            });
        }
    }
}

