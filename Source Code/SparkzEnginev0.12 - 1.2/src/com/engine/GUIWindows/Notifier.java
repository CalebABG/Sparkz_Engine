package com.engine.GUIWindows;

import com.engine.EngineHelpers.EModes;

import java.awt.*;

import static com.engine.EngineHelpers.EConstants.ENGINE_MODE;
import static com.engine.EngineHelpers.EConstants.canvas;
import static com.engine.EngineHelpers.EConstants.graphics2D;

public class Notifier {
    private static Font renderfont = new Font(Font.SERIF, Font.PLAIN, 54);
    public static boolean drawingNotification = false;
    public static int defaultTimeout = 45;
    public static int timeout = defaultTimeout;
    public static String text = "";

    public static void pushNotification(String drawText) {
        if (!drawingNotification) {
            drawingNotification = true;
            text = drawText;
            timeout = defaultTimeout;
        }
    }

    private static void renderNotification() {
        if (timeout > -1) {
            graphics2D.setFont(renderfont);
//            graphics2D.setColor(Color.black);
//            graphics2D.fillRect((canvas.getWidth() / 2) - width / 2, (canvas.getHeight() / 2) - height / 2,
//                    graphics2D.getFontMetrics().stringWidth(text), graphics2D.getFontMetrics().getHeight());
            graphics2D.setColor(Color.white);

            if (ENGINE_MODE == EModes.ENGINE_MODES.GRAPH_MODE) {
                graphics2D.drawString(text, (-graphics2D.getFontMetrics().stringWidth(text) / 2) + -5f, 0);
            } else {
                graphics2D.drawString(text, (canvas.getWidth() - graphics2D.getFontMetrics().stringWidth(text)) / 2, canvas.getHeight() / 2);
            }
        }

        if (timeout > -1) timeout--;
        if (timeout < 0) drawingNotification = false;
    }

    public static void headsUpNotifications() {
        if (drawingNotification) renderNotification();
    }
}
