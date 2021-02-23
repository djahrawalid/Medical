package com.example.medical.sync;

import android.app.Activity;

import com.example.medical.model.Product;
import com.example.medical.viewModel.ProductRepository;
import com.koushikdutta.ion.Ion;

import java.util.List;

public class InsertAll {
    public static final String SERVER="http://10.20.0.220/medical";
    public void insertAll(Activity activity, List<Product> products){
        Ion.with(activity)
                .load("POST",SERVER+"/insertOrUpdateAll.php")
                .setTimeout(2000)
                .setJsonPojoBody(products)
                .asString()
                .withResponse()
                .setCallback((e, result) -> {
                    if (e==null){
                        int code = result.getHeaders().code();
                        if (code >= 200 && code < 300) {
                            new ProductRepository(activity.getApplication()).setAllIsSync();
                        }
                    }
                });
    }
}
