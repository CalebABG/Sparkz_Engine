package com.engine.EngineHelpers;

import com.engine.GUIWindows.*;
import com.engine.JComponents.CMenuBar;
import com.engine.ParticleHelpers.ParticleModes;
import com.engine.ParticleTypes.Particle;
import com.engine.ThinkingParticles.SCCycle;
import com.engine.Utilities.ColorUtility;
import com.engine.Utilities.InputGuard;
import com.engine.Verlet.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

import static com.engine.EngineHelpers.EBOOLS.*;
import static com.engine.EngineHelpers.EConstants.*;
import static com.engine.EngineHelpers.EFLOATS.*;
import static com.engine.EngineHelpers.EINTS.*;
import static com.engine.EngineHelpers.EModes.ENGINE_MODES.*;
import static com.engine.EngineHelpers.EModes.GRAVITATION_MODES.*;
import static com.engine.EngineHelpers.EModes.PARTICLE_TYPES.*;
import static com.engine.JComponents.CMenuBar.*;
import static com.engine.Utilities.H5Util.H;
import static com.engine.Utilities.InputGuard.minValueGuard;
import static com.engine.Verlet.Vertex.Vertices;
import static org.apache.commons.math3.util.FastMath.abs;

public class EngineMethods {
    /**
     * Switches the particle mode depending on the int(mode) passed to the function.
     *
     * @param e Mouse Event.
     * @see com.engine.InputHandlers.MMotionListener
     */
    public static void switchClickMode(MouseEvent e) {
        if (ENGINE_MODE == NORMAL_MODE) {
            if (PARTICLE_TYPE == PARTICLE) ParticleModes.singleParticle(e);
            else if (PARTICLE_TYPE == GRAVITY_POINT) ParticleModes.singleGravityPoint(e);
            else if (PARTICLE_TYPE == EMITTER) ParticleModes.singleEmitter(e);
            else if (PARTICLE_TYPE == FLUX) ParticleModes.singleSemtex(e);
            else if (PARTICLE_TYPE == QED) ParticleModes.singleQED(e);
            else if (PARTICLE_TYPE == ION) ParticleModes.singleIon(e);
            else if (PARTICLE_TYPE == BLACK_HOLE) ParticleModes.singleBlackHole(e);
            else if (PARTICLE_TYPE == DUPLEX) ParticleModes.singleDuplex(e);
            else if (PARTICLE_TYPE == PORTAL) ParticleModes.singlePortal(e);
        }
        else if (ENGINE_MODE == MULTI_MODE) {
            if (PARTICLE_TYPE == PARTICLE) ParticleModes.multiParticle(e);
            else if (PARTICLE_TYPE == GRAVITY_POINT) ParticleModes.multiGravityPoint(e, 4);
            else if (PARTICLE_TYPE == EMITTER) ParticleModes.singleEmitter(e);
            else if (PARTICLE_TYPE == FLUX) ParticleModes.multiSemtex(e, 4);
            else if (PARTICLE_TYPE == QED) ParticleModes.multiQED(e, 4);
            else if (PARTICLE_TYPE == ION) ParticleModes.multiIon(e, 10);
            else if (PARTICLE_TYPE == BLACK_HOLE) ParticleModes.singleBlackHole(e);
            else if (PARTICLE_TYPE == DUPLEX) ParticleModes.singleDuplex(e);
            else if (PARTICLE_TYPE == PORTAL) ParticleModes.singlePortal(e);
        }
        else if (ENGINE_MODE == FIREWORKS_MODE) {
            MOUSE_GRAVITATION.setValue(false);
            ParticleModes.fireworksTarget(e);
        }
        else if (ENGINE_MODE == RAGDOLL_MODE) {
            VPHandler.handleRagdollClickEvent(e);
        }
    }

    /**
     * Iterates through the ParticlesArray.
     *
     * @param size sets each particles radius to the specified size
     * @see com.engine.GUIWindows.SlideEditor
     */
    public static void setParticleSize(int size) {
        for (int i = 0; i < ParticlesArray.size(); i++)
            if (size >= 0) ParticlesArray.get(i).radius = size;
    }

    /**
     * Increases each particles radius by .5
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void increaseParticleSize() {
        for (int i = 0; i < ParticlesArray.size(); i++)
            ParticlesArray.get(i).radius += 0.2;
    }

    /**
     * Decreases each particles velocity x and velocity y by 60%
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void slowParticles() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            float n = 0.6f;
            Particle p = ParticlesArray.get(i);
            p.vx *= n;
            p.vy *= n;
        }
    }

    /**
     * Multiplies each particles velocity x and velocity by 150%
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void scatterParticles() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            float n = 1.5f;
            Particle p = ParticlesArray.get(i);
            p.vx *= n;
            p.vy *= n;
        }
    }

    /**
     * Decreases each particles radius by .5 and limits the particles minimum radius to .95
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void decreaseParticleSize() {
        for (int i = 0; i < ParticlesArray.size(); i++) {
            Particle p = ParticlesArray.get(i);
            p.radius -= 0.2;
            if (p.radius < 0.2) p.radius = 0.2f;
        }
    }

    /**
     * Toggles the Engines ability to smooth the graphics. Smooth graphics decrease performance but are appealing.
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void toggleGraphicsSmoothing() {
        ENGINE_ENABLE_SMOOTH_RENDER.toggleValue();
    }

    /**
     * Handles the Engines graphics smoothing. If the Engines boolean to smooth graphics is true and the particle
     * mode is set to Ragdoll, then Engine applies graphical smoothing (Antialiasing)
     *
     * @see com.engine.Main.Engine
     */
    public static void handleGraphicsSmoothing() {
        if (ENGINE_MODE == RAGDOLL_MODE) {
            if (ENGINE_ENABLE_SMOOTH_RENDER.value()) {
                graphics2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
                graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            }
        }
    }

    /**
     * Updates the Engines Mouses' position each update.
     *
     * @see com.engine.InputHandlers.MMotionListener
     */
    public static void updateMouse(MouseEvent e) {
        Mouse.x = e.getX(); Mouse.y = e.getY();
    }

    /**
     * A useful method to change a booleans value by negation.
     *
     * @see com.engine.JComponents.CMenuBar
     * @see com.engine.InputHandlers.KHandler
     * @see ColorEditor
     * @see com.engine.Verlet.VSim
     */
    public static boolean toggle(boolean bool) {
        return !bool;
    }

    /**
     * @param amount Sets the Engines drag amount to the specified amount.
     * @see SlideEditor
     */
    public static void setDragAmount(int amount) {
        if (amount > -1) PARTICLE_DRAG_AMOUNT.setValue(amount);
    }

    /**
     * @param amount Sets the Engines fireworks amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksAmount(int amount) {
        if (amount > -1) FIREWORKS_AMOUNT.setValue(amount);
    }

    /**
     * @param amount Sets the Engines life amount to the specified amount
     * @see SlideEditor
     */
    public static void setLifeAmount(int amount) {
        if (amount > -1) PARTICLE_BASE_LIFE.setValue(amount);
    }

    /**
     * @param amount Sets the Engines wind amount to the specified amount
     * @see SlideEditor
     */
    public static void setWindAmount(int amount) {
        if (amount > -1) FIREWORKS_WIND.setValue(amount);
    }

    /**
     * @param amount Sets the Engines jitter amount to the specified amount
     * @see SlideEditor
     */
    public static void setJitterAmount(int amount) {
        if (amount > -1) FIREWORKS_JITTER.setValue(amount);
    }

    /**
     * @param amount Sets the Engines single click minimum particle size amount to the specified amount
     * @see SlideEditor
     */
    public static void setSingleClickSizeMin(int amount) {
        if (amount > -1) SINGLE_CLICK_SIZE_MIN.setValue(amount);
    }

    /**
     * @param amount Sets the Engines single click maximum particle size amount to the specified amount
     * @see SlideEditor
     */
    public static void setSingleClickSizeMax(int amount) {
        if (amount > -1) SINGLE_CLICK_SIZE_MAX.setValue(amount);
    }

    /**
     * @param amount Sets the Engines fireworks minimum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksSizeMin(int amount) {
        if (amount > -1) FIREWORKS_SIZE_MIN.setValue(amount);
    }

    /**
     * @param amount Sets the Engines fireworks maximum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksSizeMax(int amount) {
        if (amount > -1) FIREWORKS_SIZE_MAX.setValue(amount);
    }

    /**
     * @param amount Sets the Engines drag minimum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setDragSizeMin(int amount) {
        if (amount > -1) PARTICLE_DRAG_SIZE_MIN.setValue(amount);
    }

    /**
     * @param amount Sets the Engines drag maximum size amount to the specified amount
     * @see SlideEditor
     */
    public static void setDragSizeMax(int amount) {
        if (amount > -1) PARTICLE_DRAG_SIZE_MAX.setValue(amount);
    }

    /**
     * @param amount Sets the Engines single click speed amount to the specified amount
     * @see SlideEditor
     */
    public static void setSingleClickSpeed(int amount) {
        if (amount > -1) SINGLE_CLICK_SPEED.setValue(amount);
    }

    /**
     * @param amount Sets the Engines fireworks speed amount to the specified amount
     * @see SlideEditor
     */
    public static void setFireworksSpeed(int amount) {
        if (amount > -1) FIREWORKS_SPEED.setValue(amount);
    }

    /**
     * @param amount Sets the Engines drag speed amount to the specified amount
     * @see SlideEditor
     */
    public static void setDragSpeed(int amount) {
        if (amount > -1) PARTICLE_DRAG_SPEED.setValue(amount);
    }

    /**
     * Sets the Engines particle friction boolean.
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void toggleParticleFriction() {
        PARTICLE_FRICTION.toggleValue();
    }

    /**
     * Sets the Engines mouse gravitation boolean.
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void mouseGravitationToggle() {
        MOUSE_GRAVITATION.toggleValue();
    }

    /**
     * Sets the Engines connect particles boolean.
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void connectParticlesMode() {
        CONNECT_PARTICLES.toggleValue();
    }

    /**
     * Sets the Engines thinking particles boolean.
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void thinkingParticlesMode() {
        THINKING_PARTICLES.toggleValue();
    }

    public static void toggleParticleBoundsChecking() {
        PARTICLE_BOUNDLESS.toggleValue();
    }

    /**
     * Determines which gravitation mode to use for particles in the ParticlesArray ArrayList.
     */
    private static void gravitationOptions() {
        int gravityMode = (int) minValueGuard(0, PARTICLE_GRAVITATION_MODE.getValue(), GUIText.ParticleGravitationOptions);
        switch (gravityMode) {
            case 0: PARTICLE_GRAVITATION_MODE = DEFAULT; break;
            case 1: PARTICLE_GRAVITATION_MODE = COSINE_SINE; break;
            case 2: PARTICLE_GRAVITATION_MODE = ARC_TANGENT; break;
            case 3: PARTICLE_GRAVITATION_MODE = H_WAVE; break;
            case 4: PARTICLE_GRAVITATION_MODE = V_WAVE; break;
            case 5: PARTICLE_GRAVITATION_MODE = SPIRALS; break;
            case 6: PARTICLE_GRAVITATION_MODE = PARTICLE_REPELLENT; break;
            case 7: PARTICLE_GRAVITATION_MODE = ORGANIC; break;
            case 8: PARTICLE_GRAVITATION_MODE = FLOW_FIELD; break;
            default: break;
        }

        menuBar.updateGravitationModesRadios();
    }

    /**
     * Dialog window for adjusting the fireworks options (Wind, Life, Jitter)
     */
    private static void realFireworks() {
        String input = JOptionPane.showInputDialog(OptionsMenu.getInstance().frame, H(3, GUIText.FireworksOptions), null, JOptionPane.PLAIN_MESSAGE);
        int rfoInt = (InputGuard.canParseStringInt(input)) ? Integer.parseInt(input) : -1;
        switch (rfoInt) {
            case 1: windAmount();     break;
            case 2: baseLifeAmount(); break;
            case 3: jitterAmount();   break;
            default: break;
        }
    }

    /**
     * Dialog window for adjusting the particles size in different modes (Single Click, Multi) and particle size
     * while upon drag creation.
     */
    private static void particleSizeSeedOptions() {
        String input = JOptionPane.showInputDialog(OptionsMenu.getInstance().frame, GUIText.ParticleSizeSeedOptions, null, JOptionPane.PLAIN_MESSAGE);
        int seedOpt = (InputGuard.canParseStringInt(input)) ? Integer.parseInt(input) : -1;

        switch (seedOpt) {
            case 1: ParticleSizeSeed.getInstance(0); break;
            case 2: ParticleSizeSeed.getInstance(1); break;
            case 3: ParticleSizeSeed.getInstance(2); break;
            default: break;
        }
    }

    /**
     * Dialog window for adjusting the particles speed in different modes (Single Click, Multi) and particle size
     * while upon drag creation.
     */
    private static void particleSpeedSeedOptions() {
        String input = JOptionPane.showInputDialog(OptionsMenu.getInstance().frame, GUIText.ParticleSpeedSeedOptions, null, JOptionPane.PLAIN_MESSAGE);
        int seedOpt = (InputGuard.canParseStringInt(input)) ? Integer.parseInt(input) : -1;
        switch (seedOpt) {
            case 1: ParticleSpeedSeed.getInstance(0); break;
            case 2: ParticleSpeedSeed.getInstance(1); break;
            case 3: ParticleSpeedSeed.getInstance(2); break;
            default: break;
        }
    }

    /**
     * Dialog window to set the SlideEditors particle size sliders maximum.
     *
     * @see SlideEditor
     */
    public static void setMaxParticleSizeSlider() {
        SlideEditor.getInstance().ptsslider.setMaximum((int) minValueGuard(1, SlideEditor.getInstance().ptsslider.getMaximum(),
                H(3, "Enter Max Particle Size (Integer Only)"), SlideEditor.getInstance().frame));
    }

    /**
     * Dialog window to set the SlideEditors drag amount sliders maximum.
     *
     * @see SlideEditor
     */
    public static void setMaxDragSlider() {
        SlideEditor.getInstance().ptdrslider.setMaximum((int) minValueGuard(1, SlideEditor.getInstance().ptdrslider.getMaximum(),
                H(3, "Enter Max Drag Amount (Integer Only)"), SlideEditor.getInstance().frame));
    }

    /**
     * Dialog window to set the SlideEditors fireworks sliders maximum.
     *
     * @see SlideEditor
     */
    public static void setMaxFireworksSlider() {
        SlideEditor.getInstance().ptfrslider.setMaximum((int) minValueGuard(1, SlideEditor.getInstance().ptfrslider.getMaximum(),
                H(3, "Enter Max Fireworks Amount (Integer Only)"), SlideEditor.getInstance().frame));
    }

    /**
     * Dialog window to set the radius of all particles in the ParticlesArray.
     */
    private static void particleSize() {
        float r = minValueGuard(0, .95f, H(3, "Change Particle Size To (Integer or Float)"), OptionsMenu.getInstance().frame);
        if (ParticlesArray.size() > 0) {
            for (int i = 0; i < ParticlesArray.size(); i++) ParticlesArray.get(i).radius = r;
        }
    }

    /**
     * Dialog window to set the cycle time for changing particle colors. If cycling colors is on, after a valid number (in
     * second) is entered, the current Timer is stopped and reset and then started with the new cycle time.
     */
    private static void cycleTimeOptions() {
        ENGINE_COLOR_CYCLE_RATE.setValue((int) minValueGuard(1, ENGINE_COLOR_CYCLE_RATE.value(),
                H(3, "Enter Cycle Time (In Seconds)")));

        if (ENGINE_CYCLE_COLORS.value()) {
            SCCycle.stopCycle();
            SCCycle.startCycle();
        }
    }

    public static void windAmount() {
        FIREWORKS_WIND.setValue((int) minValueGuard(0, FIREWORKS_WIND.value(),
                H(3, "Enter Wind Amount (Integer Only)"), SlideEditor.getInstance().frame));
    }

    public static void baseLifeAmount() {
        PARTICLE_BASE_LIFE.setValue((int) minValueGuard(0, PARTICLE_BASE_LIFE.value(),
                H(3, "Enter Base Life Amount (Integer Only)"), SlideEditor.getInstance().frame));
    }

    public static void rfLifeAmount() {
        FIREWORKS_LIFE.setValue((int) minValueGuard(0, FIREWORKS_LIFE.value(),
                H(3, "Enter Real Fireworks Life Amount (Integer Only)"), SlideEditor.getInstance().frame));
    }

    public static void jitterAmount() {
        FIREWORKS_JITTER.setValue((int) minValueGuard(0, FIREWORKS_JITTER.value(),
                H(3, "Enter Jitter Amount (Integer Only)"), SlideEditor.getInstance().frame));
    }

    private static void particleDrag() {
        PARTICLE_DRAG_AMOUNT.setValue((int) minValueGuard(1, PARTICLE_DRAG_AMOUNT.value(),
                H(3, "Enter Drag Amount (Integer Only)")));
    }

    private static void safetyAmountOptions() {
        ENGINE_SAFETY_AMOUNT.setValue((int) minValueGuard(0, ENGINE_SAFETY_AMOUNT.value(),
                H(3, "Enter Safety Amount (Integer Only, Default: 120)")));
    }

    private static void particleFireworks() {
        FIREWORKS_AMOUNT.setValue((int) minValueGuard(1, FIREWORKS_AMOUNT.value(),
                H(3, "Enter Fireworks Amount (Integer Only)")));
    }

    /**
     * Sets the Engines title based on the state of the Engine (paused or running).
     *
     * @see CMenuBar
     * @see System
     */
    public static void setEngineTitleState() {
        if (ENGINE_IS_PAUSED.value()) {
            EFrame.setTitle(title + " - PAUSED");
        }
        else {
            EFrame.setTitle(title);
        }
    }


    public static void createEngineInstructionsWindow(JFrame parent) {
        Dimension d = toolkit.getScreenSize();
        InstructionsWindow.getInstance(0, parent,
                (int) (d.width * .44), (int) (d.height * .55),
                "Particle Engine Instructions", GUIText.EngineInstructions);
    }

    public static void createGraphInstructionsWindow(JFrame parent) {
        Dimension d = toolkit.getScreenSize();
        InstructionsWindow.getInstance(1, parent,
                (int) (d.width * .37), (int) (d.height * .55),
                "Particle Graph Instructions", GUIText.ParticleGraphInstructions);
    }

    /**
     * Toggles the state of the Engine (paused or running).
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void pauseSimulation() {
        ENGINE_IS_PAUSED.toggleValue();
        menuBar.updateState();
        setEngineTitleState();
    }

    /**
     * Different options for the Engines Options Menu.
     *
     * @see OptionsMenu
     */
    public static void getOptions(int option) {
        switch (option) {
            case 1: particleSize(); break;
            case 2: particleDrag(); break;
            case 3: particleFireworks(); break;
            case 4: ParticleTypeUI.getInstance(0, "Particle Type Options"); break; //Regular Particle Type
            case 5: ColorUtility.setPlainColor(); break;
            case 6: gravitationOptions(); break;
            case 7: particleSizeSeedOptions(); break;
            case 8: particleSpeedSeedOptions(); break;
            case 9: realFireworks(); break;
            case 10: ParticleTypeUI.getInstance(1, "Firework Type Options"); break; //Fireworks Particle Type
            case 11: ColorEditor.getInstance(); break;
            case 12: safetyAmountOptions(); break;
            case 13: cycleTimeOptions(); break;
            default: break;
        }
    }

    public static void updateEngineMode() {
        // Check whether a notification is being requested already
        // If it is then don't update or display until it's finished
        if (!Notifier.drawingNotification) {
            // Increment Mode
            ENGINE_MODE = EModes.getMode(ENGINE_MODE, 1);
            displayEngineMode();
            menuBar.updateParticleModesRadios();
        }
    }

    public static void changeVPhysicsEditorMode(VModes.EditorModes editorMode){
        if (ENGINE_MODE == RAGDOLL_MODE && !Notifier.drawingNotification){
            VPhysicsEditor.getInstance().EDITOR_MODE = editorMode;
            VPhysicsEditor.getInstance().editorModesJComboBox.setSelectedItem(editorMode);
            Notifier.pushNotification(editorMode.name());
        }
    }

    /**
     * Handles the action in response to a key listener for the left arrow key.
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void leftArrowFunction() {
        if (ENGINE_MODE == RAGDOLL_MODE) {
            if (!Notifier.drawingNotification) {
                VPhysicsEditor.getInstance().CREATION_MODE = VModes.getMode(VPhysicsEditor.getInstance().CREATION_MODE, -1);
                VPhysicsEditor.getInstance().creationModesJComboBox.setSelectedItem(VPhysicsEditor.getInstance().CREATION_MODE);
                displayParticleType();
            }
        }
        else {
            if (!Notifier.drawingNotification) {
                // Decrement
                PARTICLE_TYPE = EModes.getMode(PARTICLE_TYPE, -1);
                displayParticleType();
                menuBar.updateParticleTypesRadios();
            }
        }
    }

    /**
     * Handles the action in response to a key listener for the right arrow key.
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void rightArrowFunction() {
        if (ENGINE_MODE == RAGDOLL_MODE) {
            if (!Notifier.drawingNotification) {
                VPhysicsEditor.getInstance().CREATION_MODE = VModes.getMode(VPhysicsEditor.getInstance().CREATION_MODE, 1);
                VPhysicsEditor.getInstance().creationModesJComboBox.setSelectedItem(VPhysicsEditor.getInstance().CREATION_MODE);
                displayParticleType();
            }
        }
        else {
            if (!Notifier.drawingNotification) {
                // Increment
                PARTICLE_TYPE = EModes.getMode(PARTICLE_TYPE, 1);
                displayParticleType();
                menuBar.updateParticleTypesRadios();
            }
        }
    }

    /**
     * Handles the action in response to a key listener for the up arrow key.
     *
     * @see com.engine.InputHandlers.KHandler
     */
    public static void upArrowFunction() {
        if (PARTICLE_TYPE == DUPLEX) {
            if (!Notifier.drawingNotification){
                TOGGLE_DUPLEX_MODE.toggleValue();
                if (TOGGLE_DUPLEX_MODE.value()) Notifier.pushNotification("Contain Mode");
                else Notifier.pushNotification("Repel Mode");
            }
        }
        else PARTICLE_DRAG_AMOUNT.increment();
    }

    public static void downArrowFunction() {
        PARTICLE_DRAG_AMOUNT.decrement();
        if (PARTICLE_DRAG_AMOUNT.value() < 1) PARTICLE_DRAG_AMOUNT.setValue(1);
    }

    /**
     * Empties all of the Engines ArrayLists.
     *
     * @see com.engine.InputHandlers.KHandler
     * @see CMenuBar
     */
    public static void clearParticleArrays() {
        if(ENGINE_MODE == RAGDOLL_MODE) {
            VSim.selectedVertex = null;
            try{
                if (Vertices.size() > 0) Vertices.clear();
            }catch (Exception e){EException.append(e);}
        }
        else {
            try {
                if (ParticlesArray.size() > 0) ParticlesArray.clear();
                if (GravityPointsArray.size() > 0) GravityPointsArray.clear();
                if (EmitterArray.size() > 0) EmitterArray.clear();
                if (FireworksArray.size() > 0) FireworksArray.clear();
                if (FluxArray.size() > 0) FluxArray.clear();
                if (EraserArray.size() > 0) EraserArray.clear();
                if (QEDArray.size() > 0) QEDArray.clear();
                if (IonArray.size() > 0) IonArray.clear();
                if (BlackHoleArray.size() > 0) BlackHoleArray.clear();
                if (DuplexArray.size() > 0) DuplexArray.clear();
                if (PortalArray.size() > 0) PortalArray.clear();
            }
            catch (Exception e) {EException.append(e);}
        }
    }

    /**
     * Slices all Molecule(base class) ArrayList's sizes.
     *
     * @see com.engine.InputHandlers.KHandler
     * @see CMenuBar
     */
    public static void trimParticleArrays(){
        try {
            synchronized (ParticlesArray) {
                if (ParticlesArray.size() > 0)
                    for (int i = 0; i < ParticlesArray.size(); i++)
                        ParticlesArray.remove(i);
            }
            synchronized (GravityPointsArray) {
                if (GravityPointsArray.size() > 0)
                    for (int i = 0; i < GravityPointsArray.size(); i++)
                        GravityPointsArray.remove(i);
            }
            synchronized (FireworksArray) {
                if (FireworksArray.size() > 0)
                    for (int i = 0; i < FireworksArray.size(); i++)
                        FireworksArray.remove(i);
            }
            synchronized (EmitterArray) {
                if (EmitterArray.size() > 0)
                    for (int i = 0; i < EmitterArray.size(); i++)
                        EmitterArray.remove(i);
            }
            synchronized (FluxArray) {
                if (FluxArray.size() > 0)
                    for (int i = 0; i < FluxArray.size(); i++)
                        FluxArray.remove(i);
            }
            synchronized (EraserArray) {
                if (EraserArray.size() > 0)
                    for (int i = 0; i < EraserArray.size(); i++)
                        EraserArray.remove(i);
            }
            synchronized (QEDArray) {
                if (QEDArray.size() > 0)
                    for (int i = 0; i < QEDArray.size(); i++)
                        QEDArray.remove(i);
            }
            synchronized (IonArray) {
                if (IonArray.size() > 0)
                    for (int i = 0; i < IonArray.size(); i++)
                        IonArray.remove(i);
            }
            synchronized (BlackHoleArray) {
                if (BlackHoleArray.size() > 0)
                    for (int i = 0; i < BlackHoleArray.size(); i++)
                        BlackHoleArray.remove(i);
            }
            synchronized (DuplexArray) {
                if (DuplexArray.size() > 0)
                    for (int i = 0; i < DuplexArray.size(); i++)
                        DuplexArray.remove(i);
            }
            synchronized (PortalArray) {
                if (PortalArray.size() > 0)
                    for (int i = 0; i < PortalArray.size(); i++)
                        PortalArray.remove(i);
            }
        }
        catch (Exception ex) {EException.append(ex);}
    }

    /**
     * Displays the particle mode.
     *
     * @see CMenuBar
     * @see com.engine.InputHandlers.KHandler
     */
    public static void displayEngineMode() {
        switch (ENGINE_MODE) {
            case NORMAL_MODE: Notifier.pushNotification("Normal Mode");       break;
            case MULTI_MODE: Notifier.pushNotification("Multi Mode");         break;
            case FIREWORKS_MODE: Notifier.pushNotification("Fireworks Mode"); break;
            case GRAPH_MODE: Notifier.pushNotification("Graph Mode");         break;
            case RAGDOLL_MODE: Notifier.pushNotification("Ragdoll Mode");     break;
            default: break;
        }
    }

    /**
     * Displays the particle type.
     *
     * @see CMenuBar
     */
    public static void displayParticleType() {
        if (ENGINE_MODE == RAGDOLL_MODE) {
            switch (VPhysicsEditor.getInstance().CREATION_MODE) {
                case Point: Notifier.pushNotification("Point"); break;
                case Stick: Notifier.pushNotification("Stick"); break;
                case IKChain: Notifier.pushNotification("IKChain"); break;
                case Box: Notifier.pushNotification("Box"); break;
                case SolidMesh: Notifier.pushNotification("SolidMesh"); break;
                case ElasticMesh: Notifier.pushNotification("ElasticMesh"); break;
                case Cloth: Notifier.pushNotification("Cloth"); break;
                default: break;
            }
        }
        else {
            switch (PARTICLE_TYPE) {
                case PARTICLE: Notifier.pushNotification("Particle"); break;
                case GRAVITY_POINT: Notifier.pushNotification("Gravity Point"); break;
                case EMITTER: Notifier.pushNotification("Emitter"); break;
                case FLUX: Notifier.pushNotification("Flux"); break;
                case QED: Notifier.pushNotification("Q.E.D"); break;
                case ION: Notifier.pushNotification("Ion"); break;
                case BLACK_HOLE: Notifier.pushNotification("Black Hole"); break;
                case DUPLEX: Notifier.pushNotification("Duplex"); break;
                case PORTAL: Notifier.pushNotification("Portal"); break;
                default: break;
            }
        }
    }

    /**
     * Draws a horizontal line with a mid point for the Engines Graph mode.
     */
    public static void drawMid() {
        int neg_W = -canvas.getWidth() / 2;
        int pos_W = canvas.getWidth() / 2;
        int vHeight = 35;
        graphics2D.setColor(Color.gray);
        graphics2D.drawLine(neg_W, 0, pos_W, 0);
        graphics2D.drawLine(0, 0, 0, -vHeight);
        graphics2D.drawLine(0, 0, 0, vHeight);
    }

    /**
     * Returns whether the Engines cycle colors boolean is true or false.
     *
     * @see ColorEditor
     */
    public static String getCycle() {
        return ENGINE_CYCLE_COLORS.value() ? "On" : "Off";
    }

    /**
     * Returns whether the Engines thinking particles boolean is true or false.
     *
     * @see StatsPanel
     */
    public static String getThinkingText() {
        return THINKING_PARTICLES.value() ? "Thinking Particles: On" : "Thinking Particles: Off";
    }

    /**
     * Returns whether the Engines mouse gravitation boolean is true or false.
     *
     * @see StatsPanel
     */
    public static String getMouseAttraction() {
        return MOUSE_GRAVITATION.value() ? "Mouse Attraction: On" : "Mouse Attraction: Off";
    }

    /**
     * Returns whether the Engines particle friction boolean is true or false.
     *
     * @see StatsPanel
     */
    public static String getFrictionText() {
        return PARTICLE_FRICTION.value() ? "Particle Friction: On" : "Particle Friction: Off";
    }

    /**
     * Returns whether the Engines connect particles boolean is true or false and whether the ParticlesArray ArrayList.size() is
     * less than or equal to 100.
     *
     * @see StatsPanel
     */
    public static String getConnectText() {
        return CONNECT_PARTICLES.value() && ParticlesArray.size() <= 100 ? "On" : "Off";
    }

    /**
     * Returns the current particle mode in string representation as follows: 'Particle Mode: _____'.
     *
     * @see StatsPanel
     */
    public static String getModeText() {
        if (ENGINE_MODE == NORMAL_MODE) return "Particle Mode: Normal";
        else if (ENGINE_MODE == MULTI_MODE) return "Particle Mode: Multi";
        else if (ENGINE_MODE == FIREWORKS_MODE) return "Particle Mode: Fireworks";
        else if (ENGINE_MODE == GRAPH_MODE) return "Particle Mode: Graph";
        else return "Particle Mode: Ragdoll";
    }

    //Updates all particles in the Engines ArrayLists
    //---------------------------------------------------------------------------------------------------------------------------------------//
    private static void updateParticlesArray() {for (int i = 0; i < ParticlesArray.size(); i++) ParticlesArray.get(i).update();}
    private static void updateGravityPointsArray() {for (int i = 0; i < GravityPointsArray.size(); i++) GravityPointsArray.get(i).update();}
    private static void updateEmitterArray() {for (int i = 0; i < EmitterArray.size(); i++) EmitterArray.get(i).update();}
    private static void updateFwParticlesArray() {for (int i = 0; i < FireworksArray.size(); i++) FireworksArray.get(i).update();}
    private static void updateFluxArray() {for (int i = 0; i < FluxArray.size(); i++) FluxArray.get(i).update();}
    private static void updateEraserArray() {for (int i = 0; i < EraserArray.size(); i++) EraserArray.get(i).update();}
    private static void updateQEDArray() {for (int i = 0; i < QEDArray.size(); i++) QEDArray.get(i).update();}
    private static void updateIonsArray() {for (int i = 0; i < IonArray.size(); i++) IonArray.get(i).update();}
    private static void updateBlackHoleArray() {for (int i = 0; i < BlackHoleArray.size(); i++) BlackHoleArray.get(i).update();}
    private static void updateDuplexArray() {for (int i = 0; i < DuplexArray.size(); i++) DuplexArray.get(i).update();}
    private static void updatePortalArray() {for (int i = 0; i < PortalArray.size(); i++) PortalArray.get(i).update();}

    //Renders all particles in the Engines ArrayLists
    //--------------------------------------------------------------------------------------------------------------------------------------//
    private static void renderParticlesArray() {for (int i = 0; i < ParticlesArray.size(); i++) ParticlesArray.get(i).render();}
    private static void renderGravityPointsArray() {for (int i = 0; i < GravityPointsArray.size(); i++) GravityPointsArray.get(i).render();}
    private static void renderEmitterArray() {for (int i = 0; i < EmitterArray.size(); i++) EmitterArray.get(i).render();}
    private static void renderFwParticlesArray() {for (int i = 0; i < FireworksArray.size(); i++) FireworksArray.get(i).render();}
    private static void renderFluxArray() {for (int i = 0; i < FluxArray.size(); i++) FluxArray.get(i).render();}
    private static void renderEraserArray() {for (int i = 0; i < EraserArray.size(); i++) EraserArray.get(i).render();}
    private static void renderQEDArray() {for (int i = 0; i < QEDArray.size(); i++) QEDArray.get(i).render();}
    private static void renderIonsArray() {for (int i = 0; i < IonArray.size(); i++) IonArray.get(i).render();}
    private static void renderBlackHoleArray() {for (int i = 0; i < BlackHoleArray.size(); i++) BlackHoleArray.get(i).render();}
    private static void renderDuplexArray() {for (int i = 0; i < DuplexArray.size(); i++) DuplexArray.get(i).render();}
    private static void renderPortalArray() {for (int i = 0; i < PortalArray.size(); i++) PortalArray.get(i).render();}
    //---------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * Makes sure that the Engines fireworks mode does not cause an exception due to too many particle explosions
     * and makes sure that Ragdoll collisions stay under or equal to 285.
     */
    public static void safetyBooleanChecks() {
        PARTICLE_IS_SAFE_AMOUNT.setValue(ParticlesArray.size() <= ENGINE_SAFETY_AMOUNT.value());
        if (VSim.COLLISION_DETECTION) VSim.COLLISION_DETECTION = Vertices.size() <= VSim.MAX_COLLISIONS;
    }
    //---------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * Updates all the particles in their respective ArrayLists.
     */
    public static void handleUpdates() {
        try {
            if (ENGINE_MODE != RAGDOLL_MODE) {
                updateParticlesArray();
                updateGravityPointsArray();
                updateFwParticlesArray();
                updateEmitterArray();
                updateFluxArray();
                updateEraserArray();
                updateQEDArray();
                updateIonsArray();
                updateBlackHoleArray();
                updateDuplexArray();
                updatePortalArray();
            }
        }
        catch (Exception e) {EException.append(e);}
    }
    //---------------------------------------------------------------------------------------------------------------------------------------//

    /**
     * Renders all the particles in their respective ArrayLists and updates + renders Ragdoll physics.
     */
    public static void handleRenders() {
        if (ENGINE_MODE == GRAPH_MODE)
            graphics2D.translate(canvas.getWidth() / 2, canvas.getHeight() / 2);
        else
            graphics2D.translate(0, 0);

        try {
            if (ENGINE_MODE == RAGDOLL_MODE) {
                if (VSim.DEBUG_MODE) VSim.debugPhysics();
                Physics.update();
                Physics.render();
            }
            else {
                if (ENGINE_MODE == GRAPH_MODE) drawMid();
                renderParticlesArray();
                renderGravityPointsArray();
                renderFwParticlesArray();
                renderEmitterArray();
                renderFluxArray();
                renderEraserArray();
                renderQEDArray();
                renderIonsArray();
                renderBlackHoleArray();
                renderDuplexArray();
                renderPortalArray();
            }
        }
        catch (Exception e) {EException.append(e);}
    }
}