package com.example.kontakapp.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class UbahActivity extends AppCompatActivity {
    private int xId;
    private String xNama, xNomor;
    private EditText etNama, etNomor;
    private Button btnUbah;
    private String yNama, yNomor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubah);

        Intent terima = getIntent();
        xId = terima.getIntExtra("xId", -1);
        xNama = terima.getStringExtra("xNama");
        xNomor = terima.getStringExtra("xNomor");

        etNama = findViewById(R.id.et_nama);
        etNomor = findViewById(R.id.et_nomor);
        btnUbah = findViewById(R.id.btn_ubah);

        etNama.setText(xNama);
        etNomor.setText(xNomor);

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yNama = etNama.getText().toString();
                yNomor = etNomor.getText().toString();


                updateData();


            }
        });
    }

    private void updateData(){
        APIRequestData ardData = RetroServer.konekRetrofit().create(APIRequestData.class);
        Call<ResponseModel> ubahData = ardData.ardUpdateData(xId, yNama, yNomor);


        ubahData.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                int kode = response.body().getKode();
                String pesan = response.body().getPesan();

                Toast.makeText(UbahActivity.this, "kode: "+kode+" | Pesan:  "+pesan, Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(UbahActivity.this, "Gagal menghubungi server | "+t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}