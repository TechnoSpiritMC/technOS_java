package fr.techno;

public class Utils {

    public static String colored(int r, int g, int b, String text) {
        return ("\033[38;2;"+ r +";"+ g +";"+ b +"m"+ text +" \033[38;2;255;255;255m");
    }

    public static boolean isNull(Object object) {
        return object == null;
    }

}
