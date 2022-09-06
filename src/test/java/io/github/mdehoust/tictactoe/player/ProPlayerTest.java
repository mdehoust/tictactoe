package io.github.mdehoust.tictactoe.player;

import static io.github.mdehoust.tictactoe.Game.Square.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.Test;

import io.github.mdehoust.tictactoe.Game.Square;
import io.github.mdehoust.tictactoe.GameEngine;
import io.github.mdehoust.tictactoe.GameListener;
import io.github.mdehoust.tictactoe.Player;

public class ProPlayerTest {

    @Test
    public void shouldAlwaysPrevailAgainstAnUnwisePlayerTwo() {
        //Given
        final Player unwisePlayer = new Player() {
            final RandomPlayer random = new RandomPlayer();
            public Square choose(final Set<Square> availableSquares) {
                final Set<Square> anythingButTheMiddleSquare = remove(availableSquares, S5);
                return random.choose(availableSquares.size() >= 8 ? anythingButTheMiddleSquare : availableSquares);
            }
            public String toString() {
                return "UnwisePlayer";
            }
        };

        for (int i=0; i < 1000; i++) {
            final GameEngine engine = new GameEngine();
            final GameListener gameDescriber = gameDescriber();

            final Player pro = new ProPlayer();
            Optional<Player> winner = engine.play(pro, unwisePlayer, gameDescriber);

            assertThat(i + " " + gameDescriber.toString(), winner.orElse(null), is(pro));
        }
    }

    @Test
    public void shouldNeverLoseToTheWisePlayerTwo() {
        //Given
        final Player wisePlayer = new Player() {
            final RandomPlayer random = new RandomPlayer();
            public Square choose(final Set<Square> availableSquares) {
                return availableSquares.size() >= 8 ? S5 : random.choose(availableSquares);
            }
            public String toString() {
                return "WisePlayer";
            }
        };

        for (int i=0; i < 1000; i++) {
            final GameEngine engine = new GameEngine();
            final GameListener gameDescriber = gameDescriber();

            final Player pro = new ProPlayer();
            Optional<Player> winner = engine.play(pro, wisePlayer, gameDescriber);

            assertThat(i + " " + gameDescriber.toString(), winner.orElse(pro), is(pro));
        }
    }


    @Test
    public void shouldNeverLoseAsPlayerTwo() {
        for (int i=0; i < 10000; i++) {
            final GameEngine engine = new GameEngine();
            final GameListener gameDescriber = gameDescriber();

            final Player pro = new ProPlayer();
            Optional<Player> winner = engine.play(new RandomPlayer(), pro, gameDescriber);

            assertThat(i + " " + gameDescriber.toString(), winner.orElse(pro), is(pro));
        }
    }

    private GameListener gameDescriber() {
        final GameListener gameDescriber = new GameListener() {

            private List<Square> turns = new ArrayList<>();

            @Override
            public void squareChosen(Square s) {
                turns.add(s);
            }

            @Override
            public void gameWon(final Set<Square> winner) {
            }

            @Override
            public void gameDraw() {
            }
            
            @Override
            public String toString() {
                final String[] marks = new String[9];
                Arrays.fill(marks, 0, 9, " ");

                int i = 1;
                for (Square turn : turns) {
                    marks[turn.ordinal()] = "" + i++;
                }
                return turns.toString() + "\n" + String.format(" %s | %s | %s%n---+---+---%n %s | %s | %s%n---+---+---%n %s | %s | %s%n"
                , marks[0]
                , marks[1]
                , marks[2]
                , marks[3]
                , marks[4]
                , marks[5]
                , marks[6]
                , marks[7]
                , marks[8]
                );
            }
        };
        return gameDescriber;
    }

    private static EnumSet<Square> remove(Set<Square> squares, Square...removed) {
        final EnumSet<Square> remaining = EnumSet.copyOf(squares);
        Stream.of(removed).forEach(remaining::remove);
        return remaining;
    }

}
