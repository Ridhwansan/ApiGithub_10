package com.example.tugasakhir;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class DataRepository {
    private DataDao dataDao;
    private LiveData<List<Data>> allDatas;

    public DataRepository(Application application) {
        DataDatabase database = DataDatabase.getInstance(application);
        dataDao = database.dataDao();
        allDatas = dataDao.getAllDatas();
    }

    public void insert(Data dataa) {
        new InsertDataAsyncTask(dataDao).execute(dataa);
    }

    public void update(Data dataa) {

        new UpdateDataAsyncTask(dataDao).execute(dataa);
    }

    public void delete(Data dataa) {
        new DeleteDataAsyncTask(dataDao).execute(dataa);
    }

    public void deleteAllDatas() {
        new DeleteAllDatasAsyncTask(dataDao).execute();
    }

    public LiveData<List<Data>> getAllDatas(){
        return allDatas;
    }

    private static class InsertDataAsyncTask extends AsyncTask<Data, Void, Void> {

        private DataDao dataDao;

        private InsertDataAsyncTask(DataDao dataDao) {
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(Data... datas) {
            dataDao.insert(datas[0]);
            return null;
        }
    }

    private static class UpdateDataAsyncTask extends AsyncTask<Data, Void, Void> {

        private DataDao dataDao;

        private UpdateDataAsyncTask(DataDao dataDao) {
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(Data... datas) {
            dataDao.update(datas[0]);
            return null;
        }
    }

    private static class DeleteDataAsyncTask extends AsyncTask<Data, Void, Void> {

        private DataDao dataDao;

        private DeleteDataAsyncTask(DataDao dataDao) {
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(Data... datas) {
            dataDao.delete(datas[0]);
            return null;
        }
    }

    private static class DeleteAllDatasAsyncTask extends AsyncTask<Void, Void, Void> {

        private DataDao dataDao;

        private DeleteAllDatasAsyncTask(DataDao dataDao) {
            this.dataDao = dataDao;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            dataDao.deleteAllNotes();
            return null;
        }
    }

}
