package io.github.mdehoust.tictactoe.player;

import static java.util.Arrays.asList;

import java.util.EnumSet;

import io.github.mdehoust.tictactoe.Game.Square;

public class Squares {

    public static EnumSet<Square> squares(final Square... squares) {
        return EnumSet.copyOf(asList(squares));
    }
}
