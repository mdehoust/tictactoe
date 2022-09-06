package io.github.mdehoust.tictactoe;

import static io.github.mdehoust.tictactoe.Game.Square.S1;
import static io.github.mdehoust.tictactoe.Game.Square.S2;
import static io.github.mdehoust.tictactoe.Game.Square.S3;
import static io.github.mdehoust.tictactoe.Game.Square.S4;
import static io.github.mdehoust.tictactoe.Game.Square.S5;
import static io.github.mdehoust.tictactoe.Game.Square.S6;
import static io.github.mdehoust.tictactoe.Game.Square.S7;
import static io.github.mdehoust.tictactoe.Game.Square.S8;
import static io.github.mdehoust.tictactoe.Game.Square.S9;
import static java.util.Arrays.asList;
import static java.util.Collections.synchronizedSet;
import static java.util.EnumSet.allOf;
import static java.util.EnumSet.noneOf;
import static java.util.EnumSet.of;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Game {

    public enum Square {
        S1, S2, S3,
        S4, S5, S6,
        S7, S8, S9;

        public static Set<Square> corners() {
            return EnumSet.of(S1, S3, S7, S9);
        }

        public static Set<Square> sides() {
            return EnumSet.of(S2, S4, S6, S8);
        }
    }

    private static final Set<Set<Square>> WINNERS = new HashSet<>();

    static {
        WINNERS.add(of(S1, S2, S3));
        WINNERS.add(of(S4, S5, S6));
        WINNERS.add(of(S7, S8, S9));
        WINNERS.add(of(S1, S4, S7));
        WINNERS.add(of(S2, S5, S8));
        WINNERS.add(of(S3, S6, S9));
        WINNERS.add(of(S1, S5, S9));
        WINNERS.add(of(S3, S5, S7));
    }

    private final Set<Square> available = synchronizedSet(allOf(Square.class));
    private final Set<Square> x = synchronizedSet(noneOf(Square.class));
    private final Set<Square> o = synchronizedSet(noneOf(Square.class));
    private final Iterator<Set<Square>> turns = asList(x, o, x, o, x, o, x, o, x).iterator();
 
    public Set<Square> availableSquares() {
        return available;
    }

    public Set<Square> xSquares() {
        return x;
    }

    public Set<Square> oSquares() {
        return o;
    }

    public void choose(final Square square) {
        if (available.remove(square)) {
            turns.next().add(square);
        }
    }

    public Set<Square> winner() {
        for (final Set<Square> winner : WINNERS) {
            if (x.containsAll(winner) || o.containsAll(winner)) {
                return winner;
            }
        }

        return noneOf(Square.class);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final Square[][] rows = new Square[][] { 
                { S1, S2, S3 }, 
                { S4, S5, S6 },
                { S7, S8, S9 } };

        for (final Square[] row : rows) {
            for (final Square s : row) {
                if (x.contains(s)) {
                    sb.append("X");
                }
                else if (o.contains(s)) {
                    sb.append("O");
                }
                else {
                    sb.append("-");
                }
                sb.append(" ");
            }
            sb.replace(sb.length() - 1, sb.length(), "\n");
        }
        return sb.toString();
    }

    public static Set<Set<Square>> winners() {
        return WINNERS;
    }
}
