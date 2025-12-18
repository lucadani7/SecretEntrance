package com.lucadani;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Utility class designed to crack the Elves' safe by simulating dial rotations.
 * <p>
 * The mechanism works on a circular dial (0-99). The goal is to determine a password
 * based on how many times the dial lands on a specific target number.
 * </p>
 *
 * @author lucadani7
 * @version 1.0
 */
public class SecretEntrance {
    private static final int DIAL_SIZE = 100;
    private static final int START_POSITION = 50;
    private static final int TARGET_NUMBER = 0;
    private static final String FILE_NAME = "input.txt";

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        System.out.println("Password: " + solvePartOne(getInstructions()));
    }

    /**
     * Calculates the safe password based on a list of rotation instructions.
     * <p>
     * The logic processes each instruction sequentially. 'R' rotates the dial clockwise (addition),
     * and 'L' rotates it counter-clockwise (subtraction). It handles the circular wrap-around logic.
     * </p>
     *
     * @param instructions A list of strings representing rotations (e.g., "R50", "L10").
     * @return The total count of times the dial visited the {@code TARGET_NUMBER}.
     */
    public static int solvePartOne(List<String> instructions) {
        int currentPosition = START_POSITION;
        int passwordCounter = 0;
        System.out.println("--- Start Decriptation ---");
        System.out.println("Configuration:");
        System.out.println("  -> Start: " + START_POSITION);
        System.out.println("  -> Target: " + TARGET_NUMBER);
        System.out.println("  -> Dial size: " + DIAL_SIZE);
        System.out.println("Number of instructions to be processed: " + instructions.size());
        System.out.println("-------------------------");

        for (String instruction : instructions) {
            if (instruction == null || instruction.length() < 2) {
                throw new IllegalArgumentException("Length validation: the instruction must contain at least two characters.");
            }
            if (instruction.charAt(0) != 'L' && instruction.charAt(0) != 'R') {
                throw new IllegalArgumentException("Unknown direction. Only L or R is accepted.");
            }
            int amount;
            try {
                amount = Integer.parseInt(instruction.substring(1));
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Amount invalid in the instruction: " + instruction);
            }
            if (instruction.charAt(0) == 'R') {
                currentPosition = (currentPosition + amount) % DIAL_SIZE;
            } else {
                for (currentPosition -= amount; currentPosition < 0; currentPosition += DIAL_SIZE);
                currentPosition %= DIAL_SIZE;
            }
            if (currentPosition == TARGET_NUMBER) {
                ++passwordCounter;
            }
        }
        return passwordCounter;
    }

    /**
     * Retrieves the list of rotation instructions from the specified file.
     * <p>
     * If the file does not exist or cannot be read, the method falls back to a default
     * example set of instructions provided in the requirements.
     * </p>
     *
     * @return A list of instruction strings to be processed.
     */
    private static List<String> getInstructions() {
        List<String> lines = new ArrayList<>();
        if (Files.exists(Path.of(FILE_NAME))) {
            try (Scanner scanner = new Scanner(new File(FILE_NAME))) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    if (!line.trim().isEmpty()) {
                        lines.add(line.trim());
                    }
                }
                System.out.println("Data sources: " + FILE_NAME);
            } catch (Exception e) {
                System.err.println("Error while reading file: " + e.getMessage());
            }
        }
        if (lines.isEmpty()) {
            System.out.println("Data sources: default example (the file is missing or is empty)...");
            lines = List.of("L68", "L30", "R48", "L5", "R60", "L55", "L1", "L99", "R14", "L82");
        }
        System.out.println();
        System.out.println("Instructions:");
        lines.forEach(System.out::println);
        System.out.println();
        return lines;
    }
}