package base;


public class Log {

	public static final String ERROR = "ERROR";
	public static final String WARNING = "WARNING";
	public static final String NOTICE = "NOTICE";

	public static void log(String message, Class clazz, Exception e) {
	}

	public static void log(String message, Class clazz, String description, Exception e) {
	}

	public static void log(String message, Class clazz, String description) {
	}

	public static void log(String message, Object object, String description) {
	}

	public static void log(String message, Object object, Exception e) {
	}

	public static void log(String message, Object object, Object subst) {
	}
}
