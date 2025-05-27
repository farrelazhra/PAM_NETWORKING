package com.example.praktikummodul10;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.praktikummodul10.R;
import com.example.praktikummodul10.model.AddMahasiswaResponse;
import com.example.praktikummodul10.model.Mahasiswa;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MahasiswaAdapter extends RecyclerView.Adapter<MahasiswaAdapter.ViewHolder> {

    private Context context;
    private List<Mahasiswa> mahasiswaList;

    public MahasiswaAdapter(Context context, List<Mahasiswa> mahasiswaList) {
        this.context = context;
        this.mahasiswaList = mahasiswaList;
    }

    @NonNull
    @Override
    public MahasiswaAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_mahasiswa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MahasiswaAdapter.ViewHolder holder, int position) {
        Mahasiswa mhs = mahasiswaList.get(position);
        holder.tvNama.setText(mhs.getNama());
        holder.tvNrp.setText(mhs.getNrp());
        holder.tvEmail.setText(mhs.getEmail());
        holder.tvJurusan.setText(mhs.getJurusan());

        holder.btnEdit.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditMahasiswaActivity.class);
            intent.putExtra("id", mhs.getId());
            intent.putExtra("nrp", mhs.getNrp());
            intent.putExtra("nama", mhs.getNama());
            intent.putExtra("email", mhs.getEmail());
            intent.putExtra("jurusan", mhs.getJurusan());
            context.startActivity(intent);
        });

        holder.btnDelete.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Hapus Data")
                    .setMessage("Yakin ingin menghapus data ini?")
                    .setPositiveButton("Ya", (dialog, which) -> {
                        ApiConfig.getApiService().deleteMahasiswa(mhs.getId())
                                .enqueue(new Callback<AddMahasiswaResponse>() {
                                    @Override
                                    public void onResponse(Call<AddMahasiswaResponse> call, Response<AddMahasiswaResponse> response) {
                                        Toast.makeText(context, "Berhasil dihapus", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onFailure(Call<AddMahasiswaResponse> call, Throwable t) {
                                        Toast.makeText(context, "Gagal: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });
                    })
                    .setNegativeButton("Batal", null)
                    .show();
        });

    }
    @Override
    public int getItemCount() {
        return mahasiswaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNama, tvNrp, tvEmail, tvJurusan ;
        Button btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNama = itemView.findViewById(R.id.tvItemNama);
            tvNrp = itemView.findViewById(R.id.tvItemNrp);
            tvEmail = itemView.findViewById(R.id.tvItemEmail);
            tvJurusan = itemView.findViewById(R.id.tvItemJurusan);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}

