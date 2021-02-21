package com.github.pwittchen.neurosky.app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.github.pwittchen.neurosky.library.NeuroSky;
import com.github.pwittchen.neurosky.library.exception.BluetoothNotEnabledException;
import com.github.pwittchen.neurosky.library.listener.ExtendedDeviceMessageListener;
import com.github.pwittchen.neurosky.library.message.enums.BrainWave;
import com.github.pwittchen.neurosky.library.message.enums.Signal;
import com.github.pwittchen.neurosky.library.message.enums.State;
import java.util.Locale;
import java.util.Set;

public class Homepage extends AppCompatActivity{
    private final static String LOG_TAG = "NeuroSky";
    private NeuroSky neuroSky;

        private ImageView image;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        image =(ImageView)findViewById(R.id.logo);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openhome2();
            }
        });
    }
    public void openhome2()
    {
        Intent intent=new Intent(this,gs.class);
        startActivity(intent);
    }
}
