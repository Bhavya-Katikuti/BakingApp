package com.example.mechsrit.bakingapp;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.mechsrit.bakingapp.modelclasses.Step;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import java.util.List;


public class ItemDetailFragment extends Fragment {
    ImageView defaultImageView;
    PlayerView playerView;
    ImageView backButton,forwardButton;
    SimpleExoPlayer player;
    TextView descriptionTv;


    public static final String STEPS_LIST_KEY = "stepsList";
    public static final String KEY="pos";

    public static final String CURRENTPOS="currentposition";
    public static final String PLAYBACKPOS="playbackposition";
    public static final String PLAYWHENREADY="playwhenready";

    List<Step> steps;
    long playerstopPosition;
    long playbackPosition;
    boolean playwhenReady=true;
    boolean stopPlay;
    int currentPosition;
    String videourl;


    public ItemDetailFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.item_detail, container, false);

        if (getArguments()!= null) {
            steps=getArguments().getParcelableArrayList(STEPS_LIST_KEY);
            currentPosition=getArguments().getInt(KEY,0);
        }

        backButton=rootView.findViewById(R.id.leftarrow);
        forwardButton=rootView.findViewById(R.id.rightarrow);
        defaultImageView=rootView.findViewById(R.id.recepieimg);
        playerView=rootView.findViewById(R.id.exoplayer);
        descriptionTv=rootView.findViewById(R.id.description);

        playbackPosition= C.TIME_UNSET;
        defaultImageView.setVisibility(View.GONE);
        if (savedInstanceState != null)
        {
            playwhenReady=savedInstanceState.getBoolean(PLAYWHENREADY);
            currentPosition=savedInstanceState.getInt(CURRENTPOS);
            playbackPosition=savedInstanceState.getLong(PLAYBACKPOS,C.TIME_UNSET);
        }
        videourl=steps.get(currentPosition).getVideoURL();
        descriptionTv.setText(steps.get(currentPosition).getDescription());
        hideExo();
        if (currentPosition == 0)
        {
            backButton.setVisibility(View.INVISIBLE);
        }
        if (currentPosition ==(steps.size()-1))
        {
            forwardButton.setVisibility(View.INVISIBLE);
        }
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releasePlayer();
                forwardButton.setVisibility(View.VISIBLE);
                currentPosition--;
                descriptionTv.setText(steps.get(currentPosition).getDescription());
                if (currentPosition == 0)
                {
                    backButton.setVisibility(View.INVISIBLE);
                }
                videourl=steps.get(currentPosition).getVideoURL();
                hideExo();
                startPlayer();
                player.seekTo(0);
            }
        });
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                releasePlayer();
                backButton.setVisibility(View.VISIBLE);
                currentPosition++;
                descriptionTv.setText(steps.get(currentPosition).getDescription());
                if (currentPosition == (steps.size())-1)
                {
                    forwardButton.setVisibility(View.INVISIBLE);
                }
                videourl=steps.get(currentPosition).getVideoURL();
                hideExo();
                startPlayer();
                player.seekTo(0);
            }
        });

        return rootView;
    }
    private void hideExo() {
        if (TextUtils.isEmpty(videourl))
        {
            playerView.setVisibility(View.INVISIBLE);
            defaultImageView.setVisibility(View.VISIBLE);
            if (!TextUtils.isEmpty(steps.get(currentPosition).getThumbnailURL()))
            {
                Glide.with(this).load(steps.get(currentPosition).getThumbnailURL()).into(defaultImageView);
            }
        }else {
            playerView.setVisibility(View.VISIBLE);
            defaultImageView.setVisibility(View.INVISIBLE);
        }
    }

    private void startPlayer() {
        player= ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getActivity()),new DefaultTrackSelector(),new DefaultLoadControl());
        playerView.setPlayer(player);
        Uri uri=Uri.parse(videourl);
        MediaSource mediaSource=buildMedia(uri);
        player.prepare(mediaSource,true,false);
        player.setPlayWhenReady(playwhenReady);
        if (playbackPosition != 0 && !stopPlay)
        {
            player.seekTo(playbackPosition);
        }
        else {
            player.seekTo(playerstopPosition);
        }
    }

    private MediaSource buildMedia(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("HI"))
                .createMediaSource(uri);
    }

    private void releasePlayer() {
        if (player != null)
        {
            player.stop();
            player.release();
            player=null;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(Util.SDK_INT>23)
        {
            releasePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT<23 && player == null)
        {
            startPlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURRENTPOS,currentPosition);
        outState.putLong(PLAYBACKPOS,player.getCurrentPosition());
        outState.putBoolean(PLAYWHENREADY,player.getPlayWhenReady());
    }



}
