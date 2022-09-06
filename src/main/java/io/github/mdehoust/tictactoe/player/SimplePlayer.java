package io.github.mdehoust.tictactoe.player;

import java.util.Comparator;

import io.github.mdehoust.tictactoe.Game.Square;

public class SimplePlayer extends AbstractComparatorPlayer {

    @Override
    protected Comparator<Square> comparator() {
        return (o1, o2) -> o1.compareTo(o2);
    }

}
