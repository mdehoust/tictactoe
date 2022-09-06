package io.github.mdehoust.tictactoe;

import java.util.Optional;
import java.util.Set;

import io.github.mdehoust.tictactoe.Game.Square;

public class GameEngine {

    public Optional<Player> play(final Player one, final Player two, final GameListener listener) {
        final Game game = new Game();

        Player p = one;
        while (!game.availableSquares().isEmpty() && game.winner().isEmpty()) {

            final Set<Square> a = game.availableSquares();
            final Square s = a.size() > 1 ? p.choose(a) : a.iterator().next();
            game.choose(s);

            listener.squareChosen(s);

            if (!game.winner().isEmpty()) {
                listener.gameWon(game.winner());
                return Optional.of(p);
            }

            p = (p == one ? two : one);
        }

        listener.gameDraw();
        return Optional.empty();
    }

    public Optional<Player> play(final Player one, final Player two) {
        return play(one, two, new NullGameListener());
    }

    private final class NullGameListener implements GameListener {
        @Override
        public void squareChosen(Square s) {
            // Empty implementation
        }

        @Override
        public void gameWon(Set<Square> winner) {
            // Empty implementation
        }

        @Override
        public void gameDraw() {
            // Empty implementation
        }
    }
}
