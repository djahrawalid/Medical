package com.example.medical.affichage;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.medical.R;
import com.example.medical.background.ViewPagerAdapter;
import com.example.medical.model.Product;
import com.example.medical.sync.Get;
import com.example.medical.sync.InsertAll;
import com.example.medical.viewModel.ProductRepository;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import static com.example.medical.affichage.ProductActivity.CAMERA_SCANNER;

public class MainActivity extends AppCompatActivity {

    private List<Fragment>fragments;
    private ProductRepository productRepository;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Get().get(this);
        List<String>titles = new ArrayList<>();
        fragments = new ArrayList<>();
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        ViewPager viewPager = findViewById(R.id.viewPager);
        tabLayout.setupWithViewPager(viewPager);
        findViewById(R.id.add).setOnClickListener(v -> startActivity(new Intent(this,ProductActivity.class)));
        productRepository =new ProductRepository(getApplication());
        productRepository.findClasses().observe(this, categories -> {
            if (categories==null){
                categories = new ArrayList<>();
            }

            categories.add(0,"Tous");
            if (categories.size()>3){
                tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
            }
            titles.clear();
            fragments.clear();
            titles.addAll(categories);
            fragments.addAll(getFragments(categories));
            viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(),fragments,titles));

        });
    }



    private List<Fragment>getFragments(List<String>categories){
        List<Fragment> titles = new ArrayList<>();
        for (String category : categories){
            titles.add(new ProductsFragment().setCategory(category));
        }
        return titles;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.product_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }



    @Override
    public boolean onPrepareOptionsMenu(@NonNull Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem scan = menu.findItem(R.id.scan);
        scan.setOnMenuItemClickListener(item -> {
            startActivityForResult(new Intent(this,CameraScannerActivity.class),CAMERA_SCANNER);
            return true;
        });
        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode== CAMERA_SCANNER && data!=null && data.hasExtra("code")){
            Product product = productRepository.findProductByCode( data.getStringExtra("code"));
            if (product==null){
                Toast.makeText(getApplicationContext(), "Code_barre n'exist pas", Toast.LENGTH_SHORT).show();
            }else{
                Intent intent = new Intent(this,ProductActivity.class);
                intent.putExtra("product",product);
                startActivity(intent);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        new InsertAll().insertAll(this, productRepository.findNotSync());
    }
}