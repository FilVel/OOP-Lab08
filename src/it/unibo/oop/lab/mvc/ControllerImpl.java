package it.unibo.oop.lab.mvc;

import java.util.List;
import java.util.LinkedList;
import java.util.Objects;

public class ControllerImpl implements Controller {

    private String nextString;
    private final List<String> historyOfStrings = new LinkedList<>();

    /**@Override
     * @param nextString
     *          the string the user wants to be printable next
     */
    public void setNextStringToBePrinted(final String nextString) {
        this.nextString = Objects.requireNonNull(nextString);
    }

    /**@Override
     * @return the string that will be printed
     */
    public String getNextStringToBePrinted() {
        return this.nextString;
    }

    /**@Override
     * @return the history of the printed strings
     */
    public List<String> getStringHistory() {
        return this.historyOfStrings;
    }

    /**
     * @Override
     */
    public void printCurrentString() {
        if (this.nextString == null) {
            throw new IllegalStateException("There is no string set");
        }
        historyOfStrings.add(this.nextString);
        System.out.println(this.nextString);
    }

}
