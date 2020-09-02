package com.engine.GUIWindows;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.EngineHelpers.GUIText;
import com.engine.J8Helpers.Extensions.KAdapter;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.Utilities.InputGuard;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

import static com.engine.EngineHelpers.EConstants.EFrame;

public class OptionsMenu {
    private static OptionsMenu optionsMenu = null;

    public JFrame frame;
    private JTextField textField;

    //public static void main(String[] args) {getInstance();}

    public static OptionsMenu getInstance() {
        if (optionsMenu == null)
            optionsMenu = new OptionsMenu();

        optionsMenu.frame.toFront();

        return optionsMenu;
    }

    private OptionsMenu() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame("Options Menu");
        frame.setIconImage(Settings.iconImage);
        frame.setSize(495, 480);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(EFrame);

        JScrollPane jScrollPane1 = new JScrollPane();

        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout(new BorderLayout());

        textField = new JTextField();
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setFont(new Font(Font.SERIF, Font.PLAIN, 17));
        //Needs to be keyPressed() handler - keyReleased() will cause windows to display twice
        textField.addKeyListener(new KAdapter.KeyPressed(e -> {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) getOption();
        }));
        jPanel1.add(textField, BorderLayout.CENTER);

        JButton jButton1 = new JButton();
        jButton1.setFont(new Font(Font.SERIF, Font.BOLD, 14));
        jButton1.setText("Enter");
        jButton1.addActionListener(e -> {
            if (e.getSource() == jButton1) {
                getOption();
            }
        });
        jPanel1.add(jButton1, BorderLayout.LINE_END);

        frame.add(jPanel1, BorderLayout.PAGE_END);

        JLabel jLabel2 = new JLabel(GUIText.GeneralOptions);
        jLabel2.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        jLabel2.setHorizontalAlignment(SwingConstants.LEFT);
        jScrollPane1.setViewportView(jLabel2);

        frame.add(jScrollPane1, BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private void getOption() {
        String text = textField.getText();
        if (text != null) {
            if (InputGuard.canParseStringInt(text))
                EngineMethods.getOptions(Integer.parseInt(text));
        }
    }

    private void close() {
        frame.dispose();
        optionsMenu = null;
    }
}
