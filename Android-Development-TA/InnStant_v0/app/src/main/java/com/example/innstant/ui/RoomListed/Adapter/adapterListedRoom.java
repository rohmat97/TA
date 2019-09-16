package com.example.innstant.ui.RoomListed.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.innstant.R;
import com.example.innstant.data.model.Room;

import java.util.ArrayList;

import butterknife.BindView;

public class adapterListedRoom extends RecyclerView.Adapter<adapterListedRoom.MyViewHolder> {
    Context context;
    ArrayList<Room> Mhost;
    OnItemClickListener listener;
    @BindView(R.id.GambarHost)
    ImageView GambarHost;
    @BindView(R.id.namaKamar)
    TextView namaKamar;
    @BindView(R.id.locationRoom)
    TextView locationRoom;
    @BindView(R.id.viewRequest)
    Button viewRequest;
    @BindView(R.id.typeKamar)
    TextView typeKamar;
    @BindView(R.id.rating)
    TextView rating;
    @BindView(R.id.price)
    TextView price;

    public interface OnItemClickListener {
        void onItemClick(Room item);
    }

    public adapterListedRoom(Context context, ArrayList<Room> pencarian, OnItemClickListener listener) {
        this.context = context;
        Mhost = pencarian;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_host, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.bind(Mhost.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return Mhost.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(final Room item, final OnItemClickListener listener) {
            //gambarHost.setImageBitmap(item.getGambar());
            namaKamar.setText(item.getName());
            //    status.setText(item.get);
            //  rating.setText(item.ge);
            price.setText(item.getPrice());
            locationRoom.setText(item.getLocation());
            typeKamar.setText(item.getType());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}
