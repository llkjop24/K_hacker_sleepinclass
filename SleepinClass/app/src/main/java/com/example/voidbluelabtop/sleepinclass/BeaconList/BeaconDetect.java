package com.example.voidbluelabtop.sleepinclass.BeaconList;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import com.estimote.coresdk.common.config.EstimoteSDK;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.util.List;

import static android.content.ContentValues.TAG;

public class BeaconDetect extends Service {
    private BeaconManager bm;
    private Singlton_BeaconList SB;
    final private BeaconRegion ALL_BEACON = new BeaconRegion("regionID", null, null, null);
    @Override
    public void onCreate() {
        SB = Singlton_BeaconList.getInstance();
        bm = new BeaconManager(getApplicationContext());
        EstimoteSDK.initialize(getApplicationContext(),
                "estimotebeaconsample-ivb", "45f6fc31fd613624b0f24df41b121db6");

        bm.connect(new BeaconManager.ServiceReadyCallback(){
            @Override
            public void onServiceReady() {
                bm.startRanging(ALL_BEACON);
            }
        });
        bm.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion beaconRegion, List<Beacon> list) {
                SB.set_beaconlist(list);
                Log.d(TAG, "onBeaconsDiscovered: " + list.size());
                if(!list.isEmpty()){
                    for(int i = 0 ; i < list.size() ; i++){
                        Beacon B = list.get(i);
                        Toast.makeText(getApplicationContext(),""+B.getMajor(),Toast.LENGTH_LONG);
                    }
                }
            }
        });
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

}