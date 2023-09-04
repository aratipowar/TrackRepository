package com.trackrepository.Activities;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;
import com.trackrepository.R;
import com.trackrepository.Remote.InternetConnection;

public class OpenRepo extends AppCompatActivity
{
    WebView repoWeb;
    String repoUrl;
    ConstraintLayout rlRootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.open_repo);

        repoWeb = findViewById(R.id.web_repo);
        rlRootLayout = findViewById(R.id.rl_rootLayout);

        repoUrl = getIntent().getStringExtra("repourl");
        if (InternetConnection.checkConnection(OpenRepo.this)) {
            repoWeb.loadUrl(repoUrl);
            repoWeb.getSettings().setJavaScriptEnabled(true);
            repoWeb.setWebViewClient(new WebViewClient());
        }else{
            Snackbar.make(rlRootLayout, getResources().getString(R.string.no_internet),
                            Snackbar.LENGTH_INDEFINITE)
                    .setAction("RETRY", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            repoWeb.loadUrl(repoUrl);
                            repoWeb.getSettings().setJavaScriptEnabled(true);
                            repoWeb.setWebViewClient(new WebViewClient());
                        }
                    })
                    .show();
        }
    }

}
