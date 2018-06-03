package com.example.dawid.projectpum.DAL;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.FileWriter;
import java.util.ArrayList;

/**
 * Created by Dawid on 16.05.2018.
 */

public class FileHelper {

    public static void PrintToFile(ArrayList<String> List){

    }
    public static void PrintToFile(String string){

    }

    public static void Shared(String key, String value){
//        SharedPreferences sharedPreferences = getSharedPreferences()

    }

    public static void main(String args[],String path) {
        String csvFilename = path;
        try {
            FileWriter csvwriter = new FileWriter(csvFilename);
            csvwriter.append("FIRSTNAME");
            csvwriter.append(",");
            csvwriter.append("LASTNAME");
            csvwriter.append(",");
            csvwriter.append("AGE");
            csvwriter.append(",");
            csvwriter.append("\n");

            csvwriter.append("Narayana");
            csvwriter.append(",");
            csvwriter.append("Ragi");
            csvwriter.append(",");
            csvwriter.append("'300000000000");
            csvwriter.append(",");
            csvwriter.append("\n");
            csvwriter.close();
            Log.i("sss", "CSV file created succesfully.");
        } catch (Exception e) {
            System.out.println("exception :" + e.getMessage());
        }
    }
}
