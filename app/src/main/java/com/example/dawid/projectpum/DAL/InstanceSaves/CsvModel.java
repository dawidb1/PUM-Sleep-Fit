package com.example.dawid.projectpum.DAL.InstanceSaves;

import java.util.Date;

/**
 * Created by Dawid on 02.06.2018.
 */

public class CsvModel {
    public static String[] Headers =new String[]{"Start sleep","End sleep", "Steps","Mood","Energy","Snore","Activities","Fruits","Vegetables","Coffee","Tea","Alcohol","Energy drinks"};

    public Date StartSleep;
    public String StartSleepString;
    public Date EndSleep;
    public String EndSleepString;

    public int Steps;
    public String StepsString;

    public Enum<CsvEnums.Mood> Mood;
    public Enum<CsvEnums.Energy> Energy;

    public boolean Snore;
    public String Activities;
    public int ActivitiesScore;

    public int Fruits;
    public int Vegetables;
    public int Coffee;
    public int Tea;
    public int Alcohol;
    public int EnergyDrinks;
}
