package io.github.mdehoust.tictactoe.ui.terminal;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.util.function.Function;
import java.util.stream.Stream;

import io.github.mdehoust.tictactoe.GameEngine;
import io.github.mdehoust.tictactoe.GameListener;
import io.github.mdehoust.tictactoe.Player;
import io.github.mdehoust.tictactoe.Game.Square;
import io.github.mdehoust.tictactoe.player.Players;

public class TerminalGame {

    public static void main(final String... args) {

        final Terminal terminal = new Terminal();
        final GameEngine controller = new GameEngine();

        final TerminalPlayer terminalPlayer = new TerminalPlayer(terminal);
        final Player one = Math.random() < 0.5 ? terminalPlayer : Players.chooseAnyPlayer();
        final Player two = one == terminalPlayer ? Players.chooseAnyPlayer() : terminalPlayer;

        final Function<Player, String> description = p -> p == terminalPlayer ? "You" : p.getClass().getSimpleName();

        GameListener listener = new TerminalGameListener(terminal, one == terminalPlayer ? "X" : "O");

        terminal.print(String.format("X: %s%nO: %s%n", description.apply(one), description.apply(two)));
        terminal.renderGrid(Stream.of(Square.values())
                                            .collect(toMap(identity(), square -> square.name().substring(1))));

        controller.play(one, two, listener);
    }
}
