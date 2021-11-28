package it.unibo.oop.lab.advanced;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.FileOutputStream;

public class OutputDrawNumberView implements DrawNumberView {

    private final PrintStream out;

    public OutputDrawNumberView(final PrintStream stream) {
        out = stream;
    }

    public OutputDrawNumberView(final String path) throws FileNotFoundException {
        out = new PrintStream(new FileOutputStream(new File(path)));
    }

    @Override
    public void setObserver(final DrawNumberViewObserver observer) {

    }

    @Override
    public void start() {

    }

    @Override
    public final void numberIncorrect() {
        out.println("Please enter a valid number!");
    }


    @Override
    public final void result(final DrawResult res) {
        out.println(res.getDescription());
    }


    @Override
    public final void displayError(final String errorMessage) {
        out.println("ERROR " + errorMessage);

    }
}
