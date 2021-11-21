package it.unibo.oop.lab.mvcio2;

import it.unibo.oop.lab.mvcio.Controller;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.LayoutManager;
import java.awt.BorderLayout;

/**
 * A very simple program using a graphical interface.
 * 
 */
public final class SimpleGUIWithFileChooser {

    private final JFrame frame = new JFrame("My second Java Grafical Interface");

    /**
     * builds a new {@link SimpleGUIWithFileChooser}.
     * 
     * @param controller
     */
    public SimpleGUIWithFileChooser(final Controller controller) {

        final JTextArea text = new JTextArea();
        final JPanel panel = new JPanel();
        final LayoutManager layout = new BorderLayout();
        panel.setLayout(layout);

        panel.add(text, BorderLayout.CENTER);
        final JButton button = new JButton("Save");
        panel.add(button, BorderLayout.SOUTH);
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                if (controller.saveNewString(text.getText())) {
                    JOptionPane.showConfirmDialog(null, "Operation completed", "Save", JOptionPane.PLAIN_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(null, "Something has gone wrong...", "Save", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        frame.setContentPane(panel);

        /*
         * 1) Add a JTextField and a button "Browse..." on the upper part of the
         * graphical interface.
         * Suggestion: use a second JPanel with a second BorderLayout, put the panel
         * in the North of the main panel, put the text field in the center of the
         * new panel and put the button in the line_end of the new panel.
         * 2) The JTextField should be non modifiable. And, should display the
         * current selected file.
         */
        final JTextField filepath = new JTextField(controller.getCurrentFilePath());
        final JButton button2 = new JButton("Browse...");
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new BorderLayout());
        panel.add(panel2, BorderLayout.NORTH);
        panel2.add(filepath, BorderLayout.CENTER);
        panel2.add(button2, BorderLayout.LINE_END);
        filepath.setEditable(false);

        /* 3) On press, the button should open a JFileChooser. The program should
         * use the method showSaveDialog() to display the file chooser, and if the
         * result is equal to JFileChooser.APPROVE_OPTION the program should set as
         * new file in the Controller the file chosen. If CANCEL_OPTION is returned,
         * then the program should do nothing. Otherwise, a message dialog should be
         * shown telling the user that an error has occurred (use
         * JOptionPane.showMessageDialog()).
         *
         * 4) When in the controller a new File is set, also the graphical interface
         * must reflect such change. Suggestion: do not force the controller to
         * update the UI: in this example the UI knows when should be updated, so
         * try to keep things separated.
         */

        button2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                final JFileChooser jfc = new JFileChooser("Where would you like to save?");
                jfc.setSelectedFile(controller.getCurrentFile());
                switch (jfc.showSaveDialog(frame)) {
                case JFileChooser.APPROVE_OPTION:
                    final File newFile = jfc.getSelectedFile();
                    controller.setFile(newFile);
                    filepath.setText(newFile.getPath());
                    break;
                case JFileChooser.CANCEL_OPTION:
                    break;
                default:
                    JOptionPane.showMessageDialog(frame, "Something wrong has happened!", "ERROR", JOptionPane.ERROR_MESSAGE);
                    break;
                }
            }
        });

        final Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        final int sw = (int) screen.getWidth();
        final int sh = (int) screen.getHeight();
        frame.setSize(sw / 2, sh / 2);

        frame.setLocationByPlatform(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(final String... strings) {
        new SimpleGUIWithFileChooser(new Controller());
    }
}
