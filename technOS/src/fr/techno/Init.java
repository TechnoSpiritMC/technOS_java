package fr.techno;


import java.util.Scanner;

public class Init {
    public static void main(String[] args) {

        System.out.println(Utils.color(Utils.INFO, "\033[1mTechnOS successfully initialized\033[0m"));
        System.out.println(Utils.color(Utils.WHITE, "Please enter a command."));

        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 32767; i++) {
            String command = scanner.nextLine();
            System.out.println("Command ran: " + command);
        }

    }

}