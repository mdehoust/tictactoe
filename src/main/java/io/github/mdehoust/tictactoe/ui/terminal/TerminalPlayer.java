package io.github.mdehoust.tictactoe.ui.terminal;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.Optional;
import java.util.Set;

import io.github.mdehoust.tictactoe.Game.Square;
import io.github.mdehoust.tictactoe.Player;

public class TerminalPlayer implements Player {

    private final Terminal terminal;

    public TerminalPlayer(Terminal terminal) {
        this.terminal = terminal;
    }

    @Override
    public final Square choose(final Set<Square> availableSquares) {

        Optional<Square> chosenSquare = availableSquares.size() == 1
                                      ? availableSquares.stream().findAny()
                                      : Optional.empty();

        while (!chosenSquare.isPresent()) {
            terminal.print("Choose an available square.\n");

            final Square square = Square.valueOf("S" + terminal.readInt());
            if (availableSquares.contains(square)) {
                chosenSquare = Optional.of(square);
            }
            else {
                terminal.print("Try again\n");
            }
        }

        return chosenSquare.get();
    }
}
