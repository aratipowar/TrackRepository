package com.trackrepository.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.trackrepository.R;
import com.trackrepository.Remote.ApiClient;
import com.trackrepository.Remote.InternetConnection;
import com.trackrepository.adapter.ReposListAdapter;
import com.trackrepository.model.RepoListResponse;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoList extends AppCompatActivity
{
    RecyclerView reposListRecycler;
    ReposListAdapter reposListAdapter;
    ArrayList<RepoListResponse> listRepo;
    private ProgressBar loadingPB;
    TextView label;
    ConstraintLayout rlRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reposListRecycler = findViewById(R.id.reposListRecycler);
        label = findViewById(R.id.label);
        rlRootLayout = findViewById(R.id.rl_rootLayout);

        listRepo = new ArrayList<>();

        if (InternetConnection.checkConnection(RepoList.this)) {
            getRepolist();
        }else{
            Snackbar.make(rlRootLayout, getResources().getString(R.string.no_internet),
                            Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            getRepolist();
                        }
                    })
                    .show();
        }

    }
    public void getRepolist()
    {
        Call<ArrayList<RepoListResponse>> repoList = ApiClient.getUserService().getRepoList("UmbrellaSys");
        repoList.enqueue(new Callback<ArrayList<RepoListResponse>>() {
            @Override
            public void onResponse(Call<ArrayList<RepoListResponse>> call, Response<ArrayList<RepoListResponse>> response)
            {
                if(response.isSuccessful()){
                    if (response.body() != null)
                    {
                       // AddRepository addRepository = response.body();
                        listRepo=response.body();

                        for (int i = 0; i < listRepo.size(); i++) {
                            LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                            reposListRecycler.setLayoutManager(layoutManager);
                            reposListAdapter = new ReposListAdapter(getApplicationContext(), listRepo);
                            reposListRecycler.setAdapter(reposListAdapter);
                            reposListRecycler.setItemAnimator(new DefaultItemAnimator());
                            DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(reposListRecycler.getContext(),
                                    layoutManager.getOrientation());
                            reposListRecycler.addItemDecoration(dividerItemDecoration);
                        }

                       // Log.d("addrlistrespo : ", String.valueOf(addRepository));
                    }
                }else{
                    label.setVisibility(View.VISIBLE);
                    //Toast.makeText(RepoList.this,"Track your favourite repo with a Add Now Button", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RepoListResponse>> call, Throwable t) {
                Toast.makeText(RepoList.this,"Not Found!", Toast.LENGTH_LONG).show();
                // Log.d("Throwable : ",t.getLocalizedMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { switch(item.getItemId()) {
        case R.id.add:
            Intent intent = new Intent(RepoList.this,AddRepoToGit.class);
            startActivity(intent);
            return(true);
    }
        return(super.onOptionsItemSelected(item));
    }
}