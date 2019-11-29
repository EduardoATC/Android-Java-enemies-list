package com.example.alumnos.enemyappv2;



public class Enemy  {
    private String name;
    private float rate;
    private String permanentStringUri;



    public Enemy(String name, float rate, String uri ) {

        this.name = name;
        this.rate = rate;
        this.permanentStringUri = uri;
    }


    /**
     * name getter
     * @return
     */
    public String getName () {
        return name;

    }

    /**
     * Permanent String uri getter
     * @return
     */
    public String getPermanentStringUri (){

        return permanentStringUri;
    }
    /**
     * rate getter
     * @return
     */
    public float getRate () {
        return  rate;
    }





}
