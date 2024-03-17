package fr.techno;

import java.util.ArrayList;
import java.util.List;

public class Utils {

    public static List<Integer> INFO = new ArrayList<>(List.of(130, 225, 100));
    public static List<Integer> WARN = new ArrayList<>(List.of(225, 200, 100));
    public static List<Integer> ERROR = new ArrayList<>(List.of(255, 125, 100));
    public static List<Integer> DEBUG = new ArrayList<>(List.of(200, 125, 255));
    public static List<Integer> WHITE = new ArrayList<>(List.of(255, 255, 255));

    public static String colored(int r, int g, int b, String text) {
        return ("\033[38;2;"+ r +";"+ g +";"+ b +"m"+ text +" \033[38;2;255;255;255m");
    }

    public static String color(List<Integer> pattern, String text) {

        int r = pattern.get(0);
        int g = pattern.get(1);
        int b = pattern.get(2);

        return ("\033[38;2;"+ r +";"+ g +";"+ b +"m"+ text +" \033[0m");
    }

}
