package com.example.dawid.projectpum.DAL.Helpers;

import com.example.dawid.projectpum.DAL.CheckboxesEnums;

import java.util.ArrayList;

/**
 * Created by Dawid on 15.05.2018.
 */

public class EasyConverter {
    public static String TimeToString(int hours, int minutes){
        String result = String.format("%02d", hours) + ":"
                + String.format("%02d", minutes);
        return result;
    }
    public static String BoolToString(boolean bool){
        if (bool){return "True";}
        else {return "False";}
    }

//    public static <T> EnumToStringArray(ArrayList<T> enumList){
//        String[] array = new String[enumList.size()];
//        int i = 0;
//        for (T item : enumList){
//            array[i] = (Enum)item.value();
//            i++;
//        }
//    }
}
