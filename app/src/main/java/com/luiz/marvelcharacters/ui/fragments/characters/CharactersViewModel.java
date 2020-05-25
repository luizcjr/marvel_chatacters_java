package com.luiz.marvelcharacters.ui.fragments.characters;

import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.luiz.marvelcharacters.AppApplication;
import com.luiz.marvelcharacters.api.model.Character;
import com.luiz.marvelcharacters.api.paging.CharacterDataSourceFactory;
import com.luiz.marvelcharacters.ui.activities.main.MainActivity;
import com.luiz.marvelcharacters.ui.base.BaseViewModel;
import com.luiz.marvelcharacters.util.Utils;

public class CharactersViewModel extends BaseViewModel {

    private LiveData<PagedList<Character>> character;
    private int pageSize = 20;
    private PagedList.Config config;

    public CharactersViewModel() {
        init();
    }

    public void init() {
        CharacterDataSourceFactory characterDataSourceFactory = new CharacterDataSourceFactory(disposable, apiRepository, loading, loadError, null);

        config = (new PagedList.Config.Builder())
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(true)
                .build();

        character = new LivePagedListBuilder<>(characterDataSourceFactory, config).build();
    }

    public void initSearch(String name) {
        CharacterDataSourceFactory characterDataSourceFactory = new CharacterDataSourceFactory(disposable, apiRepository, loading, loadError, name);

        config = (new PagedList.Config.Builder())
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(true)
                .build();

        character = new LivePagedListBuilder<>(characterDataSourceFactory, config).build();
    }

    public void removeObservers(LifecycleOwner lifecycleOwner) {
        character.removeObservers(lifecycleOwner);
    }

    public LiveData<PagedList<Character>> getCharacter() {
        return character;
    }

    public void goToMainActivity() {
        Utils.startActivity(context, MainActivity.class);
    }

    public void changeDarkMode() {
        if (AppApplication.getInstance().isNightModeEnabled()) {
            AppApplication.getInstance().setIsNightModeEnabled(false);
            goToMainActivity();
        } else {
            AppApplication.getInstance().setIsNightModeEnabled(true);
            goToMainActivity();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.dispose();
    }
}
