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

import java.util.Comparator;
import java.util.List;

import io.github.mdehoust.tictactoe.Game.Square;

public class CenterCornerMiddlePlayer extends AbstractComparatorPlayer {

    private final List<Square> ord = asList(S5, S1, S3, S7, S9, S8, S4, S6, S2);

    @Override
    protected Comparator<Square> comparator() {
        return (a, b) -> ord.indexOf(a) - ord.indexOf(b);
    }

}
