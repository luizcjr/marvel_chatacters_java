package com.luiz.marvelcharacters.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.luiz.marvelcharacters.ui.activities.main.MainActivity;
import com.luiz.marvelcharacters.util.Utils;

import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseFragment<T extends ViewDataBinding, D extends BaseViewModel> extends Fragment {

    public T viewDataBinding() {
        return vDatabinding;
    }

    @LayoutRes
    public abstract int layoutId();

    public abstract int binding();

    public abstract D viewModel();

    private T vDatabinding;
    private D vModel;
    private View mRootView;
    public CompositeDisposable disposable = new CompositeDisposable();

    private void initializeDatabinding(LayoutInflater inflater, ViewGroup container) {
        vDatabinding = DataBindingUtil.inflate(inflater, layoutId(), container, false);
        vModel = viewModel();
        vDatabinding.setVariable(binding(), vModel);
        vDatabinding.executePendingBindings();
        mRootView = vDatabinding.getRoot();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        initializeDatabinding(inflater, container);

        return mRootView;
    }

    protected Observer<Boolean> loadingLiveDataObserver = isLoading -> {
        if(isLoading) {
            Utils.onStartLoading(getContext());
        } else {
            Utils.onStopLoading();
        }
    };

    protected Observer<String> errorLiveDataObserver = error -> {
        if(error != null) {
            Utils.alertError(getContext(), error);
        }
    };

    protected MainActivity getParentActivity() {
        return ((MainActivity) getActivity());
    }
}
