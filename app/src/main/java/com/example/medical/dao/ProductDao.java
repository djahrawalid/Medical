package com.example.medical.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.medical.model.Product;

import java.util.List;

/**
 * @Dao hia li taccedi l bd w texecuti les reuqetes fiha
 * kain des function pré-definit kima @Insert w @Update w @Delete ydirhm wahdo
 * onConflict = OnConflictStrategy.REPLACE  : hedi m3naha ki yji yinserer product w yl9a beli id kain men 9bel ycrasih si non nta tgolo wech ydir
 */
@Dao
public interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(List<Product> products);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);

    @Query("select * from product where className=:className order by denomination")
    LiveData <List<Product>> findAll(String className);

    @Query("select * from product order by denomination")
    LiveData <List<Product>> findAll();

    @Query("select * from product where barCode=:code ")
    Product findByCode(String code);

    /** hna m3naha jib les categories li kainin fel bd */
    @Query("select distinct className from product group by className")
    LiveData <List<String>> findClasses();

    @Query("select max(id) from product")
    long findLastId();

    @Query("update  product set isSync=1")
    void setAllIsSync();

    @Query("select * from product where isSync=0")
    List<Product> findNotSync();
}
