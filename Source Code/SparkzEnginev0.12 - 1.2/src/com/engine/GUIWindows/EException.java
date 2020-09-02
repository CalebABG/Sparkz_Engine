package com.engine.GUIWindows;

import com.engine.J8Helpers.Extensions.KAdapter;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.engine.EngineHelpers.EConstants.*;

public class EException {
    private static EException logger = null;

    public JFrame frame;
    private JTextArea textArea = new JTextArea();

    //public static void main(String[] args) {}

    public static EException getInstance() {
        if (logger == null) logger = new EException();

        return logger;
    }

    private EException() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame("Exception Log");
        frame.setIconImage(Settings.iconImage);
        frame.setSize(430, 260);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(EFrame);
        frame.addKeyListener(new KAdapter.KeyReleased(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ESCAPE) close();
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_C) textArea.setText("");
            if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_P)
                textArea.append("Project Development: " + Settings.getProjectTimespan() + "\n");
        }));

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel);

        textArea.setEditable(false);
        textArea.setLineWrap(true);
        textArea.setDragEnabled(false);
        textArea.setEnabled(false);
        textArea.setFont(new Font(Font.SERIF, Font.ITALIC, 14));
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBackground(Color.BLACK);
        scrollPane.getViewport().add(textArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static String logException(Exception e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }

    public static void append(Exception e) {
        getInstance().textArea.append(new SimpleDateFormat("h:mm:ss a").format(new Date()) + " - " + logException(e) + "\n");
    }

    public static void append(String text) {
        getInstance().textArea.append(text + '\n');
    }

    private void close() {
        frame.dispose();
        logger = null;
    }
}
