package com.hzdongcheng.softwareplatform.filter;

public class CurrentSession {
	private static ThreadLocal<Object> threadLocal = new ThreadLocal<Object>();
	
	public static void clearSessionVariable() {
		threadLocal.remove();
	}


	public static void setSession(Object session) {
		threadLocal.set(session);
	}

	public static Object getSession() {
		return (Object) threadLocal.get();
	}
}