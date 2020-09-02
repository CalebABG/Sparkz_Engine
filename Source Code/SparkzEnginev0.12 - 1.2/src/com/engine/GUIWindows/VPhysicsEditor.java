package com.engine.GUIWindows;

import com.engine.EngineHelpers.EConstants;
import com.engine.EngineHelpers.EngineMethods;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.CLabel;
import com.engine.Utilities.InputGuard;
import com.engine.Utilities.Settings;
import com.engine.Verlet.Edge;
import com.engine.Verlet.VModes;
import com.engine.Verlet.VSim;
import com.engine.Verlet.Vertex;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import static com.engine.EngineHelpers.EBOOLS.ENGINE_ENABLE_SMOOTH_RENDER;
import static com.engine.Verlet.VSim.selectedVertex;

public class VPhysicsEditor {
    private static VPhysicsEditor vPhysicsEditorInstance = null;

    private static Font font = new Font(Font.SERIF, Font.PLAIN, 14);

    public JFrame frame;
    public VModes.EditorModes EDITOR_MODE = VModes.EditorModes.Add;
    public VModes.CreationModes CREATION_MODE = VModes.CreationModes.Point;

    public JButton btnSetSimulationProperties, objectsetproperties_button, setconstraintsbutton;
    public CLabel pointcolor_panel, linkcolor_panel, selectioncolor_panel, constraint_link_panel;


    public JCheckBox showselectionconstraint_checkbox = new JCheckBox("Show Constraint"),
            constraintdrawlink_checkbox = new JCheckBox("Draw Link"),
            constrainttearable_checkbox = new JCheckBox("Tearable"),
            selectionshowpoint_checkbox = new JCheckBox("Show Point"),
            selectioncollidable_checkbox = new JCheckBox("Collidable");

    public JList<Integer> constraintJlist;
    public DefaultListModel<Integer> listModel = new DefaultListModel<>();

    public JComboBox<VModes.EditorModes> editorModesJComboBox = new JComboBox<>();
    public JComboBox<VModes.CreationModes> creationModesJComboBox = new JComboBox<>();
    public JTextField simacc_field = new JTextField(10),
            dragforce_field = new JTextField(10),
            gravity_field = new JTextField(10),
            numpoints_field = new JTextField("4", 10),
            objectsize_field = new JTextField(10),
            dampening_field = new JTextField(10),
            objectmass_field = new JTextField(10),
            spacing_field = new JTextField(10),
            stiffness_field = new JTextField(10),
            airviscosity_field = new JTextField(10),
            selectionradius_field = new JTextField(10),
            selectionmass_field = new JTextField(10),
            selectiondampening_field = new JTextField(10),
            selectionstiffness_field = new JTextField(10),
            teardistance_field = new JTextField(10),
            constraint_stiffness_field = new JTextField(10),
            constraint_teardistance_field = new JTextField(10);


    //public static void main(String[] args) {getInstance(null);}


    public static VPhysicsEditor getInstance() {
        return getInstance(EConstants.EFrame);
    }

    public static VPhysicsEditor getInstance(JFrame p) {
        if (vPhysicsEditorInstance == null) vPhysicsEditorInstance = new VPhysicsEditor(p);

        return vPhysicsEditorInstance;
    }

    public VPhysicsEditor(JFrame parent) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame("Verlet Physics Editor");
        frame.setIconImage(Settings.iconImage);
        frame.setSize(568, 610);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        listModel.addElement(-1);
        constraintJlist = new JList<>(listModel);

        JScrollPane simeditor_scrollpane = new JScrollPane();
        frame.getContentPane().add(simeditor_scrollpane, BorderLayout.CENTER);

        JToolBar simeditortoolbar = new JToolBar();
        simeditor_scrollpane.setColumnHeaderView(simeditortoolbar);

        JPanel toolbarpanel = new JPanel();
        simeditortoolbar.add(toolbarpanel);
        GridBagLayout gbl_toolbarpanel = new GridBagLayout();
        gbl_toolbarpanel.columnWidths = new int[]{69, 70, 63, 0, 72, 0, 0};
        gbl_toolbarpanel.rowHeights = new int[]{0, 0};
        gbl_toolbarpanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        gbl_toolbarpanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
        toolbarpanel.setLayout(gbl_toolbarpanel);

        JLabel editormode_label = new JLabel("Editor Mode");
        GridBagConstraints gbc_editormode_label = new GridBagConstraints();
        gbc_editormode_label.anchor = GridBagConstraints.WEST;
        gbc_editormode_label.insets = new Insets(0, 0, 0, 5);
        gbc_editormode_label.gridx = 0;
        gbc_editormode_label.gridy = 0;
        toolbarpanel.add(editormode_label, gbc_editormode_label);
        editormode_label.setFont(font);
        editormode_label.setIconTextGap(5);

        editorModesJComboBox.setModel(new DefaultComboBoxModel<>(VModes.EditorModes.values()));
        editorModesJComboBox.setSelectedItem(EDITOR_MODE);
        editorModesJComboBox.setFont(font);
        editorModesJComboBox.addActionListener(e -> {
            VModes.EditorModes selectedMode = (VModes.EditorModes) editorModesJComboBox.getSelectedItem();
            if (selectedMode != EDITOR_MODE) EDITOR_MODE = selectedMode;
        });
        GridBagConstraints gbc_editormode_combobox = new GridBagConstraints();
        gbc_editormode_combobox.fill = GridBagConstraints.HORIZONTAL;
        gbc_editormode_combobox.insets = new Insets(0, 0, 0, 5);
        gbc_editormode_combobox.gridx = 1;
        gbc_editormode_combobox.gridy = 0;
        toolbarpanel.add(editorModesJComboBox, gbc_editormode_combobox);

        JCheckBox gravitycheckbox = new JCheckBox("Zero Gravity");
        gravitycheckbox.setSelected(VSim.ZERO_GRAVITY);
        gravitycheckbox.addActionListener(e -> VSim.toggleGravity());
        gravitycheckbox.setToolTipText("Toggles Gravity on or off(default is on)");
        gravitycheckbox.setFont(font);
        GridBagConstraints gbc_gravitycheckbox = new GridBagConstraints();
        gbc_gravitycheckbox.insets = new Insets(0, 0, 0, 5);
        gbc_gravitycheckbox.gridx = 2;
        gbc_gravitycheckbox.gridy = 0;
        toolbarpanel.add(gravitycheckbox, gbc_gravitycheckbox);

        JCheckBox debugcheckbox = new JCheckBox("Debug");
        debugcheckbox.setSelected(VSim.DEBUG_MODE);
        debugcheckbox.addActionListener(e -> VSim.toggleDebug());
        debugcheckbox.setToolTipText("Toggles Debug Mode(default is off)");
        debugcheckbox.setFont(font);
        GridBagConstraints gbc_debugcheckbox = new GridBagConstraints();
        gbc_debugcheckbox.insets = new Insets(0, 0, 0, 5);
        gbc_debugcheckbox.gridx = 3;
        gbc_debugcheckbox.gridy = 0;
        toolbarpanel.add(debugcheckbox, gbc_debugcheckbox);

        JCheckBox collisionscheckbox = new JCheckBox("Collisions");
        collisionscheckbox.setSelected(VSim.COLLISION_DETECTION);
        collisionscheckbox.addActionListener(e -> VSim.toggleCollisions());
        collisionscheckbox.setToolTipText("Toggles Collision Detection");
        collisionscheckbox.setFont(font);
        GridBagConstraints gbc_collisionscheckbox = new GridBagConstraints();
        gbc_collisionscheckbox.insets = new Insets(0, 0, 0, 5);
        gbc_collisionscheckbox.anchor = GridBagConstraints.EAST;
        gbc_collisionscheckbox.gridx = 4;
        gbc_collisionscheckbox.gridy = 0;
        toolbarpanel.add(collisionscheckbox, gbc_collisionscheckbox);

        JCheckBox chckbxSmoothing = new JCheckBox("Smoothing");
        chckbxSmoothing.setSelected(ENGINE_ENABLE_SMOOTH_RENDER.value());
        chckbxSmoothing.addActionListener(e -> EngineMethods.toggleGraphicsSmoothing());
        chckbxSmoothing.setToolTipText("Toggles Graphics Smoothing(default is off)");
        chckbxSmoothing.setFont(font);
        GridBagConstraints gbc_chckbxSmoothing = new GridBagConstraints();
        gbc_chckbxSmoothing.gridx = 5;
        gbc_chckbxSmoothing.gridy = 0;
        toolbarpanel.add(chckbxSmoothing, gbc_chckbxSmoothing);

        JTabbedPane simeditorpane = new JTabbedPane(JTabbedPane.TOP);
        simeditorpane.setFont(font);
        simeditor_scrollpane.setViewportView(simeditorpane);

        JPanel simproperties_panel = new JPanel();
        simproperties_panel.setFont(font);
        simeditorpane.addTab("Simulation Properties", null, simproperties_panel, "You can change simulation properties here");
        simeditorpane.setEnabledAt(0, true);
        GridBagLayout gbl_simproperties_panel = new GridBagLayout();
        gbl_simproperties_panel.columnWidths = new int[]{164, -44};
        gbl_simproperties_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 37, 0, 0};
        gbl_simproperties_panel.columnWeights = new double[]{1.0, Double.MIN_VALUE};
        gbl_simproperties_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        simproperties_panel.setLayout(gbl_simproperties_panel);

        JLabel simacc_label = new JLabel("Simulation Accuracy(higher value = performance hit)");
        simacc_label.setFont(font);
        GridBagConstraints gbc_simacc_label = new GridBagConstraints();
        gbc_simacc_label.fill = GridBagConstraints.VERTICAL;
        gbc_simacc_label.insets = new Insets(5, 0, 5, 0);
        gbc_simacc_label.gridwidth = 2;
        gbc_simacc_label.gridx = 0;
        gbc_simacc_label.gridy = 0;
        simproperties_panel.add(simacc_label, gbc_simacc_label);

        simacc_field.setText("" + VSim.SIM_ACCURACY);
        simacc_field.setHorizontalAlignment(SwingConstants.CENTER);
        simacc_field.setFont(font);
        GridBagConstraints gbc_simacc_field = new GridBagConstraints();
        gbc_simacc_field.insets = new Insets(0, 0, 5, 0);
        gbc_simacc_field.gridwidth = 2;
        gbc_simacc_field.fill = GridBagConstraints.BOTH;
        gbc_simacc_field.gridx = 0;
        gbc_simacc_field.gridy = 1;
        simproperties_panel.add(simacc_field, gbc_simacc_field);


        JLabel dragforce_label = new JLabel("Drag Force(smaller value = stronger force)");
        dragforce_label.setFont(font);
        GridBagConstraints gbc_dragforce_label = new GridBagConstraints();
        gbc_dragforce_label.fill = GridBagConstraints.VERTICAL;
        gbc_dragforce_label.insets = new Insets(0, 0, 5, 0);
        gbc_dragforce_label.gridwidth = 2;
        gbc_dragforce_label.gridx = 0;
        gbc_dragforce_label.gridy = 2;
        simproperties_panel.add(dragforce_label, gbc_dragforce_label);

        dragforce_field.setText("" + VSim.dragForce);
        dragforce_field.setHorizontalAlignment(SwingConstants.CENTER);
        dragforce_field.setFont(font);

        GridBagConstraints gbc_dragforce_field = new GridBagConstraints();
        gbc_dragforce_field.insets = new Insets(0, 0, 5, 0);
        gbc_dragforce_field.gridwidth = 2;
        gbc_dragforce_field.fill = GridBagConstraints.BOTH;
        gbc_dragforce_field.gridx = 0;
        gbc_dragforce_field.gridy = 3;
        simproperties_panel.add(dragforce_field, gbc_dragforce_field);

        JLabel gravity_label = new JLabel("Gravity");
        gravity_label.setFont(font);
        GridBagConstraints gbc_gravity_label = new GridBagConstraints();
        gbc_gravity_label.fill = GridBagConstraints.VERTICAL;
        gbc_gravity_label.insets = new Insets(0, 0, 5, 0);
        gbc_gravity_label.gridwidth = 2;
        gbc_gravity_label.gridx = 0;
        gbc_gravity_label.gridy = 4;
        simproperties_panel.add(gravity_label, gbc_gravity_label);

        gravity_field.setText("" + VSim.GConstant);
        gravity_field.setHorizontalAlignment(SwingConstants.CENTER);
        gravity_field.setFont(font);

        GridBagConstraints gbc_gravity_field = new GridBagConstraints();
        gbc_gravity_field.insets = new Insets(0, 0, 5, 0);
        gbc_gravity_field.gridwidth = 2;
        gbc_gravity_field.fill = GridBagConstraints.BOTH;
        gbc_gravity_field.gridx = 0;
        gbc_gravity_field.gridy = 5;
        simproperties_panel.add(gravity_field, gbc_gravity_field);

        JLabel airviscosity_label = new JLabel("Air Viscosity");
        airviscosity_label.setFont(font);
        GridBagConstraints gbc_airviscosity_label = new GridBagConstraints();
        gbc_airviscosity_label.fill = GridBagConstraints.VERTICAL;
        gbc_airviscosity_label.gridwidth = 2;
        gbc_airviscosity_label.insets = new Insets(0, 0, 5, 0);
        gbc_airviscosity_label.gridx = 0;
        gbc_airviscosity_label.gridy = 6;
        simproperties_panel.add(airviscosity_label, gbc_airviscosity_label);

        airviscosity_field.setText("" + VSim.air_viscosity);
        airviscosity_field.setHorizontalAlignment(SwingConstants.CENTER);
        airviscosity_field.setFont(font);

        GridBagConstraints gbc_airviscosity_field = new GridBagConstraints();
        gbc_airviscosity_field.gridwidth = 2;
        gbc_airviscosity_field.insets = new Insets(0, 0, 5, 0);
        gbc_airviscosity_field.fill = GridBagConstraints.BOTH;
        gbc_airviscosity_field.gridx = 0;
        gbc_airviscosity_field.gridy = 7;
        simproperties_panel.add(airviscosity_field, gbc_airviscosity_field);

        btnSetSimulationProperties = new JButton("Set Simulation Properties");
        btnSetSimulationProperties.addActionListener(e -> setSimulationProperties());
        btnSetSimulationProperties.setFont(font);
        GridBagConstraints gbc_btnSetSimulationProperties = new GridBagConstraints();
        gbc_btnSetSimulationProperties.gridwidth = 2;
        gbc_btnSetSimulationProperties.fill = GridBagConstraints.BOTH;
        gbc_btnSetSimulationProperties.insets = new Insets(0, 0, 5, 0);
        gbc_btnSetSimulationProperties.gridx = 0;
        gbc_btnSetSimulationProperties.gridy = 8;
        simproperties_panel.add(btnSetSimulationProperties, gbc_btnSetSimulationProperties);

        JPanel addmode_panel = new JPanel();
        GridBagConstraints gbc_addmode_panel = new GridBagConstraints();
        gbc_addmode_panel.gridwidth = 2;
        gbc_addmode_panel.fill = GridBagConstraints.BOTH;
        gbc_addmode_panel.gridx = 0;
        gbc_addmode_panel.gridy = 9;
        simproperties_panel.add(addmode_panel, gbc_addmode_panel);
        GridBagLayout gbl_addmode_panel = new GridBagLayout();
        gbl_addmode_panel.columnWidths = new int[]{103, 110, 74, 112};
        gbl_addmode_panel.rowHeights = new int[]{30, 30, 33, 32, 29, 30, 31, 0};
        gbl_addmode_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0};
        gbl_addmode_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        addmode_panel.setLayout(gbl_addmode_panel);

        JLabel addmode_label = new JLabel("Add Mode(availability based on mode)");
        addmode_label.setFont(font);
        GridBagConstraints gbc_addmode_label = new GridBagConstraints();
        gbc_addmode_label.fill = GridBagConstraints.VERTICAL;
        gbc_addmode_label.insets = new Insets(5, 0, 5, 0);
        gbc_addmode_label.gridwidth = 4;
        gbc_addmode_label.gridx = 0;
        gbc_addmode_label.gridy = 0;
        addmode_panel.add(addmode_label, gbc_addmode_label);

        creationModesJComboBox.setModel(new DefaultComboBoxModel<>(VModes.CreationModes.values()));
        creationModesJComboBox.setSelectedItem(CREATION_MODE);
        creationModesJComboBox.setFont(font);
        creationModesJComboBox.addActionListener(e -> {
            VModes.CreationModes selectedMode = (VModes.CreationModes) creationModesJComboBox.getSelectedItem();
            if (selectedMode != CREATION_MODE) CREATION_MODE = selectedMode;

        });
        GridBagConstraints gbc_addmode_combobox = new GridBagConstraints();
        gbc_addmode_combobox.fill = GridBagConstraints.BOTH;
        gbc_addmode_combobox.insets = new Insets(0, 0, 5, 0);
        gbc_addmode_combobox.gridwidth = 4;
        gbc_addmode_combobox.gridx = 0;
        gbc_addmode_combobox.gridy = 1;
        addmode_panel.add(creationModesJComboBox, gbc_addmode_combobox);

        JLabel numpoints_label = new JLabel("Num Points");
        numpoints_label.setFont(font);
        GridBagConstraints gbc_numpoints_label = new GridBagConstraints();
        gbc_numpoints_label.fill = GridBagConstraints.VERTICAL;
        gbc_numpoints_label.anchor = GridBagConstraints.WEST;
        gbc_numpoints_label.insets = new Insets(0, 5, 5, 5);
        gbc_numpoints_label.gridx = 0;
        gbc_numpoints_label.gridy = 2;
        addmode_panel.add(numpoints_label, gbc_numpoints_label);

        numpoints_field.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_numpoints_field = new GridBagConstraints();
        gbc_numpoints_field.fill = GridBagConstraints.BOTH;
        gbc_numpoints_field.insets = new Insets(0, 0, 5, 5);
        gbc_numpoints_field.gridx = 1;
        gbc_numpoints_field.gridy = 2;
        addmode_panel.add(numpoints_field, gbc_numpoints_field);


        JCheckBox drawlinks_checkbox = new JCheckBox("Draw Links");
        drawlinks_checkbox.setFont(font);
        drawlinks_checkbox.setSelected(true);
        GridBagConstraints gbc_drawlinks_checkbox = new GridBagConstraints();
        gbc_drawlinks_checkbox.anchor = GridBagConstraints.WEST;
        gbc_drawlinks_checkbox.fill = GridBagConstraints.VERTICAL;
        gbc_drawlinks_checkbox.insets = new Insets(0, 0, 5, 5);
        gbc_drawlinks_checkbox.gridx = 2;
        gbc_drawlinks_checkbox.gridy = 2;
        addmode_panel.add(drawlinks_checkbox, gbc_drawlinks_checkbox);

        JCheckBox istearable_checkbox = new JCheckBox("Is Tearable");
        istearable_checkbox.setSelected(true);
        istearable_checkbox.setFont(font);
        GridBagConstraints gbc_istearable_checkbox = new GridBagConstraints();
        gbc_istearable_checkbox.fill = GridBagConstraints.VERTICAL;
        gbc_istearable_checkbox.insets = new Insets(0, 0, 5, 0);
        gbc_istearable_checkbox.gridx = 3;
        gbc_istearable_checkbox.gridy = 2;
        addmode_panel.add(istearable_checkbox, gbc_istearable_checkbox);

        JLabel objectsize_label = new JLabel("Size");
        objectsize_label.setFont(font);
        GridBagConstraints gbc_objectsize_label = new GridBagConstraints();
        gbc_objectsize_label.anchor = GridBagConstraints.WEST;
        gbc_objectsize_label.fill = GridBagConstraints.VERTICAL;
        gbc_objectsize_label.insets = new Insets(0, 5, 5, 5);
        gbc_objectsize_label.gridx = 0;
        gbc_objectsize_label.gridy = 3;
        addmode_panel.add(objectsize_label, gbc_objectsize_label);

        objectsize_field.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_objectsize_field = new GridBagConstraints();
        gbc_objectsize_field.insets = new Insets(0, 0, 5, 5);
        gbc_objectsize_field.fill = GridBagConstraints.BOTH;
        gbc_objectsize_field.gridx = 1;
        gbc_objectsize_field.gridy = 3;
        addmode_panel.add(objectsize_field, gbc_objectsize_field);

        JLabel teardistance_label = new JLabel("Tear Distance");
        teardistance_label.setFont(font);
        GridBagConstraints gbc_teardistance_label = new GridBagConstraints();
        gbc_teardistance_label.fill = GridBagConstraints.VERTICAL;
        gbc_teardistance_label.anchor = GridBagConstraints.WEST;
        gbc_teardistance_label.insets = new Insets(0, 0, 5, 5);
        gbc_teardistance_label.gridx = 2;
        gbc_teardistance_label.gridy = 3;
        addmode_panel.add(teardistance_label, gbc_teardistance_label);

        teardistance_field.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_teardistance_field = new GridBagConstraints();
        gbc_teardistance_field.insets = new Insets(0, 0, 5, 0);
        gbc_teardistance_field.fill = GridBagConstraints.BOTH;
        gbc_teardistance_field.gridx = 3;
        gbc_teardistance_field.gridy = 3;
        addmode_panel.add(teardistance_field, gbc_teardistance_field);

        JLabel dampening_label = new JLabel("Dampening");
        dampening_label.setFont(font);
        GridBagConstraints gbc_dampening_label = new GridBagConstraints();
        gbc_dampening_label.fill = GridBagConstraints.VERTICAL;
        gbc_dampening_label.anchor = GridBagConstraints.WEST;
        gbc_dampening_label.insets = new Insets(0, 5, 5, 5);
        gbc_dampening_label.gridx = 0;
        gbc_dampening_label.gridy = 4;
        addmode_panel.add(dampening_label, gbc_dampening_label);

        dampening_field.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_dampening_field = new GridBagConstraints();
        gbc_dampening_field.insets = new Insets(0, 0, 5, 5);
        gbc_dampening_field.fill = GridBagConstraints.BOTH;
        gbc_dampening_field.gridx = 1;
        gbc_dampening_field.gridy = 4;
        addmode_panel.add(dampening_field, gbc_dampening_field);

        JLabel objectmass_label = new JLabel("Mass");
        objectmass_label.setFont(font);
        GridBagConstraints gbc_objectmass_label = new GridBagConstraints();
        gbc_objectmass_label.fill = GridBagConstraints.VERTICAL;
        gbc_objectmass_label.anchor = GridBagConstraints.WEST;
        gbc_objectmass_label.insets = new Insets(0, 0, 5, 5);
        gbc_objectmass_label.gridx = 2;
        gbc_objectmass_label.gridy = 4;
        addmode_panel.add(objectmass_label, gbc_objectmass_label);

        objectmass_field.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_objectmass_field = new GridBagConstraints();
        gbc_objectmass_field.insets = new Insets(0, 0, 5, 0);
        gbc_objectmass_field.fill = GridBagConstraints.BOTH;
        gbc_objectmass_field.gridx = 3;
        gbc_objectmass_field.gridy = 4;
        addmode_panel.add(objectmass_field, gbc_objectmass_field);

        JLabel spacing_label = new JLabel("Spacing");
        spacing_label.setFont(font);
        GridBagConstraints gbc_spacing_label = new GridBagConstraints();
        gbc_spacing_label.fill = GridBagConstraints.VERTICAL;
        gbc_spacing_label.anchor = GridBagConstraints.WEST;
        gbc_spacing_label.insets = new Insets(0, 5, 5, 5);
        gbc_spacing_label.gridx = 0;
        gbc_spacing_label.gridy = 5;
        addmode_panel.add(spacing_label, gbc_spacing_label);

        spacing_field.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_spacing_field = new GridBagConstraints();
        gbc_spacing_field.insets = new Insets(0, 0, 5, 5);
        gbc_spacing_field.fill = GridBagConstraints.BOTH;
        gbc_spacing_field.gridx = 1;
        gbc_spacing_field.gridy = 5;
        addmode_panel.add(spacing_field, gbc_spacing_field);

        JLabel stiffness_label = new JLabel("Stiffness");
        stiffness_label.setFont(font);
        GridBagConstraints gbc_stiffness_label = new GridBagConstraints();
        gbc_stiffness_label.fill = GridBagConstraints.VERTICAL;
        gbc_stiffness_label.insets = new Insets(0, 0, 5, 5);
        gbc_stiffness_label.anchor = GridBagConstraints.WEST;
        gbc_stiffness_label.gridx = 2;
        gbc_stiffness_label.gridy = 5;
        addmode_panel.add(stiffness_label, gbc_stiffness_label);

        stiffness_field.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_stiffness_field = new GridBagConstraints();
        gbc_stiffness_field.insets = new Insets(0, 0, 5, 0);
        gbc_stiffness_field.fill = GridBagConstraints.BOTH;
        gbc_stiffness_field.gridx = 3;
        gbc_stiffness_field.gridy = 5;
        addmode_panel.add(stiffness_field, gbc_stiffness_field);

        JLabel pointcolor_label = new JLabel("Point Color");
        pointcolor_label.setFont(font);
        GridBagConstraints gbc_pointcolor_label = new GridBagConstraints();
        gbc_pointcolor_label.fill = GridBagConstraints.VERTICAL;
        gbc_pointcolor_label.anchor = GridBagConstraints.WEST;
        gbc_pointcolor_label.insets = new Insets(0, 5, 0, 5);
        gbc_pointcolor_label.gridx = 0;
        gbc_pointcolor_label.gridy = 6;
        addmode_panel.add(pointcolor_label, gbc_pointcolor_label);

        pointcolor_panel = new CLabel(Color.GREEN, 1);
        pointcolor_panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Color default_c = pointcolor_panel.getBackground();
                Color c = JColorChooser.showDialog(frame, "Point Color", default_c);
                pointcolor_panel.setBackground((c != null) ? c : default_c);
            }
        });
        GridBagConstraints gbc_pointcolor_panel = new GridBagConstraints();
        gbc_pointcolor_panel.insets = new Insets(0, 0, 0, 5);
        gbc_pointcolor_panel.fill = GridBagConstraints.BOTH;
        gbc_pointcolor_panel.gridx = 1;
        gbc_pointcolor_panel.gridy = 6;
        addmode_panel.add(pointcolor_panel, gbc_pointcolor_panel);

        JLabel linkcolor_label = new JLabel("Link Color");
        linkcolor_label.setFont(font);
        GridBagConstraints gbc_linkcolor_label = new GridBagConstraints();
        gbc_linkcolor_label.fill = GridBagConstraints.VERTICAL;
        gbc_linkcolor_label.anchor = GridBagConstraints.WEST;
        gbc_linkcolor_label.insets = new Insets(0, 0, 0, 5);
        gbc_linkcolor_label.gridx = 2;
        gbc_linkcolor_label.gridy = 6;
        addmode_panel.add(linkcolor_label, gbc_linkcolor_label);

        linkcolor_panel = new CLabel(Color.PINK, 1);
        linkcolor_panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Color default_c = linkcolor_panel.getBackground();
                Color c = JColorChooser.showDialog(frame, "Link Color", default_c);
                linkcolor_panel.setBackground((c != null) ? c : default_c);
            }
        });
        GridBagConstraints gbc_linkcolor_panel = new GridBagConstraints();
        gbc_linkcolor_panel.fill = GridBagConstraints.BOTH;
        gbc_linkcolor_panel.gridx = 3;
        gbc_linkcolor_panel.gridy = 6;
        addmode_panel.add(linkcolor_panel, gbc_linkcolor_panel);

        JPanel selection_panel = new JPanel();
        simeditorpane.addTab("Selection Properties", null, selection_panel, "You can change the selected objects properties here");
        GridBagLayout gbl_selection_panel = new GridBagLayout();
        gbl_selection_panel.columnWidths = new int[]{103, 110, 74, 112, 0};
        gbl_selection_panel.rowHeights = new int[]{0, 31, 30, 27, 0, 29, 0, 0};
        gbl_selection_panel.columnWeights = new double[]{1.0, 1.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_selection_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
        selection_panel.setLayout(gbl_selection_panel);

        JLabel selectionobjectproperties_label = new JLabel("Selection Object Properties");
        selectionobjectproperties_label.setFont(font);
        GridBagConstraints gbc_selectionobjectproperties_label = new GridBagConstraints();
        gbc_selectionobjectproperties_label.gridwidth = 4;
        gbc_selectionobjectproperties_label.insets = new Insets(5, 0, 5, 0);
        gbc_selectionobjectproperties_label.gridx = 0;
        gbc_selectionobjectproperties_label.gridy = 0;
        selection_panel.add(selectionobjectproperties_label, gbc_selectionobjectproperties_label);

        selectionshowpoint_checkbox.setSelected(false);
        selectionshowpoint_checkbox.setFont(font);
        GridBagConstraints gbc_pinned_checkbox = new GridBagConstraints();
        gbc_pinned_checkbox.anchor = GridBagConstraints.WEST;
        gbc_pinned_checkbox.insets = new Insets(0, 5, 5, 5);
        gbc_pinned_checkbox.gridx = 0;
        gbc_pinned_checkbox.gridy = 1;
        selection_panel.add(selectionshowpoint_checkbox, gbc_pinned_checkbox);

        selectioncollidable_checkbox.setSelected(true);
        selectioncollidable_checkbox.setFont(font);
        GridBagConstraints gbc_collidable_checkbox = new GridBagConstraints();
        gbc_collidable_checkbox.anchor = GridBagConstraints.WEST;
        gbc_collidable_checkbox.insets = new Insets(0, 0, 5, 5);
        gbc_collidable_checkbox.gridx = 1;
        gbc_collidable_checkbox.gridy = 1;
        selection_panel.add(selectioncollidable_checkbox, gbc_collidable_checkbox);

        JLabel objectcolor_label = new JLabel("Selection Color(Click to Change)");
        objectcolor_label.setFont(font);
        GridBagConstraints gbc_objectcolor_label = new GridBagConstraints();
        gbc_objectcolor_label.anchor = GridBagConstraints.WEST;
        gbc_objectcolor_label.insets = new Insets(0, 0, 5, 5);
        gbc_objectcolor_label.gridx = 2;
        gbc_objectcolor_label.gridy = 1;
        selection_panel.add(objectcolor_label, gbc_objectcolor_label);

        selectioncolor_panel = new CLabel(Color.CYAN, 1);
        selectioncolor_panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Color default_c = selectioncolor_panel.getBackground();
                Color c = JColorChooser.showDialog(frame, "Color", default_c);
                selectioncolor_panel.setBackground((c != null) ? c : default_c);
            }
        });
        GridBagConstraints gbc_objectcolor_panel = new GridBagConstraints();
        gbc_objectcolor_panel.fill = GridBagConstraints.BOTH;
        gbc_objectcolor_panel.insets = new Insets(0, 0, 5, 0);
        gbc_objectcolor_panel.gridx = 3;
        gbc_objectcolor_panel.gridy = 1;
        selection_panel.add(selectioncolor_panel, gbc_objectcolor_panel);

        JLabel radius_label = new JLabel("Radius");
        radius_label.setFont(font);
        GridBagConstraints gbc_radius_label = new GridBagConstraints();
        gbc_radius_label.anchor = GridBagConstraints.WEST;
        gbc_radius_label.insets = new Insets(0, 5, 5, 5);
        gbc_radius_label.gridx = 0;
        gbc_radius_label.gridy = 2;
        selection_panel.add(radius_label, gbc_radius_label);

        selectionradius_field.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_radius_field = new GridBagConstraints();
        gbc_radius_field.fill = GridBagConstraints.BOTH;
        gbc_radius_field.insets = new Insets(0, 0, 5, 5);
        gbc_radius_field.gridx = 1;
        gbc_radius_field.gridy = 2;
        selection_panel.add(selectionradius_field, gbc_radius_field);

        JLabel mass_label = new JLabel("Mass");
        mass_label.setFont(font);
        GridBagConstraints gbc_mass_label = new GridBagConstraints();
        gbc_mass_label.fill = GridBagConstraints.VERTICAL;
        gbc_mass_label.anchor = GridBagConstraints.WEST;
        gbc_mass_label.insets = new Insets(0, 0, 5, 5);
        gbc_mass_label.gridx = 2;
        gbc_mass_label.gridy = 2;
        selection_panel.add(mass_label, gbc_mass_label);

        selectionmass_field.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_mass_field = new GridBagConstraints();
        gbc_mass_field.fill = GridBagConstraints.BOTH;
        gbc_mass_field.insets = new Insets(0, 0, 5, 0);
        gbc_mass_field.gridx = 3;
        gbc_mass_field.gridy = 2;
        selection_panel.add(selectionmass_field, gbc_mass_field);

        JLabel objectdampening_label = new JLabel("Dampening");
        objectdampening_label.setFont(font);
        GridBagConstraints gbc_objectdampening_label = new GridBagConstraints();
        gbc_objectdampening_label.anchor = GridBagConstraints.WEST;
        gbc_objectdampening_label.insets = new Insets(0, 5, 5, 5);
        gbc_objectdampening_label.gridx = 0;
        gbc_objectdampening_label.gridy = 3;
        selection_panel.add(objectdampening_label, gbc_objectdampening_label);

        selectiondampening_field.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_objectdampening_field = new GridBagConstraints();
        gbc_objectdampening_field.fill = GridBagConstraints.BOTH;
        gbc_objectdampening_field.insets = new Insets(0, 0, 5, 5);
        gbc_objectdampening_field.gridx = 1;
        gbc_objectdampening_field.gridy = 3;
        selection_panel.add(selectiondampening_field, gbc_objectdampening_field);

        JLabel objectstiffness_label = new JLabel("Stiffness");
        objectstiffness_label.setFont(font);
        GridBagConstraints gbc_objectstiffness_label = new GridBagConstraints();
        gbc_objectstiffness_label.anchor = GridBagConstraints.WEST;
        gbc_objectstiffness_label.insets = new Insets(0, 0, 5, 5);
        gbc_objectstiffness_label.gridx = 2;
        gbc_objectstiffness_label.gridy = 3;
        selection_panel.add(objectstiffness_label, gbc_objectstiffness_label);

        selectionstiffness_field.setHorizontalAlignment(SwingConstants.CENTER);

        GridBagConstraints gbc_objectstiffness_field = new GridBagConstraints();
        gbc_objectstiffness_field.fill = GridBagConstraints.BOTH;
        gbc_objectstiffness_field.insets = new Insets(0, 0, 5, 0);
        gbc_objectstiffness_field.gridx = 3;
        gbc_objectstiffness_field.gridy = 3;
        selection_panel.add(selectionstiffness_field, gbc_objectstiffness_field);

        objectsetproperties_button = new JButton("Set Object Properties");
        objectsetproperties_button.setFont(font);
        objectsetproperties_button.addActionListener(e -> setObjectProperties());
        GridBagConstraints gbc_objectsetproperties_button = new GridBagConstraints();
        gbc_objectsetproperties_button.fill = GridBagConstraints.BOTH;
        gbc_objectsetproperties_button.gridwidth = 4;
        gbc_objectsetproperties_button.insets = new Insets(0, 0, 5, 0);
        gbc_objectsetproperties_button.gridx = 0;
        gbc_objectsetproperties_button.gridy = 4;
        selection_panel.add(objectsetproperties_button, gbc_objectsetproperties_button);

        JLabel lblConstraintProperties = new JLabel("<html><h3>Selection Constraint Properties <br> <span style='color: green'>Green = Selected Point</span>" +
                " <br> <span style='color: red'>Red = Selected Constraint</span></h3></html>");
        lblConstraintProperties.setFont(font);
        GridBagConstraints gbc_lblConstraintProperties = new GridBagConstraints();
        gbc_lblConstraintProperties.insets = new Insets(0, 0, 5, 0);
        gbc_lblConstraintProperties.gridwidth = 4;
        gbc_lblConstraintProperties.gridx = 0;
        gbc_lblConstraintProperties.gridy = 5;
        selection_panel.add(lblConstraintProperties, gbc_lblConstraintProperties);

        JScrollPane scrollPane = new JScrollPane();
        GridBagConstraints gbc_scrollPane = new GridBagConstraints();
        gbc_scrollPane.gridwidth = 4;
        gbc_scrollPane.fill = GridBagConstraints.BOTH;
        gbc_scrollPane.gridx = 0;
        gbc_scrollPane.gridy = 6;
        selection_panel.add(scrollPane, gbc_scrollPane);

        JPanel panel = new JPanel();
        scrollPane.setViewportView(panel);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{120, 150, 66, 72, 0};
        gbl_panel.rowHeights = new int[]{36, 50, 43, 41, 44, 47, 0};
        gbl_panel.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
        gbl_panel.rowWeights = new double[]{1.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        JScrollPane scrollPane_1 = new JScrollPane();
        GridBagConstraints gbc_scrollPane_1 = new GridBagConstraints();
        gbc_scrollPane_1.gridheight = 6;
        gbc_scrollPane_1.insets = new Insets(0, 0, 0, 5);
        gbc_scrollPane_1.fill = GridBagConstraints.BOTH;
        gbc_scrollPane_1.gridx = 0;
        gbc_scrollPane_1.gridy = 0;
        panel.add(scrollPane_1, gbc_scrollPane_1);

        JLabel lblConstraintList = new JLabel("Constraint List");
        lblConstraintList.setHorizontalAlignment(SwingConstants.CENTER);
        lblConstraintList.setFont(font);
        scrollPane_1.setColumnHeaderView(lblConstraintList);

        JPanel panel_2 = new JPanel();
        panel_2.setFont(font);
        scrollPane_1.setViewportView(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane_2 = new JScrollPane();
        panel_2.add(scrollPane_2, BorderLayout.CENTER);

        constraintJlist.setSelectedIndex(0);
        constraintJlist.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        constraintJlist.setFont(font);
        scrollPane_2.setViewportView(constraintJlist);

        JLabel label = new JLabel("Constraint Properties");
        label.setFont(font);
        GridBagConstraints gbc_label = new GridBagConstraints();
        gbc_label.gridwidth = 3;
        gbc_label.insets = new Insets(5, 0, 5, 0);
        gbc_label.gridx = 1;
        gbc_label.gridy = 0;
        panel.add(label, gbc_label);

        showselectionconstraint_checkbox.setSelected(false);
        showselectionconstraint_checkbox.setFont(font);
        GridBagConstraints gbc_checkBox = new GridBagConstraints();
        gbc_checkBox.fill = GridBagConstraints.VERTICAL;
        gbc_checkBox.anchor = GridBagConstraints.WEST;
        gbc_checkBox.insets = new Insets(0, 0, 5, 5);
        gbc_checkBox.gridx = 1;
        gbc_checkBox.gridy = 1;
        panel.add(showselectionconstraint_checkbox, gbc_checkBox);

        constraintdrawlink_checkbox.setSelected(true);
        constraintdrawlink_checkbox.setFont(font);
        GridBagConstraints gbc_checkBox_1 = new GridBagConstraints();
        gbc_checkBox_1.fill = GridBagConstraints.VERTICAL;
        gbc_checkBox_1.anchor = GridBagConstraints.WEST;
        gbc_checkBox_1.insets = new Insets(0, 0, 5, 5);
        gbc_checkBox_1.gridx = 2;
        gbc_checkBox_1.gridy = 1;
        panel.add(constraintdrawlink_checkbox, gbc_checkBox_1);

        constrainttearable_checkbox.setSelected(true);
        constrainttearable_checkbox.setFont(font);
        GridBagConstraints gbc_checkBox_2 = new GridBagConstraints();
        gbc_checkBox_2.fill = GridBagConstraints.VERTICAL;
        gbc_checkBox_2.anchor = GridBagConstraints.WEST;
        gbc_checkBox_2.gridwidth = 2;
        gbc_checkBox_2.insets = new Insets(0, 0, 5, 0);
        gbc_checkBox_2.gridx = 3;
        gbc_checkBox_2.gridy = 1;
        panel.add(constrainttearable_checkbox, gbc_checkBox_2);

        JLabel constraint_stiffness_label = new JLabel("Stiffness");
        constraint_stiffness_label.setFont(font);
        GridBagConstraints gbc_label_1 = new GridBagConstraints();
        gbc_label_1.fill = GridBagConstraints.VERTICAL;
        gbc_label_1.anchor = GridBagConstraints.WEST;
        gbc_label_1.insets = new Insets(0, 5, 5, 5);
        gbc_label_1.gridx = 1;
        gbc_label_1.gridy = 2;
        panel.add(constraint_stiffness_label, gbc_label_1);

        constraint_stiffness_field.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_textField = new GridBagConstraints();
        gbc_textField.fill = GridBagConstraints.BOTH;
        gbc_textField.gridwidth = 2;
        gbc_textField.insets = new Insets(0, 0, 5, 0);
        gbc_textField.gridx = 2;
        gbc_textField.gridy = 2;
        panel.add(constraint_stiffness_field, gbc_textField);

        JLabel label_2 = new JLabel("Tear Distance");
        label_2.setFont(font);
        GridBagConstraints gbc_label_2 = new GridBagConstraints();
        gbc_label_2.fill = GridBagConstraints.VERTICAL;
        gbc_label_2.anchor = GridBagConstraints.WEST;
        gbc_label_2.insets = new Insets(0, 5, 5, 5);
        gbc_label_2.gridx = 1;
        gbc_label_2.gridy = 3;
        panel.add(label_2, gbc_label_2);

        constraint_teardistance_field.setHorizontalAlignment(SwingConstants.CENTER);
        GridBagConstraints gbc_textField_1 = new GridBagConstraints();
        gbc_textField_1.fill = GridBagConstraints.BOTH;
        gbc_textField_1.gridwidth = 2;
        gbc_textField_1.insets = new Insets(0, 0, 5, 0);
        gbc_textField_1.gridx = 2;
        gbc_textField_1.gridy = 3;
        panel.add(constraint_teardistance_field, gbc_textField_1);

        JLabel constraint_link_color_label = new JLabel("Link Color(Click to Change)");
        constraint_link_color_label.setFont(font);
        GridBagConstraints gbc_label_3 = new GridBagConstraints();
        gbc_label_3.fill = GridBagConstraints.VERTICAL;
        gbc_label_3.anchor = GridBagConstraints.WEST;
        gbc_label_3.insets = new Insets(0, 5, 5, 5);
        gbc_label_3.gridx = 1;
        gbc_label_3.gridy = 4;
        panel.add(constraint_link_color_label, gbc_label_3);

        constraint_link_panel = new CLabel(Color.BLUE, 1);
        constraint_link_panel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                Color default_c = constraint_link_panel.getBackground();
                Color c = JColorChooser.showDialog(frame, "Link Color", default_c);
                constraint_link_panel.setBackground((c != null) ? c : default_c);
            }
        });
        GridBagConstraints gbc_panel_1 = new GridBagConstraints();
        gbc_panel_1.fill = GridBagConstraints.BOTH;
        gbc_panel_1.gridwidth = 2;
        gbc_panel_1.insets = new Insets(0, 0, 5, 0);
        gbc_panel_1.gridx = 2;
        gbc_panel_1.gridy = 4;
        panel.add(constraint_link_panel, gbc_panel_1);

        setconstraintsbutton = new JButton("Set Constraint Properties");
        setconstraintsbutton.addActionListener(e -> setObjectConstraints());
        setconstraintsbutton.setFont(font);
        GridBagConstraints gbc_button = new GridBagConstraints();
        gbc_button.fill = GridBagConstraints.BOTH;
        gbc_button.gridwidth = 3;
        gbc_button.gridx = 1;
        gbc_button.gridy = 5;
        panel.add(setconstraintsbutton, gbc_button);

        if (selectedVertex != null) setObjectPropertiesOnSelect(selectedVertex);

        frame.setVisible(true);
    }

    private void setObjectProperties() {
        if (selectedVertex != null) {
            String rad = selectionradius_field.getText();
            String mass = selectionmass_field.getText();
            String dampening = selectiondampening_field.getText();
            String stiffness = selectionstiffness_field.getText();

            selectedVertex.collidable = selectioncollidable_checkbox.isSelected();
            selectedVertex.color = selectioncolor_panel.getBackground();

            if (InputGuard.canParseStringFloat(rad)) selectedVertex.radius = Float.parseFloat(rad);
            if (InputGuard.canParseStringFloat(mass)) selectedVertex.mass = Float.parseFloat(mass);
            if (InputGuard.canParseStringFloat(dampening)) selectedVertex.damping = Float.parseFloat(dampening);
            if (InputGuard.canParseStringFloat(stiffness)) selectedVertex.stiffness = Float.parseFloat(stiffness);
        }
    }

    private void setObjectConstraints() {
        if (selectedVertex != null) {
            String cStiffness = constraint_stiffness_field.getText();
            String cTeardist = constraint_teardistance_field.getText();

            List<Integer> selectedValues = constraintJlist.getSelectedValuesList();

            if (!selectedValues.isEmpty()) {
                for (int i = 0; i < selectedValues.size(); i++) {
                    Edge c = selectedVertex.edges.get(selectedValues.get(i));

                    c.drawThis = constraintdrawlink_checkbox.isSelected();
                    c.tearable = constrainttearable_checkbox.isSelected();
                    c.color = constraint_link_panel.getBackground();
                    c.stiffness = InputGuard.floatTextfieldGuardDefault(0.0f, c.stiffness, cStiffness);
                    c.tearSensitivity = InputGuard.floatTextfieldGuardDefault(0.0f, c.tearSensitivity, cTeardist);
                }
            }
        }
    }

    public void unsetObjectPropertiesOnDeselect() {
        if (vPhysicsEditorInstance != null) {
            selectiondampening_field.setText("");
            selectionmass_field.setText("");
            selectionradius_field.setText("");
            selectionstiffness_field.setText("");
        }
    }

    public void setObjectPropertiesOnSelect(Vertex v) {
        if (vPhysicsEditorInstance != null) {
            selectioncolor_panel.setBackground(v.color);
            selectioncollidable_checkbox.setSelected(v.collidable);
            selectiondampening_field.setText("" + v.damping);
            selectionmass_field.setText("" + v.mass);
            selectionradius_field.setText("" + v.radius);
            selectionstiffness_field.setText("" + v.stiffness);
        }
    }

    private void setSimulationProperties() {
        // Setting Simulation Properties
        String simacc = simacc_field.getText(), dragforce = dragforce_field.getText(),
                grav = gravity_field.getText(), airvis = airviscosity_field.getText();

        VSim.SIM_ACCURACY = InputGuard.intTextfieldGuardDefault(1, VSim.SIM_ACCURACY, simacc);
        VSim.dragForce = InputGuard.floatTextfieldGuardDefault(0.0f, VSim.dragForce, dragforce);
        VSim.GConstant = guardFloat(VSim.GConstant, grav);
        VSim.air_viscosity = InputGuard.floatTextfieldGuardDefault(0.0f, VSim.air_viscosity, airvis);
    }

    private float guardFloat(float default_, String value) {
        if (value.isEmpty()) return default_;
        else return Float.parseFloat(value);
    }

    private void close() {
        frame.dispose();
        vPhysicsEditorInstance = null;
    }

    public void updateJListConstraints(List<Edge> edgeList) {
        if (vPhysicsEditorInstance != null) {
            listModel.clear();
            if (edgeList == null || edgeList.isEmpty()) listModel.addElement(-1);
            else {
                for (int i = 0; i < edgeList.size(); i++) listModel.addElement(i);
                constraintJlist.setSelectedIndex(0);
            }
        }
    }
}
