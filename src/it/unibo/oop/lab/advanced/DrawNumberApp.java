package it.unibo.oop.lab.advanced;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

/**
 */
public final class DrawNumberApp implements DrawNumberViewObserver {

    private final DrawNumber model;
    private final List<DrawNumberView> dNViews;

    /**
     * 
     * @param fileWithConfiguration
     * @param views
     */
    public DrawNumberApp(final String fileWithConfiguration, final DrawNumberView... views) {
        dNViews = Arrays.asList(Arrays.copyOf(views, views.length));
        for (final DrawNumberView dNView : dNViews) {
            dNView.setObserver(this);
            dNView.start();
        }

        final Configuration.NewConfiguration newConfig = new Configuration.NewConfiguration();
        try (var content = new BufferedReader(new InputStreamReader(ClassLoader.getSystemResourceAsStream(fileWithConfiguration)))) {
            for (var configuration = content.readLine(); configuration != null; configuration = content.readLine()) {
                final String[] lineElements = configuration.split(":");
                if (lineElements.length == 2) {
                    final int value = Integer.parseInt(lineElements[1].trim());
                    if (lineElements[0].contains("max")) {
                        newConfig.setMax(value);
                    } else if (lineElements[0].contains("min")) {
                        newConfig.setMin(value);
                    } else if (lineElements[0].contains("attempts")) {
                        newConfig.setAttempts(value);
                    }
                } else {
                    displayError("Please try again! I can not understand");
                }
            }
        } catch (IOException ioE) {
            displayError(ioE.getMessage());
        }
        final Configuration configuration = newConfig.newConfig();
        if (!configuration.isConsistent()) {
            displayError("Inconsistent configuration, the default one shall be used");
            this.model = new DrawNumberImpl(new Configuration.NewConfiguration().newConfig());
        } else {
            this.model = new DrawNumberImpl(configuration);
        }
    }

    private void displayError(final String err) {
        for (final DrawNumberView dNView : dNViews) {
            dNView.displayError(err);
        }
    }

    @Override
    public void resetGame() {
        this.model.reset();
    }

    @Override
    public void quit() {
        System.exit(0);
    }

    @Override
    public void newAttempt(final int n) {
        try {
            final DrawResult result = model.attempt(n);
            for (final DrawNumberView dNView : dNViews) {
                dNView.result(result);
            }
        } catch (IllegalArgumentException e) {
            for (final DrawNumberView dNView: dNViews) {
                dNView.numberIncorrect();
            }
        }
    }


    /**
     * @param args
     *            ignored
     */
    public static void main(final String... args) throws FileNotFoundException {
        new DrawNumberApp("config.yml", new DrawNumberViewImpl(), new DrawNumberViewImpl(), new OutputDrawNumberView(System.out), new OutputDrawNumberView("output.log"));
    }

}
