package com.luiz.marvelcharacters.ui.adapters;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.luiz.marvelcharacters.R;
import com.luiz.marvelcharacters.api.model.Character;
import com.luiz.marvelcharacters.databinding.ItemsCharacterBinding;
import com.luiz.marvelcharacters.ui.fragments.characters.CharactersFragmentDirections;
import com.luiz.marvelcharacters.ui.interfaces.CharacterClickListener;

public class CharactersAdapter extends PagedListAdapter<Character, CharactersAdapter.CharactersViewHolder> implements CharacterClickListener {

    public CharactersAdapter() {
        super(callback);
    }

    @NonNull
    @Override
    public CharactersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemsCharacterBinding itemsCharacterBinding =
                DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                        R.layout.items_character, parent, false);
        return new CharactersViewHolder(itemsCharacterBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharactersViewHolder holder, int position) {
        Character itemAtual = getItem(position);
        holder.view.setCharacter(itemAtual);
        holder.view.setImages(itemAtual.getThumbnail());
        holder.view.setListener(this);
    }

    @Override
    public void onClick(View view) {
        for (int i = 0; i < getItemCount(); i++) {
            if (view.getTag().equals(getItem(i).getId())) {
                NavDirections action = CharactersFragmentDirections.actionDetailCharacter(getItem(i).getId());
                Navigation.findNavController(view).navigate(action);
            }
        }
    }

    static class CharactersViewHolder extends RecyclerView.ViewHolder {

        public ItemsCharacterBinding view;

        public CharactersViewHolder(@NonNull ItemsCharacterBinding itemView) {
            super(itemView.getRoot());

            this.view = itemView;
        }
    }

    static final DiffUtil.ItemCallback<Character> callback = new DiffUtil.ItemCallback<Character>() {
        @Override
        public boolean areItemsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Character oldItem, @NonNull Character newItem) {
            return oldItem.equals(newItem);
        }
    };
}
