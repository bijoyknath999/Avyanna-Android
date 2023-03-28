package com.dev.avyanna.utils;

import android.content.Context;

import com.dev.avyanna.R;

import java.util.Arrays;
import java.util.List;

public class ArrayUtils {
    public static List<String> getSectionstitle(Context context)
    {
        List<String> titles = Arrays.asList(context.getResources().getStringArray(R.array.sections_list));
        return titles;
    }

    public static List<String> getSectionsDescription(Context context)
    {
        List<String> texts = Arrays.asList(context.getResources().getStringArray(R.array.sections_list_des));
        return texts;
    }
}
