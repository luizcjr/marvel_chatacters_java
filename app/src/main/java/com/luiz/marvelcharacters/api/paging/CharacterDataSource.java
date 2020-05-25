package com.luiz.marvelcharacters.api.paging;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.luiz.marvelcharacters.api.model.Character;
import com.luiz.marvelcharacters.api.responses.CharactersResponse;
import com.luiz.marvelcharacters.api.services.ApiRepository;
import com.luiz.marvelcharacters.util.Utils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class CharacterDataSource extends PageKeyedDataSource<Integer, Character> {

    private CompositeDisposable disposable;
    private ApiRepository apiRepository;
    private MutableLiveData<Boolean> loading;
    private MutableLiveData<String> loadError;
    @Nullable private String name;

    public CharacterDataSource(CompositeDisposable disposable, ApiRepository apiRepository, MutableLiveData<Boolean> loading, MutableLiveData<String> loadError, @Nullable String name) {
        this.disposable = disposable;
        this.apiRepository = apiRepository;
        this.loading = loading;
        this.loadError = loadError;
        this.name = name;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Character> callback) {
        int numberOfItems = params.requestedLoadSize;
        createInitialLoad(0, 1, numberOfItems, callback);
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Character> callback) {
        int page = params.key;
        int numberOfItems = params.requestedLoadSize;
        createLoads(page, page - 1, numberOfItems, callback);
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Character> callback) {
        int page = params.key;
        int numberOfItems = params.requestedLoadSize;
        createLoads(page, page + 1, numberOfItems, callback);
    }

    private void createInitialLoad(int requestedPage, int adjacentPage, int requestedLoadSize, LoadInitialCallback<Integer, Character> callback) {
        beforeRequest();

        disposable.add(
                apiRepository.getCharacters(requestedPage * requestedLoadSize, name)
                        .subscribeWith(new DisposableSingleObserver<CharactersResponse>() {
                            @Override
                            public void onSuccess(CharactersResponse charactersResponse) {
                                afterRequest();
                                callback.onResult(charactersResponse.getData().getResults(), null, adjacentPage);
                            }

                            @Override
                            public void onError(Throwable e) {
                                afterRequest(e);
                            }
                        })
        );
    }

    private void createLoads(int requestedPage, int adjacentPage, int requestedLoadSize, LoadCallback<Integer, Character> callback) {
        beforeRequest();

        disposable.add(
                apiRepository.getCharacters(requestedPage * requestedLoadSize, name)
                        .subscribeWith(new DisposableSingleObserver<CharactersResponse>() {
                            @Override
                            public void onSuccess(CharactersResponse charactersResponse) {
                                afterRequest();
                                callback.onResult(charactersResponse.getData().getResults(), adjacentPage);
                            }

                            @Override
                            public void onError(Throwable e) {
                                afterRequest(e);
                            }
                        })
        );
    }

    private void beforeRequest() {
        this.loading.postValue(true);
    }

    private void afterRequest() {
        this.loading.postValue(false);
    }

    private void afterRequest(Throwable e) {
        this.loading.postValue(false);
        this.loadError.postValue(Utils.getMessageErrorObject(e));
    }

    public MutableLiveData<Boolean> getLoading() {
        return loading;
    }

    public MutableLiveData<String> getLoadError() {
        return loadError;
    }
}