package com.engine.GUIWindows;

import com.engine.J8Helpers.Extensions.KAdapter;
import com.engine.J8Helpers.Extensions.WindowClosing;
import com.engine.Utilities.Settings;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

import static java.awt.Component.LEFT_ALIGNMENT;

public class SampleFunctions {
    private static SampleFunctions sampleFunctions = null;
    public JFrame frame;

    //public static void main(String[] args) { getInstance(null);}

    public static SampleFunctions getInstance(JFrame parent) {
        if (sampleFunctions == null) sampleFunctions = new SampleFunctions(parent);

        return sampleFunctions;
    }

    private SampleFunctions(JFrame parent) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            EException.append(e);
        }

        frame = new JFrame();
        frame.setIconImage(Settings.iconImage);
        frame.setSize(444, 322);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowClosing(windowEvent -> close()));
        frame.setLocationRelativeTo(parent);

        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new BorderLayout(0, 0));

        DefaultListModel<String> listModel = new DefaultListModel<>();
        try {
            InputStream fin = getClass().getResourceAsStream("/ParticleGraphSamples.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(fin));
            String line;
            while ((line = br.readLine()) != null) {
                listModel.addElement(line.trim());
            }
            br.close();
            fin.close();
        } catch (Exception e) {
            EException.append(e);
        }

        frame.setTitle("Sample Functions - " + listModel.size());

        JList<String> list = new JList<>(listModel);
        list.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        list.setLayoutOrientation(JList.VERTICAL);
        list.setVisibleRowCount(-1);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addKeyListener(new KAdapter.KeyReleased(e -> {
            {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) graphSelectedSample(list);
            }
        }));
        if (listModel.size() > 0) {
            list.setSelectedIndex((int) (Math.random() * listModel.size()));
        }
        panel.add(list, BorderLayout.CENTER);

        JScrollPane listScroller = new JScrollPane(list);
        listScroller.setAlignmentX(LEFT_ALIGNMENT);
        panel.add(listScroller);

        JButton graph = new JButton("Graph Selection :D");
        graph.setFont(new Font(Font.SERIF, Font.PLAIN, 18));
        graph.addActionListener(e -> graphSelectedSample(list));
        panel.add(graph, BorderLayout.SOUTH);

        frame.setVisible(true);
    }

    private void graphSelectedSample(JList<String> list) {
        String selectedSampleFunction = list.getSelectedValue();
        ParticleGraph.getInstance().textFields[0].setText(selectedSampleFunction);
        ParticleGraph.getInstance().threadGraph(selectedSampleFunction);
    }

    private void close() {
        frame.dispose();
        sampleFunctions = null;
    }
}
