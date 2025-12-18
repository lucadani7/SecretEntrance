package com.lucadani;

import org.junit.jupiter.api.Test;

import java.util.List;

import static com.lucadani.SecretEntrance.solvePartOne;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test suite for the {@link SecretEntrance} class.
 * <p>
 * This class validates the core logic of the safe dial mechanism, covering
 * standard use cases, edge cases, negative wrap-arounds, and large integer arithmetic.
 * </p>
 */
class SecretEntranceTestPartOneTest {
    /**
     * Verifies the logic using the specific example provided in the project requirements.
     * <p>
     * The sequence "L68, L30, R48, L5, R60, L55, L1, L99, R14, L82" should result in the dial landing on 0 exactly 3 times.
     * </p>
     */
    @Test
    void testExampleFromRequirements() {
        List<String> input = List.of(
                "L68", "L30", "R48", "L5", "R60",
                "L55", "L1", "L99", "R14", "L82"
        );
        int result = solvePartOne(input);
        assertEquals(3, result, "The result from example should be 3.");
    }

    /**
     * Tests rotations to the right (clockwise) that result in the target value (0).
     * <p>
     * Scenarios covered:
     * 1. Moving from start (50) to 0.
     * 2. Performing a full 360-degree rotation (R100) to land back on 0.
     * </p>
     */
    @Test
    void testFullRotationRight() {
        List<String> input = List.of("R50", "R100");
        int result = solvePartOne(input);
        assertEquals(2, result, "Right rotations that yield 0 should be counted.");
    }

    /**
     * Tests rotations to the left (counter-clockwise) that result in the target value (0).
     * <p>
     * Validates that subtraction logic correctly handles landing on 0 from various starting points.
     * </p>
     */
    @Test
    void testFullRotationLeft() {
        List<String> input = List.of("L50", "L100");
        int result = solvePartOne(input);
        assertEquals(2, result, "Left rotations that yield 0 should be counted.");
    }

    /**
     * Validates the 'wrap-around' behavior for negative numbers.
     * <p>
     * Example: If the dial is at 0 and rotates L1, it should wrap to 99, not -1.
     * This test ensures the modulo arithmetic correctly handles the transition
     * across the 0/99 boundary.
     * </p>
     */
    @Test
    void testNegativeWrapAround() {
        List<String> input = List.of("L51", "R1");
        int result = solvePartOne(input);
        assertEquals(1, result, "The dial should correctly wrap around when going below zero.");
    }

    /**
     * Stress tests boundary conditions with multiple consecutive landings on zero.
     * <p>
     * This ensures that every visit to 0 is counted, even if it happens
     * multiple times in a row or via full rotations in opposite directions.
     * </p>
     */
    @Test
    void testExactlyOnEdge() {
        List<String> input = List.of("R50", "R100", "L200", "L100");
        int result = solvePartOne(input);
        assertEquals(4, result, "Every stop on 0 must be counted, including full rotations.");
    }

    /**
     * Verifies that "near misses" are not counted.
     * <p>
     * If the dial skips over 0 (e.g., from 1 to 99 via L2), it should NOT trigger the counter.
     * The dial must land exactly on the target number.
     * </p>
     */
    @Test
    void testNeverZero() {
        List<String> input = List.of("L49", "L2", "R3", "R90");
        int result = solvePartOne(input);
        assertEquals(0, result, "Skipping over 0 should not increment the password counter.");
    }

    /**
     * Tests the handling of large numbers (multiple full rotations).
     * <p>
     * Validates that inputs significantly larger than the dial size (e.g., 950)
     * are processed correctly using modulo arithmetic.
     * </p>
     */
    @Test
    void testLargeNumberRotations() {
        List<String> input = List.of("R950", "L1050", "R5");
        int result = solvePartOne(input);
        assertEquals(1, result, "Large values should be reduced by modulo arithmetic.");
    }

    /**
     * Test invalid direction input.
     * <p>
     * Tested scenario: an input such as D34, where D is not a valid direction (only L or R is accepted)
     * Expected result: it throws an {@link IllegalArgumentException} with an adequant message.
     * </p>
     */
    @Test
    void testInvalidDirectionInput() {
        List<String> badInput = List.of("R950", "D34", "R5");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> solvePartOne(badInput));
        assertEquals("Unknown direction. Only L or R is accepted.", exception.getMessage());
    }

    /**
     * Test invalid amount value.
     * <p>
     * Tested scenario: an input such as Lxy, where xy is not a number
     * Expected result: it throws an {@link IllegalArgumentException} with an adequant message.
     * </p>
     */
    @Test
    void testInvalidAmountValue() {
        List<String> badInput = List.of("Lxy");
        assertThrows(IllegalArgumentException.class, () -> solvePartOne(badInput));
    }

    /**
     * Test lowercase is refused.
     * <p>
     * Tested scenario: an input such as r52, where r is not a valid direction (only L or R is accepted)
     * Expected result: it throws an {@link IllegalArgumentException} with an adequant message.
     * </p>
     */
    @Test
    void testLowercaseIsRefused() {
        List<String> badInput = List.of("r52");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> solvePartOne(badInput));
        assertEquals("Unknown direction. Only L or R is accepted.", exception.getMessage());
    }

    /**
     * Test invalid length character.
     * <p>
     * Tested scenario: an input who contains maximum 1 character, which is refused
     * Expected result: it throws an {@link IllegalArgumentException} with an adequant message.
     * </p>
     */
    @Test
    void testInvalidLengthCharacter() {
        List<String> badInput = List.of("L");
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> solvePartOne(badInput));
        assertEquals("Length validation: the instruction must contain at least two characters.", exception.getMessage());
    }
}