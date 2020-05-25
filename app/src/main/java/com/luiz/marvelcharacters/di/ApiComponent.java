package com.luiz.marvelcharacters.di;

import com.luiz.marvelcharacters.api.services.ApiRepository;

import dagger.Component;

@Component(modules = {ApiModule.class})
public interface ApiComponent {
    void inject(ApiRepository repository);
}
