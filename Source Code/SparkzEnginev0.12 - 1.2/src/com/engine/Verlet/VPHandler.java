package com.engine.Verlet;

import com.engine.GUIWindows.VPhysicsEditor;
import java.awt.*;
import java.awt.event.MouseEvent;
import static com.engine.EngineHelpers.EConstants.random;
import static com.engine.Utilities.ColorUtility.randHSLColor;
import static com.engine.Verlet.VModes.EditorModes.*;
import static com.engine.Verlet.Vertex.Vertices;
import static com.engine.Verlet.VSim.selectedVertex;

public class VPHandler {
    //Add functionality for pressing 'P' to pin point in Verlet mode
    public static Color LCOLOR          = Color.ORANGE.darker();
    public static Color PCOLOR          = Color.ORANGE;
    public static int NUM_POINTS        = 50;
    public static float SIZE            = 50;
    public static float SPACING         = 10;
    public static float RADIUS          = 6;
    public static float STIFFNESS       = 0.0444f;
    public static float TEARDIST        = 80;
    public static boolean DRAW_LINKS    = true;
    public static boolean TEARABLE      = false;

    public static void handleRagdollClickEvent(MouseEvent e){
        if (VPhysicsEditor.getInstance().EDITOR_MODE == Add) {
            switch (VPhysicsEditor.getInstance().CREATION_MODE) {
                case Point:
                    VCreations.createPoint(e, randHSLColor(1000, 7000, 0.8f), 25, 100);
                    break;
                case Stick:
                    VCreations.createStick(e, LCOLOR, PCOLOR, 50, 10, 10, 1, 30, STIFFNESS, TEARDIST, DRAW_LINKS, TEARABLE);
                    break;
                case IKChain:
                    VCreations.createIKChain(e, NUM_POINTS, SPACING, RADIUS, 1, TEARDIST, DRAW_LINKS, TEARABLE, Color.red, Color.orange);
                    break;
                case Box:
                    VCreations.singleBox(e, Color.blue, Color.cyan, 30, 8, 0.4f, TEARDIST, DRAW_LINKS, TEARABLE);
                    break;
                case SolidMesh:
                    VCreations.singleSolidMesh(e, 15, SIZE, 10, 0.1f, TEARDIST, true, TEARABLE, Color.blue, Color.cyan);
                    break;
                case ElasticMesh:
                    VCreations.singleElasticMesh(e, 5, SIZE, 17, 0.4f, TEARDIST, DRAW_LINKS, TEARABLE, Color.blue, Color.cyan);
                    break;
                case Cloth:
                    createCloth(e.getX(), e.getY(), 10, 10, 15);
                    break;
                default:
                    break;
            }
        }
        else if (VPhysicsEditor.getInstance().EDITOR_MODE == VModes.EditorModes.Select || VPhysicsEditor.getInstance().EDITOR_MODE == VModes.EditorModes.Drag) {
            for (int i = 0; i < Vertices.size(); i++) {
                Vertex point = Vertices.get(i);
                if (point.contains(e.getX(), e.getY())) {
                    selectedVertex = point;
                    VPhysicsEditor.getInstance().setObjectPropertiesOnSelect(selectedVertex);
                    VPhysicsEditor.getInstance().updateJListConstraints(selectedVertex.edges);
                    break;
                }
                else {
                    selectedVertex = null;
                    VPhysicsEditor.getInstance().unsetObjectPropertiesOnDeselect();
                    VPhysicsEditor.getInstance().updateJListConstraints(null);
                }
            }
        }
    }

    public static void createCloth(int x, int y, int w, int h, int spacing){
        float RADIUS               = 4;
        float STIFFNESS            = 0.8f;
        float TEARDISTANCE         = 80;

        VCreations.createCloth(w, h, spacing, y, x, STIFFNESS, TEARDISTANCE, RADIUS, PCOLOR, PCOLOR, LCOLOR,true, true, true);
    }

    public static void dragMode(MouseEvent e){
        float radius = random.nextInt(5) + 5;
        VCreations.createPoint(e, randHSLColor(0, 20_000, 0.9f), radius, random.nextInt(100) + 20);
    }
}
