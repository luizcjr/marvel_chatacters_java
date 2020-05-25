package com.luiz.marvelcharacters.di;

import com.luiz.marvelcharacters.ui.base.BaseViewModel;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ViewModelComponent {
    void inject(BaseViewModel viewModel);
}

