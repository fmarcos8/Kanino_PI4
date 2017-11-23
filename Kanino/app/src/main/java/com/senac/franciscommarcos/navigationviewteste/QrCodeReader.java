package com.senac.franciscommarcos.navigationviewteste;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;
import com.senac.franciscommarcos.navigationviewteste.Activities.MainActivity;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QrCodeReader extends AppCompatActivity {
    public ZXingScannerView qrCodeScanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        qrCodeScanner =  new ZXingScannerView(this);
        setContentView(qrCodeScanner);

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults){
        if(requestCode == 1){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_DENIED){
                Toast toast = Toast.makeText(this, "Não é possível fazer a leitura do QRCode sem permissão", Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }

    protected void onResume(){
        super.onResume();
        qrCodeScanner.setResultHandler(new ZXingScannerView.ResultHandler(){
            @Override
            public void handleResult(Result result) {
                Intent intent = new Intent(QrCodeReader.this, MainActivity.class);
                intent.putExtra("id_product", result.getText());
                setResult(RESULT_OK, intent);
                finish();
            }
        });
        qrCodeScanner.startCamera();
    }

    public void onPause(){
        super.onPause();
        qrCodeScanner.stopCamera();
    }

}
