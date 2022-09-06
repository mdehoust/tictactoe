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
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CenterCornerMiddlePlayerTest {
    private final CenterCornerMiddlePlayer player = new CenterCornerMiddlePlayer();

    @Test
    public void shouldChooseInOrder() {
        assertThat(player.choose(squares(S1, S2, S3, S4, S5, S6, S7, S8, S9)), is(S5));
        assertThat(player.choose(squares(S1, S2, S3, S4, S6, S7, S8, S9)), is(S1));
        assertThat(player.choose(squares(S2, S3, S4, S6, S7, S8, S9)), is(S3));
        assertThat(player.choose(squares(S2, S4, S6, S7, S8, S9)), is(S7));
        assertThat(player.choose(squares(S2, S4, S6, S8, S9)), is(S9));
        assertThat(player.choose(squares(S2, S4, S6, S8)), is(S8));
        assertThat(player.choose(squares(S2, S4, S6)), is(S4));
        assertThat(player.choose(squares(S2, S6)), is(S6));
        assertThat(player.choose(squares(S2)), is(S2));
    }
}
