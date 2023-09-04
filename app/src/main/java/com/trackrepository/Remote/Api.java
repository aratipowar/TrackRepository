package com.trackrepository.Remote;

import com.trackrepository.model.AddRepository;
import com.trackrepository.model.RepoListResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface Api
{
    @Headers({"Accept: application/vnd.github+json","X-GitHub-Api-Version: 2022-11-28","Authorization: Bearer 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"})
    @POST("/orgs/{org}/repos")
    Call<AddRepository> addRepo(@Path("org") String org, @Body AddRepository addRepository);

    @GET("/orgs/{org}/repos")//https://api.github.com/orgs/UmbrellaSys/repos
    Call<ArrayList<RepoListResponse>> getRepoList(@Path("org") String org);
}
