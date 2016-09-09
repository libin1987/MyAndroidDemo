package com.lib.base;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.paolorotolo.appintro.AppIntro;
import com.lib.mydemo.MainActivity;
import com.lib.mydemo.R;
import com.lib.utility.ActivityUtil;

public class SplashIntro extends AppIntro {
    @Override
    public void init(Bundle savedInstanceState) {
        addSlide(SampleSlide.newInstanceImg(R.drawable.intro2));
        addSlide(SampleSlide.newInstanceImg(R.drawable.intro3));
        addSlide(SampleSlide.newInstanceImg(R.drawable.intro4));
    }

    private void loadMainActivity(){
        ActivityUtil.startActivity(MainActivity.class);
        int versionCode=Util.getAppVersionCode(getApplicationContext());
        Util.set(getApplicationContext(),Util.FILE_NAME,versionCode+"",true);
    }

    @Override
    public void onNextPressed() {
    }

    @Override
    public void onSkipPressed() {
        loadMainActivity();
        Toast.makeText(getApplicationContext(),
                "跳过", Toast.LENGTH_SHORT).show();
        
    }

    @Override
    public void onDonePressed() {
        loadMainActivity();
    }

    @Override
    public void onSlideChanged() {
    }

    public void getStarted(View v){
        loadMainActivity();
    }
}
