package com.luiz.marvelcharacters.api.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.luiz.marvelcharacters.api.model.Character;
import com.luiz.marvelcharacters.api.services.ApiRepository;

import io.reactivex.disposables.CompositeDisposable;

public class CharacterDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Character>> characterLiveDataSource = new MutableLiveData<>();

    private CompositeDisposable disposable;
    private ApiRepository apiRepository;
    public MutableLiveData<Boolean> loading;
    public MutableLiveData<String> loadError;
    @Nullable
    private String name;

    public CharacterDataSourceFactory(CompositeDisposable disposable, ApiRepository apiRepository, MutableLiveData<Boolean> loading, MutableLiveData<String> loadError, @Nullable String name) {
        this.disposable = disposable;
        this.apiRepository = apiRepository;
        this.loading = loading;
        this.loadError = loadError;
        this.name = name;
    }

    @NonNull
    @Override
    public DataSource create() {
        CharacterDataSource characterDataSource = new CharacterDataSource(disposable, apiRepository, loading, loadError, name);

        characterLiveDataSource.postValue(characterDataSource);
        loadError = characterDataSource.getLoadError();
        loading = characterDataSource.getLoading();

        return characterDataSource;
    }

    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, Character>> getCharacterLiveDataSource() {
        return characterLiveDataSource;
    }
}
