package com.luiz.marvelcharacters.api.paging;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.luiz.marvelcharacters.api.model.Comics;
import com.luiz.marvelcharacters.api.services.ApiRepository;

import io.reactivex.disposables.CompositeDisposable;

public class ComicsDataSourceFactory extends DataSource.Factory {

    private MutableLiveData<PageKeyedDataSource<Integer, Comics>> comicsLiveDataSource = new MutableLiveData<>();

    private CompositeDisposable disposable;
    private ApiRepository apiRepository;
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<String> loadError;
    private int id;

    public ComicsDataSourceFactory(CompositeDisposable disposable, ApiRepository apiRepository, MutableLiveData<Boolean> loading, MutableLiveData<String> loadError, int id) {
        this.disposable = disposable;
        this.apiRepository = apiRepository;
        this.loading = loading;
        this.loadError = loadError;
        this.id = id;
    }

    @NonNull
    @Override
    public DataSource create() {
        ComicsDataSource comicsDataSource = new ComicsDataSource(disposable, apiRepository, loading, loadError, id);

        comicsLiveDataSource.postValue(comicsDataSource);
        loadError = comicsDataSource.getLoadError();
        loading = comicsDataSource.getLoading();

        return comicsDataSource;
    }

    //getter for itemlivedatasource
    public MutableLiveData<PageKeyedDataSource<Integer, Comics>> getComicsLiveDataSource() {
        return comicsLiveDataSource;
    }
}
