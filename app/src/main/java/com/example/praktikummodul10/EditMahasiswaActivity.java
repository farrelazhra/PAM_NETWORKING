package com.example.praktikummodul10;

import android.os.Bundle;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import com.example.praktikummodul10.ApiConfig;
import com.example.praktikummodul10.model.AddMahasiswaResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditMahasiswaActivity extends AppCompatActivity {

    EditText editNrp, editNama, editEmail, editJurusan;
    Button btnUpdate;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mahasiswa);

        editNrp = findViewById(R.id.editNrp);
        editNama = findViewById(R.id.editNama);
        editEmail = findViewById(R.id.editEmail);
        editJurusan = findViewById(R.id.editJurusan);
        btnUpdate = findViewById(R.id.btnUpdate);

        // mengambil data dari intent
        id = getIntent().getStringExtra("id");
        editNrp.setText(getIntent().getStringExtra("nrp"));
        editNama.setText(getIntent().getStringExtra("nama"));
        editEmail.setText(getIntent().getStringExtra("email"));
        editJurusan.setText(getIntent().getStringExtra("jurusan"));

        btnUpdate.setOnClickListener(v -> {
            Call<AddMahasiswaResponse> call = ApiConfig.getApiService().updateMahasiswa(
                    id,
                    editNrp.getText().toString(),
                    editNama.getText().toString(),
                    editEmail.getText().toString(),
                    editJurusan.getText().toString()
            );

            call.enqueue(new Callback<AddMahasiswaResponse>() {
                @Override
                public void onResponse(Call<AddMahasiswaResponse> call, Response<AddMahasiswaResponse> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(EditMahasiswaActivity.this, "Berhasil diupdate", Toast.LENGTH_SHORT).show();
                        finish(); // kembali ke list
                    }
                }

                @Override
                public void onFailure(Call<AddMahasiswaResponse> call, Throwable t) {
                    Toast.makeText(EditMahasiswaActivity.this, "Gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });
    }
}
