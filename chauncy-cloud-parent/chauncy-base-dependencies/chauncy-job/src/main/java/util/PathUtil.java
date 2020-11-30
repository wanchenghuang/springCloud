package util;

/**
 * @Author cheng
 * @create 2020-10-29 23:13
 */
public class PathUtil {
    public PathUtil() {
    }

    public static String getRootPath(String classPath) {
        String subClassPathString = classPath.substring(0, classPath.lastIndexOf("classes"));
        return subClassPathString.contains("/target") ? subClassPathString.substring(0, classPath.lastIndexOf("target")) : subClassPathString;
    }

    public static String getCacheRootByOS(String path) {
        if (path.startsWith("file:")) {
            return ".job-log/";
        } else {
            return isOSWindows() ? path.substring(1, path.length()) : path;
        }
    }

    public static boolean isOSWindows() {
        String osName = System.getProperty("os.name");
        return null != osName && !osName.equals("") ? osName.startsWith("Windows") : false;
    }
}
