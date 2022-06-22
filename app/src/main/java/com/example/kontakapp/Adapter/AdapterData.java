package com.example.kontakapp.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kontakapp.Activity.MainActivity;
import com.example.kontakapp.Activity.UbahActivity;
import com.example.kontakapp.Api.APIRequestData;
import com.example.kontakapp.Api.RetroServer;
import com.example.kontakapp.Model.DataModel;
import com.example.kontakapp.Model.ResponseModel;
import com.example.kontakapp.R;

import java.util.List;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdapterData extends RecyclerView.Adapter<AdapterData.HolderData> {
    private Context ctx;
    private List<DataModel> listData;
    private List<DataModel> listKontak;
    private int idKontak;

    public AdapterData(Context ctx, List<DataModel> listData) {
        this.ctx = ctx;
        listData = listData;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        HolderData holder  = new HolderData(layout);
        return holder;

    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        DataModel dm = listData .get(position);

        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvNama.setText(dm.getNama());
        holder.tvNomor.setText(dm.getNomor());

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvId, tvNama, tvNomor;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id);
            tvNama = itemView.findViewById(R.id.tv_nama);
            tvNomor = itemView.findViewById(R.id.tv_nomor);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih operasi yang akan anda lakukan");
                    dialogPesan.setTitle("Perhatian!");
                    dialogPesan.setIcon(R.mipmap.ic_launcher_round);
                    //dialogPesan.setIcon(R.mipmap.ic_launcher);
                    dialogPesan.setCancelable(true);

                    idKontak = Integer.parseInt(tvId.getText().toString());


                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteData();
                            dialogInterface.dismiss();
//                            Handler hand = new Handler();
//                            hand.postDelayed(new Runnable(){
//
//                            });
                            ((MainActivity)ctx).retrieveData();


                        }
                    });
                    dialogPesan.setNegativeButton("Ubah", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            getData();
                            dialogInterface.dismiss();


                        }
                    });
                    dialogPesan.show();

                    return false;
                }
            });
        }
        private void deleteData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> hapusData = ardData.ardDeleteData(idKontak);

            hapusData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();

                    Toast.makeText(ctx, "Kode "+kode+" | pesan: "+pesan, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server: "+ t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
        private void getData(){
            APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
            Call<ResponseModel> ambilData = ardData.ardGetData(idKontak);

            ambilData.enqueue(new Callback<ResponseModel>() {
                @Override
                public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                    int kode = response.body().getKode();
                    String pesan = response.body().getPesan();
                    listKontak = response.body().getData();

                    int varIdKontak = listKontak.get(0).getId();
                    String varNamaKontak = listKontak.get(0).getNama();
                    String varNomorKontak = listKontak.get(0).getNomor();

                    //Toast.makeText(ctx, "Kode: "+kode+" |Pesan: "+pesan+ "| Data : "+varIdKontak+" | "+varNamaKontak + " | " +varNomorKontak , Toast.LENGTH_SHORT).show();

                    Intent kirim = new Intent(ctx , UbahActivity.class);
                    kirim.putExtra("xId", varIdKontak);
                    kirim.putExtra("xNama", varNamaKontak);
                    kirim.putExtra("xNomor", varNomorKontak);
                    ctx.startActivity(kirim);
                }

                @Override
                public void onFailure(Call<ResponseModel> call, Throwable t) {
                    Toast.makeText(ctx, "Gagal Menghubungi Server: "+ t.getMessage(), Toast.LENGTH_SHORT).show();

                }
            });
        }
    }
}
