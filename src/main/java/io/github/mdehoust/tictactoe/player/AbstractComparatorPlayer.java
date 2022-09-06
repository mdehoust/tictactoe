package io.github.mdehoust.tictactoe.player;

import static java.util.Collections.sort;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;

import io.github.mdehoust.tictactoe.Game.Square;
import io.github.mdehoust.tictactoe.Player;

public abstract class AbstractComparatorPlayer implements Player {

    @Override
    public final Square choose(final Set<Square> availableSquares) {

        final ArrayList<Square> ordered = new ArrayList<>(availableSquares);
        sort(ordered, comparator());

        return ordered.get(0);
    }

    protected abstract Comparator<Square> comparator();
}
