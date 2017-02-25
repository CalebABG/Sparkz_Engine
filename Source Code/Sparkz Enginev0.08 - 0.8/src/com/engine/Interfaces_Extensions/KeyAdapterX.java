package com.engine.Interfaces_Extensions;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public interface KeyAdapterX extends KeyListener {
    default void keyTyped(KeyEvent e) {}
    default void keyPressed(KeyEvent e) {}
    default void keyReleased(KeyEvent e) {}
}

