package com.example.tugasakhir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddEditDataActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.githubproject.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.githubproject.EXTRA_NAME";
    public static final String EXTRA_LINK =
            "com.example.githubproject.EXTRA_LINK";
    public static final String EXTRA_EMAIL =
            "com.example.githubproject.EXTRA_EMAIL";
    public static final String EXTRA_NUMBER =
            "com.example.githubproject.EXTRA_NUMBER";

    private EditText editTextName;
    private EditText editTextLink;
    private EditText editTextEmail;
    private NumberPicker numberPickerNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        editTextName = findViewById(R.id.edit_text_name);
        editTextLink = findViewById(R.id.edit_text_link);
        editTextEmail = findViewById(R.id.edit_text_email);
        numberPickerNumber = findViewById(R.id.number_picker_number);

        numberPickerNumber.setMinValue(1);
        numberPickerNumber.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Data");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextLink.setText(intent.getStringExtra(EXTRA_LINK));
            editTextEmail.setText(intent.getStringExtra(EXTRA_EMAIL));
            numberPickerNumber.setValue(intent.getIntExtra(EXTRA_NUMBER, 1));
        }else {
            setTitle("Add Data");
        }
    }

    private void saveData() {
        String name = editTextName.getText().toString();
        String link = editTextLink.getText().toString();
        String email = editTextEmail.getText().toString();
        int number = numberPickerNumber.getValue();

        if (name.trim().isEmpty() || link.trim().isEmpty() || email.trim().isEmpty()) {
            Toast.makeText(this, "Mohon isi nama, link, dan email", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent dataa = new Intent();
        dataa.putExtra(EXTRA_NAME, name);
        dataa.putExtra(EXTRA_LINK, link);
        dataa.putExtra(EXTRA_EMAIL, email);
        dataa.putExtra(EXTRA_NUMBER, number);

        int id = getIntent().getIntExtra(EXTRA_ID,-1);
        if (id != -1) {
            dataa.putExtra(EXTRA_ID, id);
        }

        setResult(RESULT_OK, dataa);
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_data_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_data:
                saveData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}