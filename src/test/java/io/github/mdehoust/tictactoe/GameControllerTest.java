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
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;

public class GameControllerTest {

    @Test
    public void playerOneWins() {
        // Given
        final Player one = new StubPlayer("one", S1, S3, S5, S7);
        final Player two = new StubPlayer("two", S2, S4, S6);
        // x o x
        // o x o
        // x

        // When
        final GameEngine controller = new GameEngine();
        final Optional<Player> winner = controller.play(one, two);

        // Then
        assertThat(winner.get(), is(one));
    }

    @Test
    public void playerTwoWins() {
        // Given
        final Player one = new StubPlayer("one", S2, S4, S6);
        final Player two = new StubPlayer("two", S1, S5, S9);
        // o x _
        // x o x
        // _ _ o

        // When
        final GameEngine controller = new GameEngine();
        final Optional<Player> winner = controller.play(one, two);

        // Then
        assertThat(winner.get(), is(two));
    }

    @Test
    public void tieGame() {
        // Given
        final Player one = new StubPlayer("one", S1, S2, S6, S7, S9);
        final Player two = new StubPlayer("two", S3, S4, S5, S8);
        // x x o
        // o o x
        // x o x

        // When
        final GameEngine controller = new GameEngine();
        final Optional<Player> winner = controller.play(one, two);

        // Then
        assertThat(winner.isPresent(), is(false));
    }
}
