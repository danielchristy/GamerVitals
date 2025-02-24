package main.utilities.interfaces.general;

import java.util.Scanner;

public class StartingInterface {
    static final private Scanner scan = new Scanner(System.in);
    static final String divider = "============================================";
    static final private String controlEmoji = "🎮";
    static final private String increaseEmoji = "📈";
    static final private String decreaseEmoji = "📉";
    static final String intro = increaseEmoji + " " + "GamerVitals" + controlEmoji +
            " " + decreaseEmoji + "\n ~~Track Stats for Players and Games~~";

    public static String printMainInterface() {
        System.out.println(divider);
        System.out.println(intro);

        System.out.print("[L]ogin or [S]ignup? [Q]uit. > ");
        String input = scan.next().toLowerCase().trim();

        return switch (input) {
            case "l" -> "l";
            case "s" -> "s";
            case "q" -> "q";
            default -> "";
        };
    }
}
