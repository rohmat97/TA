package com.example.innstant.ui.Admin.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.innstant.R;
import com.example.innstant.data.model.Room;

import java.util.ArrayList;

public class AdapterListKamar extends RecyclerView.Adapter<AdapterListKamar.MyViewHolder>  {
    Context context;
    ArrayList<Room> Mhost;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Room item);
    }

    public AdapterListKamar(Context context, ArrayList<Room> pencarian, OnItemClickListener listener) {
        this.context = context;
        Mhost = pencarian;
        this.listener = listener;
    }
    @NonNull
    @Override
    public AdapterListKamar.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_kamar, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListKamar.MyViewHolder holder, int position) {
        holder.bind(Mhost.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return Mhost.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView gambarKamar;
        TextView Nama,type,tarif;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gambarKamar = itemView.findViewById(R.id.gambarKamar);
            Nama = itemView.findViewById(R.id.namaKamar);
            type= itemView.findViewById(R.id.type);
            tarif= itemView.findViewById(R.id.tarif);
        }

        public void bind(final Room item, final OnItemClickListener listener) {
//            gambarAkun.setImageBitmap(item.getProfilePhotoBitmap());
            Nama.setText(item.getName());
            type.setText(item.getType());
            tarif.setText(item.getPrice());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
