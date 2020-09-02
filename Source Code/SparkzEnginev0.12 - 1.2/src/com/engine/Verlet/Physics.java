package com.engine.Verlet;

import com.engine.GUIWindows.VPhysicsEditor;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;

import static com.engine.EngineHelpers.EBOOLS.*;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.Verlet.Vertex.Vertices;
import static com.engine.Verlet.VSim.*;

public class Physics {
    /**
     * Actively updates all Verlet Physics objects
     * If the engine is paused, then the solving of constraints and collision
     * detection is halted. When paused only mouse interactions with objects is updated.
     */
    public static void update() {
        if (!ENGINE_IS_PAUSED.value()) {
            solveConstraints();
            handleMouseInteraction();
            integrate();
            collisionsNoVelPres();
            collisionsWithVelPres();
        }
        else handleMouseInteraction();
    }

    /**
     * Renders all of the Vertex objects, and also shows their constraints if selected
     * @see Vertex
     * @see Edge
     */
    public static void render() {
        for (int i = 0; i < Vertices.size(); i++) Vertices.get(i).draw();

        if (VPhysicsEditor.getInstance() != null && selectedVertex != null) {
            List<Integer> selectedPointConstraintList = VPhysicsEditor.getInstance().constraintJlist.getSelectedValuesList();

            // Check whether the list of constraints is empty; check if the point has any constrains (if the first index in the array
            // is not -1; and check whether to show the constraint at all (checkbox)
            if (!selectedPointConstraintList.isEmpty() && (selectedPointConstraintList.get(0) != -1) && VPhysicsEditor.getInstance().showselectionconstraint_checkbox.isSelected()) {
                for (int i = 0; i < selectedPointConstraintList.size(); i++) {
                    Vertex constraintPoint = selectedVertex.edges.get(selectedPointConstraintList.get(i)).p2;

                    graphics2D.setColor(Color.red);
                    graphics2D.drawLine((int)selectedVertex.currX, (int)selectedVertex.currY, (int)constraintPoint.currX, (int)constraintPoint.currY);

                    float scale = 3.0f;
                    graphics2D.setColor(Color.red);
                    graphics2D.drawOval(
                            (int) (constraintPoint.currX - ((scale / 2) * constraintPoint.radius)),
                            (int) (constraintPoint.currY - ((scale / 2) * constraintPoint.radius)),
                            (int) (scale * constraintPoint.radius),
                            (int) (scale * constraintPoint.radius));
                }
            }
        }
    }

    /**
     * Calculates the interactions between Vertex objects with length constraints(Edges)
     * This calculation is performed various times (up to SIM_ACCURACY times)
     * @see Edge
     */
    private static void solveConstraints() {
        for (int i = 0; i < SIM_ACCURACY; i++)
            for (int j = 0; j < Vertices.size(); j++)
                Vertices.get(j).solveConstraints();
    }

    /**
     * Handles the interaction between the mouse pointer and Verlet physics objects
     */
    private static void handleMouseInteraction() {
        if (VPhysicsEditor.getInstance().EDITOR_MODE == VModes.EditorModes.Drag) {
            //  Handle if the Left mouse button is held down in drag mode
            if (LEFT_MOUSE_IS_DOWN.value()) {
                //  If the the point we want to drag isn't null and if the engine isn't paused move it around
                if (dragVertex != null) {
                    if (!ENGINE_IS_PAUSED.value()) {
                        float s = dragVertex.mass * dragForce;
                        dragVertex.currX += (Mouse.x - dragVertex.currX) / s;
                        dragVertex.currY += (Mouse.y - dragVertex.currY) / s;
                    }
                }

                //  Else, if the point we want to drag is null, then search through the list of points we have so far
                //  Check the distance between a given point in the list and the mouse. If the mouse point (x, y) is within the radius
                //  of the given point in the list, then we've found a point to drag. Set the dragPoint variable to that point and break out of searching
                else {
                    boolean found = false;
                    for (int i = 0; i < Vertices.size() && !found; i++) {
                        Vertex searchVertex = Vertices.get(i);
                        if (searchVertex.contains(Mouse.x, Mouse.y)) {
                            dragVertex = searchVertex;
                            selectedVertex = searchVertex;
                            VPhysicsEditor.getInstance().setObjectPropertiesOnSelect(selectedVertex);
                            VPhysicsEditor.getInstance().updateJListConstraints(selectedVertex.edges);
                            found = true;
                        }
                    }
                }
            }

            // Else, if the right mouse button is held down,
            // search to see if the mouse is within the bounds of a verlet physics object.
            // If that's the case, then remove that objects constraints.
            else if (RIGHT_MOUSE_IS_DOWN.value()) {
                for (int i = 0; i < Vertices.size(); i++) {
                    Vertex searchVertex = Vertices.get(i);
                    float tear_distance = searchVertex.getDistance(Mouse);

                    if (tear_distance < mouseTearSize) {
                        if (searchVertex == selectedVertex) {
                            searchVertex.edges.clear();
                            VPhysicsEditor.getInstance().updateJListConstraints(selectedVertex.edges);
                        }
                        else searchVertex.edges.clear();
                    }
                }
            }
        }
    }

    private static void collisionsNoVelPres() {
        for (int i = 0; i < Vertices.size(); i++) {
            for (int j = 0; j < Vertices.size(); j++) {
                Vertices.get(i).solveCollisions(Vertices.get(j), false);
            }
        }
    }

    private static void collisionsWithVelPres() {
        for (int i = 0; i < Vertices.size(); i++) {
            for (int j = 0; j < Vertices.size(); j++) {
                Vertices.get(i).solveCollisions(Vertices.get(j), true);
            }
        }
    }

    private static void integrate() {
        for (int i = 0; i < Vertices.size(); i++)
            Vertices.get(i).accelerate();
    }
}