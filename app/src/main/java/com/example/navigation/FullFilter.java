package com.example.navigation;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.zomato.photofilters.geometry.Point;

import java.io.Serializable;

public class FullFilter<mContext> implements Serializable {
    Activity a;

    String nameFilter;
    Point[] rgbKnots = new Point[3];

    public String MY_PREFS_NAME;

    public void setMY_PREFS_NAME(String MY_PREFS_NAME) {
        this.MY_PREFS_NAME = MY_PREFS_NAME;
    }

    public String getMY_PREFS_NAME() {
        return MY_PREFS_NAME;
    }

    float contrast;
    float saturation;
    int colorOverlay_depth;
    float colorOverlay_red;
    float colorOverlay_green;
    float colorOverlay_blue;

    int rPointX;
    int rPointY;
    int gPointX;
    int gPointY;
    int bPointX;
    int bPointY;

    public FullFilter() {

    }

    int brightness;
    int vignette;

    public FullFilter(Activity a, String pref){
        this.MY_PREFS_NAME=pref;
        this.a=a;
    }

    public FullFilter(Activity a, String nameFilter, String MY_PREFS_NAME, float contrast, float saturation, int brightness, int vignette) {
        this.a = a;
        this.nameFilter = nameFilter;
        this.MY_PREFS_NAME = MY_PREFS_NAME;
        this.contrast = contrast;
        this.saturation = saturation;
        this.brightness = brightness;
        this.vignette = vignette;
    }

    public FullFilter(Activity a, String nameFilter, String MY_PREFS_NAME, float contrast, float saturation, int colorOverlay_depth, float colorOverlay_red, float colorOverlay_green, float colorOverlay_blue) {
        this.a = a;
        this.nameFilter = nameFilter;
        this.MY_PREFS_NAME = MY_PREFS_NAME;
        this.contrast = contrast;
        this.saturation = saturation;
        this.colorOverlay_depth = colorOverlay_depth;
        this.colorOverlay_red = colorOverlay_red;
        this.colorOverlay_green = colorOverlay_green;
        this.colorOverlay_blue = colorOverlay_blue;
    }

    public FullFilter(Activity a,
                      String name,
                      String pref,
                      float contrast,
                      float saturation,
                      int colorOverlay_depth, float colorOverlay_red, float colorOverlay_green, float colorOverlay_blue,
                      int rPointX, int rPointY,
                      int gPointX, int gPointY,
                      int bPointX, int bPointY,
                      int brightness,
                      int vignette) {
        this.contrast = contrast;
        this.saturation = saturation;
        this.colorOverlay_depth = colorOverlay_depth;
        this.colorOverlay_red = colorOverlay_red;
        this.colorOverlay_green = colorOverlay_green;
        this.colorOverlay_blue = colorOverlay_blue;
        this.rPointX = rPointX;
        this.rPointY = rPointY;
        this.gPointX = gPointX;
        this.gPointY = gPointY;
        this.bPointX = bPointX;
        this.bPointY = bPointY;
        this.brightness = brightness;
        this.vignette = vignette;
        this.nameFilter = name;
        this.MY_PREFS_NAME = pref;
        this.a = a;
        rgbKnots[0] = new Point(rPointX, rPointY);
        rgbKnots[1] = new Point(gPointX, gPointY);
        rgbKnots[2] = new Point(bPointX, bPointY);
    }

    public Point[] getRgbKnots() {
        return rgbKnots;
    }

    public String getNameFilter() {
        return nameFilter;
    }

    public void setNameFilter(String nameFilter) {
        this.nameFilter = nameFilter;
    }

    public float getContrast() {
        return contrast;
    }

    public void setContrast(float contrast) {
        this.contrast = contrast;
    }

    public float getSaturation() {
        return saturation;
    }

    public void setSaturation(float saturation) {
        this.saturation = saturation;
    }

    public int getColorOverlay_depth() {
        return colorOverlay_depth;
    }

    public void setColorOverlay_depth(int colorOverlay_depth) {
        this.colorOverlay_depth = colorOverlay_depth;
    }

    public float getColorOverlay_red() {
        return colorOverlay_red;
    }

    public void setColorOverlay_red(float colorOverlay_red) {
        this.colorOverlay_red = colorOverlay_red;
    }

    public float getColorOverlay_green() {
        return colorOverlay_green;
    }

    public void setColorOverlay_green(float colorOverlay_green) {
        this.colorOverlay_green = colorOverlay_green;
    }

    public float getColorOverlay_blue() {
        return colorOverlay_blue;
    }

    public void setColorOverlay_blue(float colorOverlay_blue) {
        this.colorOverlay_blue = colorOverlay_blue;
    }

    public int getrPointX() {
        return rPointX;
    }

    public void setrPointX(int rPointX) {
        this.rPointX = rPointX;
    }

    public int getrPointY() {
        return rPointY;
    }

    public void setrPointY(int rPointY) {
        this.rPointY = rPointY;
    }

    public int getgPointX() {
        return gPointX;
    }

    public void setgPointX(int gPointX) {
        this.gPointX = gPointX;
    }

    public int getgPointY() {
        return gPointY;
    }

    public void setgPointY(int gPointY) {
        this.gPointY = gPointY;
    }

    public int getbPointX() {
        return bPointX;
    }

    public void setbPointX(int bPointX) {
        this.bPointX = bPointX;
    }

    public int getbPointY() {
        return bPointY;
    }

    public void setbPointY(int bPointY) {
        this.bPointY = bPointY;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getVignette() {
        return vignette;
    }

    public void setVignette(int vignette) {
        this.vignette = vignette;
    }
    //        String nameFilter;
//
//        public String MY_PREFS_NAME;
//
//        float contrast;
//        float saturation;
//        int colorOverlay_depth;
//        float colorOverlay_red;
//        float colorOverlay_green;
//        float colorOverlay_blue;
//
//        int rPointX;
//        int rPointY;
//        int gPointX;
//        int gPointY;
//        int bPointX;
//        int bPointY;
//
//        int brightness;
//        int vignette;
    public void saveFilter(SharedPreferences prefs, Activity a,SharedPreferences pref){

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("name", getNameFilter())
                .putString("prefs", getMY_PREFS_NAME())
                .putFloat("contrast", getContrast())
                .putFloat("saturation", getSaturation()).
                putInt("colorOverlay_depth", getColorOverlay_depth()).
                putFloat("colorOverlay_red",getColorOverlay_red()).
                putFloat("colorOverlay_green",getColorOverlay_green()).
                putFloat("colorOverlay_blue",getColorOverlay_blue()).
                putInt("rPointX", getrPointX()).
                putInt("rPointY", getrPointY()).
                putInt("gPointX", getgPointX()).
                putInt("gPointY", getgPointY()).
                putInt("bPointX", getbPointX()).
                putInt("bPointY", getbPointY()).
                putInt("vignette", getVignette()).
                putInt("brightness", getBrightness()).commit();
        editor.apply();
    }

    public void getFilter(SharedPreferences prefs, String shared){

        this.contrast = prefs.getFloat("contrast", 0);
        this.saturation = prefs.getFloat("saturation", 0);
        this.colorOverlay_depth = prefs.getInt("colorOverlay_depth", 0);
        this.colorOverlay_red = prefs.getFloat("colorOverlay_red", 0);
        this.colorOverlay_green = prefs.getFloat("colorOverlay_green", 0);
        this.colorOverlay_blue = prefs.getFloat("colorOverlay_blue", 0);;
        this.rPointX = prefs.getInt("rPointX", 0);
        this.rPointY = prefs.getInt("rPointY", 0);
        this.gPointX = prefs.getInt("gPointX", 0);
        this.gPointY = prefs.getInt("gPointY", 0);
        this.bPointX = prefs.getInt("bPointX", 0);
        this.bPointY = prefs.getInt("bPointY", 0);
        this.brightness = prefs.getInt("brightness", 0);
        this.vignette = prefs.getInt("vignette", 0);
        this.nameFilter = prefs.getString("name", null);
        this.MY_PREFS_NAME = prefs.getString("prefs", "filter_names_2");
        rgbKnots[0] = new Point(rPointX, rPointY);
        rgbKnots[1] = new Point(gPointX, gPointY);
        rgbKnots[2] = new Point(bPointX, bPointY);
    }
}