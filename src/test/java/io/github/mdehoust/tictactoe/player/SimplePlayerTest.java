package io.github.mdehoust.tictactoe.player;

import static io.github.mdehoust.tictactoe.Game.Square.S4;
import static io.github.mdehoust.tictactoe.Game.Square.S5;
import static io.github.mdehoust.tictactoe.Game.Square.S6;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import io.github.mdehoust.tictactoe.Game.Square;
import io.github.mdehoust.tictactoe.Player;

public class SimplePlayerTest {

    @Test
    public void shouldChooseFirstAvailableSquare() {
        final Player player = new SimplePlayer();
        final Set<Square> availableSquares = new HashSet<>();
        availableSquares.add(S4);
        availableSquares.add(S5);
        availableSquares.add(S6);

        assertThat(player.choose(availableSquares), is(S4));
    }
}
