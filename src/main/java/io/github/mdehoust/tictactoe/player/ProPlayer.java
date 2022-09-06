package io.github.mdehoust.tictactoe.player;

import static io.github.mdehoust.tictactoe.Game.Square.S1;
import static io.github.mdehoust.tictactoe.Game.Square.S2;
import static io.github.mdehoust.tictactoe.Game.Square.S3;
import static io.github.mdehoust.tictactoe.Game.Square.S4;
import static io.github.mdehoust.tictactoe.Game.Square.S5;
import static io.github.mdehoust.tictactoe.Game.Square.S6;
import static io.github.mdehoust.tictactoe.Game.Square.S7;
import static io.github.mdehoust.tictactoe.Game.Square.S8;
import static io.github.mdehoust.tictactoe.Game.Square.S9;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.EnumSet.complementOf;
import static java.util.EnumSet.copyOf;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

import io.github.mdehoust.tictactoe.Game.Square;
import io.github.mdehoust.tictactoe.Player;

public class ProPlayer implements Player {

    private ProPlayerStrategy asPlayerOne = new PlayerOneStrategy();
    private ProPlayerStrategy asPlayerTwo = new PlayerTwoStrategy();

    private List<Square> sequence = new ArrayList<>();

    @Override
    public Square choose(Set<Square> availableSquares) {
        
        otherPlayerChoice(availableSquares).ifPresent(sequence::add);
        final Square choice = applyChoiceStrategy(availableSquares);

        sequence.add(choice);

        return choice;
    }

    private Square applyChoiceStrategy(Set<Square> availableSquares) {
        final ProPlayerStrategy strategy = availableSquares.size() % 2 == 1
                                         ? asPlayerOne
                                         : asPlayerTwo;
        return strategy.choose(availableSquares, sequence);
    }

    private Optional<Square> otherPlayerChoice(Set<Square> availableSquares) {
        final EnumSet<Square> otherChoice = complementOf(copyOf(availableSquares));
        otherChoice.removeAll(sequence);
        return otherChoice.stream().findAny();
    }

}

interface ProPlayerStrategy {
    public static Comparator<Square> RANDOM_ORDER = (a, b) -> Math.random() < 0.5 ? -1 : 1;

    Square choose(Set<Square> availableSquares, List<Square> sequenceOfChoices);
}


class PlayerOneStrategy implements ProPlayerStrategy {
    private static final Map<List<Square>, Set<Square>> NEXT_SQUARE = new HashMap<>();

    static {
        NEXT_SQUARE.put(emptyList(), EnumSet.of(S1, S3, S7, S9));

        NEXT_SQUARE.put(asList(S1, S2), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S2, S7, S3), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S2, S7, S5), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S2, S7, S6), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S2, S7, S8), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S2, S7, S9), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S2, S7, S4), EnumSet.of(S9));
        NEXT_SQUARE.put(asList(S1, S2, S7, S4, S9, S5), EnumSet.of(S8));
        NEXT_SQUARE.put(asList(S1, S2, S7, S4, S9, S8), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S2, S7, S4, S9, S3), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S2, S7, S4, S9, S6), EnumSet.of(S5));

        NEXT_SQUARE.put(asList(S1, S3), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S3, S7, S2), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S3, S7, S5), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S3, S7, S6), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S3, S7, S8), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S3, S7, S9), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S3, S7, S4), EnumSet.of(S9));
        NEXT_SQUARE.put(asList(S1, S3, S7, S4, S9, S5), EnumSet.of(S8));
        NEXT_SQUARE.put(asList(S1, S3, S7, S4, S9, S6), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S3, S7, S4, S9, S8), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S3, S7, S4, S9, S2), EnumSet.of(S5));

        NEXT_SQUARE.put(asList(S1, S4), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S4, S3, S2), EnumSet.of(S9));
        NEXT_SQUARE.put(asList(S1, S4, S3, S5), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S4, S3, S6), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S4, S3, S7), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S4, S3, S8), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S4, S3, S9), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S4, S3, S2, S9, S5), EnumSet.of(S6));
        NEXT_SQUARE.put(asList(S1, S4, S3, S2, S9, S6), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S4, S3, S2, S9, S7), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S4, S3, S2, S9, S8), EnumSet.of(S5));

        NEXT_SQUARE.put(asList(S1, S5), EnumSet.of(S9));
        NEXT_SQUARE.put(asList(S1, S5, S9, S3), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S5, S9, S3, S7, S4), EnumSet.of(S8));
        NEXT_SQUARE.put(asList(S1, S5, S9, S3, S7, S8), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S5, S9, S3, S7, S2), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S5, S9, S3, S7, S6), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S5, S9, S7), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S5, S9, S7, S3, S2), EnumSet.of(S6));
        NEXT_SQUARE.put(asList(S1, S5, S9, S7, S3, S4), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S5, S9, S7, S3, S8), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S5, S9, S7, S3, S6), EnumSet.of(S2));
     
        // Draws
        NEXT_SQUARE.put(asList(S1, S5, S9, S2), EnumSet.of(S8));
        NEXT_SQUARE.put(asList(S1, S5, S9, S2, S8, S7), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S5, S9, S2, S8, S7, S3, S6), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S5, S9, S4), EnumSet.of(S6));
        NEXT_SQUARE.put(asList(S1, S5, S9, S4, S6, S3), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S5, S9, S4, S6, S3, S7, S8), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S5, S9, S6), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S5, S9, S6, S4, S7), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S5, S9, S6, S4, S7, S3, S2), EnumSet.of(S8));
        NEXT_SQUARE.put(asList(S1, S5, S9, S8), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S5, S9, S8, S2, S3), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S5, S9, S8, S2, S3, S7, S4), EnumSet.of(S6));

        // P2 mistakes
        NEXT_SQUARE.put(asList(S1, S5, S9, S3, S7, S4), EnumSet.of(S8));
        NEXT_SQUARE.put(asList(S1, S5, S9, S3, S7, S2), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S5, S9, S3, S7, S6), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S5, S9, S3, S7, S8), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S5, S9, S6, S4, S2), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S5, S9, S6, S4, S3), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S5, S9, S6, S4, S8), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S5, S9, S4, S6, S2), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S5, S9, S4, S6, S7), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S5, S9, S4, S6, S8), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S5, S9, S2, S8, S3), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S5, S9, S2, S8, S4), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S5, S9, S2, S8, S6), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S5, S9, S8, S2, S4), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S5, S9, S8, S2, S6), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S5, S9, S8, S2, S7), EnumSet.of(S3));

        // x o x
        // - o -
        // o x x
        NEXT_SQUARE.put(asList(S1, S5, S9, S2, S8, S7, S3, S4), EnumSet.of(S6));
        // x - o
        // o o x
        // x - x
        NEXT_SQUARE.put(asList(S1, S5, S9, S4, S6, S3, S7, S2), EnumSet.of(S8));
        // x - x
        // x o o
        // o - x
        NEXT_SQUARE.put(asList(S1, S5, S9, S6, S4, S7, S3, S8), EnumSet.of(S2));
        // x x o
        // - o -
        // x o x
        NEXT_SQUARE.put(asList(S1, S5, S9, S8, S2, S3, S7, S6), EnumSet.of(S4));


        NEXT_SQUARE.put(asList(S1, S6), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S6, S3, S4), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S6, S3, S5), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S6, S3, S7), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S6, S3, S8), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S6, S3, S9), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S6, S3, S2), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S6, S3, S2, S7, S4), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S6, S3, S2, S7, S5), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S6, S3, S2, S7, S8), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S6, S3, S2, S7, S9), EnumSet.of(S4));

        NEXT_SQUARE.put(asList(S1, S7), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S7, S3, S2), EnumSet.of(S9));
        NEXT_SQUARE.put(asList(S1, S7, S3, S4), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S7, S3, S5), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S7, S3, S6), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S7, S3, S8), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S7, S3, S9), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S7, S3, S2, S9, S5), EnumSet.of(S6));
        NEXT_SQUARE.put(asList(S1, S7, S3, S2, S9, S6), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S7, S3, S2, S9, S4), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S7, S3, S2, S9, S8), EnumSet.of(S5));

        NEXT_SQUARE.put(asList(S1, S8), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S8, S7, S2), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S8, S7, S3), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S8, S7, S5), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S8, S7, S6), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S8, S7, S9), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S8, S7, S4), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S8, S7, S4, S3, S2), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S8, S7, S4, S3, S5), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S8, S7, S4, S3, S6), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S8, S7, S4, S3, S9), EnumSet.of(S2));

        NEXT_SQUARE.put(asList(S1, S9), EnumSet.of(S7));
        NEXT_SQUARE.put(asList(S1, S9, S7, S2), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S9, S7, S3), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S9, S7, S5), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S9, S7, S6), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S9, S7, S8), EnumSet.of(S4));
        NEXT_SQUARE.put(asList(S1, S9, S7, S4), EnumSet.of(S3));
        NEXT_SQUARE.put(asList(S1, S9, S7, S4, S3, S2), EnumSet.of(S5));
        NEXT_SQUARE.put(asList(S1, S9, S7, S4, S3, S5), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S9, S7, S4, S3, S6), EnumSet.of(S2));
        NEXT_SQUARE.put(asList(S1, S9, S7, S4, S3, S8), EnumSet.of(S2));

        PlayerTwoStrategy.addSymmetryMappings(NEXT_SQUARE);
    }

    @Override
    public Square choose(Set<Square> availableSquares, List<Square> sequenceOfChoices) {
        return NEXT_SQUARE.get(sequenceOfChoices)
                                     .stream()
                                     .sorted(RANDOM_ORDER)
                                     .findAny()
                                     .orElseThrow(IllegalStateException::new);
    }
}

class PlayerTwoStrategy implements ProPlayerStrategy {
    final List<Square> mySquares = new ArrayList<>();

    @Override
    public Square choose(Set<Square> availableSquares, List<Square> sequenceOfChoices) {
        final Optional<Square> winner = winIfPossible(availableSquares);
        winner.ifPresent(mySquares::add);
        if (winner.isPresent()) {
            return winner.get();
        }

        final Optional<Square> blocker = blockIfNecessary(availableSquares);
        blocker.ifPresent(mySquares::add);
        if (blocker.isPresent()) {
            return blocker.get();
        }

        // Always defend from the center, if possible
        if (mySquares.isEmpty() && availableSquares.contains(S5)) {
            mySquares.add(S5);
            return S5;
        }

        // Opponent started in the center; there are no possible wins
        if (mySquares.isEmpty()) {
            final Square choice = Square.corners()
                                        .stream()
                                        .sorted(RANDOM_ORDER)
                                        .filter(availableSquares::contains)
                                        .findAny()
                                        .orElseThrow(IllegalStateException::new);
            mySquares.add(choice);
            return choice;
        }

        if (availableSquares.size() <= 2) {
            final Square choice = availableSquares.iterator().next();
            mySquares.add(choice);
            return choice;
        }

        // Possible non-winner, non-blocker scenarios after 3 turns

        // Corner

        // 1 - ~   1 - ~   1 ~ ~ 
        // ~ 2 3   - 2 -   - 2 -
        // ~ - ~   ~ - 3   - 3 -
        final Map<List<Square>, Set<Square>> shapes = new HashMap<>();
        shapes.put(asList(S1, S5, S6), EnumSet.of(S2, S8));
        shapes.put(asList(S1, S5, S9), EnumSet.of(S2, S4, S6, S8));
        shapes.put(asList(S1, S5, S8), EnumSet.of(S4, S6, S7, S9));

        // x o -
        // - o x
        // - x -
        shapes.put(asList(S1, S5, S6, S2, S8), EnumSet.of(S7));

        // ~ x ~
        // - o -
        // x o x
        shapes.put(asList(S9, S5, S7, S8, S2), EnumSet.of(S2, S6));
        
        // o x -
        // - x -
        // - o x
        shapes.put(asList(S5, S1, S2, S8, S9), EnumSet.of(S6));

        // - - x
        // x o -
        // o x -
        shapes.put(asList(S3, S5, S8, S7, S4), EnumSet.of(S1));

        // Side

        // - 3 -   - - 3   - - - 
        // 1 2 ~   1 2 ~   1 2 3
        // - ~ ~   - - ~   - - -
        shapes.put(asList(S4, S5, S2), EnumSet.of(S1, S3, S7));
        shapes.put(asList(S4, S5, S3), EnumSet.of(S1, S2, S7, S8));
        shapes.put(asList(S4, S5, S6), EnumSet.of(S1, S3, S7, S9));

        // x o -
        // - o x
        // - x -
        shapes.put(asList(S6, S5, S1, S2, S8), EnumSet.of(S7));
        
        // - - x
        // x o -
        // o x -
        shapes.put(asList(S8, S5, S3, S7, S4), EnumSet.of(S1));

        // - - x
        // x o -
        // o x -
        shapes.put(asList(S8, S5, S4, S7, S3), EnumSet.of(S1));

        // Center

        // 2 ~ - 
        // ~ 1 ~ 
        // - ~ 3 
        shapes.put(asList(S5, S1, S9), EnumSet.of(S3, S7));

        // Avoid symmetry trap
        addSymmetryMappings(shapes);

        final Set<Square> options = shapes.get(sequenceOfChoices);

        Objects.requireNonNull(options, "No options found for " + sequenceOfChoices);

        final Optional<Square> choice = options.stream()
                                               .filter(availableSquares::contains)
                                               .findAny();
        choice.ifPresent(mySquares::add);
        return choice.orElseThrow(IllegalStateException::new);
    }

    public static void addSymmetryMappings(Map<List<Square>, Set<Square>> shapes) {

        //   1 2 3   7 4 1   9 8 7   3 6 9
        //   4 5 6   8 5 2   6 5 4   2 5 8
        //   7 8 9   9 6 3   3 2 1   1 4 7
        //
        //   7 8 9   9 6 3   3 2 1   1 4 7
        //   4 5 6   8 5 2   6 5 4   2 5 8
        //   1 2 3   7 4 1   9 8 7   3 6 9

        final EnumMap<Square, Square> m1 = new EnumMap<>(Square.class);
        m1.put(S1, S7);
        m1.put(S2, S4);
        m1.put(S3, S1);
        m1.put(S4, S8);
        m1.put(S5, S5);
        m1.put(S6, S2);
        m1.put(S7, S9);
        m1.put(S8, S6);
        m1.put(S9, S3);

        final EnumMap<Square, Square> m2 = new EnumMap<>(Square.class);
        m2.put(S1, S9);
        m2.put(S2, S8);
        m2.put(S3, S7);
        m2.put(S4, S6);
        m2.put(S5, S5);
        m2.put(S6, S4);
        m2.put(S7, S3);
        m2.put(S8, S2);
        m2.put(S9, S1);

        final EnumMap<Square, Square> m3 = new EnumMap<>(Square.class);
        m3.put(S1, S3);
        m3.put(S2, S6);
        m3.put(S3, S9);
        m3.put(S4, S2);
        m3.put(S5, S5);
        m3.put(S6, S8);
        m3.put(S7, S1);
        m3.put(S8, S4);
        m3.put(S9, S7);

        final EnumMap<Square, Square> m4 = new EnumMap<>(Square.class);
        m4.put(S1, S7);
        m4.put(S2, S8);
        m4.put(S3, S9);
        m4.put(S4, S4);
        m4.put(S5, S5);
        m4.put(S6, S6);
        m4.put(S7, S1);
        m4.put(S8, S2);
        m4.put(S9, S3);

        final EnumMap<Square, Square> m5 = new EnumMap<>(Square.class);
        m5.put(S1, S9);
        m5.put(S2, S6);
        m5.put(S3, S3);
        m5.put(S4, S8);
        m5.put(S5, S5);
        m5.put(S6, S2);
        m5.put(S7, S7);
        m5.put(S8, S4);
        m5.put(S9, S1);

        final EnumMap<Square, Square> m6 = new EnumMap<>(Square.class);
        m6.put(S1, S3);
        m6.put(S2, S2);
        m6.put(S3, S1);
        m6.put(S4, S6);
        m6.put(S5, S5);
        m6.put(S6, S4);
        m6.put(S7, S9);
        m6.put(S8, S8);
        m6.put(S9, S7);

        final EnumMap<Square, Square> m7 = new EnumMap<>(Square.class);
        m7.put(S1, S1);
        m7.put(S2, S4);
        m7.put(S3, S7);
        m7.put(S4, S2);
        m7.put(S5, S5);
        m7.put(S6, S8);
        m7.put(S7, S3);
        m7.put(S8, S6);
        m7.put(S9, S9);

        final Map<List<Square>, Set<Square>> mappings = new HashMap<>();
        for (EnumMap<Square, Square> symmetry : asList(m1, m2, m3, m4, m5, m6, m7)) {
            for (Entry<List<Square>,Set<Square>> entry : shapes.entrySet()) {
                final List<Square> sequence = entry.getKey().stream().map(symmetry::get).collect(toList());
                final Set<Square> options = entry.getValue().stream().map(symmetry::get).collect(toSet());
                mappings.put(sequence, options);
            }
        }

        shapes.putAll(mappings);
    }

    private Optional<Square> blockIfNecessary(Set<Square> availableSquares) {
        final Set<Square> opponentSquares = EnumSet.allOf(Square.class);
        opponentSquares.removeAll(mySquares);
        opponentSquares.removeAll(availableSquares);
        return findWinner(opponentSquares, availableSquares);
    }

    private Optional<Square> winIfPossible(Set<Square> availableSquares) {
        return findWinner(mySquares, availableSquares);
    }

    private Optional<Square> findWinner(final Collection<Square> currentSquares, final Set<Square> availableSquares) {
        if (currentSquares.isEmpty()) {
            return Optional.empty();
        }
        for (Square availableSquare : availableSquares) {
            final Set<Square> squares = EnumSet.copyOf(currentSquares);
            squares.add(availableSquare);
            if (containsWinner(squares)) {
                return Optional.of(availableSquare);
            }
        }
        return Optional.empty();
    }

    private boolean containsWinner(final Set<Square> squares) {
        return squares.containsAll(asList(S1, S2, S3))
        || squares.containsAll(asList(S4, S5, S6))
        || squares.containsAll(asList(S7, S8, S9))
        || squares.containsAll(asList(S1, S4, S7))
        || squares.containsAll(asList(S2, S5, S8))
        || squares.containsAll(asList(S3, S6, S9))
        || squares.containsAll(asList(S1, S5, S9))
        || squares.containsAll(asList(S3, S5, S7))
        ;
    }
}