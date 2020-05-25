package com.luiz.marvelcharacters.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.luiz.marvelcharacters.R;
import com.luiz.marvelcharacters.api.model.Comics;
import com.luiz.marvelcharacters.databinding.ItemsComicBinding;

public class ComicsAdapter extends PagedListAdapter<Comics, ComicsAdapter.ComicsViewHolder> {

    static final DiffUtil.ItemCallback<Comics> callback = new DiffUtil.ItemCallback<Comics>() {
        @Override
        public boolean areItemsTheSame(@NonNull Comics oldItem, @NonNull Comics newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Comics oldItem, @NonNull Comics newItem) {
            return oldItem.equals(newItem);
        }
    };

    public ComicsAdapter() {
        super(callback);
    }

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsComicBinding comicBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.items_comic, parent, false);
        return new ComicsViewHolder(comicBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        Comics itemAtual = getItem(position);
        holder.view.setComic(itemAtual);
        holder.view.setImages(itemAtual.getThumbnail());

    }

    static class ComicsViewHolder extends RecyclerView.ViewHolder {

        public ItemsComicBinding view;

        public ComicsViewHolder(@NonNull ItemsComicBinding itemView) {
            super(itemView.getRoot());

            this.view = itemView;
        }
    }
}
