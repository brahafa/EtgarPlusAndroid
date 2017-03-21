package com.plus.etgar.etgarapp.adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.plus.etgar.etgarapp.R;
import com.plus.etgar.etgarapp.logic.ProductLogic;
import com.plus.etgar.etgarapp.models.ProductBike;
import com.plus.etgar.etgarapp.utils.Utils;
import com.plus.etgar.etgarapp.utils.UtilsPopup;
import com.plus.etgar.etgarapp.utils.ZoomableImageView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

public class OrderRVAdapter extends RecyclerView.Adapter<OrderRVAdapter.ProductHolder> {

    private List<ProductBike> productList;
    private Context context;
    private ZoomableImageView image;

    public class ProductHolder extends RecyclerView.ViewHolder {
        public TextView name, model, price, code,more_details, delete_item, copy_item;
        public ImageView image;
        public View view;
        public LinearLayout item_pager_layout, amount;
        public TextView size, color ,amountText, add_to_order;

        public ProductHolder(View view) {
            super(view);
            this.view=view;
            name= (TextView) view.findViewById(R.id.name);
            model= (TextView) view.findViewById(R.id.model);
            price= (TextView) view.findViewById(R.id.price);
            more_details= (TextView) view.findViewById(R.id.more_details);
            code= (TextView) view.findViewById(R.id.code);
            copy_item= (TextView) view.findViewById(R.id.copy_item);
            delete_item= (TextView) view.findViewById(R.id.delete_item);
            size= (TextView) view.findViewById(R.id.size);
            amountText= (TextView) view.findViewById(R.id.amountText);
            color= (TextView) view.findViewById(R.id.color);
            add_to_order= (TextView) view.findViewById(R.id.add_to_order);
            image= (ImageView) view.findViewById(R.id.product_image);
            item_pager_layout= (LinearLayout) view.findViewById(R.id.item_pager_layout);
            amount= (LinearLayout) view.findViewById(R.id.amount);
        }
    }

    public OrderRVAdapter(List<ProductBike> moviesList, Context context) {
        this.productList = moviesList;
        this.context=context;
    }


    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_product_item, parent, false);

        return new ProductHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ProductHolder holder, final int position) {
        final ProductBike product = productList.get(position);

        holder.name.setText(product.getName());
        holder.model.setText(product.getModel());
        holder.price.setText(Utils.moneyFormat(product.getPrice()));
        holder.code.setText(product.getCode());
        holder.add_to_order.setVisibility(View.GONE);
        holder.item_pager_layout.setBackgroundColor(ContextCompat.getColor(context, R.color.white));
        if(product.getColor()==null || product.getColor().equals("") ){
            product.setColor("בחר צבע");
        }else{
            holder.color.setText(product.getColor());
        }
        if(product.getSize()==null || product.getSize().equals("")){
            product.setSize("בחר מידה");
        }else{
            holder.size.setText(product.getSize());
        }
        holder.amountText.setText(product.getAmount()+"");

        if(product.getColors()==null || product.getColors().size()==0){
            holder.color.setTextColor(context.getResources().getColor(R.color.gray_766868));
        }
        if(product.getSizes()==null || product.getSizes().size()==0){
            holder.size.setTextColor(context.getResources().getColor(R.color.gray_766868));
        }

        UtilsPopup.DialogChooseFromList(context, convertListToSequens(product.getColors()), holder.color, new UtilsPopup.ChooseItemDialog() {
            @Override
            public void onItemChoose(String returnChooseItem , int index) {
                productList.get(position).setColor(returnChooseItem);
                notifyDataSetChanged();
            }
        });

        UtilsPopup.DialogChooseFromList(context, convertListToSequens(product.getSizes()), holder.size, new UtilsPopup.ChooseItemDialog() {
            @Override
            public void onItemChoose(String returnChooseItem , int index) {
                productList.get(position).setSize(returnChooseItem);
                notifyDataSetChanged();
            }
        });


        UtilsPopup.DialogChooseFromList(context, UtilsPopup.getAmountList(), holder.amount, new UtilsPopup.ChooseItemDialog() {
            @Override
            public void onItemChoose(String returnChooseItem , int index) {
                productList.get(position).setAmount(Integer.parseInt(returnChooseItem));
                notifyDataSetChanged();
            }
        });


        setImage(holder, product);

//        holder.amount.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                openNumberPuckerDialog(holder.amountText, product);
//            }
//        });
        setMoreDetailsPopup(holder, product);

        addOrRemoveItem(holder, position);


    }

    private CharSequence[] convertListToSequens(List<String> list) {
        if (list != null) {
            CharSequence charSequence[] = new CharSequence[list.size()];
            for (int i = 0; i < list.size(); i++) {
                charSequence[i] = list.get(i);
            }
            return charSequence;

        }else{
            return null;
        }
    }

    private void addOrRemoveItem(ProductHolder holder, final int position) {
        holder.delete_item.setVisibility(View.VISIBLE);
        holder.copy_item.setVisibility(View.VISIBLE);

        holder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.remove(position);
                notifyDataSetChanged();
            }
        });
        holder.copy_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.add(position+1, new ProductBike(productList.get(position)));
                 notifyDataSetChanged();

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

//    private void openNumberPuckerDialog(final EditText amountText,final ProductBike product){
//            final Dialog d = new Dialog(context);
//            d.setTitle("NumberPicker");
//            d.setContentView(R.layout.number_picker_dialog);
//            Button b1 = (Button) d.findViewById(R.id.button1);
//            Button b2 = (Button) d.findViewById(R.id.button2);
//            final NumberPicker np = (NumberPicker) d.findViewById(R.id.numberPicker1);
//            np.setMaxValue(100);
//            np.setMinValue(0);
//            np.setWrapSelectorWheel(false);
//           // np.setOnValueChangedListener(context);
//            b1.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v) {
//                    amountText.setText(String.valueOf(np.getValue()));
//                    d.dismiss();
//                }
//            });
//            b2.setOnClickListener(new View.OnClickListener()
//            {
//                @Override
//                public void onClick(View v) {
//                    d.dismiss();
//                }
//            });
//            d.show();
//        }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
