package br.org.eldorado.cst.collector;

import static android.Manifest.permission.ACCESS_BACKGROUND_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;
import static android.Manifest.permission.FOREGROUND_SERVICE;
import static android.Manifest.permission.POST_NOTIFICATIONS;
import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static br.org.eldorado.cst.collector.constants.Constants.APP_VERSION;
import static br.org.eldorado.cst.collector.constants.Constants.DB_VERSION;
import static br.org.eldorado.cst.collector.constants.Constants.NOTIFICATION_CHANNEL_ID;
import static br.org.eldorado.cst.collector.constants.Constants.TAG;
import static br.org.eldorado.cst.collector.constants.Constants.HANDLER_ACTION;
import static br.org.eldorado.cst.collector.constants.Constants.HANDLER_MESSAGE;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import br.org.eldorado.cst.collector.foreground.ForegroundService;
import br.org.eldorado.cst.collector.utils.Dialogs;

/**
 * Main activity doesn't really do much, but start the service and then finish.
 * In oreo to run a background service when the app is not running it must
 * startForegroundService(Intent) in the activity
 * in service, make a notification low or higher. persistent.
 * and startForground (int id, Notification notification )
 */

public class MainActivity extends AppCompatActivity {
    ActivityResultLauncher<String[]> rpl;
    TextView alertTxt = null;
    TextView versionTxt = null;
    ToggleButton startBtn = null;

    private final Vector<String> REQUIRED_PERMISSIONS = new Vector<>(Arrays.asList(ACCESS_BACKGROUND_LOCATION,
                                                                                   ACCESS_NETWORK_STATE,
                                                                                   FOREGROUND_SERVICE,
                                                                                   POST_NOTIFICATIONS,
                                                                                   ACCESS_FINE_LOCATION,
                                                                                   ACCESS_COARSE_LOCATION));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("CST Collector");

        alertTxt = findViewById(R.id.alertTxt);
        startBtn = findViewById(R.id.startBtn);
        versionTxt = findViewById(R.id.versionTxt);

        versionTxt.setText(APP_VERSION + "." + DB_VERSION);

        // Request necessary permission to the users
        requestPermission();

        // Check service state
        startBtn.setChecked(isMyServiceRunning(ForegroundService.class));

        //IntentService start with 5 random number toasts
        startBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {

                if (isMyServiceRunning(ForegroundService.class)) {
                    // Foreground already running, so stop it.
                    stopService(new Intent(getBaseContext(), ForegroundService.class));

                } else {
                    // Foreground service not running, start it.
                    // Check permissions
                    List<String> missingPermissions = getPermissionsNotGranted();
                    if (missingPermissions.size() > 0) {
                        alertTxt.setText(getString(R.string.msg_error_missing_permission) +
                                ": " + missingPermissions);
                        startBtn.setChecked(false);
                        return;
                    }

                    // Start Foreground service
                    Intent startIntent = new Intent(getBaseContext(), ForegroundService.class);
                    startIntent.setAction(ForegroundService.ACTION_START_FOREGROUND_SERVICE);
                    startIntent.putExtra(HANDLER_MESSAGE.COMMAND, HANDLER_ACTION.START);
                    startForegroundService(startIntent);

                    // Finish this activity, and keep only Foreground service running in the
                    // notification bar.
                    finish();
                }
            }
        });

        // Needed for the persistent notification created in service.
        createChannel();

        // Check if all permissions are granted
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!allPermissionsGranted()) {
                rpl.launch(REQUIRED_PERMISSIONS.toArray(new String[REQUIRED_PERMISSIONS.size()]));
            }
        }
    }

    /**
     * for API 26+ create notification channels
     */
    private void createChannel() {
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel mChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                                                               getString(R.string.channel_name),  //name of the channel
                                                               NotificationManager.IMPORTANCE_HIGH);

        // Configure the notification channel.
        mChannel.setDescription(getString(R.string.channel_description));
        mChannel.setShowBadge(true);

        nm.createNotificationChannel(mChannel);
    }

    private void requestPermission() {
        rpl = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(),
                new ActivityResultCallback<Map<String, Boolean>>() {
                    @Override
                    public void onActivityResult(Map<String, Boolean> isGranted) {
                        for (Map.Entry<String, Boolean> x : isGranted.entrySet()) {
                            Log.d(TAG, x.getKey() + " is " + x.getValue());

                            if (!x.getValue()) {
                                // Missing required permissions
                                Log.e(TAG, x.getKey() + " is not allowed.");

                                if (x.getKey().equals(POST_NOTIFICATIONS)) {
                                    requestNotificationPermission();
                                } else if (x.getKey().equals(ACCESS_BACKGROUND_LOCATION)) {
                                    requestLocationPermission();
                                }
                            }
                        }
                    }
                }
        );
    }

    private void requestNotificationPermission() {
        ActivityCompat.requestPermissions(this,
                new String[]{POST_NOTIFICATIONS}, 1);
    }

    private void requestLocationPermission() {
        // API 30+ requires 'background location', that must be set as 'allow all the time'.
        // Here it displays a dialog informing user to correctly set this permission.
        Dialogs.goToSetting(this);
    }

    private boolean allPermissionsGranted() {
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }

        return true;
    }

    private List<String> getPermissionsNotGranted() {
        List<String> missingPermissions = new ArrayList<>();
        for (String permission : REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                missingPermissions.add(permission);
            }
        }

        return missingPermissions;
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
