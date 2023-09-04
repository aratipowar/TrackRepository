package com.trackrepository.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.trackrepository.R;
import com.trackrepository.Remote.ApiClient;
import com.trackrepository.Remote.InternetConnection;
import com.trackrepository.model.AddOwnerData;
import com.trackrepository.model.AddRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRepoToGit extends AppCompatActivity
{
    TextInputLayout ownername,reponame;
    Button add;
    String name_owner,name_repo;
    AddRepository addRepository;
    ConstraintLayout rlRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_repo);

        add = findViewById(R.id.add);
        ownername = findViewById(R.id.owner_name);
        reponame = findViewById(R.id.repo_name);
        rlRootLayout = findViewById(R.id.rl_rootLayout);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name_owner = ownername.getEditText().getText().toString();
                name_repo = reponame.getEditText().getText().toString();

                if (InternetConnection.checkConnection(AddRepoToGit.this)) {
                    if (TextUtils.isEmpty(name_owner) || TextUtils.isEmpty(name_repo)) {
                        Toast.makeText(AddRepoToGit.this, "Check Owner name and Repository name", Toast.LENGTH_LONG).show();
                    } else {
                        Log.d("Ownername",name_owner);
                        Log.d("reponame",name_repo);
                        AddOwnerData a = new AddOwnerData();
                        a.setLogin(name_owner);
                        addRepository = new AddRepository();
                        addRepository.setName(name_repo);
                        addRepository.setAddOwnerData(a);
                        addrepo(addRepository);

                    }                }else{
                    Snackbar.make(rlRootLayout, getResources().getString(R.string.no_internet),
                                    Snackbar.LENGTH_INDEFINITE)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (TextUtils.isEmpty(name_owner) || TextUtils.isEmpty(name_repo)) {
                                        Toast.makeText(AddRepoToGit.this, "Check Owner name and Repository name", Toast.LENGTH_LONG).show();
                                    } else {
                                        Log.d("Ownername",name_owner);
                                        Log.d("reponame",name_repo);
                                        AddOwnerData a = new AddOwnerData();
                                        a.setLogin(name_owner);
                                        addRepository = new AddRepository();
                                        addRepository.setName(name_repo);
                                        addRepository.setAddOwnerData(a);
                                        addrepo(addRepository);

                                    }                                }
                            })
                            .show();
                }

            }
        });
    }

    private void addrepo(AddRepository addRepository) {
        Call<AddRepository> addresporesponse = ApiClient.getUserService().addRepo("UmbrellaSys",addRepository);
        addresporesponse.enqueue(new Callback<AddRepository>() {
            @Override
            public void onResponse(Call<AddRepository> call, Response<AddRepository> response) {
                if (response.isSuccessful())
                {
                    Toast.makeText(AddRepoToGit.this,"Repository Added Successfully.", Toast.LENGTH_LONG).show();

                    Intent i = new Intent(AddRepoToGit.this, RepoList.class);
                    startActivity(i);
                    finish();

                    ownername.getEditText().setText("");
                    reponame.getEditText().setText("");

                    Log.d("AddRepoAct :" + "AddResp : ", response.toString());
                }
                else
                {
                    Toast.makeText(AddRepoToGit.this,"Failed", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddRepository> call, Throwable t) {
                Toast.makeText(AddRepoToGit.this,"Check Credentials", Toast.LENGTH_LONG).show();
                Log.d("Throwable : ",t.getLocalizedMessage());
            }
        });
    }
}
