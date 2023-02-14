package com.example.foodies.model.recipe;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodies.MainActivity;
import com.example.foodies.MyApplication;

@Database(entities = {Recipe.class}, version = 77)
abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract RecipeDao recipeDao();
}

public class AppLocalDb{
    static public AppLocalDbRepository getAppDb() {
        return Room.databaseBuilder(MyApplication.getMyContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }

    private AppLocalDb(){}
}

