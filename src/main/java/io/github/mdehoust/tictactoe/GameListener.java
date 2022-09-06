package io.github.mdehoust.tictactoe;

import java.util.Set;

import io.github.mdehoust.tictactoe.Game.Square;

public interface GameListener {

    void squareChosen(Square s);

    void gameWon(Set<Square> winner);

    void gameDraw();

}
