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
import com.example.innstant.data.model.User;

import java.util.ArrayList;

public class AdapterListAccount extends RecyclerView.Adapter<AdapterListAccount.MyViewHolder>  {
    Context context;
    ArrayList<User> Mhost;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(User item);
    }

    public AdapterListAccount(Context context, ArrayList<User> pencarian, OnItemClickListener listener) {
        this.context = context;
        Mhost = pencarian;
        this.listener = listener;
    }
    @NonNull
    @Override
    public AdapterListAccount.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_account, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterListAccount.MyViewHolder holder, int position) {
        holder.bind(Mhost.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return Mhost.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView gambarAkun;
        TextView Nama,noKtp,noHp;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            gambarAkun = itemView.findViewById(R.id.gambarAkun);
            Nama = itemView.findViewById(R.id.namaPemilik);
            noKtp= itemView.findViewById(R.id.noKtp);
            noHp= itemView.findViewById(R.id.noHp);
        }

        public void bind(final User item, final OnItemClickListener listener) {
//            gambarAkun.setImageBitmap(item.getProfilePhotoBitmap());
            Nama.setText(item.getFirstName()+" "+item.getLastName());
            noKtp.setText(item.getIdCardNumber());
            noHp.setText(item.getPhoneNumber());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}

