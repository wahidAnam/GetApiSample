package com.example.apisample;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.renderscript.ScriptGroup;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FlowersAdapter extends RecyclerView.Adapter<FlowersAdapter.ViewHolder> {

    Context context;
    List<FlowerResponse> flowerResponseList = new ArrayList<>();


    private int selectedPosition = 0;

    public FlowersAdapter(Context context, List<FlowerResponse> flowerResponseList) {
        this.context = context;
        this.flowerResponseList = flowerResponseList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout, null, false);
        return new ViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {


        if (selectedPosition == position ) {
            holder.itemView.setSelected(true); //using selector drawable
            holder.cardviewID.setBackgroundResource(R.drawable.item_select);
            holder.nameTV.setTextColor(ContextCompat.getColor(holder.nameTV.getContext(),R.color.white));
        } else {
            holder.itemView.setSelected(false);
            holder.cardviewID.setBackgroundResource(R.drawable.item_unselected);
            holder.nameTV.setTextColor(ContextCompat.getColor(holder.nameTV.getContext(),R.color.black));
        }

        holder.itemView.setOnClickListener(v -> {
            if (selectedPosition >= 0)
                notifyItemChanged(selectedPosition);
            selectedPosition = holder.getAdapterPosition();
            notifyItemChanged(selectedPosition);
        });

        FlowerResponse data = flowerResponseList.get(position);

        String url = "https://services.hanselandpetal.com/photos/" + data.getPhoto();

        Picasso.get().load(url).placeholder(R.drawable.rose).into(holder.imageIV);
        holder.nameTV.setText("Name : " + data.getName());
        holder.categoryTV.setText("Catogeory : " + data.getCategory());
        holder.instructionTV.setText("Instruction : " + data.getInstructions());
        holder.priceTV.setText("Price : " + String.valueOf(data.getPrice()) + " $");


    }


    @Override
    public int getItemCount() {
        return flowerResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageIV;
        TextView nameTV, categoryTV, instructionTV, priceTV;
        CardView cardviewID;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageIV = itemView.findViewById(R.id.imageIV);
            nameTV = itemView.findViewById(R.id.nameTV);
            categoryTV = itemView.findViewById(R.id.categoryTV);
            instructionTV = itemView.findViewById(R.id.instructionTV);
            priceTV = itemView.findViewById(R.id.priceTV);
            cardviewID = itemView.findViewById(R.id.cardviewID);


        }
    }
}
