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

import com.example.mechsrit.bakingapp.ItemDetailActivity;
import com.example.mechsrit.bakingapp.ItemDetailFragment;
import com.example.mechsrit.bakingapp.ItemListActivity;
import com.example.mechsrit.bakingapp.R;
import com.example.mechsrit.bakingapp.modelclasses.Ingredient;
import com.example.mechsrit.bakingapp.modelclasses.Step;

import java.util.ArrayList;
import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder> {
    List<Step> mySteps;
    Context ct;
    boolean mTwoPane;
    List<Ingredient> ingredients;

    public StepsAdapter(ItemListActivity itemListActivity, List<Step> steps, boolean mTwoPane, List<Ingredient> ingredients) {
        ct=itemListActivity;
        mySteps=steps;
        this.mTwoPane=mTwoPane;
        this.ingredients=ingredients;
    }


    @NonNull
    @Override
    public StepsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(ct).inflate(R.layout.stepslist,viewGroup,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.MyViewHolder myViewHolder, int i) {
        myViewHolder.textView.setText(mySteps.get(i).getShortDescription());


    }

    @Override
    public int getItemCount() {
        if (mySteps!=null) {
            return mySteps.size();
        }
        else{
            return 0;
        }
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);
            textView=itemView.findViewById(R.id.stepsname);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle=new Bundle();
                    bundle.putParcelableArrayList("stepsList", (ArrayList<? extends Parcelable>) mySteps);
                    bundle.putInt("pos",getAdapterPosition());
                    if(mTwoPane){

                        ItemDetailFragment fa=new ItemDetailFragment();
                        fa.setArguments(bundle);
                        ((ItemListActivity)ct).getSupportFragmentManager()
                                .beginTransaction().replace(R.id.item_detail_container,fa).commit();
                    }else {
                        Context context=itemView.getContext();
                        Intent i=new Intent(context,ItemDetailActivity.class);
                        i.putExtras(bundle);
                        context.startActivity(i);

                    }
                }
            });
        }
    }
}

