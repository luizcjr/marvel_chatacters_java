package com.luiz.marvelcharacters.ui.fragments.detail_character;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.luiz.marvelcharacters.api.model.Character;
import com.luiz.marvelcharacters.api.model.Comics;
import com.luiz.marvelcharacters.api.paging.ComicsDataSourceFactory;
import com.luiz.marvelcharacters.api.responses.CharactersResponse;
import com.luiz.marvelcharacters.ui.base.BaseViewModel;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class DetailCharacterViewModel extends BaseViewModel {

    public MutableLiveData<List<Character>> character;
    public LiveData<PagedList<Comics>> comics;
    private int pageSize = 20;
    private PagedList.Config config;
    private ComicsDataSourceFactory comicsDataSourceFactory;


    void getCharacter(int characterId) {
        character = new MutableLiveData<>();

        this.beforeRequest();

        disposable.add(
                apiRepository.getCharacterId(characterId)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableSingleObserver<CharactersResponse>() {

                            @Override
                            public void onSuccess(CharactersResponse charactersResponse) {
                                afterRequest();

                                character.postValue(charactersResponse.getData().getResults());
                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                afterRequest(e);
                            }
                        })
        );
    }

    public void init(int id) {
        comicsDataSourceFactory = new ComicsDataSourceFactory(disposable, apiRepository, loading, loadError, id);

        config = (new PagedList.Config.Builder())
                .setPageSize(pageSize)
                .setInitialLoadSizeHint(pageSize * 2)
                .setPrefetchDistance(10)
                .setEnablePlaceholders(true)
                .build();

        comics = new LivePagedListBuilder<>(comicsDataSourceFactory, config).build();
    }

    public LiveData<PagedList<Comics>> getComics() {
        return comics;
    }
}
