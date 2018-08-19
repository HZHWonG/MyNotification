package com.yc.cn.ycnotification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ReminderReceiver extends BroadcastReceiver {

    private static final String TAG = "ReminderReceiver";
    public static final String ACTION = "TIMING";
    public static final String ACTION2 = "TIMING2";
    private Disposable mDisposable;

    /**
     * 关闭定时器
     */
    public void closeTimer(){
        if (mDisposable != null) {
            mDisposable.dispose();
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (TextUtils.equals(action, ACTION)) {

            Log.i(TAG, "ReminderReceiver");
            //Calendar now = GregorianCalendar.getInstance();
            Notification.Builder mBuilder = new Notification.Builder(context)
                    .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("广播接受者标题，小杨")
                    .setContentText("广播接受者内容，扯犊子")
                    .setAutoCancel(true);

            Log.i(TAG, "onReceive: intent" + intent.getClass().getName());
            Intent resultIntent = new Intent(context, DialogActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            //将该Activity添加为栈顶
            stackBuilder.addParentStack(DialogActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setFullScreenIntent(resultPendingIntent, true);
            final NotificationManager mNotificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());





            Observable.timer(5, TimeUnit.SECONDS)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Long>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            mDisposable = d;
                        }

                        @Override
                        public void onNext(Long value) {
                            //Log.d("Timer",""+value);
                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {
                            // TODO:2017/12/1
                            mNotificationManager.cancel(1);
                            closeTimer();
                        }
                    });


        } else if (TextUtils.equals(action, ACTION2)) {

            Notification.Builder mBuilder = new Notification.Builder(context)
                    .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)        //
                    .setSmallIcon(R.mipmap.ic_launcher)                                         //设置通知的图标
                    .setTicker("有新消息呢3")                                                     //设置状态栏的标题
                    .setContentTitle("这个是标题3")                                               //设置标题
                    .setContentText("这个是内容3")                                                //消息内容
                    .setDefaults(Notification.DEFAULT_ALL)                                      //设置默认的提示音
                    .setPriority(Notification.PRIORITY_DEFAULT)                                 //设置该通知的优先级
                    .setOngoing(true)                                                          //让通知左右滑的时候不能取消通知
                    .setPriority(Notification.PRIORITY_DEFAULT)                                 //设置该通知的优先级
                    .setWhen(System.currentTimeMillis())                                        //设置通知时间，默认为系统发出通知的时间，通常不用设置
                    .setAutoCancel(true);                                                       //打开程序后图标消失


            Intent resultIntent = new Intent(context, NoTraceDialogActivity.class);


            //处理点击Notification的逻辑
            //创建intent
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);           //添加为栈顶Activity
            resultIntent.putExtra("what",3);
            PendingIntent resultPendingIntent = PendingIntent.getActivity(context,3,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            //发送pendingIntent
            mBuilder.setContentIntent(resultPendingIntent);

            // Sets the Activity to start in a new, empty task
            resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                    | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            PendingIntent notifyPendingIntent =
                    PendingIntent.getActivity(context, 0, resultIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

            mBuilder.setContentIntent(notifyPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());

        } else {
            Log.i(TAG, "ReminderReceiver");
            //Calendar now = GregorianCalendar.getInstance();
            Notification.Builder mBuilder = new Notification.Builder(context)
                    .setSound(android.provider.Settings.System.DEFAULT_NOTIFICATION_URI)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("广播接受者标题，小杨")
                    .setContentText("广播接受者内容，扯犊子")
                    .setAutoCancel(true);

            Log.i(TAG, "onReceive: intent" + intent.getClass().getName());
            Intent resultIntent = new Intent(context, TestActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            //将该Activity添加为栈顶
            stackBuilder.addParentStack(TestActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);
            mBuilder.setFullScreenIntent(resultPendingIntent, true);
            NotificationManager mNotificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());
        }


    }

}
