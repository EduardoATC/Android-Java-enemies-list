package com.example.alumnos.enemyappv2;



import java.util.ArrayList;

public class EnemiesList {
   private ArrayList<Enemy> enemiesList;

  public  EnemiesList () {
       this.enemiesList = new ArrayList<>();

   }


   public ArrayList<Enemy> getArrayList () {

       return enemiesList;
   }

}
