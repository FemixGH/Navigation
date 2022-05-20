package com.example.navigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.vader.sentiment.analyzer.SentimentAnalyzer;
import com.zomato.photofilters.
import com.zomato.photofilters.SampleFilters;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;

public class WorkingWithFilters {
    Filter fooFilter = SampleFilters.getBlueMessFilter();
    Bitmap outputImage = fooFilter.processFilter(R.mipmap.kit);
    Filter myFilter = new Filter();
myFilter.addSubFilter(new BrightnessSubFilter(30));
myFilter.addSubFilter(new ContrastSubFilter(1.1f));
    Bitmap outputImage = myFilter.processFilter(inputImage)

}