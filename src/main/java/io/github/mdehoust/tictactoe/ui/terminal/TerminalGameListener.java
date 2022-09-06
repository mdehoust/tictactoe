package io.github.mdehoust.tictactoe.ui.terminal;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

import io.github.mdehoust.tictactoe.Game.Square;
import io.github.mdehoust.tictactoe.GameListener;

public class TerminalGameListener implements GameListener {

    private Map<Square, String> squares = new EnumMap<>(Square.class);
    private String symbol = "";

    private final Terminal terminal;
    private final String renderOnTurn;

    public TerminalGameListener(Terminal terminal, String renderOnTurn) {
        super();
        this.terminal = terminal;
        this.renderOnTurn = renderOnTurn;
    }

    @Override
    public void squareChosen(Square s) {
        symbol = "X".equals(symbol) ? "O" : "X";

        squares.put(s, symbol);

        if (!renderOnTurn.equals(symbol)) {
            terminal.print(String.format("%s chooses square %s%n", symbol, s.name().substring(1)));
            terminal.renderGrid(squares);
        }
    }

    @Override
    public void gameWon(final Set<Square> winner) {
        terminal.renderGrid(squares);
        terminal.print(symbol + " wins!\n");
    }

    @Override
    public void gameDraw() {
        terminal.renderGrid(squares);
        terminal.print("Draw.\n");
    }

}
