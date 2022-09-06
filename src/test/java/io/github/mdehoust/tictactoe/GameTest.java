package io.github.mdehoust.tictactoe;

import static io.github.mdehoust.tictactoe.Game.Square.S1;
import static io.github.mdehoust.tictactoe.Game.Square.S2;
import static io.github.mdehoust.tictactoe.Game.Square.S3;
import static io.github.mdehoust.tictactoe.Game.Square.S4;
import static io.github.mdehoust.tictactoe.Game.Square.S5;
import static io.github.mdehoust.tictactoe.Game.Square.S6;
import static io.github.mdehoust.tictactoe.Game.Square.S7;
import static io.github.mdehoust.tictactoe.Game.Square.S8;
import static io.github.mdehoust.tictactoe.Game.Square.S9;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsEmptyCollection.empty;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

import io.github.mdehoust.tictactoe.Game.Square;

public class GameTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testAllSquaresAvailable() {
        assertThat(game.availableSquares(), contains(S1, S2, S3, S4, S5, S6, S7, S8, S9));
    }

    @Test
    public void testChooseAll() {
        for (final Square square : Square.values()) {
            game.choose(square);
        }
        assertThat(game.availableSquares(), empty());
    }

    @Test
    public void testChooseX() {
        for (final Square square : Square.values()) {
            game.choose(square);
        }
        assertThat(game.xSquares(), contains(S1, S3, S5, S7, S9));
    }

    @Test
    public void testChooseO() {
        for (final Square square : Square.values()) {
            game.choose(square);
        }
        assertThat(game.oSquares(), contains(S2, S4, S6, S8));
    }

    @Test
    public void testWinner1() {
        //X              //O
        game.choose(S1); game.choose(S9);
        game.choose(S2); game.choose(S8);
        game.choose(S3);

        assertThat(game.winner(), contains(S1, S2, S3));
    }

    @Test
    public void testWinner2() {
        //X              //O
        game.choose(S4); game.choose(S9);
        game.choose(S5); game.choose(S8);
        game.choose(S6);

        assertThat(game.winner(), contains(S4, S5, S6));
    }

    @Test
    public void testWinner3() {
        //X              //O
        game.choose(S7); game.choose(S1);
        game.choose(S8); game.choose(S2);
        game.choose(S9);

        assertThat(game.winner(), contains(S7, S8, S9));
    }

    @Test
    public void testWinner4() {
        //X              //O
        game.choose(S1); game.choose(S9);
        game.choose(S4); game.choose(S8);
        game.choose(S7);

        assertThat(game.winner(), contains(S1, S4, S7));
    }

    @Test
    public void testWinner5() {
        //X              //O
        game.choose(S2); game.choose(S9);
        game.choose(S5); game.choose(S1);
        game.choose(S8);

        assertThat(game.winner(), contains(S2, S5, S8));
    }

    @Test
    public void testWinner6() {
        //X              //O
        game.choose(S3); game.choose(S2);
        game.choose(S6); game.choose(S1);
        game.choose(S9);

        assertThat(game.winner(), contains(S3, S6, S9));
    }

    @Test
    public void testWinner7() {
        //X              //O
        game.choose(S1); game.choose(S2);
        game.choose(S5); game.choose(S3);
        game.choose(S9);

        assertThat(game.winner(), contains(S1, S5, S9));
    }

    @Test
    public void testWinner8() {
        //X              //O
        game.choose(S3); game.choose(S2);
        game.choose(S5); game.choose(S1);
        game.choose(S7);

        assertThat(game.winner(), contains(S3, S5, S7));
    }

    @Test
    public void testNoWinner() {
        //X              //O
        game.choose(S1); game.choose(S4);
        game.choose(S3); game.choose(S2);
        game.choose(S5); game.choose(S9);
        game.choose(S8); game.choose(S7);
        game.choose(S6);

        assertThat(game.winner(), empty());
    }

    @Test
    public void testToString() {
        //X              //O
        game.choose(S1); game.choose(S4);
        game.choose(S3); game.choose(S2);
        game.choose(S5); game.choose(S9);
        game.choose(S8); game.choose(S7);
        game.choose(S6);

        assertThat(game.toString(), is("X O X\nO X X\nO X O\n"));
    }
}
