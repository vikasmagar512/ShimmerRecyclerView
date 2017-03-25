/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.support.android.designlibdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cooltechworks.views.shimmer.ShimmerRecyclerView;

import java.util.ArrayList;
import java.util.Random;

public class CheeseDetailActivity extends AppCompatActivity {

    public static final String EXTRA_NAME = "cheese_name";
    ArrayList<String> facultyArray;
    FacultyAdapter facultyAdapter;
    ShimmerRecyclerView shimmerRecycler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        final String cheeseName = intent.getStringExtra(EXTRA_NAME);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(cheeseName);

        loadBackdrop();
        facultyArray = new ArrayList<>();
        facultyAdapter = new FacultyAdapter(facultyArray);
        shimmerRecycler = (ShimmerRecyclerView) findViewById(R.id.shimmer_recycler_view);
        shimmerRecycler.setLayoutManager(new LinearLayoutManager(this));
        shimmerRecycler.setAdapter(facultyAdapter);
        shimmerRecycler.showShimmerAdapter();
        shimmerRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCards();
            }
        }, 2000);
    }

    private void loadCards() {
        ArrayList<String> facultyArrayAdd = new ArrayList<>();
        facultyArrayAdd.add("vikas");
        facultyArrayAdd.add("magar");
        facultyArrayAdd.add("vikas");
        facultyArrayAdd.add("magar");
        facultyAdapter.addItems(facultyArrayAdd);
        shimmerRecycler.hideShimmerAdapter();
    }

    public class FacultyAdapter extends RecyclerView.Adapter<FacultyAdapter.ListItemViewHolder> {
        public ArrayList<String> mitems;
        String TAG = "faculty adapter";
        public String RV_Type;
        public FacultyAdapter(ArrayList<String> items){
            this.mitems= items;
            Log.d(TAG, "FacultyAdapter: size "+ mitems.size());
        }
        @Override
        public FacultyAdapter.ListItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.course_about_content_item,parent,false);
            FacultyAdapter.ListItemViewHolder listItemViewHolder = new FacultyAdapter.ListItemViewHolder(v);
            return listItemViewHolder;
        }

        @Override
        public void onBindViewHolder(FacultyAdapter.ListItemViewHolder holder, int position) {
            holder.name.setText(mitems.get(position));
        }

        @Override
        public int getItemCount() {
            Log.d(TAG, "faculty getItemCount: "+mitems.size());return mitems.size();
        }
        public class ListItemViewHolder extends RecyclerView.ViewHolder {
            public TextView name;
            public ImageView circle;

            public ListItemViewHolder(View itemView) {
                super(itemView);
                name =(TextView) itemView.findViewById(R.id.name);
            }
        }
        public void addItems(ArrayList<String> Items){
            this.mitems.addAll(Items);
            notifyDataSetChanged();
        }
    }

    private void loadBackdrop() {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(Cheeses.getRandomCheeseDrawable()).centerCrop().into(imageView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sample_actions, menu);
        return true;
    }
}
