package com.engine.ParticleTypes;

import com.engine.GUIWindows.FlowFieldUI;
import com.engine.GUIWindows.ParticleGraph;
import com.engine.ParticleTypes.Interfaces.ThinkingColors;
import com.flowpowered.noise.Noise;
import com.flowpowered.noise.NoiseQuality;
import static com.engine.EngineHelpers.EBOOLS.*;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EConstants.PI;
import static com.engine.EngineHelpers.EINTS.PARTICLE_BASE_LIFE;
import static com.engine.EngineHelpers.EModes.ENGINE_MODES.FIREWORKS_MODE;
import static com.engine.EngineHelpers.EModes.ENGINE_MODES.GRAPH_MODE;
import static com.engine.EngineHelpers.EModes.GRAVITATION_MODES.FLOW_FIELD;
import static com.engine.EngineHelpers.EModes.GRAVITATION_MODES.ORGANIC;
import static com.engine.GUIWindows.OrganicForces.*;
import static com.engine.ParticleHelpers.DrawModes.giveStyle;
import static com.engine.ParticleHelpers.ParticleModes.fireworksMode;
import static com.engine.Verlet.Vect2.clamp;
import static com.engine.Verlet.Vect2.map;
import static org.apache.commons.math3.util.FastMath.*;

public class Particle extends Molecule implements ThinkingColors {
    public int life = PARTICLE_BASE_LIFE.value();
    public float angle = 0.0f;

    public Particle() {super();}
    public Particle(float x, float y, float radius, float speed, float direction) {
        super(x, y, radius, speed, direction, 0);
    }

    public Particle(float x, float y, float radius, float speed, int pX, int pY, int tX, int tY) {
        super(x, y, (float) cos(atan2(toRadians(tY - pY), toRadians(tX - pX))) * speed,
                    (float) sin(atan2(toRadians(tY - pY), toRadians(tX - pX))) * speed, radius);
    }

    public Particle(float x, float y, float radius) {
        super(x, y, 0, 0, radius);
    }

    /**
     * The organic gravitation mode uses a profuse amount of memory, for some reason the JavaScript (Nashorn)
     * engine has a terrible memory usage problem with the eval method
     *
     * If The object of type Molecule is not null, gravitation will be based on the molecule. * Also means that the object for type Point must be null,
     * and vice versa.
    */

    public void gravitateTo(float px, float py) {
        if (PARTICLE_GRAVITATION_MODE == ORGANIC && ParticlesArray.size() <= 850) {
            ParticleGraph.engine.put("x", angle);

            Float tvx = ParticleGraph.getInstance().evaluateExpr(ParticleGraph.engine, expressionForceX,false);
            Float tvy = ParticleGraph.getInstance().evaluateExpr(ParticleGraph.engine, expressionForceY,false);

            vx = tvx == null ? vx : tvx;
            vy = tvy == null ? vy : tvy;

            angle += angleIncrement;

            if (angle >= 1000 * (2 * PI) * angleIncrement) angle = 0.0f;
        }
        else if (PARTICLE_GRAVITATION_MODE == FLOW_FIELD)
        {
            float a = (float) (Noise.gradientCoherentNoise3D(
                                x * FlowFieldUI.noiseX,
                                y * FlowFieldUI.noiseY,
                                frameCount * FlowFieldUI.noiseZ,
                                86317, NoiseQuality.FAST) * FlowFieldUI.scaleC1 * PI * FlowFieldUI.scalceC2);

            float x = (float) cos(FlowFieldUI.startAngle);
            float y = (float) sin(FlowFieldUI.startAngle);

            //Rotate
            float xTemp = x;
            x = (float) (x * cos(a) - y * sin(a));
            y = (float) (xTemp * sin(a) + y * cos(a));

            //Normalize
            float m = (float) sqrt(x * x + y * y);
            if (m != 0.0 && m != 1.0) {
                x /= m;
                y /= m;
            }

            //Set Magnitude
            x *= FlowFieldUI.velocityMagnitude;
            y *= FlowFieldUI.velocityMagnitude;

            //add to acceleration
            ax += x;
            ay += y;

            //Limit velocity
            vx = clamp(vx, -FlowFieldUI.velocityLimit, FlowFieldUI.velocityLimit);
            vy = clamp(vy, -FlowFieldUI.velocityLimit, FlowFieldUI.velocityLimit);
        }
        else {
            float dx = px - x;
            float dy = py - y;
            float dist = (float) sqrt(dx * dx + dy * dy);
            float forceX = dx / 5 / dist;
            float forceY = dy / 5 / dist;

            switch (PARTICLE_GRAVITATION_MODE) {

                case COSINE_SINE:
                    ax += cos(forceX);
                    ay += sin(forceY);
                    break;

                case ARC_TANGENT:
                    float force = (float) atan2(forceY, forceX);
                    ax += force;
                    ay += force;
                    break;

                case H_WAVE:
                    forceX = (dx / Float.POSITIVE_INFINITY / dist);
                    ax += forceX;
                    ay += forceY;
                    break;

                case V_WAVE:
                    forceY = (dy / Float.POSITIVE_INFINITY) / dist;
                    ax += forceX;
                    ay += forceY;
                    break;

                case SPIRALS:
                    forceX = (float) (dx / atan2(10000.0, atan2(py, px)) / dist);
                    ax += forceX;
                    ay += forceY;
                    break;

                case PARTICLE_REPELLENT:
                    float scale = .35f;
                    ax += -forceX * scale;
                    ay += -forceY * scale;
                    break;

                case DEFAULT:
                default:
                    ax += forceX;
                    ay += forceY;
                    break;
            }
        }
    }

    private void connectAll() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle particle = ParticlesArray.get(i);
            graphics2D.setColor(particle.color);
            graphics2D.drawLine((int) particle.x, (int) particle.y, (int) x, (int) y);
        }
    }
    private void connectSequential() {
        Particle p2 = ParticlesArray.get((ParticlesArray.indexOf(this) + 1) % ParticlesArray.size());
        graphics2D.setColor(color);
        graphics2D.drawLine((int) p2.x, (int) p2.y, (int) x, (int) y);
    }

    public void boundsCheck() {
        float cw = canvas.getWidth();
        float ch = canvas.getHeight();

        if (ENGINE_MODE == GRAPH_MODE) {
            if (x > cw / 2)  x = -cw / 2;
            if (x < -cw / 2) x = cw / 2;
            if (y > ch / 2)  y = -ch / 2;
            if (y < -ch / 2) y = ch / 2;
        }
        else {
            if (PARTICLE_BOUNDLESS.value()) {
                if (x > cw + radius) x = -radius;
                if (x < -radius)     x = cw + radius;
                if (y > ch + radius) y = -radius;
                if (y < -radius)     y = ch + radius;
            }
            else super.boundsCheck();
        }
    }

    public void render() {
        if (THINKING_PARTICLES.value()) color = getSelfColor(velocity());
        else color = plain_color;

        if (CONNECT_PARTICLES.value()) {
            if (TOGGLE_PARTICLE_LINK_RENDER.value()) connectSequential();
            else if (ParticlesArray.size() <= 100) connectAll();
        }

        giveStyle(x - radius, y - radius, 2 * radius, color, PARTICLE_RENDER_MODE, baseParticleText);
    }

    public void update() {
        if (MOUSE_GRAVITATION.value()){
            if (ENGINE_MODE == GRAPH_MODE) {
                int cw = canvas.getWidth();
                int ch = canvas.getHeight();

                int mx = (int) map(Mouse.x, 0, cw, -cw / 2.0f, cw / 2.0f);
                int my = (int) map(Mouse.y, 0, ch, -ch / 2.0f, ch / 2.0f);

                gravitateTo(mx, my);
            }
            else gravitateTo(Mouse.x, Mouse.y);
        }

        accelerate();

        if (PARTICLE_FRICTION.value()) {
            float scale = 0.9993f;
            vx *= scale;
            vy *= scale;
        }

        boundsCheck();

        if (ENGINE_MODE == FIREWORKS_MODE) {
            if (life < 0) {
                if (PARTICLE_IS_SAFE_AMOUNT.value()) fireworksMode(x, y);
                ParticlesArray.remove(this);
            }
            life -= 1;
        }
    }
}