package com.example.navigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;

public class WorkingWithFilters {
   public void useFilter(String nameOfFilter){
       Filter myFilter = new Filter();
       myFilter.addSubFilter(new BrightnessSubFilter(30));
       myFilter.addSubFilter(new ContrastSubFilter(1.1f));
//       BitmapFactory.decodeResource(resources, R.mipmap.kit);
//       Bitmap outputImage = myFilter.processFilter();
   }



}
