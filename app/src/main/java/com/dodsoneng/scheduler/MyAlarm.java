package com.dodsoneng.scheduler;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import android.util.Log;
import android.widget.Toast;


/**
 * Created by eng on 9/8/16.
 */
public class MyAlarm extends BroadcastReceiver {

    private String          TAG = "DESCHED.MyAlarm";
    static private int      _counter = 0;
    static private Context  _ctx;
    static private Intent   _intent;
    static private long     _endTimeMS;


    @Override
        public void onReceive(Context context, Intent intent)
        {
            ++_counter ;

            Log.d (TAG, "onReceive(): Alarm ["+ _counter +"]" + " currentTime="+String.valueOf(System.currentTimeMillis()/1000)+" endTime="+String.valueOf(_endTimeMS/1000));

            /*
            =====================================================================================
            MR DODSON
            For now you can put your code here.
            I will create a setCallback method in MyAlarm class, that you can set the callback.
            And I will call teh callback here.
            =====================================================================================
             */

            if (System.currentTimeMillis() >= _endTimeMS) {
                Log.d (TAG, "onReceive(): stoping alarm");
                stopAlarm();
                _counter = 0;
            }
        }

    public void setAlarm(Context context, long bgnTimeMS, long endTimeMS)
    {
        Log.d (TAG, "setAlarm(): counter ["+ _counter +"] bgnTimeMS="+bgnTimeMS+" endTimeMS="+endTimeMS);

        _ctx = context;
        _endTimeMS = endTimeMS;

        AlarmManager am =( AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        _intent = new Intent(context, MyAlarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, _intent, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, bgnTimeMS /*System.currentTimeMillis()*/, 1000 * 60 * 1, pi); // Millisec * Second * Minute

        Toast.makeText(_ctx, "Alarm Set", Toast.LENGTH_SHORT).show();

    }

    public void stopAlarm () {
        Log.d (TAG, "stopAlarm(): _counter ["+ _counter +"]");
        Log.d (TAG, "stopAlarm(): _ctx["+ _ctx+"]");
        Log.d (TAG, "stopAlarm(): _intent["+ _intent +"]");

         // Intent intent = new Intent(_ctx, MyAlarm.class);
         PendingIntent sender = PendingIntent.getBroadcast(_ctx, 0, _intent, 0);
         AlarmManager alarmManager = (AlarmManager) _ctx.getSystemService(Context.ALARM_SERVICE);
         alarmManager.cancel(sender);

         Toast.makeText(_ctx, "Alarm Canceled", Toast.LENGTH_SHORT).show();

    }

    public long getTimeInMilisecs (int hour, int min) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);

        return calendar.getTimeInMillis();

    }
}
