package com.example.alumnos.enemyappv2;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.List;

public class EnemiesGridAdapter extends ArrayAdapter {
    Context context;
    int item_layout;
    List <Enemy> enemies;
   private Fragment fragment;

    AddEnemiesFragment addEnemiesFragment;

    public EnemiesGridAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.context =context;
        this.item_layout =resource;
        this.enemies = objects;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(item_layout, parent,false);

        }

        fragment = new AddEnemiesFragment();
        addEnemiesFragment = (AddEnemiesFragment)fragment;
        String name = enemies.get(position).getName();
        float rate = enemies.get(position).getRate();
        Uri image = Uri.parse(enemies.get(position).getPermanentStringUri());





        TextView enemyName = convertView.findViewById(R.id.grid_layout_title);
        enemyName.setText(name);

        RatingBar enemyRateBar = convertView.findViewById(R.id.grid_layout_rating_bar);
        enemyRateBar.setRating(rate);

        ImageView enemyPicture = convertView.findViewById(R.id.grid_layout_image);
        enemyPicture.setImageURI(image);


        notifyDataSetChanged();
        return convertView;

    }


}
