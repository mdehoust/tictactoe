package io.github.mdehoust.tictactoe;

import java.util.Set;

import io.github.mdehoust.tictactoe.Game.Square;

public interface Player {

    Square choose(Set<Square> availableSquares);

}
