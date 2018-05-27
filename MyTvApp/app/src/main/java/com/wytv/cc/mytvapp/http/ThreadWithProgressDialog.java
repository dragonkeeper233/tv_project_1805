package com.wytv.cc.mytvapp.http;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Looper;
import android.util.Log;

import com.wytv.cc.mytvapp.R;


/**
 * ProgressDialogThread ： ProgressDialog线程任务管理类： 功能： 前台UI显示ProgressDialog，
 * 后台启动线程完成指定工作,并根据任务完成/用户取消两个情况回调对应方法 其中‘指定工作’，
 * 两个‘回调方法’均通过实现ProgressDialogThreadTask接口来定义。 所以，需以ProgressDialogThreadTask
 * interface配合使用
 * <p>
 * 使用方法 1. 通过继承 ProgressDialogThreadTask来定义 a. 需要在线程中完成的工作，即TaskMain方法 b.
 * 两个回调方法： 任务被成功执行的响应： OnTaskDone 任务被用户被取消的响应 ： OnTaskDismissed 例子： class MyTask
 * implements ProgressDialogThreadTask { boolean TaskMain() { 一个耗时的工作 } //定义任务
 * <p>
 * public boolean OnTaskDone() { //在此处理如果任务 return true; }
 * <p>
 * public boolean OnTaskDismissed() { //在此处理如果任务被取消 return true; } }
 * <p>
 * 2. 新建一个ProgressDialog实例， ProgressDialogThread myPDT = new
 * ProgressDialogThread();
 * <p>
 * 3. 启动任务 myPDT.Run(context, //正确的上下文 new MyTask(), //要执行的任务和响应
 * "一个耗时的工作正在进行，请稍等..." //进度框上显示的消息 )
 * <p>
 * <p>
 * 一个具体的使用例子：
 * <p>
 * class MyActivity extends Activity implements ProgressDialogThreadTask {
 * ProgressDialogThread myPDT = new ProgressDialogThread;
 * //定义ProgressDialog线程任务管理类实例
 * <p>
 * protected void onCreate(Bundle savedInstanceState) { { //显示ProgressDialog，
 * 启动线程任务 myPTD.Run(this, //这里上下文是MyActivity自己 this,
 * //要执行的任务和响应也是MyActivity自己实现的 "一个耗时的工作正在进行，请稍等..." ); }
 * <p>
 * boolean TaskMain() { //一个耗时的工作 }
 * <p>
 * public boolean OnTaskDone() { //在此处理如果任务 return true; }
 * <p>
 * public boolean OnTaskDismissed() { //在此处理如果任务被取消 return true; } }
 *
 * @author Qym
 */
public class ThreadWithProgressDialog {

    /**
     * @param context : 当前活动的context （android有的activity为非活动，需要取其getParent)
     * @param task    : 其中定义了需要完成的任务
     * @param msg     ： 在进度对话框中显示的消息
     * @return : boolean
     */
    public boolean Run(Context context, ThreadWithProgressDialogTask task,
                       String msg) {
        return Run(context, task, msg, true);
    }

    public boolean Run(Context context, ThreadWithProgressDialogTask task,
                       String msg, boolean isCenceled) {
        Log.d("ProgressDialogThread", "ProgressDialogThread.Run...");

        try {
            // ProgressDialog customDialog = new ProgressDialog(context);
            // ProgressThread thread = new ProgressThread(task, customDialog);

            // customDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//
            // 设置风格为圆形进度条
            // customDialog.setCanceledOnTouchOutside(false);
            // customDialog.setMessage(msg);
            // customDialog.setIndeterminate(false);// 设置进度条是否为不明确
            // customDialog.setOnDismissListener(thread);
            //
            // customDialog.show();
            int cenceled = 0;
            if (isCenceled)
                cenceled = R.drawable.loading_dismiss;

            LoadProgressDialog customDialog = new LoadProgressDialog(context,
                    R.style.CustomProgressDialog, R.layout.progress_dialog,
                    R.anim.rotate_refresh, 0, R.drawable.loading_refresh,
                    cenceled, msg);
            customDialog.setCancelable(isCenceled);

            ProgressThread thread = new ProgressThread(task, customDialog);

            customDialog.setOnDismissListener(thread);
            customDialog.show();

            thread.start();

        } catch (Exception e) {
            Log.d("ProgressDialogThread",
                    "!!!!! ProgressDialogThread - Error exception : "
                            + e.toString());
            e.printStackTrace();

        }

        return true;
    }

    public boolean Run(Context context, ThreadWithProgressDialogTask task,
                       int R_Id) {
        return Run(context, task, context.getResources().getString(R_Id));
    }

    /**
     * 启动线程跑ProgressDialogThreadTask.TaskMain的管理类，
     *
     * @author born
     */
    public static class ProgressThread extends Thread implements
            OnDismissListener {

        ProgressThread(ThreadWithProgressDialogTask task,
                       LoadProgressDialog customDialog) {
            task_ = task;
            pDialog_ = customDialog;
        }

        ThreadWithProgressDialogTask task_;
        LoadProgressDialog pDialog_;
        boolean bTaskOk = false;

        public void run() {

            Log.d("ProgressDialogThread", "ProgressThread.run...");

            Looper.prepare();

            bTaskOk = task_.TaskMain();
            if (pDialog_ != null) {
                pDialog_.dismiss();
            }

            Looper.loop();
        }

        // ok, I make the thread a OnDismissListener, a little bit ugly, but
        // just let it go....
        @Override
        public void onDismiss(DialogInterface dialog) {
            // 远程处理完毕进来这里，在这里处理你要do的事
            if (bTaskOk) {

                task_.OnTaskDone();
                // SingleToast.show(getParent(),"任务完成",Toast.LENGTH_LONG);

            } else {
                task_.OnTaskDismissed();
                // SingleToast.show(LoginActivity.this,"任务被取消",Toast.LENGTH_LONG);
            }
        }
    }
}
