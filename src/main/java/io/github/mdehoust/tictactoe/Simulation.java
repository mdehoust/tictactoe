package io.github.mdehoust.tictactoe;

import static java.util.Arrays.asList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import io.github.mdehoust.tictactoe.player.CenterCornerMiddlePlayer;
import io.github.mdehoust.tictactoe.player.ProPlayer;
import io.github.mdehoust.tictactoe.player.RandomPlayer;
import io.github.mdehoust.tictactoe.player.SimplePlayer;
import io.github.mdehoust.tictactoe.player.WinOrSurvivePlayer;

public class Simulation {

    public static void main(final String... args) {

        final GameEngine controller = new GameEngine();

        final List<Class<? extends Player>> playerTypes = asList(
                RandomPlayer.class,
                WinOrSurvivePlayer.class, SimplePlayer.class,
                CenterCornerMiddlePlayer.class, ProPlayer.class);

        final Map<Class<? extends Player>, WinLossRecord> stats = new HashMap<>();

        for (Class<? extends Player> playerOneType : playerTypes) {
            for (Class<? extends Player> playerTwoType : playerTypes) {
                int p1wins = 0;
                int p2wins = 0;
                int ties = 0;

                for (int i = 0; i < 10000; i++) {
                    final Player one = createInstance(playerOneType);
                    final Player two = createInstance(playerTwoType);
                    final Optional<Player> winner = controller.play(one, two);

                    p1wins += winner.map(w -> w.equals(one) ? 1 : 0).orElse(0);
                    p2wins += winner.map(w -> w.equals(two) ? 1 : 0).orElse(0);
                    ties += winner.isPresent() ? 0 : 1;
                }

                stats.putIfAbsent(playerOneType, new WinLossRecord(playerOneType.getSimpleName()));
                stats.putIfAbsent(playerTwoType, new WinLossRecord(playerTwoType.getSimpleName()));
                stats.get(playerOneType).accumulate(p1wins, p2wins, ties);
                stats.get(playerTwoType).accumulate(p2wins, p1wins, ties);

                System.out.printf("%-24s vs %-24s, %5d ties, %5d p1, %5d p2%n",
                        playerOneType.getSimpleName(),
                        playerTwoType.getSimpleName(), ties, p1wins, p2wins);
            }
        }

        stats.values()
             .stream()
             .sorted((a, b) -> b.compareTo(a))
             .forEach(System.out::println);
    }

    private static <T extends Player> T createInstance(Class<T> playerType) {
        try {
            return playerType.newInstance();
        }
        catch (InstantiationException | IllegalAccessException e) {
            throw new SimulationException(e);
        }
    }

    static class WinLossRecord implements Comparable<WinLossRecord> {
        String playerName;
        double winCount;
        double lossCount;
        double tieCount;

        public WinLossRecord(final String playerName) {
            super();
            this.playerName = playerName;
        }

        public void accumulate(final int wins, final int losses, final int ties) {
            this.winCount += wins;
            this.lossCount += losses;
            this.tieCount += ties;
        }

        public double winPercent() {
            final double wins = winCount + tieCount / 2;
            final double losses = lossCount + tieCount / 2;
            return wins / (wins + losses);
        }

        public String toString() {
            return String.format("%-24s  %5d wins, %5d losses, %5d ties,  %5.2f%% wp",
                                  playerName, (int) winCount, (int) lossCount, (int) tieCount, winPercent());
        }

        @Override
        public int compareTo(WinLossRecord other) {
            return Double.compare(this.winPercent(), other.winPercent());
        }
    }
}

class SimulationException extends RuntimeException {
    public SimulationException(Throwable cause) {
        super(cause);
    }
}