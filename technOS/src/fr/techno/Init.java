package fr.techno;

import java.io.File;

public class Init {
    public static void main(String[] args) {

        System.out.println(Utils.colored(175, 255, 125, "\033[1mBonjour et bienvenue sur Xtentions\033[0m"));

        System.out.println(Utils.colored(125, 255, 175, LoadConfig.getConfig().get("name")));
        System.out.println(Utils.colored(125, 255, 175, LoadConfig.getConfig().get("version")));
    }

}