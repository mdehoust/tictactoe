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
import static io.github.mdehoust.tictactoe.player.Squares.squares;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.collection.IsIn.isIn;
import static org.hamcrest.core.DescribedAs.describedAs;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.hamcrest.Matcher;
import org.junit.Before;
import org.junit.Test;

import io.github.mdehoust.tictactoe.Game.Square;

public class WinOrSurvivePlayerTest {

    private static final Set<Square> CORNERS = squares(S1, S3, S7, S9);
    private WinOrSurvivePlayer player;
    private Set<Square> available;
    private Square chosen;

    @Before
    public void setUp() {
        player = new WinOrSurvivePlayer();
        available = squares(S1, S2, S3, S4, S5, S6, S7, S8, S9);
    }

    @Test
    public void shouldChooseAnAvailableSquare() {
        final int squareCount = available.size();
        final Set<Square> chosen = new HashSet<>();
        for (int i = 1; i <= squareCount; i++) {
            chosen.add(choose());
            assertThat(chosen, hasSize(i));
            assertThat(available, hasSize(squareCount - i));
        }
    }

    @Test
    public void shouldChooseACorner() {
        assertThat(choose(), isIn(CORNERS));
    }

    @Test
    public void shouldChooseAnotherCorner() {
        final Square first = choose();
        final Square second = choose();
        assertThat(second, isIn(CORNERS));
        assertThat(second, is(not(first)));
    }

    @Test
    public void shouldChooseTheWinner() {
        final Square[][] scenarios = {
                { S1, S2, S3 }, { S1, S3, S2 }, { S2, S3, S1 },
                { S4, S5, S6 }, { S4, S6, S5 }, { S5, S6, S4 },
                { S7, S8, S9 }, { S7, S9, S8 }, { S8, S9, S7 },

                { S1, S4, S7 }, { S1, S7, S4 }, { S4, S7, S1 },
                { S2, S5, S8 }, { S2, S8, S5 }, { S5, S8, S2 },
                { S3, S6, S9 }, { S3, S9, S6 }, { S6, S9, S3 },

                { S1, S5, S9 }, { S1, S9, S5 }, { S5, S9, S1 },
                { S3, S5, S7 }, { S3, S7, S5 }, { S5, S7, S3 },
        };

        for (final Square[] squares : scenarios) {
            setUp();
            given(squares[0]);
            given(squares[1]);
            when(choose());
            then(describedAs("Given %0 and %1, expect %2", is(squares[2]),
                    squares[0], squares[1], squares[2]));
        }
    }

    @Test
    public void shouldChooseTheBlocker() {
        final Square[][] scenarios = {
                { S1, S2, S3 }, { S1, S3, S2 }, { S2, S3, S1 },
                { S4, S5, S6 }, { S4, S6, S5 }, { S5, S6, S4 },
                { S7, S8, S9 }, { S7, S9, S8 }, { S8, S9, S7 },

                { S1, S4, S7 }, { S1, S7, S4 }, { S4, S7, S1 },
                { S2, S5, S8 }, { S2, S8, S5 }, { S5, S8, S2 },
                { S3, S6, S9 }, { S3, S9, S6 }, { S6, S9, S3 },

                { S1, S5, S9 }, { S1, S9, S5 }, { S5, S9, S1 },
                { S3, S5, S7 }, { S3, S7, S5 }, { S5, S7, S3 },
        };

        for (final Square[] squares : scenarios) {
            setUp();
            available.remove(squares[0]);
            available.remove(squares[1]);
            when(choose());
            then(describedAs("Given %0 and %1, expect %2", is(squares[2]),
                    squares[0], squares[1], squares[2]));
        }
    }

    private void then(final Matcher<Square> matcher) {
        assertThat(chosen, matcher);
    }

    private void when(final Square chosen) {
        this.chosen = chosen;
    }

    private void given(final Square square) {
        player.addChosen(square);
        available.remove(square);
    }

    private Square choose() {
        final Square chosenSquare = player.choose(available);
        available.remove(chosenSquare);
        return chosenSquare;
    }
}
