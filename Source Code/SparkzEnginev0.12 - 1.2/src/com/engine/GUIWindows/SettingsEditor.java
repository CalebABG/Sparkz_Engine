package com.engine.GUIWindows;

import com.engine.EngineHelpers.EConstants;
import com.engine.EngineHelpers.EModes;
import com.engine.J8Helpers.Extensions.KAdapter;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.CTextField;
import com.engine.JComponents.RLabel;
import com.engine.Utilities.EJsonHelpers;
import com.engine.Utilities.InputGuard;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;

import static com.engine.EngineHelpers.EBOOLS.*;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EFLOATS.*;
import static com.engine.EngineHelpers.EINTS.*;
import static com.engine.Utilities.InputGuard.floatTextfieldGuardDefault;
import static com.engine.Utilities.InputGuard.intTextfieldGuardDefault;

public class SettingsEditor {
    private static SettingsEditor settingsEditor = null;

    private static Font menuFont = new Font(Font.SERIF, Font.PLAIN, 17);
    private static Font textFieldFont = new Font(Font.SERIF, Font.PLAIN, 18);

    public JFrame frame;
    public JPanel panel;
    private CTextField[] textFields = new CTextField[32];

    //public static void main(String[] args) {}

    public static SettingsEditor getInstance() {
        if (settingsEditor == null) settingsEditor = new SettingsEditor();

        return settingsEditor;
    }

    private SettingsEditor() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame("Settings Editor");
        frame.setIconImage(Settings.iconImage);
        frame.setSize(670, 500);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(EFrame);

        JScrollPane scrollPane = new JScrollPane();
        frame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{138, 64, 0};
        gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
                0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
        gbl_panel.columnWeights = new double[]{1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JMenuBar menuBar = new JMenuBar();
        scrollPane.setColumnHeaderView(menuBar);

        JButton setSettingsBtn = new JButton("Set Settings");
        setSettingsBtn.setMargin(new Insets(1, 1, 1, 1));
        setSettingsBtn.setFont(menuFont);
        setSettingsBtn.addActionListener(e -> {
            setSettings();
            EConstants.menuBar.updateAllRadios();
        });
        menuBar.add(setSettingsBtn);

        menuBar.add(Box.createHorizontalStrut(8));

        JButton quicksave = new JButton("Save Settings");
        quicksave.setMargin(new Insets(1, 1, 1, 1));
        quicksave.setFont(menuFont);
        quicksave.addActionListener(e -> saveButton());
        menuBar.add(quicksave);

        menuBar.add(Box.createHorizontalStrut(8));

        JButton refreshUIBtn = new JButton("Refresh Editor");
        refreshUIBtn.setMargin(new Insets(1, 1, 1, 1));
        refreshUIBtn.setFont(menuFont);
        refreshUIBtn.addActionListener(e -> refreshUI());
        menuBar.add(refreshUIBtn);

        Insets textFieldInsets = new Insets(0, 0, 5, 0);
        RLabel lblNewLabel = new RLabel("-- Ints --", textFieldFont, 2, textFieldInsets, new int[]{0, 0});
        panel.add(lblNewLabel, lblNewLabel.gridBagConstraints);

        RLabel lblNewLabel_1 = new RLabel("Engine Mode - set: 0 - " + (EModes.ENGINE_MODES.values().length - 1), textFieldFont, textFieldInsets, GridBagConstraints.WEST, new int[]{0, 1}, new int[]{1, 4});
        panel.add(lblNewLabel_1, lblNewLabel_1.gridBagConstraints);

        textFields[0] = new CTextField("" + ENGINE_MODE.getValue(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 1});
        panel.add(textFields[0], textFields[0].gridBagConstraints);

        RLabel lblParticletypeSet = new RLabel("Particle Type - set: 0 - " + (EModes.PARTICLE_TYPES.values().length - 1), textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 2);
        panel.add(lblParticletypeSet, lblParticletypeSet.gridBagConstraints);

        textFields[1] = new CTextField("" + PARTICLE_TYPE.getValue(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 2});
        panel.add(textFields[1], textFields[1].gridBagConstraints);

        RLabel lblPtgravitationintSet = new RLabel("Particle Gravitation - set: 0 - " + (EModes.GRAVITATION_MODES.values().length - 1), textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 3);
        panel.add(lblPtgravitationintSet, lblPtgravitationintSet.gridBagConstraints);

        textFields[2] = new CTextField("" + PARTICLE_GRAVITATION_MODE.getValue(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 3});
        panel.add(textFields[2], textFields[2].gridBagConstraints);

        RLabel lblFireworksamountSet = new RLabel("Fireworks Amount - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 4);
        panel.add(lblFireworksamountSet, lblFireworksamountSet.gridBagConstraints);

        textFields[3] = new CTextField("" + FIREWORKS_AMOUNT.value(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 4});
        panel.add(textFields[3], textFields[3].gridBagConstraints);

        RLabel lblParticlemodeSet = new RLabel("Particle Render Mode - set: 0 - " + (EModes.MOLECULE_RENDER_MODES.values().length - 1), textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 5);
        panel.add(lblParticlemodeSet, lblParticlemodeSet.gridBagConstraints);

        textFields[4] = new CTextField("" + PARTICLE_RENDER_MODE.getValue(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 5});
        panel.add(textFields[4], textFields[4].gridBagConstraints);

        RLabel lblDragamountSet = new RLabel("Drag Amount - set: 1 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 6);
        panel.add(lblDragamountSet, lblDragamountSet.gridBagConstraints);

        textFields[5] = new CTextField("" + PARTICLE_DRAG_AMOUNT.value(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 6});
        panel.add(textFields[5], textFields[5].gridBagConstraints);

        RLabel lblBaselifeSet = new RLabel("Particle Life - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 7);
        panel.add(lblBaselifeSet, lblBaselifeSet.gridBagConstraints);

        textFields[6] = new CTextField("" + PARTICLE_BASE_LIFE.value(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 7});
        panel.add(textFields[6], textFields[6].gridBagConstraints);

        RLabel lblRfparticlemodeSet = new RLabel("Fireworks Render Mode - set: 0 - " + (EModes.MOLECULE_RENDER_MODES.values().length - 1), textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 8);
        panel.add(lblRfparticlemodeSet, lblRfparticlemodeSet.gridBagConstraints);

        textFields[7] = new CTextField("" + FIREWORKS_RENDER_MODE.getValue(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 8});
        panel.add(textFields[7], textFields[7].gridBagConstraints);

        RLabel lblRflifeSet = new RLabel("Fireworks Life - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 9);
        panel.add(lblRflifeSet, lblRflifeSet.gridBagConstraints);

        textFields[8] = new CTextField("" + FIREWORKS_LIFE.value(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 9});
        panel.add(textFields[8], textFields[8].gridBagConstraints);

        RLabel lblRfwindSet = new RLabel("Fireworks Wind - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 10);
        panel.add(lblRfwindSet, lblRfwindSet.gridBagConstraints);

        textFields[9] = new CTextField("" + FIREWORKS_WIND.value(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 10});
        panel.add(textFields[9], textFields[9].gridBagConstraints);

        RLabel lblRfjitterSet = new RLabel("Fireworks Jitter - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 11);
        panel.add(lblRfjitterSet, lblRfjitterSet.gridBagConstraints);

        textFields[10] = new CTextField("" + FIREWORKS_JITTER.value(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 11});
        panel.add(textFields[10], textFields[10].gridBagConstraints);

        RLabel lblSafetyamountSet = new RLabel("Safety Amount - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 12);
        panel.add(lblSafetyamountSet, lblSafetyamountSet.gridBagConstraints);

        textFields[11] = new CTextField("" + ENGINE_SAFETY_AMOUNT.value(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 12});
        panel.add(textFields[11], textFields[11].gridBagConstraints);

        RLabel lblCyclerateSet = new RLabel("Color Cycle Rate - set: 1 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 13);
        panel.add(lblCyclerateSet, lblCyclerateSet.gridBagConstraints);

        textFields[12] = new CTextField("" + ENGINE_COLOR_CYCLE_RATE.value(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 13});
        panel.add(textFields[12], textFields[12].gridBagConstraints);

        RLabel lblNewLabel_2 = new RLabel("-- Doubles --", textFieldFont, 2, textFieldInsets, new int[]{0, 14});
        panel.add(lblNewLabel_2, lblNewLabel_2.gridBagConstraints);

        RLabel lblIfinput = new RLabel("SingleClickSizeMaxVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 15);
        panel.add(lblIfinput, lblIfinput.gridBagConstraints);

        textFields[13] = new CTextField(SINGLE_CLICK_SIZE_MAX.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 15});
        panel.add(textFields[13], textFields[13].gridBagConstraints);

        RLabel lblSingleclicksizeminvalSet = new RLabel("SingleClickSizeMinVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 16);
        panel.add(lblSingleclicksizeminvalSet, lblSingleclicksizeminvalSet.gridBagConstraints);

        textFields[14] = new CTextField(SINGLE_CLICK_SIZE_MIN.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 16});
        panel.add(textFields[14], textFields[14].gridBagConstraints);

        RLabel lblMulticlicksizemaxvalSet = new RLabel("MultiClickSizeMaxVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 17);
        panel.add(lblMulticlicksizemaxvalSet, lblMulticlicksizemaxvalSet.gridBagConstraints);

        textFields[15] = new CTextField(MULTI_CLICK_SIZE_MAX.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 17});
        panel.add(textFields[15], textFields[15].gridBagConstraints);

        RLabel lblMulticlicksizeminvalSet = new RLabel("MultiClickSizeMinVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 18);
        panel.add(lblMulticlicksizeminvalSet, lblMulticlicksizeminvalSet.gridBagConstraints);

        textFields[16] = new CTextField(MULTI_CLICK_SIZE_MIN.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 18});
        panel.add(textFields[16], textFields[16].gridBagConstraints);

        RLabel lblFireworkssizemaxvalSet = new RLabel("FireworksSizeMaxVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 19);
        panel.add(lblFireworkssizemaxvalSet, lblFireworkssizemaxvalSet.gridBagConstraints);

        textFields[17] = new CTextField(FIREWORKS_SIZE_MAX.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 19});
        panel.add(textFields[17], textFields[17].gridBagConstraints);

        RLabel lblFireworkssizeminvalSet = new RLabel("FireworksSizeMinVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 20);
        panel.add(lblFireworkssizeminvalSet, lblFireworkssizeminvalSet.gridBagConstraints);

        textFields[18] = new CTextField(FIREWORKS_SIZE_MIN.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 20});
        panel.add(textFields[18], textFields[18].gridBagConstraints);

        RLabel lblDragsizemaxvalSet = new RLabel("DragSizeMaxVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 21);
        panel.add(lblDragsizemaxvalSet, lblDragsizemaxvalSet.gridBagConstraints);

        textFields[19] = new CTextField(PARTICLE_DRAG_SIZE_MAX.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 21});
        panel.add(textFields[19], textFields[19].gridBagConstraints);

        RLabel lblDragsizeminvalSet = new RLabel("DragSizeMinVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 22);
        panel.add(lblDragsizeminvalSet, lblDragsizeminvalSet.gridBagConstraints);

        textFields[20] = new CTextField(PARTICLE_DRAG_SIZE_MIN.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 22});
        panel.add(textFields[20], textFields[20].gridBagConstraints);

        RLabel lblsingleClickSpeedValSet = new RLabel("SingleClickSpeedVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 23);
        panel.add(lblsingleClickSpeedValSet, lblsingleClickSpeedValSet.gridBagConstraints);

        textFields[21] = new CTextField(SINGLE_CLICK_SPEED.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 23});
        panel.add(textFields[21], textFields[21].gridBagConstraints);

        RLabel lblmultiClickSpeedValSet = new RLabel("MultiClickSpeedVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 24);
        panel.add(lblmultiClickSpeedValSet, lblmultiClickSpeedValSet.gridBagConstraints);

        textFields[22] = new CTextField(MULTI_CLICK_SPEED.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 24});
        panel.add(textFields[22], textFields[22].gridBagConstraints);

        RLabel lblfireworksSpeedValSet = new RLabel("FireworksSpeedVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 25);
        panel.add(lblfireworksSpeedValSet, lblfireworksSpeedValSet.gridBagConstraints);

        textFields[23] = new CTextField(FIREWORKS_SPEED.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 25});
        panel.add(textFields[23], textFields[23].gridBagConstraints);

        RLabel lbldragSpeedValSet = new RLabel("DragSpeedVal - set: 0 - ∞", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 26);
        panel.add(lbldragSpeedValSet, lbldragSpeedValSet.gridBagConstraints);

        textFields[24] = new CTextField(PARTICLE_DRAG_SPEED.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 26});
        panel.add(textFields[24], textFields[24].gridBagConstraints);

        RLabel lblNewLabel_3 = new RLabel("-- Booleans --", textFieldFont, 2, textFieldInsets, new int[]{0, 27});
        panel.add(lblNewLabel_3, lblNewLabel_3.gridBagConstraints);

        RLabel lblthinkingParticlesSet = new RLabel("Thinking Particles - set: true - false", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 28);
        panel.add(lblthinkingParticlesSet, lblthinkingParticlesSet.gridBagConstraints);

        textFields[25] = new CTextField(THINKING_PARTICLES.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 28});
        panel.add(textFields[25], textFields[25].gridBagConstraints);

        RLabel lblconnectParticlesSet = new RLabel("Connect Particles - set: true - false", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 29);
        panel.add(lblconnectParticlesSet, lblconnectParticlesSet.gridBagConstraints);

        textFields[26] = new CTextField(CONNECT_PARTICLES.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 29});
        panel.add(textFields[26], textFields[26].gridBagConstraints);

        RLabel lblparticleFrictionSet = new RLabel("Particle Friction - set: true - false", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 30);
        panel.add(lblparticleFrictionSet, lblparticleFrictionSet.gridBagConstraints);

        textFields[27] = new CTextField(PARTICLE_FRICTION.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 30});
        panel.add(textFields[27], textFields[27].gridBagConstraints);

        RLabel lblmouseGravitationSet = new RLabel("Mouse Gravitation - set: true - false", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 31);
        panel.add(lblmouseGravitationSet, lblmouseGravitationSet.gridBagConstraints);

        textFields[28] = new CTextField(MOUSE_GRAVITATION.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 31});
        panel.add(textFields[28], textFields[28].gridBagConstraints);

        RLabel lblispausedSet = new RLabel("Pause Simulation - set: true - false", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 32);
        panel.add(lblispausedSet, lblispausedSet.gridBagConstraints);

        textFields[29] = new CTextField(ENGINE_IS_PAUSED.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 32});
        panel.add(textFields[29], textFields[29].gridBagConstraints);

        RLabel lblGDMODEBOOLSet = new RLabel("Toggle GravityPoints Link Render - set: true - false", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 33);
        panel.add(lblGDMODEBOOLSet, lblGDMODEBOOLSet.gridBagConstraints);

        textFields[30] = new CTextField(TOGGLE_GRAVITYPOINTS_LINK_RENDER.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 33});
        panel.add(textFields[30], textFields[30].gridBagConstraints);

        RLabel lblduplexModeSwapSet = new RLabel("Toggle Duplex Mode - set: true - false", textFieldFont, GridBagConstraints.WEST, textFieldInsets, 0, 34);
        panel.add(lblduplexModeSwapSet, lblduplexModeSwapSet.gridBagConstraints);

        textFields[31] = new CTextField(TOGGLE_DUPLEX_MODE.valueS(), textFieldFont, textFieldInsets, GridBagConstraints.HORIZONTAL, new int[]{1, 34});
        panel.add(textFields[31], textFields[31].gridBagConstraints);

        for (int i = 25; i < textFields.length; i++) {
            final int j = i;
            if (textFields[j].getText().equalsIgnoreCase("true"))
                textFields[j].setForeground(Color.GREEN.darker().darker());
            else if (textFields[j].getText().equalsIgnoreCase("false"))
                textFields[j].setForeground(Color.red.darker().darker());
            textFields[j].addKeyListener(new KAdapter(e -> {
            }, e -> {
                if (textFields[j].getText().equalsIgnoreCase("true")) {
                    textFields[j].setForeground(Color.GREEN.darker().darker());
                } else if (textFields[j].getText().equalsIgnoreCase("false")) {
                    textFields[j].setForeground(Color.red.darker().darker());
                } else {
                    textFields[j].setForeground(Color.black);
                }
            }));
        }

        frame.setVisible(true);
    }

    private void saveButton() {
        setSettings();
        EConstants.menuBar.updateAllRadios();
        Settings.saveSettings();
    }

    private void setSettings() {
        //Ints
        ENGINE_MODE = EModes.ENGINE_MODES.values()[constrain(0, EModes.ENGINE_MODES.values().length - 1, ENGINE_MODE.getValue(), textFields[0].getText())];
        PARTICLE_TYPE = EModes.PARTICLE_TYPES.values()[constrain(0, EModes.PARTICLE_TYPES.values().length - 1, PARTICLE_TYPE.getValue(), textFields[1].getText())];
        PARTICLE_GRAVITATION_MODE = EModes.GRAVITATION_MODES.values()[constrain(0, EModes.GRAVITATION_MODES.values().length - 1, PARTICLE_GRAVITATION_MODE.getValue(), textFields[2].getText())];
        FIREWORKS_AMOUNT.setValue(intTextfieldGuardDefault(0, FIREWORKS_AMOUNT.value(), textFields[3].getText()));
        PARTICLE_RENDER_MODE = EModes.MOLECULE_RENDER_MODES.values()[constrain(0, EModes.MOLECULE_RENDER_MODES.values().length - 1, PARTICLE_RENDER_MODE.getValue(), textFields[4].getText())];
        PARTICLE_DRAG_AMOUNT.setValue(intTextfieldGuardDefault(0, PARTICLE_DRAG_AMOUNT.value(), textFields[5].getText()));
        PARTICLE_BASE_LIFE.setValue(intTextfieldGuardDefault(0, PARTICLE_BASE_LIFE.value(), textFields[6].getText()));
        FIREWORKS_RENDER_MODE = EModes.MOLECULE_RENDER_MODES.values()[constrain(0, EModes.MOLECULE_RENDER_MODES.values().length - 1, FIREWORKS_RENDER_MODE.getValue(), textFields[7].getText())];
        FIREWORKS_LIFE.setValue(intTextfieldGuardDefault(0, FIREWORKS_LIFE.value(), textFields[8].getText()));
        FIREWORKS_WIND.setValue(intTextfieldGuardDefault(0, FIREWORKS_WIND.value(), textFields[9].getText()));
        FIREWORKS_JITTER.setValue(intTextfieldGuardDefault(0, FIREWORKS_JITTER.value(), textFields[10].getText()));
        ENGINE_SAFETY_AMOUNT.setValue(intTextfieldGuardDefault(0, ENGINE_SAFETY_AMOUNT.value(), textFields[11].getText()));
        ENGINE_COLOR_CYCLE_RATE.setValue(intTextfieldGuardDefault(1, ENGINE_COLOR_CYCLE_RATE.value(), textFields[12].getText()));

        //Doubles
        SINGLE_CLICK_SIZE_MAX.setValue(floatTextfieldGuardDefault(0, SINGLE_CLICK_SIZE_MAX.value(), textFields[13].getText()));
        SINGLE_CLICK_SIZE_MIN.setValue(floatTextfieldGuardDefault(0, SINGLE_CLICK_SIZE_MIN.value(), textFields[14].getText()));
        MULTI_CLICK_SIZE_MAX.setValue(floatTextfieldGuardDefault(0, MULTI_CLICK_SIZE_MAX.value(), textFields[15].getText()));
        MULTI_CLICK_SIZE_MIN.setValue(floatTextfieldGuardDefault(0, MULTI_CLICK_SIZE_MIN.value(), textFields[16].getText()));
        FIREWORKS_SIZE_MAX.setValue(floatTextfieldGuardDefault(0, FIREWORKS_SIZE_MAX.value(), textFields[17].getText()));
        FIREWORKS_SIZE_MIN.setValue(floatTextfieldGuardDefault(0, FIREWORKS_SIZE_MIN.value(), textFields[18].getText()));
        PARTICLE_DRAG_SIZE_MAX.setValue(floatTextfieldGuardDefault(0, PARTICLE_DRAG_SIZE_MAX.value(), textFields[19].getText()));
        PARTICLE_DRAG_SIZE_MIN.setValue(floatTextfieldGuardDefault(0, PARTICLE_DRAG_SIZE_MIN.value(), textFields[20].getText()));
        SINGLE_CLICK_SPEED.setValue(floatTextfieldGuardDefault(0, SINGLE_CLICK_SPEED.value(), textFields[21].getText()));
        MULTI_CLICK_SPEED.setValue(floatTextfieldGuardDefault(0, MULTI_CLICK_SPEED.value(), textFields[22].getText()));
        FIREWORKS_SPEED.setValue(floatTextfieldGuardDefault(0, FIREWORKS_SPEED.value(), textFields[23].getText()));
        PARTICLE_DRAG_SPEED.setValue(floatTextfieldGuardDefault(0, PARTICLE_DRAG_SPEED.value(), textFields[24].getText()));

        //Booleans
        THINKING_PARTICLES.setValue(getBoolFromString(THINKING_PARTICLES.value(), textFields[25].getText()));
        CONNECT_PARTICLES.setValue(getBoolFromString(CONNECT_PARTICLES.value(), textFields[26].getText()));
        PARTICLE_FRICTION.setValue(getBoolFromString(PARTICLE_FRICTION.value(), textFields[27].getText()));
        MOUSE_GRAVITATION.setValue(getBoolFromString(MOUSE_GRAVITATION.value(), textFields[28].getText()));
        ENGINE_IS_PAUSED.setValue(getBoolFromString(ENGINE_IS_PAUSED.value(), textFields[29].getText()));
        TOGGLE_GRAVITYPOINTS_LINK_RENDER.setValue(getBoolFromString(TOGGLE_GRAVITYPOINTS_LINK_RENDER.value(), textFields[30].getText()));
        TOGGLE_DUPLEX_MODE.setValue(getBoolFromString(TOGGLE_DUPLEX_MODE.value(), textFields[31].getText()));
    }

    private int constrain(int min, int max, int def, String s) {
        if (!s.isEmpty() && InputGuard.canParseStringInt(s)) {
            int val = Integer.parseInt(s);
            return EJsonHelpers.constrain(def, val, min, max);
        } else return def;
    }

    private boolean getBoolFromString(boolean def, String input) {
        return input.equalsIgnoreCase("true") || !input.equalsIgnoreCase("false") && def;
    }

    private void refreshUI() {
        textFields[0].setText(ENGINE_MODE.getValueS());
        textFields[1].setText(PARTICLE_TYPE.getValueS());
        textFields[2].setText(PARTICLE_GRAVITATION_MODE.getValueS());
        textFields[3].setText(FIREWORKS_AMOUNT.getValueS());
        textFields[4].setText(PARTICLE_RENDER_MODE.getValueS());
        textFields[5].setText(PARTICLE_DRAG_AMOUNT.getValueS());
        textFields[6].setText(PARTICLE_BASE_LIFE.getValueS());
        textFields[7].setText(FIREWORKS_RENDER_MODE.getValueS());
        textFields[8].setText(FIREWORKS_LIFE.getValueS());
        textFields[9].setText(FIREWORKS_WIND.getValueS());
        textFields[10].setText(FIREWORKS_JITTER.getValueS());
        textFields[11].setText(ENGINE_SAFETY_AMOUNT.getValueS());
        textFields[12].setText(ENGINE_COLOR_CYCLE_RATE.getValueS());
        textFields[13].setText(SINGLE_CLICK_SIZE_MAX.valueS());
        textFields[14].setText(SINGLE_CLICK_SIZE_MIN.valueS());
        textFields[15].setText(MULTI_CLICK_SIZE_MAX.valueS());
        textFields[16].setText(MULTI_CLICK_SIZE_MIN.valueS());
        textFields[17].setText(FIREWORKS_SIZE_MAX.valueS());
        textFields[18].setText(FIREWORKS_SIZE_MIN.valueS());
        textFields[19].setText(PARTICLE_DRAG_SIZE_MAX.valueS());
        textFields[20].setText(PARTICLE_DRAG_SIZE_MIN.valueS());
        textFields[21].setText(SINGLE_CLICK_SPEED.valueS());
        textFields[22].setText(MULTI_CLICK_SPEED.valueS());
        textFields[23].setText(FIREWORKS_SPEED.valueS());
        textFields[24].setText(PARTICLE_DRAG_SPEED.valueS());
        textFields[25].setText(THINKING_PARTICLES.valueS());
        textFields[26].setText(CONNECT_PARTICLES.valueS());
        textFields[27].setText(PARTICLE_FRICTION.valueS());
        textFields[28].setText(MOUSE_GRAVITATION.valueS());
        textFields[29].setText(ENGINE_IS_PAUSED.valueS());
        textFields[30].setText(TOGGLE_GRAVITYPOINTS_LINK_RENDER.valueS());
        textFields[31].setText(TOGGLE_DUPLEX_MODE.valueS());
        for (int i = 25; i < textFields.length; i++) {
            if (textFields[i].getText().equalsIgnoreCase("TRUE"))
                textFields[i].setForeground(Color.GREEN.darker().darker());
            else if (textFields[i].getText().equalsIgnoreCase("FALSE"))
                textFields[i].setForeground(Color.red.darker().darker());
        }
    }

    private void clearText() {
        for (CTextField textField : textFields) textField.setText("");
    }

    public void close() {
        frame.dispose();
        settingsEditor = null;
    }
}