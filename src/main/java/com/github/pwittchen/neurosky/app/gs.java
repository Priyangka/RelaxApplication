package com.github.pwittchen.neurosky.app;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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

public class gs extends AppCompatActivity {
    private final static String LOG_TAG = "NeuroSky";
    private NeuroSky neuroSky;

    @BindView(R.id.tv_state) TextView tvState;

    @BindView(R.id.tv_meditation) TextView tvMeditation;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        neuroSky = createNeuroSky();
        btn =(Button) findViewById(R.id.btn_nexr);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openmain();
            }
        });
    }
    public void openmain()
    {
        Intent intent=new Intent(this,MainActivity.class);
        startActivity(intent);
    }


    @Override protected void onResume() {
        super.onResume();
        if (neuroSky != null && neuroSky.isConnected()) {
            neuroSky.start();
        }
    }

    @Override protected void onPause() {
        super.onPause();
        if (neuroSky != null && neuroSky.isConnected()) {
            neuroSky.stop();
        }
    }

    @NonNull private NeuroSky createNeuroSky() {
        return new NeuroSky(new ExtendedDeviceMessageListener() {
            @Override public void onStateChange(State state) {
                handleStateChange(state);
            }

            @Override public void onSignalChange(Signal signal) {
                handleSignalChange(signal);
            }

            @Override public void onBrainWavesChange(Set<BrainWave> brainWaves) {
                handleBrainWavesChange(brainWaves);
            }
        });
    }

    private void handleStateChange(final State state) {
        if (neuroSky != null && state.equals(State.CONNECTED)) {
            neuroSky.start();
        }

        tvState.setText(state.toString());
        Log.d(LOG_TAG, state.toString());
    }

    private void handleSignalChange(final Signal signal) {
        switch (signal) {

            case MEDITATION:
                tvMeditation.setText(getFormattedMessage("meditation: %d", signal));
                break;

        }

        Log.d(LOG_TAG, String.format("%s: %d", signal.toString(), signal.getValue()));
    }

    private String getFormattedMessage(String messageFormat, Signal signal) {
        return String.format(Locale.getDefault(), messageFormat, signal.getValue());
    }

    private void handleBrainWavesChange(final Set<BrainWave> brainWaves) {
        for (BrainWave brainWave : brainWaves) {
            Log.d(LOG_TAG, String.format("%s: %d", brainWave.toString(), brainWave.getValue()));
        }
    }

    @OnClick(R.id.btn_connect) void connect() {
        try {
            neuroSky.connect();
        } catch (BluetoothNotEnabledException e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.d(LOG_TAG, e.getMessage());
        }
    }

}
