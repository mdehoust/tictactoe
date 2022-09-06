package io.github.mdehoust.tictactoe.player;

import static io.github.mdehoust.tictactoe.Game.winners;
import static io.github.mdehoust.tictactoe.Game.Square.S1;
import static io.github.mdehoust.tictactoe.Game.Square.S3;
import static io.github.mdehoust.tictactoe.Game.Square.S7;
import static io.github.mdehoust.tictactoe.Game.Square.S9;
import static java.util.stream.Collectors.toSet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import io.github.mdehoust.tictactoe.Game.Square;
import io.github.mdehoust.tictactoe.Player;

public class WinOrSurvivePlayer implements Player {

    private final Set<Square> chosen = new HashSet<>();

    @Override
    public Square choose(final Set<Square> availableSquares) {

        final List<Square> choices = new ArrayList<>();

        choices.addAll(findWinners(availableSquares));

        if (choices.isEmpty()) {
            choices.addAll(findOponentWinners(availableSquares));
        }

        if (choices.isEmpty()) {
            choices.addAll(findAvailableCorners(availableSquares));
        }

        if (choices.isEmpty()) {
            choices.addAll(availableSquares);
        }

        final Square square = choices.iterator().next();
        chosen.add(square);
        return square;
    }

    private Set<Square> findAvailableCorners(final Set<Square> availableSquares) {
        return intersect(availableSquares, asSet(S1, S3, S7, S9));
    }

    private Set<Square> findOponentWinners(final Set<Square> availableSquares) {
        final Set<Square> mustPlay = new HashSet<>();
        for (final Set<Square> winner : winners()) {
            final Set<Square> intersect = intersect(availableSquares, winner);
            if (intersect.size() == 1 && intersect(chosen, winner).isEmpty()) {
                mustPlay.addAll(intersect);
            }
        }
        return mustPlay;
    }

    private Set<Square> findWinners(final Set<Square> availableSquares) {
        final Set<Square> potentialWinners = new HashSet<>();
        for (final Set<Square> winner : winners()) {
            final Set<Square> intersect = intersect(chosen, winner);
            if (intersect.size() == 2) {
                potentialWinners.addAll(difference(winner, intersect));
            }
        }

        return intersect(availableSquares, potentialWinners);
    }

    protected void addChosen(final Square s) {
        chosen.add(s);
    }

    private static Set<Square> asSet(final Square... squares) {
        return Stream.of(squares).collect(toSet());
    }

    private static Set<Square> intersect(final Set<Square> a,
            final Set<Square> b) {
        final Set<Square> intersection = new HashSet<>(a);
        intersection.retainAll(b);
        return intersection;
    }

    private static Set<Square> difference(final Set<Square> a,
            final Set<Square> b) {
        final Set<Square> diff = new HashSet<>(a);
        diff.removeAll(b);
        return diff;
    }
}
