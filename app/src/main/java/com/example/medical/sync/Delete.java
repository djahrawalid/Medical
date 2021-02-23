package com.example.medical.sync;

import android.app.Activity;
import android.app.ProgressDialog;
import android.widget.Toast;

import com.example.medical.model.Product;
import com.example.medical.viewModel.ProductRepository;
import com.koushikdutta.ion.Ion;

import static com.example.medical.sync.InsertAll.SERVER;

public class Delete {
    public void delete(Activity activity,Product product){
        ProgressDialog progressDialog = new ProgressDialog(activity);
        progressDialog.setMessage("Wait...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Ion.with(activity)
                .load("GET",SERVER+"/delete.php?id="+product.getId())
                .setTimeout(2000)
                .asString()
                .withResponse()
                .setCallback((e, result) -> {
                    progressDialog.dismiss();
                    if (e!=null){
                        product.setSync(false);
                        Toast.makeText(activity, "Erreur de la connexion", Toast.LENGTH_SHORT).show();
                    }else {
                        int code = result.getHeaders().code();
                        if (code >= 200 && code < 300) {
                            new ProductRepository(activity.getApplication()).delete(product);
                            Toast.makeText(activity, "Produit été supprimé avec succés", Toast.LENGTH_SHORT).show();
                        } else {
                            product.setSync(false);
                            Toast.makeText(activity, "Erreur de la connexion", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
