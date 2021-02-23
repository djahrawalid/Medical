package com.example.medical.affichage;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Switch;
import android.widget.Toast;

import com.example.medical.R;
import com.example.medical.model.Product;
import com.example.medical.sync.Insert;
import com.example.medical.sync.Update;
import com.example.medical.viewModel.ProductRepository;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductActivity extends AppCompatActivity {
    TextInputEditText barCodeEt,denominationEt,nomComercialEt,laboratoireEt,
            formePharmaEt,lotEt,descriptionEt,dateFabricationEt,datePeremptionEt,dureeConservationEt,
            quantityEt,priceEt, classNameEt;

    Switch isRemborsableSwitch;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public final static int CAMERA_SCANNER=20;
    public final static int READ_NOM_COMER=21;
    public final static int READ_DENOM=22;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Product product = (Product)getIntent().getSerializableExtra("product");
         barCodeEt = findViewById(R.id.barCodeEt);
         denominationEt = findViewById(R.id.denominationEt);
         nomComercialEt = findViewById(R.id.nomComercialEt);
         classNameEt = findViewById(R.id.classNameEt);
         laboratoireEt = findViewById(R.id.laboratoireEt);
         formePharmaEt = findViewById(R.id.formePharmaEt);
         lotEt = findViewById(R.id.lotEt);
         descriptionEt = findViewById(R.id.descriptionEt);
         dateFabricationEt = findViewById(R.id.dateFabricationEt);
         datePeremptionEt = findViewById(R.id.datePeremptionEt);
         dureeConservationEt = findViewById(R.id.dureeConservationEt);
         quantityEt = findViewById(R.id.quantityEt);
         priceEt = findViewById(R.id.priceEt);
         isRemborsableSwitch = findViewById(R.id.isRemborsableSwitch);
        if (product!=null){
            barCodeEt.setText(product.getBarCode());
            denominationEt.setText(product.getDenomination());
            nomComercialEt.setText(product.getNomComercial());
            laboratoireEt.setText(product.getLaboratoire());
            formePharmaEt.setText(product.getFormePharma());
            lotEt.setText(product.getLot());
            classNameEt.setText(product.getClassName());
            descriptionEt.setText(product.getDescription());
            simpleDateFormat.format(product.getDateFabrication());
            dateFabricationEt.setText(simpleDateFormat.format(product.getDateFabrication()));
            datePeremptionEt.setText(simpleDateFormat.format(product.getDatePeremption()));
            dureeConservationEt.setText(product.getDureeConservation()+"");
            quantityEt.setText(product.getQuantity()+"");
            priceEt.setText(product.getPrice()+"");
            isRemborsableSwitch.setChecked(product.isRemborsable());
        }
        findViewById(R.id.save).setOnClickListener(v -> {
             if (existOneFieldEmpty()){
                  return;
             }
             if (barCodeEt.getText().toString().isEmpty()){
                 Toast.makeText(this, "champ code-barre est vide", Toast.LENGTH_SHORT).show();
                 return;
             }else{
                 Product product1 =new ProductRepository(getApplication()).findProductByCode(barCodeEt.getText().toString());
                 if (product1!=null){
                     if (product!=null){
                         if (product1.getId()!= product.getId()){
                             Toast.makeText(this, "Code-barre exist dans autre produit ", Toast.LENGTH_SHORT).show();
                             return;
                         }
                     }else{
                         Toast.makeText(this, "Code-barre exist dans autre produit ", Toast.LENGTH_SHORT).show();
                         return;
                     }
                 }
             }
             if (product!=null){
                 update(product);
             }else{
                 insert();
             }
        });
        findViewById(R.id.barCode).setOnClickListener(v -> {
            startActivityForResult(new Intent(this,CameraScannerActivity.class),CAMERA_SCANNER);
        });
        findViewById(R.id.readDenom).setOnClickListener(v -> {
            startActivityForResult(new Intent(this,OCRActivity.class),READ_DENOM);
        });
        findViewById(R.id.readNomComerc).setOnClickListener(v -> {
            startActivityForResult(new Intent(this,OCRActivity.class),READ_NOM_COMER);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== CAMERA_SCANNER && data!=null && data.hasExtra("code")){
            barCodeEt.setText(data.getStringExtra("code"));
        }else if (data!=null && data.hasExtra("text")){
            if (requestCode== READ_NOM_COMER ){
                nomComercialEt.setText(data.getStringExtra("text"));
            }else{
                denominationEt.setText(data.getStringExtra("text"));
            }
        }
    }


    private void insert() {
        Product product = new Product();
        product.setBarCode(barCodeEt.getText().toString());
        product.setDenomination(denominationEt.getText().toString());
        product.setNomComercial(nomComercialEt.getText().toString());
        product.setLaboratoire(laboratoireEt.getText().toString());
        product.setFormePharma(formePharmaEt.getText().toString());
        product.setLot(lotEt.getText().toString());
        product.setDescription(descriptionEt.getText().toString());
        product.setClassName(classNameEt.getText().toString());
        product.setDateFabrication(getDate(dateFabricationEt.getText().toString()));
        product.setDatePeremption(getDate(datePeremptionEt.getText().toString()));

        product.setQuantity(Integer.parseInt(quantityEt.getText().toString()));
        product.setDureeConservation(Integer.parseInt(dureeConservationEt.getText().toString()));
        product.setPrice(Double.parseDouble(priceEt.getText().toString()));
        product.setRemborsable(isRemborsableSwitch.isChecked());
        long id=new ProductRepository(getApplication()).findLastId()+1;
        product.setId(id);
        new Insert().insert(this,product);
    }

    private void update(Product product) {
        product.setBarCode(barCodeEt.getText().toString());
        product.setDenomination(denominationEt.getText().toString());
        product.setNomComercial(nomComercialEt.getText().toString());
        product.setLaboratoire(laboratoireEt.getText().toString());
        product.setFormePharma(formePharmaEt.getText().toString());
        product.setLot(lotEt.getText().toString());
        product.setDescription(descriptionEt.getText().toString());

        product.setDateFabrication(getDate(dateFabricationEt.getText().toString()));
        product.setDatePeremption(getDate(datePeremptionEt.getText().toString()));

        product.setQuantity(Integer.parseInt(quantityEt.getText().toString()));
        product.setDureeConservation(Integer.parseInt(dureeConservationEt.getText().toString()));
        product.setPrice(Double.parseDouble(priceEt.getText().toString()));
        product.setRemborsable(isRemborsableSwitch.isChecked());
        product.setClassName(classNameEt.getText().toString());

        new Update().update(this,product);
    }

    private boolean existOneFieldEmpty() {
        return barCodeEt.getText().toString().isEmpty() ||denominationEt.getText().toString().isEmpty() ||
               nomComercialEt.getText().toString().isEmpty() ||laboratoireEt.getText().toString().isEmpty() ||
               formePharmaEt.getText().toString().isEmpty() ||lotEt.getText().toString().isEmpty() ||
               descriptionEt.getText().toString().isEmpty() ||dateFabricationEt.getText().toString().isEmpty() ||
               datePeremptionEt.getText().toString().isEmpty() ||dureeConservationEt.getText().toString().isEmpty() ||
               quantityEt.getText().toString().isEmpty()||priceEt.getText().toString().isEmpty()||
                classNameEt.getText().toString().isEmpty();
    }
    private Date getDate(String date){

        Date date1;
        try {
            date1=simpleDateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            date1 = new Date();
        }
        return date1;
    }
}