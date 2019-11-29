package com.example.alumnos.enemyappv2;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class AllEnemiesFragment extends Fragment {
ArrayAdapter adapter;
MainActivity mainActivity;

GridView allEnemies;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.all_enemies_fragment,container,false);
        mainActivity = (MainActivity)getActivity();
        allEnemies = view.findViewById(R.id.enemy_grid);
        adapter = new EnemiesGridAdapter(getContext(),R.layout.all_enemies_grid_layout, mainActivity.getEnemiesListClass().getArrayList());
        allEnemies.setAdapter(adapter);

        return  view;
    }


}
