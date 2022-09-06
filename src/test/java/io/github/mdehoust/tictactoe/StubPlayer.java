package io.github.mdehoust.tictactoe;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.IsCollectionContaining.hasItem;
import static org.junit.Assert.assertThat;

import java.util.Set;

import io.github.mdehoust.tictactoe.Game.Square;

public class StubPlayer implements Player {

    private final String name;
    private final Square[] choices;
    private int choiceIndex;

    public StubPlayer(final String name, final Square... choices) {
        this.name = name;
        this.choices = choices;
        this.choiceIndex = 0;
    }

    @Override
    public String toString() {
        return String.format("StubPlayer[%s]", name);
    }

    @Override
    public Square choose(final Set<Square> availableSquares) {
        assertThat(choices.length, greaterThan(choiceIndex));
        final Square square = choices[choiceIndex++];
        assertThat(availableSquares, hasItem(square));
        return square;
    }
}
