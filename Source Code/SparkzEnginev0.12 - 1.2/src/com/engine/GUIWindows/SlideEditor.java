package com.engine.GUIWindows;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.JComponents.CSlider;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;

import static com.engine.EngineHelpers.EConstants.EFrame;
import static com.engine.EngineHelpers.EFLOATS.*;
import static com.engine.EngineHelpers.EINTS.*;

public class SlideEditor {
    private static SlideEditor sliderUI = null;

    private static Font font = new Font(Font.SERIF, Font.PLAIN, 17);

    private final int MAX_TICK_LINES = 500;
    public JFrame frame;
    public JPanel ptoptionspanel, rfoptionspanel, ptssopanel, ptspeedspanel;

    public JSlider ptsslider, ptdrslider, ptfrslider,
            ptlifeslider, ptwindslider, ptjitterslider,
            ptscminslider, ptscmaxslider, ptfrmaxslider,
            ptfrminslider, ptdragminslider, ptdragmaxslider,
            ptdrspeedslider, ptfrspeedminslider, ptscspeedminslider;

    private JLabel ptslabel, ptsamount, drlabel, dramount, frlabel,
            framount, ptlifelabel, ptlifeamount, ptwindlabel, ptwindamount,
            ptjitterlabel, ptjitteramount, ptscslabel, ptscminlabel, ptscmaxlabel,
            ptfrslabel, ptfrmaxlabel, ptfrminlabel, ptdragmaxlabel, ptdragminlabel,
            ptdraglabel, ptscspeedlabel, ptscspeedmin, ptfrspeedlabel, ptfrspeedminlabel, ptdrspeedlabel,
            ptdrminspeedlabel, lblMin, lblMax, label, label_1, label_2, label_3;

    //public static void main(String[] args) {new SlideEditor();}

    public static SlideEditor getInstance() {
        if (sliderUI == null)
            sliderUI = new SlideEditor();

        sliderUI.frame.toFront();

        return sliderUI;
    }

    private SlideEditor() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }
        frame = new JFrame("Particle Slide Editor");
        frame.setIconImage(Settings.iconImage);
        frame.setSize(489, 690);
        frame.setResizable(false);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(e -> close()));
        frame.setLocationRelativeTo(EFrame);

        JTabbedPane jTabbedPane = new JTabbedPane(JTabbedPane.TOP);
        jTabbedPane.setBounds(0, 0, 483, 653);
        frame.add(jTabbedPane);

        ptoptionspanel = new JPanel(null);
        jTabbedPane.addTab("Particle Options", null, ptoptionspanel, null);

        int MAX_TICKS = 49;
        ptsslider = new CSlider(0, 200, 1, new Rectangle(10, 60, 463, 43), 1, MAX_TICKS, true, true, true);
        ptsslider.addChangeListener(e -> {
            int particleSize = ptsslider.getValue();
            ptsamount.setText("" + particleSize);
            EngineMethods.setParticleSize(particleSize);
            setPaintLabels(ptsslider, MAX_TICK_LINES);
        });
        ptoptionspanel.add(ptsslider);

        ptslabel = new JLabel("Particle Size:");
        ptslabel.setFont(font);

        ptslabel.setBounds(10, 32, 106, 22);
        ptoptionspanel.add(ptslabel);

        ptsamount = new JLabel("" + ptsslider.getValue());
        ptsamount.setFont(font);
        ptsamount.setHorizontalAlignment(JLabel.CENTER);
        ptsamount.setBounds(149, 32, 187, 22);
        ptoptionspanel.add(ptsamount);

        drlabel = new JLabel("Drag Amount:");
        drlabel.setFont(font);
        drlabel.setBounds(10, 245, 106, 22);
        ptoptionspanel.add(drlabel);

        ptdrslider = new CSlider(0, 200, PARTICLE_DRAG_AMOUNT.value(), new Rectangle(10, 273, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdrslider.addChangeListener(e -> {
            int particleDrag = ptdrslider.getValue();
            dramount.setText(Integer.toString(particleDrag));
            EngineMethods.setDragAmount(particleDrag);
            setPaintLabels(ptdrslider, MAX_TICK_LINES);
        });
        ptoptionspanel.add(ptdrslider);

        dramount = new JLabel(PARTICLE_DRAG_AMOUNT.getValueS());
        dramount.setFont(font);
        dramount.setHorizontalAlignment(JLabel.CENTER);
        dramount.setBounds(149, 245, 187, 22);
        ptoptionspanel.add(dramount);

        frlabel = new JLabel("Fireworks Amount:");
        frlabel.setFont(font);
        frlabel.setBounds(10, 469, 142, 22);
        ptoptionspanel.add(frlabel);

        ptfrslider = new CSlider(0, 400, FIREWORKS_AMOUNT.value(), new Rectangle(10, 497, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrslider.addChangeListener(e -> {
            int fireworksAmount1 = ptfrslider.getValue();
            framount.setText(Integer.toString(fireworksAmount1));
            EngineMethods.setFireworksAmount(fireworksAmount1);
            setPaintLabels(ptfrslider, MAX_TICK_LINES);
        });
        ptoptionspanel.add(ptfrslider);

        framount = new JLabel(FIREWORKS_AMOUNT.value() + "");
        framount.setFont(font);
        framount.setHorizontalAlignment(JLabel.CENTER);
        framount.setBounds(169, 469, 187, 22);
        ptoptionspanel.add(framount);

        JButton btnEditSize = new JButton("Edit Size");
        btnEditSize.addActionListener(e -> EngineMethods.setMaxParticleSizeSlider());
        btnEditSize.setFont(font);
        btnEditSize.setBounds(169, 147, 137, 39);
        ptoptionspanel.add(btnEditSize);

        JButton btnEditDrag = new JButton("Edit Drag");
        btnEditDrag.addActionListener(e -> EngineMethods.setMaxDragSlider());
        btnEditDrag.setFont(font);
        btnEditDrag.setBounds(172, 363, 134, 39);
        ptoptionspanel.add(btnEditDrag);

        JButton btnEditFireworks = new JButton("Edit Fireworks");
        btnEditFireworks.addActionListener(e -> EngineMethods.setMaxFireworksSlider());
        btnEditFireworks.setFont(font);
        btnEditFireworks.setBounds(169, 561, 144, 39);
        ptoptionspanel.add(btnEditFireworks);

        rfoptionspanel = new JPanel(null);
        jTabbedPane.addTab("Real Fireworks Options", null, rfoptionspanel, null);

        ptlifelabel = new JLabel("Particle Life:");
        ptlifelabel.setFont(font);
        ptlifelabel.setBounds(10, 39, 106, 22);
        rfoptionspanel.add(ptlifelabel);

        ptlifeamount = new JLabel(PARTICLE_BASE_LIFE.getValueS());
        ptlifeamount.setFont(font);
        ptlifeamount.setHorizontalAlignment(JLabel.CENTER);
        ptlifeamount.setBounds(149, 39, 187, 22);
        rfoptionspanel.add(ptlifeamount);

        ptlifeslider = new CSlider(0, 200, PARTICLE_BASE_LIFE.value(), new Rectangle(10, 67, 463, 43), 1, MAX_TICKS, true, true, true);
        ptlifeslider.addChangeListener(e -> {
            int lifeAmount = ptlifeslider.getValue();
            ptlifeamount.setText(Integer.toString(lifeAmount));
            EngineMethods.setLifeAmount(lifeAmount);
            setPaintLabels(ptlifeslider, MAX_TICK_LINES);
        });
        rfoptionspanel.add(ptlifeslider);

        ptwindslider = new CSlider(0, 200, FIREWORKS_WIND.value(), new Rectangle(10, 257, 463, 43), 1, MAX_TICKS, true, true, true);
        ptwindslider.addChangeListener(e -> {
            int windAmount = ptwindslider.getValue();
            ptwindamount.setText(Integer.toString(windAmount));
            EngineMethods.setWindAmount(windAmount);
            setPaintLabels(ptwindslider, MAX_TICK_LINES);
        });
        rfoptionspanel.add(ptwindslider);

        ptwindamount = new JLabel(FIREWORKS_WIND.getValueS());
        ptwindamount.setFont(font);
        ptwindamount.setHorizontalAlignment(JLabel.CENTER);
        ptwindamount.setBounds(149, 229, 187, 22);
        rfoptionspanel.add(ptwindamount);

        ptwindlabel = new JLabel("Particle Wind:");
        ptwindlabel.setFont(font);
        ptwindlabel.setBounds(10, 229, 106, 22);
        rfoptionspanel.add(ptwindlabel);

        ptjitterslider = new CSlider(0, 200, FIREWORKS_JITTER.value(), new Rectangle(10, 468, 463, 43), 1, MAX_TICKS, true, true, true);
        ptjitterslider.addChangeListener(e -> {
            int jitterAmount = ptjitterslider.getValue();
            ptjitteramount.setText(Integer.toString(jitterAmount));
            EngineMethods.setJitterAmount(jitterAmount);
            setPaintLabels(ptjitterslider, MAX_TICK_LINES);
        });
        rfoptionspanel.add(ptjitterslider);

        ptjitteramount = new JLabel(FIREWORKS_JITTER.getValueS());
        ptjitteramount.setFont(font);
        ptjitteramount.setHorizontalAlignment(JLabel.CENTER);
        ptjitteramount.setBounds(149, 440, 187, 22);
        rfoptionspanel.add(ptjitteramount);

        ptjitterlabel = new JLabel("Particle Jitter:");
        ptjitterlabel.setFont(font);
        ptjitterlabel.setBounds(10, 440, 106, 22);
        rfoptionspanel.add(ptjitterlabel);

        JButton btnEditBaseLife = new JButton("<html>Edit Base Life <br> <h4 style='text-align:center;color:blue'>(Single Particle)</h4></html>");
        btnEditBaseLife.addActionListener(e -> EngineMethods.baseLifeAmount());
        btnEditBaseLife.setFont(font);
        btnEditBaseLife.setBounds(27, 134, 186, 66);
        rfoptionspanel.add(btnEditBaseLife);

        JButton btnEditRfLife = new JButton("<html>Edit Real Fireworks Life <br> <h4 style='text-align:center;color:blue'>(Explosion Fireworks)</h4></html>");
        btnEditRfLife.addActionListener(e -> EngineMethods.rfLifeAmount());
        btnEditRfLife.setFont(font);
        btnEditRfLife.setBounds(227, 134, 226, 66);
        rfoptionspanel.add(btnEditRfLife);

        JButton btnEditWind = new JButton("Edit Wind");
        btnEditWind.addActionListener(e -> EngineMethods.windAmount());
        btnEditWind.setFont(font);
        btnEditWind.setBounds(177, 344, 139, 36);
        rfoptionspanel.add(btnEditWind);

        JButton btnEditJitter = new JButton("Edit Jitter");
        btnEditJitter.addActionListener(e -> EngineMethods.jitterAmount());
        btnEditJitter.setFont(font);
        btnEditJitter.setBounds(177, 555, 137, 36);
        rfoptionspanel.add(btnEditJitter);

        //Particle Size Seed
        ptssopanel = new JPanel(null);
        jTabbedPane.addTab("Particle Size Options", null, ptssopanel, null);

        ptscslabel = new JLabel("Single Click Size:");
        ptscslabel.setForeground(Color.BLACK);
        ptscslabel.setFont(font);
        ptscslabel.setBounds(10, 25, 131, 22);
        ptssopanel.add(ptscslabel);

        ptscminlabel = new JLabel(SINGLE_CLICK_SIZE_MIN.valueS());
        ptscminlabel.setFont(font);
        ptscminlabel.setHorizontalAlignment(JLabel.CENTER);
        ptscminlabel.setBounds(256, 25, 146, 22);
        ptssopanel.add(ptscminlabel);

        ptscminslider = new CSlider(0, 200, (int) SINGLE_CLICK_SIZE_MIN.value(), new Rectangle(10, 53, 463, 43), 1, MAX_TICKS, true, true, true);
        ptscminslider.addChangeListener(e -> {
            int singleclickmin = ptscminslider.getValue();
            ptscminlabel.setText(Integer.toString(singleclickmin));
            EngineMethods.setSingleClickSizeMin(singleclickmin);
            setPaintLabels(ptscminslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptscminslider);

        ptscmaxslider = new CSlider(0, 200, (int) SINGLE_CLICK_SIZE_MAX.value(), new Rectangle(10, 152, 463, 43), 1, MAX_TICKS, true, true, true);
        ptscmaxslider.addChangeListener(e -> {
            int singleclickmax = ptscmaxslider.getValue();
            ptscmaxlabel.setText(Integer.toString(singleclickmax));
            EngineMethods.setSingleClickSizeMax(singleclickmax);
            setPaintLabels(ptscmaxslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptscmaxslider);

        ptscmaxlabel = new JLabel(SINGLE_CLICK_SIZE_MAX.valueS());
        ptscmaxlabel.setFont(font);
        ptscmaxlabel.setHorizontalAlignment(JLabel.CENTER);
        ptscmaxlabel.setBounds(256, 124, 146, 22);
        ptssopanel.add(ptscmaxlabel);

        //Fireworks
        ptfrmaxslider = new CSlider(0, 200, 1, new Rectangle(10, 354, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrmaxslider.addChangeListener(e -> {
            int fireworksmax = ptfrmaxslider.getValue();
            ptfrmaxlabel.setText(Integer.toString(fireworksmax));
            EngineMethods.setFireworksSizeMax(fireworksmax);
            setPaintLabels(ptfrmaxslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptfrmaxslider);

        ptfrmaxlabel = new JLabel(FIREWORKS_SIZE_MAX.valueS());
        ptfrmaxlabel.setFont(font);
        ptfrmaxlabel.setHorizontalAlignment(JLabel.CENTER);
        ptfrmaxlabel.setBounds(256, 326, 146, 22);
        ptssopanel.add(ptfrmaxlabel);

        ptfrminslider = new CSlider(0, 200, 1, new Rectangle(10, 255, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrminslider.addChangeListener(e -> {
            int fireworksmin = ptfrminslider.getValue();
            ptfrminlabel.setText(Integer.toString(fireworksmin));
            EngineMethods.setFireworksSizeMin(fireworksmin);
            setPaintLabels(ptfrminslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptfrminslider);

        ptfrminlabel = new JLabel(FIREWORKS_SIZE_MIN.valueS());
        ptfrminlabel.setFont(font);
        ptfrminlabel.setHorizontalAlignment(JLabel.CENTER);
        ptfrminlabel.setBounds(256, 227, 146, 22);
        ptssopanel.add(ptfrminlabel);

        ptfrslabel = new JLabel("Fireworks Size:");
        ptfrslabel.setForeground(Color.BLACK);
        ptfrslabel.setFont(font);
        ptfrslabel.setBounds(10, 227, 117, 22);
        ptssopanel.add(ptfrslabel);

        //Drag
        ptdragmaxslider = new CSlider(0, 200, (int) PARTICLE_DRAG_SIZE_MAX.value(), new Rectangle(10, 555, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdragmaxslider.addChangeListener(e -> {
            int dragmax = ptdragmaxslider.getValue();
            ptdragmaxlabel.setText(Integer.toString(dragmax));
            EngineMethods.setDragSizeMax(dragmax);
            setPaintLabels(ptdragmaxslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptdragmaxslider);

        ptdragmaxlabel = new JLabel(PARTICLE_DRAG_SIZE_MAX.valueS());
        ptdragmaxlabel.setFont(font);
        ptdragmaxlabel.setHorizontalAlignment(JLabel.CENTER);
        ptdragmaxlabel.setBounds(256, 527, 146, 22);
        ptssopanel.add(ptdragmaxlabel);

        ptdragminslider = new CSlider(0, 200, (int) PARTICLE_DRAG_SIZE_MIN.value(), new Rectangle(10, 456, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdragminslider.addChangeListener(e -> {
            int dragmin = ptdragminslider.getValue();
            ptdragminlabel.setText(Integer.toString(dragmin));
            EngineMethods.setDragSizeMin(dragmin);
            setPaintLabels(ptdragminslider, MAX_TICK_LINES);
        });
        ptssopanel.add(ptdragminslider);

        ptdragminlabel = new JLabel(PARTICLE_DRAG_SIZE_MIN.valueS());
        ptdragminlabel.setFont(font);
        ptdragminlabel.setHorizontalAlignment(JLabel.CENTER);
        ptdragminlabel.setBounds(256, 428, 146, 22);
        ptssopanel.add(ptdragminlabel);

        ptdraglabel = new JLabel("Drag Size:");
        ptdraglabel.setForeground(Color.BLACK);
        ptdraglabel.setFont(font);
        ptdraglabel.setBounds(10, 428, 82, 22);
        ptssopanel.add(ptdraglabel);

        lblMin = new JLabel("Min:");
        lblMin.setFont(font);
        lblMin.setBounds(189, 25, 46, 14);
        ptssopanel.add(lblMin);

        lblMax = new JLabel("Max:");
        lblMax.setFont(font);
        lblMax.setBounds(189, 124, 46, 14);
        ptssopanel.add(lblMax);

        label = new JLabel("Min:");
        label.setFont(font);
        label.setBounds(189, 227, 46, 14);
        ptssopanel.add(label);

        label_1 = new JLabel("Max:");
        label_1.setFont(font);
        label_1.setBounds(189, 326, 46, 14);
        ptssopanel.add(label_1);

        label_2 = new JLabel("Min:");
        label_2.setFont(font);
        label_2.setBounds(189, 428, 46, 14);
        ptssopanel.add(label_2);

        label_3 = new JLabel("Max:");
        label_3.setFont(font);
        label_3.setBounds(189, 527, 46, 14);
        ptssopanel.add(label_3);

        //Particle speed options
        ptspeedspanel = new JPanel(null);
        jTabbedPane.addTab("Particle Speed Options", null, ptspeedspanel, null);

        ptscspeedlabel = new JLabel("Single Click Speed:");
        ptscspeedlabel.setForeground(Color.BLACK);
        ptscspeedlabel.setFont(font);
        ptscspeedlabel.setBounds(10, 21, 146, 22);
        ptspeedspanel.add(ptscspeedlabel);

        ptscspeedmin = new JLabel(SINGLE_CLICK_SPEED.valueS());
        ptscspeedmin.setFont(font);
        ptscspeedmin.setHorizontalAlignment(JLabel.CENTER);
        ptscspeedmin.setBounds(190, 21, 146, 22);
        ptspeedspanel.add(ptscspeedmin);

        ptscspeedminslider = new CSlider(0, 200, (SINGLE_CLICK_SPEED.value() <= 1) ? 1 : (int) (SINGLE_CLICK_SPEED.value()), new Rectangle(10, 82, 463, 43), 1, MAX_TICKS, true, true, true);
        ptscspeedminslider.addChangeListener(e -> {
            int singleclickspeed = ptscspeedminslider.getValue();
            ptscspeedmin.setText(Integer.toString(singleclickspeed));
            EngineMethods.setSingleClickSpeed(singleclickspeed);
            setPaintLabels(ptscspeedminslider, MAX_TICK_LINES);
        });
        ptspeedspanel.add(ptscspeedminslider);

        //fireworks
        ptfrspeedminlabel = new JLabel(FIREWORKS_SPEED.valueS());
        ptfrspeedminlabel.setFont(font);
        ptfrspeedminlabel.setHorizontalAlignment(JLabel.CENTER);
        ptfrspeedminlabel.setBounds(190, 223, 146, 22);
        ptspeedspanel.add(ptfrspeedminlabel);

        ptfrspeedlabel = new JLabel("Fireworks Speed:");
        ptfrspeedlabel.setForeground(Color.BLACK);
        ptfrspeedlabel.setFont(font);
        ptfrspeedlabel.setBounds(10, 223, 132, 22);
        ptspeedspanel.add(ptfrspeedlabel);

        ptfrspeedminslider = new CSlider(0, 200, (FIREWORKS_SPEED.value() <= 1) ? 1 : (int) (FIREWORKS_SPEED.value()), new Rectangle(10, 284, 463, 43), 1, MAX_TICKS, true, true, true);
        ptfrspeedminslider.addChangeListener(e -> {
            int fireworksSpeed = ptfrspeedminslider.getValue();
            ptfrspeedminlabel.setText(Integer.toString(fireworksSpeed));
            EngineMethods.setFireworksSpeed(fireworksSpeed);
            setPaintLabels(ptfrspeedminslider, MAX_TICK_LINES);
        });
        ptspeedspanel.add(ptfrspeedminslider);

        //drag
        ptdrspeedlabel = new JLabel("Drag Speed:");
        ptdrspeedlabel.setForeground(Color.BLACK);
        ptdrspeedlabel.setFont(font);
        ptdrspeedlabel.setBounds(10, 424, 97, 22);
        ptspeedspanel.add(ptdrspeedlabel);

        ptdrminspeedlabel = new JLabel(PARTICLE_DRAG_SPEED.valueS());
        ptdrminspeedlabel.setFont(font);
        ptdrminspeedlabel.setHorizontalAlignment(JLabel.CENTER);
        ptdrminspeedlabel.setBounds(190, 424, 146, 22);
        ptspeedspanel.add(ptdrminspeedlabel);

        ptdrspeedslider = new CSlider(0, 200, (PARTICLE_DRAG_SPEED.value() <= 1) ? 1 : (int) (PARTICLE_DRAG_SPEED.value()), new Rectangle(10, 485, 463, 43), 1, MAX_TICKS, true, true, true);
        ptdrspeedslider.addChangeListener(e -> {
            int dragSpeed = ptdrspeedslider.getValue();
            ptdrminspeedlabel.setText(Integer.toString(dragSpeed));
            EngineMethods.setDragSpeed(dragSpeed);
            setPaintLabels(ptdrspeedslider, MAX_TICK_LINES);
        });
        ptspeedspanel.add(ptdrspeedslider);
        frame.setVisible(true);
    }

    private static void setPaintLabels(JSlider slider, int maxTickLines) {
        if (slider.getMaximum() > maxTickLines) {
            slider.setPaintLabels(false);
        } else {
            slider.setPaintLabels(true);
        }
    }

    public void close() {
        frame.dispose();
        sliderUI = null;
    }
}