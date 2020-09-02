package com.engine.EngineHelpers;

import com.engine.JComponents.CMenuBar;
import com.engine.ParticleTypes.*;
import com.engine.Verlet.Vect2;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.*;
import java.util.List;
import java.util.Timer;

import static com.engine.EngineHelpers.EModes.*;
import static com.engine.EngineHelpers.EModes.ENGINE_MODES.*;
import static com.engine.EngineHelpers.EModes.GRAVITATION_MODES.*;
import static com.engine.EngineHelpers.EModes.MOLECULE_RENDER_MODES.*;
import static com.engine.EngineHelpers.EModes.PARTICLE_TYPES.*;

public abstract class EConstants {
    // Enum<Integer>
    public static ENGINE_MODES          ENGINE_MODE                 = NORMAL_MODE; // Limit @ 4
    public static PARTICLE_TYPES        PARTICLE_TYPE               = PARTICLE;  // Limit @ 8
    public static GRAVITATION_MODES     PARTICLE_GRAVITATION_MODE   = DEFAULT; // Limit @ 8
    public static MOLECULE_RENDER_MODES PARTICLE_RENDER_MODE        = RECTANGLE_NOFILL; // Limit @ 19
    public static MOLECULE_RENDER_MODES FIREWORKS_RENDER_MODE       = RECTANGLE_NOFILL; // Limit @ 19

    /*Ints*/
    public static int fps                                           = 0;
    public static int frameCount                                    = 0;

    /*Floats*/
    public static final float PI                                    = 3.141592653589793f;
    public static final float HALF_PI                               = PI / 2f;
    public static final float TWO_PI                                = 2f * PI;

    /*Strings*/
    public static String title                                      = "Sparkz Engine :D";
    public static String baseParticleText                           = "*";
    public static String fireworksParticleText                      = "*";

    /*Random Number Generator*/
    public static final Random random                               = new Random();

    /*Background Color*/
    public static Color backgroundColor                             = Color.black;

    /*Canvas*/
    public static final Canvas canvas                               = new Canvas();

    /*MenuBar*/
    public static final CMenuBar menuBar                            = new CMenuBar();

    /*Mouse Point*/
    public static final Vect2 Mouse                                 = new Vect2();

    /*Particle ArrayLists*/
    public static final List<Particle> ParticlesArray               = new ArrayList<>(150_000);
    public static final List<Fireworks> FireworksArray              = new ArrayList<>(150_000);
    public static final List<GravityPoint> GravityPointsArray       = new ArrayList<>(350);
    public static final List<Emitter> EmitterArray                  = new ArrayList<>(350);
    public static final List<Flux> FluxArray                        = new ArrayList<>(350);
    public static final List<Eraser> EraserArray                    = new ArrayList<>(350);
    public static final List<QED> QEDArray                          = new ArrayList<>(350);
    public static final List<Ion> IonArray                          = new ArrayList<>(350);
    public static final List<BlackHole> BlackHoleArray              = new ArrayList<>(350);
    public static final List<Duplex> DuplexArray                    = new ArrayList<>(350);
    public static final List<Portal> PortalArray                    = new ArrayList<>(2);

    /*Frame and Graphics*/
    public static JFrame EFrame;
    public static BufferStrategy buff;
    public static Graphics2D graphics2D;
    public static Timer renderer                                    = new Timer();
    public static Toolkit toolkit                                   = Toolkit.getDefaultToolkit();
}
