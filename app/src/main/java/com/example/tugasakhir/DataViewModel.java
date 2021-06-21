package com.example.tugasakhir;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private DataRepository repository;
    private LiveData<List<Data>> allDatas;

    public DataViewModel(@NonNull Application application) {
        super(application);
        repository = new DataRepository(application);
        allDatas = repository.getAllDatas();
    }

    public void insert(Data dataa) {
        repository.insert(dataa);
    }
    public void update(Data dataa) {
        repository.update(dataa);
    }
    public void delete(Data dataa) {
        repository.delete(dataa);
    }
    public void deleteAllDatas() {
        repository.deleteAllDatas();
    }
    public LiveData<List<Data>> getAllDatas() {
        return allDatas;
    }
}
