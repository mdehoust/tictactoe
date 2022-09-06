package io.github.mdehoust.tictactoe.player;

import java.util.Comparator;
import java.util.Random;

import io.github.mdehoust.tictactoe.Game.Square;

public class RandomPlayer extends AbstractComparatorPlayer {

    private final Random random;

    public RandomPlayer() {
        this.random = new Random(System.currentTimeMillis());
    }

    @Override
    protected Comparator<Square> comparator() {
        return (o1, o2) -> random.nextBoolean() ? 1 : -1;
    }

}
