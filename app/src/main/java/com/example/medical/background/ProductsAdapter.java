package com.example.medical.background;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medical.R;
import com.example.medical.model.Product;

import java.util.List;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.PrView> {
    private List<Product>products;
    private OnClickListener listener;

    class PrView extends RecyclerView.ViewHolder  {
        TextView firstLater,nameProduct,codeProduct;
        PrView(View v) {
            super(v);
            firstLater=v.findViewById(R.id.firstLater);
            nameProduct=v.findViewById(R.id.nameProduct);
            codeProduct=v.findViewById(R.id.codeProduct);
            v.setOnClickListener(v1 -> {
                int pos = getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION){
                    listener.onItemClickListener(products.get(pos));
                }
            });
            v.setOnLongClickListener(v1 -> {
                int pos = getAdapterPosition();
                if (pos!=RecyclerView.NO_POSITION){
                    listener.onItemLongClickListener(products.get(pos));
                }
                return true;
            });
        }
    }

    @NonNull
    @Override
    public PrView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PrView(LayoutInflater.from(parent.getContext()).inflate(R.layout.product_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull PrView holder, int position) {
        Product product = products.get(position);
        if (product!=null){
            holder.nameProduct.setText(product.getDenomination());
            holder.codeProduct.setText(product.getBarCode());
            holder.firstLater.setText(getFirsCharacter(product.getDenomination()));
        }
    }

    @Override
    public int getItemCount() {
        return products == null ? 0 : products.size();
    }

    private String getFirsCharacter(String name){
        return name==null?"#":name.trim().isEmpty()?"#":String.valueOf(name.trim().charAt(0)).toUpperCase();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public interface OnClickListener{
        void onItemClickListener(Product model);
        void onItemLongClickListener(Product model);
    }

    public void setOnClickListener(OnClickListener listener) {
        this.listener = listener;
    }
}
