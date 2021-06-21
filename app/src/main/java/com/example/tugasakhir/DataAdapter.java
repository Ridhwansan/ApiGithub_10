package com.example.tugasakhir;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
public class DataAdapter extends ListAdapter<Data, DataAdapter.DataHolder> {

    private OnItemClickListener listener;

    public DataAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Data> DIFF_CALLBACK = new DiffUtil.ItemCallback<Data>() {
        @Override
        public boolean areItemsTheSame(@NonNull Data oldItem, @NonNull Data newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Data oldItem, @NonNull Data newItem) {
            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getLink().equals(newItem.getLink()) &&
                    oldItem.getEmail().equals(newItem.getEmail()) &&
                    oldItem.getNumber() == (newItem.getNumber());
        }
    };


    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.data_item, parent, false);
        return new DataHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        Data currentData = getItem(position);
        holder.textViewName.setText(currentData.getName());
        holder.textViewLink.setText(currentData.getLink());
        holder.textViewEmail.setText(currentData.getEmail());
        holder.textViewNumber.setText(String.valueOf(currentData.getNumber()));
    }



    public Data getDataAt(int position) {
        return getItem(position);

    }

    class DataHolder extends RecyclerView.ViewHolder {
        private TextView textViewName;
        private TextView textViewLink;
        private TextView textViewEmail;
        private TextView textViewNumber;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.text_view_name);
            textViewLink = itemView.findViewById(R.id.text_view_link);
            textViewEmail = itemView.findViewById(R.id.text_view_email);
            textViewNumber = itemView.findViewById(R.id.text_view_number);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Data dataa);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
