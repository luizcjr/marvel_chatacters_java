package com.luiz.marvelcharacters.ui.fragments.detail_character;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.luiz.marvelcharacters.AppApplication;
import com.luiz.marvelcharacters.R;
import com.luiz.marvelcharacters.api.model.Character;
import com.luiz.marvelcharacters.api.model.Comics;
import com.luiz.marvelcharacters.databinding.DetailCharacterBinding;
import com.luiz.marvelcharacters.ui.adapters.ComicsAdapter;
import com.luiz.marvelcharacters.ui.base.BaseFragment;
import com.luiz.marvelcharacters.util.Utils;
import com.mlykotom.valifi.BR;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailCharacterFragment extends BaseFragment<DetailCharacterBinding, DetailCharacterViewModel> {

    private ComicsAdapter comicsAdapter;
    private int id;

    private Observer<PagedList<Comics>> comicLiveDataObserver = comics -> {
        if (comics != null && comics.size() > 0) {
            comicsAdapter.submitList(comics);
        } else {
            this.viewDataBinding().rvComics.setLayoutManager(new LinearLayoutManager(getContext()));
            this.viewDataBinding().rvComics.setAdapter(Utils.noResultAdapter(getContext(), getContext().getString(R.string.comics_empty), R.drawable.ic_sad));
        }
    };

    private Observer<List<Character>> characterLiveDataObserver = character -> {
        if (character != null) {
            this.viewModel().getComics().observe(getViewLifecycleOwner(), comicLiveDataObserver);

            this.viewDataBinding().setCharacter(character.get(0));
            this.viewDataBinding().setImages(character.get(0).getThumbnail());
        }
    };

    public DetailCharacterFragment() {
        // Required empty public constructor
    }

    @Override
    public int layoutId() {
        return R.layout.fragment_detail_character;
    }

    @Override
    public int binding() {
        return BR.detailsCharacterViewModel;
    }

    @Override
    public DetailCharacterViewModel viewModel() {
        return new ViewModelProvider(requireActivity()).get(DetailCharacterViewModel.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setupToolbar();
        getParentActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (getArguments() != null) {
            id = DetailCharacterFragmentArgs.fromBundle(getArguments()).getCharacterId();
        }

        observers(id);
        initializeRecycler();
    }

    private void observers(int id) {
        this.viewModel().context = getContext();
        this.viewModel().getCharacter(id);
        this.viewModel().getComicsById(id);
        this.viewModel().character.observe(getViewLifecycleOwner(), characterLiveDataObserver);
        this.viewModel().loading.observe(getViewLifecycleOwner(), loadingLiveDataObserver);
        this.viewModel().loadError.observe(getViewLifecycleOwner(), errorLiveDataObserver);
    }

    private void initializeRecycler() {
        comicsAdapter = new ComicsAdapter();

        this.viewDataBinding().rvComics.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false));
        this.viewDataBinding().rvComics.setAdapter(comicsAdapter);
        this.viewDataBinding().rvComics.setOnFlingListener(null);
        PagerSnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(this.viewDataBinding().rvComics);
    }

    private void setupToolbar() {
        getParentActivity().setSupportActionBar(this.viewDataBinding().toolbar);
        if (AppApplication.getInstance().isNightModeEnabled()) {
            this.viewDataBinding().ctlCharacter.setContentScrimColor(getResources().getColor(android.R.color.black));
        } else {
            this.viewDataBinding().ctlCharacter.setContentScrimColor(getResources().getColor(R.color.colorPrimary));
        }

        this.viewDataBinding().toolbar.setNavigationOnClickListener(v -> {
            getActivity().onBackPressed();
        });
    }
}
