package io.github.mdehoust.tictactoe.ui.terminal;

import java.io.PrintStream;
import java.util.Map;
import java.util.Scanner;

import io.github.mdehoust.tictactoe.Game.Square;

public class Terminal {

    private final PrintStream out;
    private final Scanner scanner;

    public Terminal() {
        this.out = System.out;
        this.scanner = new Scanner(System.in);
    }

    public void print(final String message) {
        out.print(message);
    }

    public int readInt() {
        return scanner.nextInt();
    }

    public void renderGrid(Map<Square, String> symbols) {
        print(
            String.format(" %s | %s | %s %n---+---+---%n %s | %s | %s %n---+---+---%n %s | %s | %s%n%n"
                , symbols.getOrDefault(Square.S1, " ")
                , symbols.getOrDefault(Square.S2, " ")
                , symbols.getOrDefault(Square.S3, " ")
                , symbols.getOrDefault(Square.S4, " ")
                , symbols.getOrDefault(Square.S5, " ")
                , symbols.getOrDefault(Square.S6, " ")
                , symbols.getOrDefault(Square.S7, " ")
                , symbols.getOrDefault(Square.S8, " ")
                , symbols.getOrDefault(Square.S9, " ")
            )
        );
    }
}
