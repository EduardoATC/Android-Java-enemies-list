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

public class Top5Fragment extends Fragment {
    ArrayAdapter adapter;
    GridView top5GridView;
    MainActivity mainActivity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_5_enemies_fragment,container,false);
        mainActivity = (MainActivity)getActivity();

        top5GridView = view.findViewById(R.id.top5_grid);
        adapter = new Top5GridAdapter(getContext(), R.layout.top_5_enemies_grid_layout, mainActivity.getEnemiesListClass().getArrayList());
        top5GridView.setAdapter(adapter);

        return  view;
    }
}
