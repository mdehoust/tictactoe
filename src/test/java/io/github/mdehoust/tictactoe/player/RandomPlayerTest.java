package io.github.mdehoust.tictactoe.player;

import static io.github.mdehoust.tictactoe.Game.Square.S4;
import static io.github.mdehoust.tictactoe.Game.Square.S5;
import static io.github.mdehoust.tictactoe.Game.Square.S6;
import static org.hamcrest.collection.IsIn.isIn;
import static org.junit.Assert.assertThat;

import java.util.LinkedHashSet;
import java.util.Set;

import org.junit.Test;

import io.github.mdehoust.tictactoe.Game.Square;
import io.github.mdehoust.tictactoe.Player;

public class RandomPlayerTest {

    @Test
    public void shouldChooseAnyAvailableSquare() {
        final Player player = new RandomPlayer();
        final Set<Square> availableSquares = new LinkedHashSet<>();
        availableSquares.add(S4);
        availableSquares.add(S5);
        availableSquares.add(S6);

        assertThat(player.choose(availableSquares), isIn(availableSquares));
    }
}
