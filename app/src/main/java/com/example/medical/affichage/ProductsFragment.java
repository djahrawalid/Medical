package com.example.medical.affichage;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.model.Product;
import com.example.medical.sync.Delete;
import com.example.medical.viewModel.ProductRepository;
import com.example.medical.background.ProductsAdapter;


public class ProductsFragment extends Fragment {

    private String category;

    public ProductsFragment setCategory(String state) {
        this.category = state;
        return this;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_products, container, false);
        RecyclerView productsRec = view.findViewById(R.id.productsRec);
        productsRec.setHasFixedSize(true);
        productsRec.setLayoutManager(new LinearLayoutManager(getContext()));
        ProductsAdapter adapter = new ProductsAdapter();
        adapter.setOnClickListener(new ProductsAdapter.OnClickListener() {
            @Override
            public void onItemClickListener(Product model) {
                Intent intent = new Intent(getContext(),ProductActivity.class);// raih men activity hedi l activity ProductActivity
                intent.putExtra("product",model);
                startActivity(intent);
            }

            @Override
            public void onItemLongClickListener(Product model) {
                new AlertDialog.Builder(getContext()).setTitle("Confirmation")
                        .setMessage("Do you want remove this product")
                        .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                        .setPositiveButton("Remove", (dialog, which) -> new Delete().delete(getActivity(),model))
                        .create().show();
            }
        });
        productsRec.setAdapter(adapter);
        ProductRepository productRepository = new ProductRepository(requireActivity().getApplication());
        if (category!=null && !category.equals("Tous")) {
            productRepository.findAll(category).observe(getViewLifecycleOwner(), adapter::setProducts);
        }else{
            productRepository.findAll().observe(getViewLifecycleOwner(), adapter::setProducts);
        }
        return view;
    }

}