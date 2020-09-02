package com.engine.Utilities;

import com.engine.GUIWindows.EException;
import com.engine.GUIWindows.OptionsMenu;
import com.engine.GUIWindows.ParticleTypeUI;
import javax.swing.*;
import javax.swing.text.JTextComponent;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import static com.engine.Utilities.H5Util.H;

public class InputGuard {
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean canParseStringInt(String input) {try {Integer.parseInt(input); return true;}catch (NumberFormatException e){return false;}}
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static boolean canParseStringFloat(String input) {try {Float.parseFloat(input); return true;}catch (NumberFormatException e){return false;}}

    public static float minValueGuard(float min, float default_val, String promptText) {
        JLabel label = new JLabel(promptText);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 18));

        String amount = JOptionPane.showInputDialog(OptionsMenu.getInstance().frame, label, null, JOptionPane.PLAIN_MESSAGE);
        float newAmount = (amount != null && amount.length() > 0 && canParseStringFloat(amount)) ? Float.parseFloat(amount) : default_val;
        if (newAmount < min) {
            while (newAmount < min) {
                amount = JOptionPane.showInputDialog(OptionsMenu.getInstance().frame, H(3, "Not Allowed, Enter an Amount > " + min));
                newAmount = (amount != null && amount.length() > 0) ? Float.parseFloat(amount) : default_val;
            }
        }
        return newAmount;
    }

    public static float minValueGuard(float min, float default_val, String promptText, JFrame parent) {
        JLabel label = new JLabel(promptText);
        label.setFont(new Font(Font.SERIF, Font.PLAIN, 18));

        String amount = JOptionPane.showInputDialog(parent, label, null, JOptionPane.PLAIN_MESSAGE);
        float newAmount = (amount != null && amount.length() > 0 && canParseStringFloat(amount)) ? Float.parseFloat(amount) : default_val;
        if (newAmount < min) {
            while (newAmount < min) {
                amount = JOptionPane.showInputDialog(parent, H(3, "Not Allowed, Enter an Amount > " + min));
                newAmount = (amount != null && amount.length() > 0) ? Float.parseFloat(amount) : default_val;
            }
        }
        return newAmount;
    }

    public static String valueGuardString(int mode, ParticleTypeUI ui, String default_val, String promptText) {
        if (mode == 0) {return default_val;}
        else {
            String amount = JOptionPane.showInputDialog(ui.frame, promptText, null, JOptionPane.PLAIN_MESSAGE);
            return (amount != null && amount.length() > 0) ? amount : default_val;}
    }

    public static int intTextfieldGuardDefault(int min, int default_val, String input) {
        if (input == null || input.isEmpty()) return default_val;
        else {
            if (canParseStringInt(input)) {
                if (Integer.parseInt(input) >= min) return Integer.parseInt(input);
                else return default_val;
            }
            else return default_val;
        }
    }

    public static float floatTextfieldGuardDefault(float min, float default_val, String input) {
        if (input == null || input.isEmpty()) return default_val;
        else {
            if (canParseStringFloat(input)) {
                if (Float.parseFloat(input) >= min) return Float.parseFloat(input);
                else return default_val;
            }
            else return default_val;
        }
    }

    public static boolean stringNotNull(String ref){return (ref != null && ref.length() > 0);}

    //Credit for Undo functionality: http://stackoverflow.com/questions/10532286/how-to-use-ctrlz-and-ctrly-with-all-text-components
    public static void addUndoFunctionality(JTextComponent pTextComponent) {
        final UndoManager undoMgr = new UndoManager();

        // Add listener for undoable events
        pTextComponent.getDocument().addUndoableEditListener(evt -> undoMgr.addEdit(evt.getEdit()));

        // Add undo/redo actions
        pTextComponent.getActionMap().put("undoKeystroke", new AbstractAction("undoKeystroke") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undoMgr.canUndo()) undoMgr.undo();
                } catch (Exception e) {
                    EException.append(e);}
            }
        });

        pTextComponent.getActionMap().put("redoKeystroke", new AbstractAction("redoKeystroke") {
            public void actionPerformed(ActionEvent evt) {
                try {
                    if (undoMgr.canRedo()) undoMgr.redo();
                } catch (Exception e) {EException.append(e);}
            }
        });

        // Create keyboard accelerators for undo/redo actions (Ctrl+Z/Ctrl+Y)
        pTextComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_DOWN_MASK), "undoKeystroke");
        pTextComponent.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_DOWN_MASK), "redoKeystroke");
    }
}
