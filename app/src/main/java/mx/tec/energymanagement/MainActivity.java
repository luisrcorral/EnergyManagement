package mx.tec.energymanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView powerStatus;

    private BroadcastReceiver plugInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);

            if (plugged == BatteryManager.BATTERY_PLUGGED_AC)
                powerStatus.setText("Plugged to AC");
            else if (plugged == BatteryManager.BATTERY_PLUGGED_USB)
                powerStatus.setText("Plugged to USB");
            else if (plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS)
                powerStatus.setText("Plugged to Wireless Charger");
            else
                powerStatus.setText("Not plugged");

        }
    };

    private TextView batteryTxt;

    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver(){
        @Override
        public void onReceive(Context ctxt, Intent intent) {
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

            batteryTxt.setText("Battery Level: " + String.valueOf(level) + "%");


        }
    };


    private TextView connStatusTxt;

    private void netWorkStatus() {

        String networkType = "";

        Context myContext = getApplicationContext();
        ConnectivityManager cm = (ConnectivityManager) myContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        try{
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork.isConnectedOrConnecting()) {

                if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    networkType = "WiFi";
                }
                else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE){
                    networkType = "Mobile";
                }
                else {
                    networkType = "Other";
                }

            }
            connStatusTxt.setText("Network Type: " + networkType);
        }
        catch (Exception e){
            connStatusTxt.setText("Device is not online");
        }

    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

            setContentView(R.layout.activity_main);

            batteryTxt = findViewById(R.id.batteryLevelTxt);
            powerStatus = findViewById(R.id.powerStatusTxt);
            connStatusTxt = findViewById(R.id.connStatusTxt);

            this.registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            this.registerReceiver(this.plugInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            this.netWorkStatus();

    }
}