package com.engine.Verlet;

import com.engine.EngineHelpers.EngineMethods;
import com.engine.GUIWindows.VPhysicsEditor;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.Verlet.VSim.*;
import static org.apache.commons.math3.util.FastMath.*;

public class Vertex {
    public static final DecimalFormat dcFormat = new DecimalFormat("0.000");
    public static final List<Vertex> Vertices = new ArrayList<>();

    public final int index;
    public float currX, currY;
    public float prevX, prevY;
    public float radius = 10.0f;
    public float mass = 1.0f;
    public float damping = 0.997f;
    public float stiffness = 1.0f;
    public boolean pinned = false;
    public boolean collidable = true;

    public Color color = Color.white;
    public final ArrayList<Edge> edges = new ArrayList<>();

    public Vertex(float xPos, float yPos) {
        currX = xPos;
        currY = yPos;
        prevX = currX;
        prevY = currY;
        Vertices.add(this);
        index = Vertices.size() - 1;
    }

    public Vertex(float xPos, float yPos, Color c) {
        this(xPos, yPos);
        color = c;
    }

    public Vertex(float xPos, float yPos, float radius, Color c) {
        this(xPos, yPos, c);
        this.radius = radius;
    }

    public Vertex(float xPos, float yPos, float radius, float mass, Color c) {
        this(xPos, yPos, radius, c);
        this.mass = mass;
    }

    public Vertex(float xPos, float yPos, float radius, float mass, float stiffness, Color c) {
        this(xPos, yPos, radius, mass, c);
        this.stiffness = stiffness;
    }

    public Vertex(float xPos, float yPos, float radius, float mass, float stiffness, float damping, boolean collidable, boolean pinned, Color c) {
        this(xPos, yPos, radius, mass, stiffness, c);
        this.damping = damping;
        this.collidable = collidable;
        this.pinned = pinned;
    }

    public void accelerate() {
        if (pinned) {
            currX = prevX;
            currY = prevY;
        } else {
            gravity = ZERO_GRAVITY ? 0.0f : GConstant;

            float tempX = currX, tempY = currY;

            currX += (air_viscosity * currX - air_viscosity * prevX) * damping;
            currY += ((air_viscosity * currY - air_viscosity * prevY) + gravity);

            prevX = tempX;
            prevY = tempY;
        }
    }

    public void solveConstraints() {
        for (int i = 0; i < edges.size(); i++) edges.get(i).solve();
    }

    public void solveCollisions(Vertex p2, boolean preserveImpulse) {
        float vx = 0;
        float vy = 0;
        float canvasWidth = canvas.getWidth();
        float canvasHeight = canvas.getHeight();

        if (preserveImpulse) {
            vx = (prevX - currX) * damping;
            vy = (prevY - currY) * damping;
        }

        if (currX - radius < 0) {
            currX = 2 * (radius) - currX;
            if (preserveImpulse) prevX = (currX - vx);
        }
        if (currX + radius > canvasWidth) {
            currX = 2 * (canvasWidth - radius) - currX;
            if (preserveImpulse) prevX = (currX - vx);
        }
        if (currY - radius < 0) {
            currY = 2 * (radius) - currY;
            if (preserveImpulse) prevY = (currY - vy);
        }
        if (currY + radius > canvasHeight) {
            currY = 2 * (canvasHeight - radius) - currY;
            if (preserveImpulse) prevY = (currY - vy);
        }

        if (COLLISION_DETECTION) if (collidable) collide(p2);
    }

    public void collide(Vertex p2) {
        if (p2 != this) {
            float dampening = .97f;
            float radii = radius + p2.radius;
            float diffX = currX - p2.currX;
            float diffY = currY - p2.currY;
            float lenDiff = (float) sqrt(diffX * diffX + diffY * diffY);

            // first make sure they're intersecting
            if (lenDiff <= radii) {
                // distance between centers
                float d = lenDiff;

                // minimum translation distance to push balls apart after intersecting
                if (d <= 0) d = radius / 2 + p2.radius / 2;

                // find the difference, or the ratio of how far along the restingDistance the actual distance is.
                float difference = (radii - d) / d;

                // Inverse the mass quantities
                float im1 = 1 / mass;
                float im2 = 1 / p2.mass;
                float scalarP1 = (im1 / (im1 + im2)) * stiffness;
                float scalarP2 = stiffness - scalarP1;

                // Push/pull based on mass
                // heavier objects will be pushed/pulled less than attached light objects
                currX += (diffX * scalarP1 * difference) * dampening;
                currY += (diffY * scalarP1 * difference) * dampening;
                p2.currX -= (diffX * scalarP2 * difference) * dampening;
                p2.currY -= (diffY * scalarP2 * difference) * dampening;
            }
        }
    }

    public void draw() {
        if (!edges.isEmpty()) {
            for (int i = 0; i < edges.size(); i++) edges.get(i).draw();
        }

        if (this == dragVertex) {
            graphics2D.setColor(color);
            graphics2D.draw(new Line2D.Float(Mouse.x, Mouse.y, currX, currY));
            graphics2D.fill(new Ellipse2D.Float(currX - radius, currY - radius, 2 * radius, 2 * radius));
        }

        if (this == selectedVertex && (VPhysicsEditor.getInstance().selectionshowpoint_checkbox != null && VPhysicsEditor.getInstance().selectionshowpoint_checkbox.isSelected())) {
            graphics2D.setColor(color);
            graphics2D.fill(new Ellipse2D.Float(currX - radius, currY - radius, 2 * radius, 2 * radius));
            graphics2D.setColor(Color.green);
            float scale = 3.0f;
            graphics2D.draw(new Ellipse2D.Float(currX - ((scale / 2) * radius), currY - ((scale / 2) * radius), scale * radius, scale * radius));
        } else {
            graphics2D.setColor(color);
            graphics2D.fill(new Ellipse2D.Float(currX - radius, currY - radius, 2 * radius, 2 * radius));
        }
    }

    public boolean contains(float x, float y) {
        float dx = abs(currX - x);
        float dy = abs(currY - y);
        return hypot(dx, dy) < radius;
    }

    public final float getVelocityX() {
        return currX - prevX;
    }

    public final float getVelocityY() {
        return currY - prevY;
    }

    public float getDistance(Vertex p) {
        return (float) hypot(currX - p.currX, currY - p.currY);
    }

    public float getDistance(Vect2 p) {
        return (float) hypot(currX - p.x, currY - p.y);
    }

    public void attachTo(Vertex P, float restingDist, float stiff) {
        attachTo(P, restingDist, stiff, 30, true);
    }

    public void attachTo(Vertex P, float restingDist, float stiff, boolean drawLink) {
        attachTo(P, restingDist, stiff, 30, drawLink);
    }

    public void attachTo(Vertex P, float restingDist, float stiff, float tearSensitivity) {
        attachTo(P, restingDist, stiff, tearSensitivity, true);
    }

    public void attachTo(Vertex P, float restingDist, float stiff, float tearSensitivity, boolean drawLink) {
        Edge lnk = new Edge(this, P, restingDist, stiff, tearSensitivity, drawLink);
        edges.add(lnk);
    }

    public void attachTo(Vertex P, float restingDist, float stiff, float tearSensitivity, boolean drawLink, boolean tearable, Color c) {
        Edge lnk = new Edge(this, P, restingDist, stiff, tearSensitivity, drawLink, tearable, c);
        edges.add(lnk);
    }

    public void removeLink(Edge lnk) {
        edges.remove(lnk);
    }

    public void togglePin() {
        pinned = EngineMethods.toggle(pinned);
    }

    public void isPinned(boolean pinned) {
        this.pinned = pinned;
    }

    public void unPin() {
        pinned = false;
    }

    public String toString() {
        return "Pos: (" + dcFormat.format(currX) + " , " + dcFormat.format(currY) +
                " ) | Velocity: (" + dcFormat.format(getVelocityX()) + " , " + dcFormat.format(getVelocityY()) +
                ") | Radius: " + dcFormat.format(radius) +
                " | Mass: " + dcFormat.format(mass);
    }
}