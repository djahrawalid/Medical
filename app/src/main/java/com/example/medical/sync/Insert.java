package com.example.medical.sync;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.medical.model.Product;
import com.example.medical.viewModel.ProductRepository;
import com.koushikdutta.ion.Ion;

import static com.example.medical.sync.InsertAll.SERVER;

public class Insert {
    public void insert(Activity activity,Product product){
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(activity)
                .load("POST",SERVER+"/insertOrUpdate.php")
                .setTimeout(2000)
                .setJsonPojoBody(product)
                .asString()
                .withResponse()
                .setCallback((e, result) -> {
                    if (e!=null){
                        product.setSync(false);
                        Toast.makeText(activity, "Erreur de la connexion", Toast.LENGTH_SHORT).show();
                    }else {
                        int code = result.getHeaders().code();
                        progressDialog.dismiss();
                        if (code >= 200 && code < 300) {
                            product.setSync(true);
                        } else {
                            product.setSync(false);
                            Toast.makeText(activity, "Erreur de la connexion", Toast.LENGTH_SHORT).show();
                        }
                    }
                    new ProductRepository(activity.getApplication()).insert(product);
                    activity.finish();
                });
    }
}
