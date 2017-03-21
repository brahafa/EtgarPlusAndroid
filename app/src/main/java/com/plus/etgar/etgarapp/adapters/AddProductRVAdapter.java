package com.plus.etgar.etgarapp.adapters;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.plus.etgar.etgarapp.R;
import com.plus.etgar.etgarapp.logic.ProductLogic;
import com.plus.etgar.etgarapp.models.ProductBike;
import com.plus.etgar.etgarapp.utils.Utils;
import com.plus.etgar.etgarapp.utils.ZoomableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.net.URL;
import java.util.List;

import static android.content.ContentValues.TAG;

public class AddProductRVAdapter extends RecyclerView.Adapter<AddProductRVAdapter.ProductHolder> {

    private List<ProductBike> productList;
    private Context context;
    private ZoomableImageView image;
    private AdapterCallback adapterCallback;

    public class ProductHolder extends RecyclerView.ViewHolder {
        public TextView name, model, price, code,more_details, add_to_order;
        public ImageView image;
        public View view;

        public ProductHolder(View view) {
            super(view);
            this.view=view;
            name= (TextView) view.findViewById(R.id.name);
            model= (TextView) view.findViewById(R.id.model);
            price= (TextView) view.findViewById(R.id.price);
            more_details= (TextView) view.findViewById(R.id.more_details);
            code= (TextView) view.findViewById(R.id.code);
            add_to_order= (TextView) view.findViewById(R.id.add_to_order);
            image= (ImageView) view.findViewById(R.id.product_image);
        }
    }

    public AddProductRVAdapter(List<ProductBike> moviesList, Context context, AdapterCallback adapterCallback) {
        this.productList = moviesList;
        this.context=context;
        this.adapterCallback= adapterCallback;
    }


    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.pager_item, parent, false);

        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ProductHolder holder, final int position) {
        final ProductBike product = productList.get(position);

        holder.name.setText(product.getName());
        holder.model.setText(product.getModel());
        holder.price.setText(Utils.moneyFormat(product.getPrice()));
        holder.code.setText(product.getCode());
        setImage(holder, product);
        setMoreDetailsPopup(holder, product);
        holder.add_to_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!ProductLogic.getInstance().isManager()) {
                    ProductLogic.getInstance().addOrderProductsToList(product);
                }
                adapterCallback.onItemChoose(position);
            }
        });

    }

    private void setMoreDetailsPopup(ProductHolder holder, final ProductBike product) {
        holder.more_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.more_details_dialog);
                dialog.setTitle("פרטים נוספים");
                dialog.show();
                TextView details = (TextView) dialog.findViewById(R.id.details);
                details.setText(product.getName()+"\n" + product.getModel()+"\n"+ product.getDescription());
                image = (ZoomableImageView) dialog.findViewById(R.id.details_image);
                Picasso.with(context)
                        .load(product.getImage())
                        .into (target);
            }
        });
    }

    Target target = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            image.setImageBitmap(bitmap);
        }
        @Override
        public void onBitmapFailed(Drawable errorDrawable) {
        }
        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private void setImage(final ProductHolder holder, final ProductBike product) {
        Picasso.with(context)
                .load(product.getImage())
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.zoom_image_dialog);
                dialog.setTitle("תצוגה מקדימה");
                dialog.show();
                image = (ZoomableImageView) dialog.findViewById(R.id.image_dialog);
                Picasso.with(context)
                        .load(product.getImage())
                        .into (target);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }


    public interface AdapterCallback {
        void onItemChoose(int index);
    }
}
