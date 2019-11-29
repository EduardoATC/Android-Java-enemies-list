package com.example.alumnos.enemyappv2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.ArrayList;



import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import android.widget.ListView;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {
    /**
     * Variables declarations
     */
    private Fragment fragment = null;
    private DrawerLayout drawerLayout;
    private ListView menu;
    private ActionBarDrawerToggle  drawerToggle;
    private AddEnemiesFragment addEnemiesFragment;
    private final  int GALLERY_CODE = 1;
    protected   Uri permanentUri;
    private EnemiesList enemiesListClass;
    private SharedPreferences sharedPreferences;
   private Gson gson= new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /**
        Loading data from SharedPreferences
         */
        if(loadDataFromSharedPreferences() == null)
        {
            enemiesListClass = new EnemiesList();
        }
        else {
            enemiesListClass = loadDataFromSharedPreferences();
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /**
         * Navigation Drawer setup
         */
        menu = findViewById(R.id.menu);
        menu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                changeToDrawerOptionClicked(position);

            }
        });


        drawerLayout = findViewById(R.id.drawerlayout);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.abrir_menu, R.string.cerrar_menu);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        changeToDrawerOptionClicked(1);

    }


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Change to the differents fragments based on the option clicked on the navigation drawer
     * @param screen
     */
    void changeToDrawerOptionClicked(int screen) {

        FragmentManager manager = getSupportFragmentManager();
        switch (screen){

            case 0:

                fragment = new Top5Fragment();
                break;
            case 1:

                fragment = new AddEnemiesFragment();
                break;
            case 2:

                fragment = new AllEnemiesFragment();
                break;
        }
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.commit();
        drawerLayout.closeDrawer(Gravity.START);
    }


    /**
     * Open the gallery and start onActivityResult waiting for an image
     */
    void OpenGallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_CODE);
    }


    /**
     * Transform the uri(temporary image reference)of the image picked in the activity for result in a bitmap,
     * this bitmap is passed to the saveImageFromGallery() method, this method save the bitmap on the app storage to get a permanent reference to the image.
     * After that the method putImage from the AddEnemyFragment is called to transform that permanent reference in bitmap and then set that bitmap in the AddEnemyFragment imageView
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == GALLERY_CODE && data != null)

                try {
                    addEnemiesFragment = (AddEnemiesFragment)fragment;
                    Uri uri = data.getData();
                    InputStream inputStream = this.getContentResolver().openInputStream(uri);
                    Bitmap imageFromGallery = BitmapFactory.decodeStream(inputStream);



                    permanentUri = saveImageFromGallery(imageFromGallery);

                    addEnemiesFragment.putImage(permanentUri);

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
        }
    }

    /**
     * Save bitmap in the app storage to get a permanent Uri
     * @param image
     * @return
     */
    protected Uri saveImageFromGallery(Bitmap image) {

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, bytes);

        String path = MediaStore.Images.Media.insertImage(getContentResolver(), image, "Titulo", null);
        return Uri.parse(path);
    }


    /**
     * Return the class EnemiesList
     * @return
     */

public EnemiesList getEnemiesListClass() {

        return  enemiesListClass;
}


    /**
     * Save the EnemiesList class to SharedPreferences
     * @param enemiesList
     */
   public void saveDataToSharedPreferences(EnemiesList enemiesList) {
        sharedPreferences = getSharedPreferences("enemies", Context.MODE_PRIVATE);

        String json = toJson(enemiesList);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("data", json);
        editor.commit();
    }

    /**
     * load the EnemiesList class to SharedPreferences
     * @return
     */
    public EnemiesList loadDataFromSharedPreferences() {
        sharedPreferences = getSharedPreferences("enemies", Context.MODE_PRIVATE);

        String json = sharedPreferences.getString("data", "");
        System.out.println("Preferences"+json);
        return fromJson(json);
    }

    /**
     * Transform EnemiesList class to Json
     * @param enemiesList
     * @return
     */
    private String toJson(EnemiesList enemiesList) {
        return gson.toJson(enemiesList);
    }

    /**
     * Transform Json class to EnemiesList class
     * @param json
     * @return
     */
    private EnemiesList fromJson(String json) {
        return gson.fromJson(json, EnemiesList.class);
    }
}
