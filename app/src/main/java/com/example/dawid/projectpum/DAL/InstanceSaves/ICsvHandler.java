package com.example.dawid.projectpum.DAL.InstanceSaves;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * Created by Dawid on 02.06.2018.
 */

public interface ICsvHandler {
    void fillModel();
    void saveSharedPrefFromModel();
    void saveStateFromModel(Bundle outState);
}
