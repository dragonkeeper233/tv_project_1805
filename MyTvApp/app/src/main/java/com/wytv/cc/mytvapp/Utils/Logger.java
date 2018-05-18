package com.wytv.cc.mytvapp.Utils;

import android.util.Log;


/**/
public class Logger {
	
	private static Logger instance = new Logger();
	
	private static String getFunctionName() {
		StackTraceElement[] sts = Thread.currentThread().getStackTrace();

		if (sts == null) {
			return null;
		}

		for (StackTraceElement st : sts) {
			if (st.isNativeMethod()) {
				continue;
			}

			if (st.getClassName().equals(Thread.class.getName())) {
				continue;
			}

			if (st.getClassName().equals(
					instance.getClass().getName())) {
				continue;
			}

			return "[" + Thread.currentThread().getName() + "("
					+ Thread.currentThread().getId() + "): " + st.getFileName()
					+ ":" + st.getLineNumber() + "]";
		}

		return null;
	}
	
	private static String createMessage(String msg) {
		String functionName = getFunctionName();
		String message = (functionName == null ? msg
				: (functionName + " --- " + msg));
		return msg;
	}
	
	/**
	 * 默认TAG
	 * @param msg
	 */
	public static void v(String msg) {
		v(Tags.APP, msg);
	}
	
	public static void v(String tag , String msg) {
	    if (tag == null || msg == null) {
	        return;
	    }
		if(isDebug())
		{
			int maxLogSize = 4000;    
			for(int i = 0; i <= msg.length() / maxLogSize; i++) {
				int start = i * maxLogSize;        
				int end = (i+1) * maxLogSize;       
				end = end > msg.length() ? msg.length() : end;        
				String message = createMessage(msg.substring(start, end));
				Log.v(tag, message);
			}
		}
//		Log.v(tag, msg);
	}

	/**
	 * 默认TAG
	 * @param msg
	 */
	public static void d(String msg) {
		d(Tags.APP, msg);
	}
	
	public static void d(String tag , String msg) {
	    if (tag == null || msg == null) {
            return;
        }
		if(isDebug()) 
		{
			int maxLogSize = 4000;    
			for(int i = 0; i <= msg.length() / maxLogSize; i++) {
				int start = i * maxLogSize;        
				int end = (i+1) * maxLogSize;       
				end = end > msg.length() ? msg.length() : end;        
				String message = createMessage(msg.substring(start, end));
				Log.d(tag, message);
			}
		}
//		Log.d(tag, msg);
	}

	/**
	 * 默认TAG
	 * @param msg
	 */
	public static void i(String msg) {
		i(Tags.APP, msg);
	}
	
	public static void i(String tag , String msg) {
	    if (tag == null || msg == null) {
            return;
        }
		if(isDebug()) 
		{
			int maxLogSize = 4000;    
			for(int i = 0; i <= msg.length() / maxLogSize; i++) {
				int start = i * maxLogSize;        
				int end = (i+1) * maxLogSize;       
				end = end > msg.length() ? msg.length() : end;        
				String message = createMessage(msg.substring(start, end));
				Log.i(tag, message);
			}
		}
//		Log.i(tag, msg);
	}

	/**
	 * 默认TAG
	 * @param msg
	 */
	public static void w(String msg) {
		w(Tags.APP, msg);
	}
	
	public static void w(String tag , String msg) {
	    if (tag == null || msg == null) {
            return;
        }
		if(isDebug())
		{
			int maxLogSize = 4000;    
			for(int i = 0; i <= msg.length() / maxLogSize; i++) {
				int start = i * maxLogSize;        
				int end = (i+1) * maxLogSize;       
				end = end > msg.length() ? msg.length() : end;        
				String message = createMessage(msg.substring(start, end));
				Log.w(tag, message);
			}
		}
//		Log.w(tag, msg);
	}

	/**
	 * 默认TAG
	 * @param msg
	 */
	public static void e(String msg) {
		e(Tags.APP, msg);
	}
	
	public static void e(String tag , String msg) {
	    if (tag == null || msg == null) {
            return;
        }
		if(isDebug())
		{
			int maxLogSize = 4000;
			for(int i = 0; i <= msg.length() / maxLogSize; i++) {
				int start = i * maxLogSize;        
				int end = (i+1) * maxLogSize;       
				end = end > msg.length() ? msg.length() : end;        
				String message = createMessage(msg.substring(start, end));
				Log.e(tag, message);
			}
		}
//		Log.e(tag, msg);
	}
	
	public static void eSuper(String tag, String info) {
		StackTraceElement[] ste = new Throwable().getStackTrace();
		int i = 1;
		if (isDebug()) {
			StackTraceElement s = ste[i];
			String className = s.getClassName().contains(".") ? s
					.getClassName().substring(
							s.getClassName().lastIndexOf("."),
							s.getClassName().length()) : s.getClassName();
			
			Log.e(tag, String.format("======[%s][%s][%s]=====%s",
					className, s.getLineNumber(), s.getMethodName(),
					info));
		}
	}

	public static void eSuperChild(String tag, String info) {
		StackTraceElement[] ste = new Throwable().getStackTrace();
		if (isDebug()) {
			int tempLog = 0;
			int i = 0;
			while (i < ste.length) {
				StackTraceElement s = ste[i];
				String className = s.getClassName().contains(".") ? s
						.getClassName().substring(
								s.getClassName().lastIndexOf("."),
								s.getClassName().length()) : s.getClassName();
				if (className.equalsIgnoreCase(".Activity")) {
					tempLog = i - 1;
					i = ste.length;
				}
				i++;
			}
			StackTraceElement s = ste[tempLog];
			String className = s.getClassName().contains(".") ? s
					.getClassName().substring(
							s.getClassName().lastIndexOf("."),
							s.getClassName().length()) : s.getClassName();
			Log.e(
					tag,
					String.format("======[%s][%s][%s]=====%s", className,
							s.getLineNumber(), s.getMethodName(), info));
		}
	}
	
	private static boolean isDebug() {
		return true;
	}
	
	public static class Tags {
		public static final String ORDER = "order";
		public static final String CHAT = "chat";
		public static final String APP = "app";
	}
}
