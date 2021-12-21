package com.example.apisample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private RecyclerView flowerlistRv;
    FlowersAdapter flowersAdapter;
    private List<FlowerResponse> flowerResponseList= new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        loaddatafromapi();
    }

    private void loaddatafromapi() {

        RetrofitClient.getApiInterface().getFlowers().enqueue(new Callback<List<FlowerResponse>>() {
            @Override
            public void onResponse(Call<List<FlowerResponse>> call, Response<List<FlowerResponse>> response) {
                if(response.isSuccessful()){
                    flowerResponseList = response.body();
                    Toast.makeText(MainActivity.this, "Size of "+flowerResponseList.size(), Toast.LENGTH_SHORT).show();
                    init();

                    Toast.makeText(MainActivity.this, "photo: "+flowerResponseList.get(1).getPhoto(), Toast.LENGTH_SHORT).show();

                    Log.d("anam", String.valueOf(flowerResponseList.get(1).getPhoto()));
                }
            }

            @Override
            public void onFailure(Call<List<FlowerResponse>> call, Throwable t) {

            }
        });
    }

    public void init(){

        flowerlistRv = findViewById(R.id.flowerlistRv);
        flowersAdapter = new FlowersAdapter(this,flowerResponseList);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        flowerlistRv.setLayoutManager(layoutManager);
        flowerlistRv.setAdapter(flowersAdapter);
        flowersAdapter.notifyDataSetChanged();

    }
}