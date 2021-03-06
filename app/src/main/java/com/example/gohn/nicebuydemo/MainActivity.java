package com.example.gohn.nicebuydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    public static final int REQUEST_CONFIRMITEM_AND_MAIN = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onButtonConfirm(View v){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.addExtra("PROMPT_MESSAGE","상품코드를 사각형 안에 비춰주세요.");
        integrator.setOrientationLocked(false);
        integrator.initiateScan();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==49374) {            //49374 는 바코드 인식 zxing 후 주는 요청코드입니다.
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result.getContents() != null) {
                Intent intent_Confirm = new Intent(getApplicationContext(), ConFirmItemActivity.class);
                intent_Confirm.putExtra("BarCode_Content", result.getContents());
                intent_Confirm.putExtra("BarCode_Format", result.getFormatName());
                startActivityForResult(intent_Confirm, REQUEST_CONFIRMITEM_AND_MAIN);
            }else{
                // http://helloms.kr/entry/Android%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9C-Zxing-%EC%82%AC%EC%9A%A9%ED%95%98%EA%B8%B0-%EC%84%B8%EB%A1%9C%EB%AA%A8%EB%93%9C    //
                // http://thegreedyman.tistory.com/archive/20150604
                Toast.makeText(getApplicationContext(), "인식이 되지 않았습니다.", Toast.LENGTH_LONG).show();
            }
        }
    }
}
