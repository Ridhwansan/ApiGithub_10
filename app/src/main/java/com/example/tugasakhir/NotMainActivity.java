package com.example.tugasakhir;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static android.app.Activity.RESULT_OK;

public class NotMainActivity extends Fragment{
    public static final int ADD_DATA_REQUEST = 1;
    public static final int EDIT_DATA_REQUEST = 2;

    private DataViewModel dataViewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup containter, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.activity_main1, containter, false);

        FloatingActionButton buttonAddData = view.findViewById(R.id.button_add_data);
        buttonAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddEditDataActivity.class);
                startActivityForResult(intent, ADD_DATA_REQUEST);
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);

        DataAdapter adapter = new DataAdapter();
        recyclerView.setAdapter(adapter);

        dataViewModel = ViewModelProviders.of(getActivity()).get(DataViewModel.class);
        dataViewModel.getAllDatas().observe(getActivity(), new Observer<List<Data>>() {
            @Override
            public void onChanged(List<Data> datas) {
                adapter.submitList(datas);
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView,  RecyclerView.ViewHolder viewHolder,  RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped( RecyclerView.ViewHolder viewHolder, int direction) {
              dataViewModel.delete(adapter.getDataAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getActivity(), "Data Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(new DataAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Data dataa) {
                Intent intent =new Intent(getActivity(), AddEditDataActivity.class);
                intent.putExtra(AddEditDataActivity.EXTRA_ID, dataa.getId());
                intent.putExtra(AddEditDataActivity.EXTRA_NAME, dataa.getName());
                intent.putExtra(AddEditDataActivity.EXTRA_LINK, dataa.getLink());
                intent.putExtra(AddEditDataActivity.EXTRA_EMAIL, dataa.getEmail());
                intent.putExtra(AddEditDataActivity.EXTRA_NUMBER, dataa.getNumber());
                startActivityForResult(intent, EDIT_DATA_REQUEST);
            }
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_DATA_REQUEST && resultCode == RESULT_OK) {
            assert data != null;
            String name = data.getStringExtra(AddEditDataActivity.EXTRA_NAME);
            String link = data.getStringExtra(AddEditDataActivity.EXTRA_LINK);
            String email = data.getStringExtra(AddEditDataActivity.EXTRA_EMAIL);
            int number = data.getIntExtra(AddEditDataActivity.EXTRA_NUMBER, 1);

            Data dataa = new Data(name, link, email, number);
            dataViewModel.insert(dataa);

            Toast.makeText(getActivity(), "Data Saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_DATA_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(AddEditDataActivity.EXTRA_ID, -1);

            if (id == -1) {
                Toast.makeText(getActivity(), "Data Can't be Updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String name = data.getStringExtra(AddEditDataActivity.EXTRA_NAME);
            String link = data.getStringExtra(AddEditDataActivity.EXTRA_LINK);
            String email = data.getStringExtra(AddEditDataActivity.EXTRA_EMAIL);
            int number = data.getIntExtra(AddEditDataActivity.EXTRA_NUMBER, 1);

            Data dataa = new Data(name,link,email,number);
            dataa.setId(id);
            dataViewModel.update(dataa);

            Toast.makeText(getActivity(), "Data Updated", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getActivity(), "Data not Saved", Toast.LENGTH_SHORT).show();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        super.onCreateOptionsMenu(menu, inflater);
//        inflater = getMenuInflater();
//        inflater.inflate(R.menu.main_menu, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_all_data:
                dataViewModel.deleteAllDatas();
                Toast.makeText(getActivity(), "All Data Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}