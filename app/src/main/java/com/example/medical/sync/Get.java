package com.example.medical.sync;

import android.app.Activity;
import android.util.Log;

import com.example.medical.model.Product;
import com.example.medical.viewModel.ProductRepository;
import com.google.gson.reflect.TypeToken;
import com.koushikdutta.ion.Ion;

import java.util.List;

import static com.example.medical.sync.InsertAll.SERVER;

public class Get {
    public void get(Activity activity){

        Ion.with(activity)
                .load("GET",SERVER+"/get.php")
                .setTimeout(2000)
                .as(new TypeToken<List<Product>>() {})
                .withResponse()
                .setCallback((e, result) -> {
                    if (e==null){
                        if (result.getResult()!=null) {
                            new ProductRepository(activity.getApplication()).insert(result.getResult());
                        }
                    }else{
                        Log.e("error:",e.getMessage()+"");
                    }
                });

    }
}
