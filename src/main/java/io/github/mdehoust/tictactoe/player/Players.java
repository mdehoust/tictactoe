package io.github.mdehoust.tictactoe.player;

import java.util.stream.Stream;

import io.github.mdehoust.tictactoe.Player;

public class Players {
    
    public static Player chooseAnyPlayer() {
        return Stream.of(new ProPlayer(), new WinOrSurvivePlayer(), new CenterCornerMiddlePlayer(), new SimplePlayer(), new RandomPlayer())
                     .sorted((a, b) -> Math.random() < 0.5 ? -1 : 1)
                     .findFirst()
                     .orElse(new RandomPlayer());

    }
}
