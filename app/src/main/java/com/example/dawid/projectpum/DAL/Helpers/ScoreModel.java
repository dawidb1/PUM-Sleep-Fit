package com.example.dawid.projectpum.DAL.Helpers;

import static com.example.dawid.projectpum.DAL.CheckboxesEnums.*;
import static com.example.dawid.projectpum.DAL.Helpers.ScoreWeights.*;

/**
 * Created by Dawid on 12.06.2018.
 */

public class ScoreModel {
    static int HALF_HOUR = 30;
    static int HOUR = 60;

    static int SCORE_FACTOR = 10;
    static int MAX_RATING = 5;

    public int Rating;
    public int FullScore;

    public int ActivitiesScore;
    public int FruitAndVegetableScore;
    public int SnoreScore;

    public int FruitsScore;
    public int VegetablesScore;
    public int CoffeeScore;
    public int TeaScore;
    public int AlcoholScore;
    public int EnergyDrinksScore;

    public void setRating(int fullScore) {
        Rating = fullScore/SCORE_FACTOR;

        if(Rating>MAX_RATING) Rating = MAX_RATING;
    }

    public void setFullScore(ScoreModel model) {
        FullScore = model.SnoreScore + model.ActivitiesScore + model.FruitAndVegetableScore;
    }

    public void setFruitAndVegetableScore(ScoreModel scored) {
        FruitAndVegetableScore = scored.FruitsScore + scored.VegetablesScore
                + scored.CoffeeScore + scored.TeaScore + scored.AlcoholScore
                + scored.EnergyDrinksScore;
    }

    public static int getActivityIntenseScore(String activityIntense) {
        SportValues value = SportValues.fromString(activityIntense);
        switch (value){
            case LARGE: return HIGH_INTENSE_ACTIVITY;
            case MEDIUM: return MID_INTENSE_ACTIVITY;
            case SMALL: return LOW_INTENSE_ACTIVITY;
            default: return 0;
        }
    }

    public static int getActivityTimeScore(int time) {
        if (time<HALF_HOUR) return LOW_TIME_ACTIVITY;
        else if(time<HOUR) return MID_TIME_ACTIVITY;
        else return  HIGH_TIME_ACTIVITY;
    }

    public void setSnoreScore(boolean isSnore) {
        if (isSnore) SnoreScore = SNORE;
        else SnoreScore = 0;
    }

    public void setFruitsScore(int number) {
        FruitsScore = number*FRUIT;
    }
    public void setVegetablesScore(int number) {
        VegetablesScore = number*VEGETABLE;
    }

    public void setCoffeeScore(int number) {
        CoffeeScore = number*COFFEE;
    }

    public void setTeaScore(int number) {
        TeaScore = number*TEA;
    }

    public void setAlcoholScore(int number) {
        AlcoholScore = number*ALCOHOL;
    }

    public void setEnergyDrinksScore(int number) {
        EnergyDrinksScore = number*ENERGY_DRINKS;
    }
}
