package io.github.mdehoust.tictactoe.ui.swing;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.toMap;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.OverlayLayout;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;

import io.github.mdehoust.tictactoe.Game.Square;
import io.github.mdehoust.tictactoe.GameEngine;
import io.github.mdehoust.tictactoe.GameListener;
import io.github.mdehoust.tictactoe.Player;
import io.github.mdehoust.tictactoe.player.Players;

public class TicTacToe {

    private final AtomicInteger windowCounter = new AtomicInteger(0);
    private final BlockingQueue<Square> playerChoiceQueue = new SynchronousQueue<>();
    private final Player buttonPlayer = availableSquares -> waitForValue(playerChoiceQueue::take);

    private final Executor executor = Executors.newSingleThreadExecutor();

    private Consumer<String> playerOneDescriber = description -> {};
    private Consumer<String> playerTwoDescriber = description -> {};
 
    public static void main(final String... args) {
        new TicTacToe().createGameWindow();
    }

    public void createGameWindow() {
        final JFrame frame = new JFrame("Tic Tac Toe " + windowCounter.incrementAndGet());
        frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
 
        JPanel contentPane = new JPanel();
        contentPane.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));
        contentPane.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

        JPanel leftPanel = new JPanel();
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        leftPanel.setLayout(new GridLayout(2, 1));

        JPanel labelPanel = new JPanel();
        labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));

        JLabel p1Description = new JLabel("X:                                         .");
        p1Description.setMaximumSize(new Dimension(240, 80));
        playerOneDescriber = description -> p1Description.setText("X: " + description);

        JLabel p2Description = new JLabel("O:                                         .");
        playerTwoDescriber = description -> p2Description.setText("O: " + description);

        JPanel buttonLeftPanel = new JPanel();
        JButton playButton = new JButton("Play Again");
        playButton.setEnabled(false);
        playButton.addActionListener(e -> createGameWindow());

        labelPanel.add(p1Description);
        labelPanel.add(p2Description);

        buttonLeftPanel.add(playButton);
        leftPanel.add(labelPanel);
        leftPanel.add(buttonLeftPanel);

        contentPane.add(leftPanel);

        JPanel overlayPanel = new JPanel();
        overlayPanel.setLayout(new OverlayLayout(overlayPanel));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 3, 0, 0));

        SlashPanel slashPanel = new SlashPanel();

        overlayPanel.add(slashPanel);
        overlayPanel.add(buttonPanel);

        final Function<Square, JButton> createButton = square -> {
            final JButton button = new JButton();
            button.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 48));
            buttonPanel.add(button);
            return button;
        };

        final Map<Square, JButton> squareButtons = Stream.of(Square.values()).collect(toMap(identity(), createButton));

        contentPane.add(overlayPanel);

        frame.setContentPane(contentPane);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

        executor.execute(() -> startNewGame(squareButtons, playButton, slashPanel));
    }

    private void startNewGame(final Map<Square, JButton> squareButtons, final JButton playButton, SlashPanel slashPanel) {

        squareButtons.entrySet().forEach(entry -> {
            Square square = entry.getKey();
            JButton button = entry.getValue();
            button.addActionListener(e -> ignoreInterrupt(() -> playerChoiceQueue.put(square)));
        });

        final Player one = chooseOpponent();
        final Player two = one == buttonPlayer ? chooseOpponent() : buttonPlayer;

        playerOneDescriber.accept(one == buttonPlayer ? "Human" : one.getClass().getSimpleName());
        playerTwoDescriber.accept(two == buttonPlayer ? "Human" : two.getClass().getSimpleName());

        new GameEngine().play(one, two, new GameListener() {
            String mark = "X";

            @Override
            public void squareChosen(Square s) {
                JButton button = squareButtons.get(s);
                button.setText(mark);
                button.setEnabled(false);
                mark = "X".equals(mark) ? "O" : "X";
            }
        
            @Override
            public void gameWon(final Set<Square> winner) {
                squareButtons.values().forEach(b -> b.setEnabled(false));
                playButton.setEnabled(true);
                slashPanel.slashWinner(winner);
                SwingUtilities.invokeLater(slashPanel::repaint);
            }
        
            @Override
            public void gameDraw() {
                squareButtons.values().forEach(b -> b.setEnabled(false));
                playButton.setEnabled(true);
            }
        
        });
    }

    private Player chooseOpponent() {
        return Math.random() < 0.5 ? buttonPlayer : Players.chooseAnyPlayer();
    }

    private static <T> T waitForValue(final Interruptable<T> action) {
        try {
            return action.attempt();
        } 
        catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SwingGameException(e);
        }
    }

    private static void ignoreInterrupt(final InterruptableVoid action) {
        try {
            action.attempt();
        } 
        catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new SwingGameException(e);
        }
    }
}

interface Interruptable<T> {
    T attempt() throws InterruptedException;
}

interface InterruptableVoid {
    void attempt() throws InterruptedException;
}

class SwingGameException extends RuntimeException {
    SwingGameException(Throwable cause) {
        super(cause);
    }
}

class SlashPanel extends JPanel {

    int x1 = 0;
    int y1 = 0;

    int x2 = 0;
    int y2 = 0;

    SlashPanel () {
        setOpaque(false);
    }

    public void slashWinner(final Set<Square> winner) {
        if (winner.containsAll(EnumSet.of(Square.S1, Square.S3))) {
            x1 = 0;
            y1 = 35;
            x2 = 250;
            y2 = 35;
        }
        if (winner.containsAll(EnumSet.of(Square.S4, Square.S6))) {
            x1 = 0;
            y1 = 100;
            x2 = 250;
            y2 = 100;
        }
        if (winner.containsAll(EnumSet.of(Square.S7, Square.S9))) {
            x1 = 0;
            y1 = 165;
            x2 = 250;
            y2 = 165;
        }

        if (winner.containsAll(EnumSet.of(Square.S1, Square.S7))) {
            x1 = 40;
            y1 = 0;
            x2 = 40;
            y2 = 200;
        }
        if (winner.containsAll(EnumSet.of(Square.S2, Square.S8))) {
            x1 = 125;
            y1 = 0;
            x2 = 125;
            y2 = 200;
        }
        if (winner.containsAll(EnumSet.of(Square.S3, Square.S9))) {
            x1 = 207;
            y1 = 0;
            x2 = 207;
            y2 = 200;
        }

        if (winner.containsAll(EnumSet.of(Square.S1, Square.S9))) {
            x1 = 0;
            y1 = 0;
            x2 = 250;
            y2 = 200;
        }
        if (winner.containsAll(EnumSet.of(Square.S3, Square.S7))) {
            x1 = 250;
            y1 = 0;
            x2 = 0;
            y2 = 200;
        }
    }


    @Override
    public Dimension getPreferredSize() {
        return new Dimension(250,200);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (y2 == 0) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(3));
        g2.setColor(Color.DARK_GRAY);
        g2.drawLine(x1, y1, x2, y2);
    }
}