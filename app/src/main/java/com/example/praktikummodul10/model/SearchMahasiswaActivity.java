package com.example.praktikummodul10.model;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import com.example.praktikummodul10.ApiConfig;
import com.example.praktikummodul10.R;
import com.example.praktikummodul10.model.Mahasiswa;
import com.example.praktikummodul10.model.MahasiswaResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMahasiswaActivity extends AppCompatActivity {

    private EditText edtChecNrp;
    private Button btnSearch;
    private ProgressBar progressBar;
    private TextView tvNrp, tvNama, tvEmail, tvJurusan;
    private List<Mahasiswa> mahasiswaList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_mahasiswa);

        edtChecNrp = findViewById(R.id.edtChckNrp);
        btnSearch = findViewById(R.id.btnSearch);
        progressBar = findViewById(R.id.progressBar);
        tvNrp = findViewById(R.id.tvValNrp);
        tvNama = findViewById(R.id.tvValNama);
        tvEmail = findViewById(R.id.tvValEmail);
        tvJurusan = findViewById(R.id.tvValJurusan);

        mahasiswaList = new ArrayList<>();

        btnSearch.setOnClickListener(view -> {
            showLoading(true);
            String nrp = edtChecNrp.getText().toString();
            if (nrp.isEmpty()) {
                edtChecNrp.setError("Silahkan isi NRP terlebih dahulu");
                showLoading(false);
            } else {
                Call<MahasiswaResponse> client = ApiConfig.getApiService().getMahasiswa(nrp);
                client.enqueue(new Callback<MahasiswaResponse>() {
                    @Override
                    public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                        showLoading(false);
                        if (response.isSuccessful()) {
                            if (response.body() != null) {
                                mahasiswaList = response.body().getData();
                                setData(mahasiswaList);
                            }
                        } else {
                            Toast.makeText(SearchMahasiswaActivity.this, "Data tidak ditemukan", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                        showLoading(false);
                        Log.e("Error Retrofit", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }

    private void setData(List<Mahasiswa> mahasiswaList) {
        if (!mahasiswaList.isEmpty()) {
            Mahasiswa mhs = mahasiswaList.get(0);
            tvNrp.setText(mhs.getNrp());
            tvNama.setText(mhs.getNama());
            tvEmail.setText(mhs.getEmail());
            tvJurusan.setText(mhs.getJurusan());
        } else {
            Toast.makeText(this, "Data kosong!", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLoading(Boolean isLoading) {
        if (isLoading) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }
}