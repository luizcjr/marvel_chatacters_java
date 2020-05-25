package com.luiz.marvelcharacters.ui.base;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.luiz.marvelcharacters.api.services.ApiRepository;
import com.luiz.marvelcharacters.di.DaggerViewModelComponent;
import com.luiz.marvelcharacters.util.Utils;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseViewModel extends ViewModel {

    public Context context;
    public CompositeDisposable disposable = new CompositeDisposable();
    public MutableLiveData<Boolean> loading = new MutableLiveData<Boolean>();
    public MutableLiveData<String> loadError = new MutableLiveData<String>();

    public void beforeRequest() {
        this.loading.postValue(true);
    }

    public void afterRequest() {
        this.loading.postValue(false);
    }

    public void afterRequest(Throwable e) {
        this.loading.postValue(false);
        this.loadError.postValue(Utils.getMessageErrorObject(e));
    }

    @Inject
    protected ApiRepository apiRepository;

    public BaseViewModel() {
        DaggerViewModelComponent.create().inject(this);
    }

    @Override
    protected void onCleared() {
        disposable.clear();

        super.onCleared();
    }
}
