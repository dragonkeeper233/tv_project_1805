package com.wytv.cc.mytvapp.http;

/**
 * 
 * ProgressDialogThread 的Interface
 * 
 * ProgressDialogThreadMain ： 线程的主函数，将在ProgressDialog显示的时候在线程中运行 OnTaskDismissed
 * ： 在任务未完成，ProgressDialog被dismiss时的响应 OnTaskDone ： 任务完成时的响应
 * 
 * @author Qym
 * 
 */
public interface ThreadWithProgressDialogTask {

	boolean TaskMain();

	boolean OnTaskDismissed();

	boolean OnTaskDone();

}
