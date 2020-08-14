package org.techtown.creative_total_construction;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

public class MyAutoRunApp extends BroadcastReceiver {

    @Override
        public void onReceive(Context context, Intent intent){
            Intent i = new Intent(context, MyService.class);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                context.startForegroundService(i); }
        else { context.startService(i);
        }
        }
    }
// 부팅 시 자동으로 BackGround Service를 동작시킬 수 있는 동작과 죽지 않는 서비스 기능을 지원하는Class