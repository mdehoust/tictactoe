package io.github.mdehoust.tictactoe;

import java.util.stream.Stream;

import io.github.mdehoust.tictactoe.ui.swing.TicTacToe;
import io.github.mdehoust.tictactoe.ui.terminal.TerminalGame;

public class Main {

    private static final Launcher DEFAULT_LAUNCHER = TicTacToe::main;

    public static void main(String...args) {
        Launcher launcher = Stream.of(args)
                                  .map(Main::launcher)
                                  .findFirst()
                                  .orElse(Main::help);

        launcher.accept(args);
    }

    private static void help(String...args) {
        System.out.println("Specify one of term, gui, or stat.");
        DEFAULT_LAUNCHER.accept(args);
    }

    private static Launcher launcher(final String name) {
        if (name.startsWith("term")) {
            return TerminalGame::main;
        }
        if (name.startsWith("gui")) {
            return TicTacToe::main;
        }
        if (name.startsWith("stat")) {
            return Simulation::main;
        }
        return null;
    }
}

interface Launcher {
    void accept(String...args);
}