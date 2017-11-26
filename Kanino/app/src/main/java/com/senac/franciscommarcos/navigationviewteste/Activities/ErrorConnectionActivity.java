package com.senac.franciscommarcos.navigationviewteste.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.senac.franciscommarcos.navigationviewteste.R;

public class ErrorConnectionActivity extends AppCompatActivity {
    private Button btn_reload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error_connection);

        btn_reload = (Button) findViewById(R.id.btn_reload);

        btn_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = ErrorConnectionActivity.this.getPackageManager()
                        .getLaunchIntentForPackage(ErrorConnectionActivity.this.getPackageName());
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
    }
}
