package com.luiz.marvelcharacters.api.services;

import com.luiz.marvelcharacters.api.responses.CharactersResponse;
import com.luiz.marvelcharacters.api.responses.ComicsResponse;
import com.luiz.marvelcharacters.di.DaggerApiComponent;

import javax.inject.Inject;

import io.reactivex.Single;

public class ApiRepository {

    @Inject
    public ApiDataSource api;

    public ApiRepository() {
        DaggerApiComponent.create().inject(this);
    }

    public Single<CharactersResponse> getCharacters(int offset, String name) {
        return api.getCharacters(offset, name);
    }

    public Single<CharactersResponse> getCharacterId(int characterId) {
        return api.getCharacterId(characterId);
    }

    public Single<ComicsResponse> getComicsCharacter(int characterId, int offset) {
        return api.getComicsCharacter(characterId, offset);
    }
}
