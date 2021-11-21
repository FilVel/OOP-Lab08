package it.unibo.oop.lab.mvc;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUI {

    private final JFrame frame = new JFrame();
    private final Controller controller;

    /*
     * Once the Controller is done, implement this class in such a way that:
     * 
     * 1) I has a main method that starts the graphical application V
     * 
     * 2) In its constructor, sets up the whole view V
     * 
     * 3) The graphical interface consists of a JTextField in the upper part of the frame, 
     * a JTextArea in the center and two buttons below it: "Print", and "Show history". 
     * SUGGESTION: Use a JPanel with BorderLayout V
     * 
     * 4) By default, if the graphical interface is closed the program must exit
     * (call setDefaultCloseOperation) V
     * 
     * 5) The behavior of the program is that, if "Print" is pressed, the
     * controller is asked to show the string contained in the text field on standard output. 
     * If "show history" is pressed instead, the GUI must show all the prints that
     * have been done to this moment in the text area. V
     * 
     */

    /**
     * builds a new {@link SimpleGUI}.
     * @param controller
     *          controller responsible of I/O access
     */
    public SimpleGUI(final Controller controller) {

        this.controller = controller;
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        final JTextArea textArea = new JTextArea();
        textArea.setEditable(false);
        mainPanel.add(textArea, BorderLayout.CENTER);
        final JTextField textField = new JTextField();
        mainPanel.add(textField, BorderLayout.NORTH);
        textField.setBackground(Color.cyan);
        final JPanel subPanel = new JPanel();
        subPanel.setLayout(new BoxLayout(subPanel, BoxLayout.LINE_AXIS));
        final JButton print = new JButton("Print");
        final JButton history = new JButton("Show History");
        subPanel.add(print);
        subPanel.add(history);
        mainPanel.add(subPanel, BorderLayout.SOUTH);
        frame.setContentPane(mainPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        print.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                SimpleGUI.this.controller.setNextStringToBePrinted(textField.getText());
                SimpleGUI.this.controller.printCurrentString();
            }
        });

        history.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final StringBuilder writtenHistory = new StringBuilder();
                final List<String> history = SimpleGUI.this.controller.getStringHistory();
                for (final String printedString : history) {
                    writtenHistory.append(printedString + '\n');
                }
                textArea.setText(writtenHistory.toString());
            }
        });
        /*
         * Make the frame half the resolution of the screen. This very method is
         * enough for a single screen setup. In case of multiple monitors, the
         * primary is selected.
         * 
         * In order to deal coherently with multimonitor setups, other
         * facilities exist (see the Java documentation about this issue). It is
         * MUCH better than manually specify the size of a window in pixel: it
         * takes into account the current resolution.
         */
        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);

        /*
         * Instead of appearing at (0,0), upper left corner of the screen, this
         * flag makes the OS window manager take care of the default positioning
         * on screen. Results may vary, but it is generally the best choice.
         */
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    public static void main(final String...strings) {
        new SimpleGUI(new ControllerImpl());
    }

}
