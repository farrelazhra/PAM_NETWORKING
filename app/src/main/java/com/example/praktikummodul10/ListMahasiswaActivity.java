package com.example.praktikummodul10;

import android.os.Bundle;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikummodul10.MahasiswaAdapter;
import com.example.praktikummodul10.ApiConfig;
import com.example.praktikummodul10.model.Mahasiswa;
import com.example.praktikummodul10.model.MahasiswaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListMahasiswaActivity extends AppCompatActivity {

    private RecyclerView rvMahasiswa;
    private MahasiswaAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mahasiswa);

        rvMahasiswa = findViewById(R.id.rvMahasiswa);
        rvMahasiswa.setLayoutManager(new LinearLayoutManager(this));

        ApiConfig.getApiService().getAllMahasiswa().enqueue(new Callback<MahasiswaResponse>() {
            @Override
            public void onResponse(Call<MahasiswaResponse> call, Response<MahasiswaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Mahasiswa> data = response.body().getData();
                    adapter = new MahasiswaAdapter(ListMahasiswaActivity.this, data);
                    rvMahasiswa.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<MahasiswaResponse> call, Throwable t) {
                Log.e("API Error", "onFailure: " + t.getMessage());
            }
        });
    }
}
