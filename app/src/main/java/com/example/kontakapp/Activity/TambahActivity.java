package com.example.kontakapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kontakapp.Api.APIRequestData;
import com.example.kontakapp.Api.RetroServer;
import com.example.kontakapp.Model.ResponseModel;
import com.example.kontakapp.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TambahActivity extends AppCompatActivity {
    private EditText etNama, etNomor;
    private Button btnSimpan;
    private String nama, nomor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah);

        etNama = findViewById(R.id.et_nama);
        etNomor = findViewById(R.id.et_nomor);
        btnSimpan = findViewById(R.id.btn_simpan);

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nama = etNama.getText().toString();
                nomor = etNomor.getText().toString();


                if(nama.trim().equals("")){
                    etNama.setError("Nama harus di isi");

                }else if (nomor.trim().equals("")){
                    etNomor.setError("Nomor harus di isi");

                }else{
                    createData();

                }
            }
        });
    }

    private void createData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> simpanData = ardData.ardCreateData(nama, nomor);

        simpanData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(TambahActivity.this, "kode: "+kode+" | Pesan:  "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(TambahActivity.this, "Gagal menghubungi server | "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}