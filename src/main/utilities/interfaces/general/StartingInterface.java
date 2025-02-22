package main.utilities.interfaces.general;

import java.util.Scanner;

public class StartingInterface {
    static final private Scanner scan = new Scanner(System.in);

    static final private String controlEmoji = "🎮";
    static final private String increaseEmoji = "📈";
    static final private String decreaseEmoji = "📉";

    final static String intro = increaseEmoji + " " + "GamerVitals"
            + controlEmoji + " " + decreaseEmoji;
    final static String tag = "Track Stats for Players and Games";

    public static String printMainInterface() {
        System.out.println(intro);
        System.out.println(tag);

        System.out.println("[L]ogin or [S]ignup? [Q]uit.");
        String input = scan.nextLine().toLowerCase().trim();

        return switch (input) {
            case "l" -> "l";
            case "s" -> "s";
            case "q" -> "q";
            default -> throw new IllegalStateException("Invalid Response.");
        };
    }
}
