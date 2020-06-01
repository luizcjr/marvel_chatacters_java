package com.luiz.marvelcharacters.ui.fragments.characters;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.luiz.marvelcharacters.AppApplication;
import com.luiz.marvelcharacters.R;
import com.luiz.marvelcharacters.api.model.Character;
import com.luiz.marvelcharacters.databinding.CharactersBinding;
import com.luiz.marvelcharacters.ui.adapters.CharactersAdapter;
import com.luiz.marvelcharacters.ui.base.BaseFragment;
import com.luiz.marvelcharacters.util.Utils;
import com.mlykotom.valifi.BR;

/**
 * A simple {@link Fragment} subclass.
 */
public class CharactersFragment extends BaseFragment<CharactersBinding, CharactersViewModel> {

    private CharactersAdapter charactersAdapter;
    private boolean search = false;

    private Observer<PagedList<Character>> characterLiveDataObserver = character -> {
        if (character != null && character.size() > 0) {
            charactersAdapter.submitList(character);
        } else {
            this.viewDataBinding().rvGallery.setLayoutManager(new LinearLayoutManager(getContext()));
            this.viewDataBinding().rvGallery.setAdapter(Utils.noResultAdapter(getContext(), getContext().getString(R.string.character_empty), R.drawable.ic_sad));
        }
    };

    public CharactersFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_characters;
    }

    @Override
    public int binding() {
        return BR.character;
    }

    @Override
    public CharactersViewModel viewModel() {
        return new ViewModelProvider(requireActivity()).get(CharactersViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupToolbar();
        setHasOptionsMenu(true);

        observers();
        observerSearch();
    }

    private void setupToolbar() {
        getParentActivity().setSupportActionBar(this.viewDataBinding().toolbar);
        if (AppApplication.getInstance().isNightModeEnabled()) {
            this.viewDataBinding().toolbar.setBackgroundColor(getResources().getColor(android.R.color.black));
        } else {
            this.viewDataBinding().toolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    private void observers() {
        initializeRecycler();

        this.viewModel().context = getContext();
        this.viewModel().getCharacter().observe(getViewLifecycleOwner(), characterLiveDataObserver);
        this.viewModel().loading.observe(getViewLifecycleOwner(), loadingLiveDataObserver);
        this.viewModel().loadError.observe(getViewLifecycleOwner(), errorLiveDataObserver);
    }

    private void observerSearch() {
        this.viewDataBinding().edtSearch.setQueryHint(getResources().getString(R.string.query_hint_search));
        this.viewDataBinding().edtSearch.setOnClickListener(v -> viewDataBinding().edtSearch.setIconified(false));
        this.viewDataBinding().edtSearch.setOnCloseListener(() -> {
            if (viewModel().getCharacter() == null) {
                viewModel().getCharacters();
                viewModel().removeObservers(getViewLifecycleOwner());
                viewDataBinding().edtSearch.setFocusable(false);
                viewDataBinding().clMain.setFocusable(true);
                Utils.hideKeyboardFrom(getContext(), viewDataBinding().edtSearch);
                observers();
            }
            return true;
        });

        this.viewDataBinding().edtSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                viewModel().getCharactersByName(query);
                viewModel().removeObservers(getViewLifecycleOwner());
                Utils.hideKeyboardFrom(getContext(), viewDataBinding().edtSearch);
                observers();
                search = true;
                return true;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText.isEmpty() && search) {
                    search = false;
                    viewModel().getCharacters();
                    viewModel().removeObservers(getViewLifecycleOwner());
                    Utils.hideKeyboardFrom(getContext(), viewDataBinding().edtSearch);
                    observers();

                    return true;
                }
                return false;
            }
        });
    }

    private void initializeRecycler() {
        charactersAdapter = new CharactersAdapter();

        this.viewDataBinding().rvGallery.setLayoutManager(new GridLayoutManager(getContext(), 2));
        this.viewDataBinding().rvGallery.setAdapter(charactersAdapter);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_dark_mode) {
            this.viewModel().changeDarkMode();
        }
        return super.onOptionsItemSelected(item);
    }
}
