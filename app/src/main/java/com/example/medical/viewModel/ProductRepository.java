package com.example.medical.viewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.example.medical.background.AppDatabase;
import com.example.medical.dao.ProductDao;
import com.example.medical.model.Product;

import java.util.List;

public class ProductRepository extends AndroidViewModel {
    private final ProductDao productDao;
    public ProductRepository(@NonNull Application application) {
        super(application);
        productDao = AppDatabase.getInstance(application).productDao();
    }

    public void insert(Product product){
        productDao.insert(product);
    }

    public void insert(List<Product> products){
        productDao.insert(products);
    }

    public void update(Product product){
        productDao.update(product);
    }

    public void delete(Product product){
        productDao.delete(product);
    }

    public Product findProductByCode(String code){
        return productDao.findByCode(code);
    }

    public LiveData <List<Product>> findAll(String idCategory){
         return productDao.findAll(idCategory);
    }

    public LiveData <List<Product>> findAll(){
        return productDao.findAll();
    }

    public List<Product> findNotSync(){
        return productDao.findNotSync();
    }

    public  LiveData <List<String>> findClasses() {
        return productDao.findClasses();
    }
    public  long findLastId() {
        return productDao.findLastId();
    }

    public void setAllIsSync() {
        productDao.setAllIsSync();
    }
}
