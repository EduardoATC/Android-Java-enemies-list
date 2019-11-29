package com.example.alumnos.enemyappv2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class AddEnemiesFragment extends Fragment {
    private ImageView enemyPicture;
    private EditText nameEditText;
    private Button acceptButton;
    private  MainActivity mainActivity;
    private RatingBar ratingBar;
    private Enemy enemy;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainActivity = (MainActivity)getActivity();
        final View view = inflater.inflate(R.layout.add_enemies_fragment,container,false);
        enemyPicture = view.findViewById(R.id.add_enemy_fragment_image);
        enemyPicture.setImageResource(R.drawable.select);
        acceptButton = view.findViewById(R.id.add_enemy_fragment_accept_button);
        nameEditText = view.findViewById(R.id.add_enemy_fragment_edit_text);
        ratingBar = view.findViewById(R.id.add_enemies_fragment_rating_bar);


/**
 * open the gallery when the user click on the imageview
 */

       enemyPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.OpenGallery();

            }
        });

/**
 * get all the data written by the user, create an Enemy and add it to the EnemyList arraylist
 */
       acceptButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

               String name = nameEditText.getText().toString();
               float rate = ratingBar.getRating();
               Uri internalUri = mainActivity.permanentUri;


               if((name.equals("") || rate==0 || internalUri.equals(""))){
                   Toast.makeText(getActivity(), "Data is missing",
                           Toast.LENGTH_LONG).show();

               }
               else{
                   String internalUriString = internalUri.toString();
                   enemy = new Enemy (name, rate, internalUriString);
                   mainActivity.getEnemiesListClass().getArrayList().add(enemy);
                   mainActivity.saveDataToSharedPreferences(mainActivity.getEnemiesListClass());
                   Toast.makeText(getActivity(), "Enemy saved",
                           Toast.LENGTH_LONG).show();
                   mainActivity.changeToDrawerOptionClicked(2);
               }





           }
       });

        return  view;
    }

    /**
     * Recieve an image uri from the app storage, transmform it in bitmap and set it to the imageview of this fragment.
     * @param uri
     */
    public void putImage(Uri uri)
    {
        InputStream inputStream = null;

        Uri internalUti = uri;
        if (getActivity().getContentResolver() !=null) {
            try {
                inputStream = getActivity().getContentResolver().openInputStream(internalUti);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        Bitmap image = BitmapFactory.decodeStream(inputStream);
        enemyPicture.setImageBitmap(image);
    }



}
