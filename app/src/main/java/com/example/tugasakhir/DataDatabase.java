package com.example.tugasakhir;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Data.class}, version = 1)
public abstract class DataDatabase extends RoomDatabase {
    private static DataDatabase instance;

    public abstract DataDao dataDao();
    
    public static synchronized DataDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DataDatabase.class, "Data_Database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DataDao dataDao;
        private  PopulateDbAsyncTask(DataDatabase db) {
            dataDao = db.dataDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dataDao.insert(new Data("User Name", "Github Profile Link", "User Email", 1));
            dataDao.insert(new Data("User Name", "Github Profile Link", "User Email", 2));
            dataDao.insert(new Data("User Name", "Github Profile Link", "User Email", 3));
            dataDao.insert(new Data("User Name", "Github Profile Link", "User Email", 4));
            return null;
        }
    }

}
