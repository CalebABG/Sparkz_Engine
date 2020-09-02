package com.engine.GUIWindows;

import com.engine.J8Helpers.Extensions.KAdapter;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.Utilities.Settings;
import org.fife.ui.autocomplete.AutoCompletion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;

public class OrganicForces {
    private static OrganicForces organicForces = null;

    public JFrame frame;
    private JTextField forceXTextField, forceYTextField, angleIncrementTextField;

    private static Font font = new Font(Font.SERIF, Font.PLAIN, 18);

    public static boolean withError = false;
    public static float angleIncrement = 0.05f;
    public static String expressionForceX = "cos(x)", expressionForceY = "sin(x)";

    public static OrganicForces getInstance(JFrame parent) {
        if (organicForces == null)
            organicForces = new OrganicForces(parent);

        organicForces.frame.toFront();

        return organicForces;
    }

    //public static void main(String[] args) {getInstance(null);}

    private OrganicForces(JFrame parent) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame("Organic Forces");
        frame.setIconImage(Settings.iconImage);
        frame.setSize(335, 354);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{47, 116, 0};
        gbl_panel.rowHeights = new int[]{73, 0, 0, 0, 0, 25, 0};
        gbl_panel.columnWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JLabel lblPleaseEnterTwo = new JLabel("Please Enter Two Force Expressions");
        lblPleaseEnterTwo.setForeground(Color.BLACK);
        lblPleaseEnterTwo.setFont(font);
        lblPleaseEnterTwo.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_lblPleaseEnterTwo = new GridBagConstraints();
        gbc_lblPleaseEnterTwo.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblPleaseEnterTwo.gridwidth = 2;
        gbc_lblPleaseEnterTwo.insets = new Insets(0, 0, 0, 0);
        gbc_lblPleaseEnterTwo.gridx = 0;
        gbc_lblPleaseEnterTwo.gridy = 0;
        panel.add(lblPleaseEnterTwo, gbc_lblPleaseEnterTwo);

        JLabel lblNewLabel = new JLabel("Force X Expression");
        lblNewLabel.setForeground(Color.DARK_GRAY);
        lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        lblNewLabel.setFont(font);
        lblNewLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
        gbc_lblNewLabel.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblNewLabel.gridwidth = 2;
        gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
        gbc_lblNewLabel.gridx = 0;
        gbc_lblNewLabel.gridy = 1;
        panel.add(lblNewLabel, gbc_lblNewLabel);

        forceXTextField = new JTextField(expressionForceX, 10);
        forceXTextField.setFont(font);
        forceXTextField.setHorizontalAlignment(SwingConstants.CENTER);
        forceXTextField.addKeyListener(new KAdapter.KeyReleased(e -> {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) forceXTextField.setForeground(Color.black);
        }));

        AutoCompletion ac = new AutoCompletion(ParticleGraph.autoCompleteProvider);
        ac.setListCellRenderer(ParticleGraph.cellRenderer);
        ac.install(forceXTextField);

        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.gridwidth = 2;
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField.gridx = 0;
        gbc_textField.gridy = 2;
        panel.add(forceXTextField, gbc_textField);

        JLabel lblForceYExpression = new JLabel("Force Y Expression");
        lblForceYExpression.setForeground(Color.DARK_GRAY);
        lblForceYExpression.setHorizontalTextPosition(SwingConstants.CENTER);
        lblForceYExpression.setHorizontalAlignment(SwingConstants.CENTER);
        lblForceYExpression.setFont(font);
        lblForceYExpression.setAlignmentX(0.5f);
        GridBagConstraints gbc_lblForceYExpression = new GridBagConstraints();
        gbc_lblForceYExpression.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblForceYExpression.gridwidth = 2;
        gbc_lblForceYExpression.insets = new Insets(0, 0, 5, 0);
        gbc_lblForceYExpression.gridx = 0;
        gbc_lblForceYExpression.gridy = 3;
        panel.add(lblForceYExpression, gbc_lblForceYExpression);

        forceYTextField = new JTextField(expressionForceY, 10);
        forceYTextField.setHorizontalAlignment(SwingConstants.CENTER);
        forceYTextField.setFont(font);
        forceYTextField.addKeyListener(new KAdapter.KeyReleased(e -> {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) forceYTextField.setForeground(Color.black);
        }));

        AutoCompletion ac2 = new AutoCompletion(ParticleGraph.autoCompleteProvider);
        ac2.setListCellRenderer(ParticleGraph.cellRenderer);
        ac2.install(forceYTextField);

        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.gridwidth = 2;
        gbc_textField_1.insets = new Insets(0, 0, 5, 0);
        gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_1.gridx = 0;
        gbc_textField_1.gridy = 4;
        panel.add(forceYTextField, gbc_textField_1);

        JLabel lblAngleIncrement = new JLabel("Angle Increment");
        lblAngleIncrement.setHorizontalTextPosition(SwingConstants.CENTER);
        lblAngleIncrement.setHorizontalAlignment(SwingConstants.CENTER);
        lblAngleIncrement.setForeground(Color.DARK_GRAY);
        lblAngleIncrement.setFont(font);
        lblAngleIncrement.setAlignmentX(0.5f);
        GridBagConstraints gbc_lblAngleIncrement = new GridBagConstraints();
        gbc_lblAngleIncrement.gridwidth = 2;
        gbc_lblAngleIncrement.fill = GridBagConstraints.HORIZONTAL;
        gbc_lblAngleIncrement.insets = new Insets(0, 0, 5, 0);
        gbc_lblAngleIncrement.gridx = 0;
        gbc_lblAngleIncrement.gridy = 5;
        panel.add(lblAngleIncrement, gbc_lblAngleIncrement);

        angleIncrementTextField = new JTextField("" + angleIncrement, 10);
        angleIncrementTextField.setHorizontalAlignment(SwingConstants.CENTER);
        angleIncrementTextField.setFont(font);
        angleIncrementTextField.addKeyListener(new KAdapter.KeyReleased(e -> {
            if (e.getKeyCode() != KeyEvent.VK_ENTER) angleIncrementTextField.setForeground(Color.black);
        }));

        AutoCompletion ac3 = new AutoCompletion(ParticleGraph.autoCompleteProvider);
        ac3.setListCellRenderer(ParticleGraph.cellRenderer);
        ac3.install(angleIncrementTextField);

        GridBagConstraints gbc_textField_2 = new GridBagConstraints();
        gbc_textField_2.gridwidth = 2;
        gbc_textField_2.insets = new Insets(0, 0, 5, 0);
        gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
        gbc_textField_2.gridx = 0;
        gbc_textField_2.gridy = 6;
        panel.add(angleIncrementTextField, gbc_textField_2);

        JButton button1 = new JButton("Set Forces");
        button1.setFont(font);
        button1.addActionListener(e -> setForces());
        GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
        gbc_btnNewButton_1.gridwidth = 2;
        gbc_btnNewButton_1.fill = GridBagConstraints.HORIZONTAL;
        gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
        gbc_btnNewButton_1.insets = new Insets(0, 5, 5, 5);
        gbc_btnNewButton_1.gridx = 0;
        gbc_btnNewButton_1.gridy = 7;
        panel.add(button1, gbc_btnNewButton_1);

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        frame.setVisible(true);
    }

    private void setForces() {
        String fxText = forceXTextField.getText();
        String fyText = forceYTextField.getText();
        String axText = angleIncrementTextField.getText();
        float tempAngleIncrement = ParticleGraph.getInstance().guardDouble(axText, ParticleGraph.engine, angleIncrementTextField);

        expressionForceX = fxText;
        expressionForceY = fyText;
        angleIncrement = tempAngleIncrement;
    }

    private void close() {
        frame.dispose();
        organicForces = null;
    }
}
