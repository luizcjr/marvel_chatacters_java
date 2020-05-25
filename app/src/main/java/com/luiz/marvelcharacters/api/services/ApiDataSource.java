package com.luiz.marvelcharacters.api.services;

import com.luiz.marvelcharacters.api.constants.Constants;
import com.luiz.marvelcharacters.api.responses.CharactersResponse;
import com.luiz.marvelcharacters.api.responses.ComicsResponse;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiDataSource {

    @GET(Constants.CHARACTERS)
    Single<CharactersResponse> getCharacters(@Query("offset") int offset, @Query("name") String name);

    @GET(Constants.CHARACTERS + "/{characterId}")
    Single<CharactersResponse> getCharacterId(@Path("characterId") int characterId);

    @GET(Constants.CHARACTERS + "/{characterId}/" + Constants.COMICS)
    Single<ComicsResponse> getComicsCharacter(@Path("characterId") int characterId, @Query("offset") int offset);
}
