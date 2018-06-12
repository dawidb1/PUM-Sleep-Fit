package com.example.dawid.projectpum.DAL.Helpers;

/**
 * Created by Dawid on 12.06.2018.
 */

public class ScoreWeights {
    // zalecane przez WHO - min 60 minut aktywności dziennie
    public final static int SNORE = 5;

    public final static int LOW_INTENSE_ACTIVITY = 2;
    public final static int MID_INTENSE_ACTIVITY = 4;
    public final static int HIGH_INTENSE_ACTIVITY = 6;

    public final static int LOW_TIME_ACTIVITY = 2;
    public final static int MID_TIME_ACTIVITY = 4;
    public final static int HIGH_TIME_ACTIVITY = 6;

    public final static int FRUIT = 3;
    public final static int VEGETABLE = 4;

    public final static int COFFEE = 1;
    public final static int TEA = 1;

    public final static int ALCOHOL = -4;
    public final static int ENERGY_DRINKS = -4;
}
