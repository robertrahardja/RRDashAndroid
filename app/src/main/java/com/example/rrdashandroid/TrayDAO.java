package com.example.rrdashandroid;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.rrdashandroid.Objects.Tray;

import java.util.List;

@Dao
public interface TrayDAO {
    @Query("SELECT * FROM tray")
    List<Tray> getAll();

    @Insert
    void insertAll(Tray... trays);

    @Query("DELETE FROM tray")
    void deleteAll();

    @Query("SELECT * FROM tray WHERE meal_id = :mealId")
    Tray getTray(String mealId);

    @Query("UPDATE tray SET meal_quantity = meal_quantity + :mealQty WHERE id = :trayId")
    void updateTray(int trayId, int mealQty);
}
