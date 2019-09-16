package com.example.innstant.ui.Dashboard.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.innstant.R;
import com.example.innstant.data.model.Transaction;

import java.util.ArrayList;

public class adapterNofication extends RecyclerView.Adapter<adapterNofication.MyViewHolder>  {
    Context context;
    ArrayList<Transaction> Mhost;
    OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(Transaction item);
    }

    public adapterNofication(Context context, ArrayList<Transaction> pencarian, OnItemClickListener listener) {
        this.context = context;
        Mhost = pencarian;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_transaction, parent, false);
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
        TextView transaksiID,hosID,guessID,transaksiTimestamp,bookStart,bookEnd,statusPembayaran,roomID;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            transaksiID = itemView.findViewById(R.id.transaksiID);
            hosID = itemView.findViewById(R.id.hostID);
            guessID = itemView.findViewById(R.id.guessID);
            roomID = itemView.findViewById(R.id.roomID);
            transaksiTimestamp = itemView.findViewById(R.id.timestampTransaction);
            bookStart = itemView.findViewById(R.id.bookStart);
            bookEnd =itemView.findViewById(R.id.bookEnd);
            statusPembayaran=itemView.findViewById(R.id.statusBayar);
        }

        public void bind(final Transaction item, final OnItemClickListener listener) {
            transaksiID.setText(item.getTransactionId());
            hosID.setText(item.getHostName());
            guessID.setText(item.getGuestName());
            roomID.setText(item.getRoomId());
            transaksiTimestamp.setText(item.getTransactionTimestamp().toString());
            bookStart.setText(String.valueOf(item.getBookStartDate()));
            bookEnd.setText(String.valueOf(item.getBookEndDate()));
            statusPembayaran.setText(item.getPaymentStatus());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }
}