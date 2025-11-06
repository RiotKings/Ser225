package Level;

import Engine.GraphicsHandler;
import Engine.Keyboard;
import Engine.Key;
import Level.Player;
import Utils.Direction;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.awt.Color;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.util.Random;

public class KnowledgeSystem {
    private float knowledgeLevel = 0f; // 0.0 to 1.0
    private int knowledgeTier = 0; // 0-4 (none, 25%, 50%, 75%, 100%)
    
    // visual effect variables
    private Random random = new Random();
    private float screenShakeX = 0f;
    private float screenShakeY = 0f;
    private long lastDistortionTime = 0;
    private long distortionInterval = 5000; 
    
    // control reversal
    private boolean controlsReversed = false;
    private long controlReversalEndTime = 0;
    private static final long CONTROL_REVERSAL_DURATION = 3000; // 3 seconds
    
    // audio
    private Clip ambientDroneClip;
    private Clip whispersClip;
    private boolean audioInitialized = false;
    
    public KnowledgeSystem() {
    }
    
    // call this to add knowledge (when player takes damage)
    public void addKnowledge(float amount) {
        knowledgeLevel = Math.min(1.0f, knowledgeLevel + amount);
        updateTier();
    }
    
    private void updateTier() {
        int newTier;
        if (knowledgeLevel >= 1.0f) newTier = 4;
        else if (knowledgeLevel >= 0.75f) newTier = 3;
        else if (knowledgeLevel >= 0.5f) newTier = 2;
        else if (knowledgeLevel >= 0.25f) newTier = 1;
        else newTier = 0;
        
        if (newTier > knowledgeTier) {
            onTierIncrease(newTier);
        }
        knowledgeTier = newTier;
        
        // updates audio based on tier
        updateAudio();
    }
    
    private void onTierIncrease(int newTier) {
        System.out.println("Knowledge tier increased to: " + newTier);
        // may add
        
    }
    
    
    public void update(Player player) {
        long currentTime = System.currentTimeMillis();
        
        // random control reversal at high knowledge
        if (knowledgeTier >= 4 && !controlsReversed && random.nextFloat() < 0.0005f) { // 0.05% per frame
            startControlReversal();
        }
        
        // end control reversal if duration is up
        if (controlsReversed && currentTime >= controlReversalEndTime) {
            controlsReversed = false;
            System.out.println("Controls returned to normal");
        }
        
        //  screen shake for tier 3+
        if (knowledgeTier >= 3) {
            screenShakeX = (random.nextFloat() - 0.5f) * 2f * knowledgeTier;
            screenShakeY = (random.nextFloat() - 0.5f) * 2f * knowledgeTier;
        } else {
            screenShakeX = 0f;
            screenShakeY = 0f;
        }
    }
    
    private void startControlReversal() {
        controlsReversed = true;
        controlReversalEndTime = System.currentTimeMillis() + CONTROL_REVERSAL_DURATION;
        System.out.println("CONTROLS REVERSED!");
    }
    
    // Apply visual effects - call this in your draw method
public void applyVisualEffects(GraphicsHandler graphicsHandler, int screenWidth, int screenHeight) {
    Graphics2D g = graphicsHandler.getGraphics();
    
    // Save original composite
    Composite originalComposite = g.getComposite();
    
    // Apply desaturation/color tint
    float desaturation = getDesaturation();
    if (desaturation > 0) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, desaturation * 0.3f)); // Reduced from 0.5f
        g.setColor(new Color(50, 20, 70)); // Purple tint
        g.fillRect(0, 0, screenWidth, screenHeight);
    }
    
    // Vignette effect (darkened edges) - FIXED to not stack
    if (knowledgeTier >= 2) {
        int vignetteSize = 100; // Fixed size instead of multiplying by tier
        float vignetteAlpha = 0.15f + (0.1f * (knowledgeTier - 2)); // Gradually increases: 0.15, 0.25, 0.35
        
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, vignetteAlpha));
        g.setColor(Color.BLACK);
        
        // Top
        g.fillRect(0, 0, screenWidth, vignetteSize);
        // Bottom
        g.fillRect(0, screenHeight - vignetteSize, screenWidth, vignetteSize);
        // Left
        g.fillRect(0, 0, vignetteSize, screenHeight);
        // Right
        g.fillRect(screenWidth - vignetteSize, 0, vignetteSize, screenHeight);
    }
    
    // Occasional screen glitch at high tiers
    if (knowledgeTier >= 3 && random.nextFloat() < 0.01f) {
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
        g.setColor(new Color(255, 0, 255)); // Magenta glitch
        int glitchY = random.nextInt(screenHeight);
        int glitchHeight = 5 + random.nextInt(20);
        g.fillRect(0, glitchY, screenWidth, glitchHeight);
    }
    
    // Restore original composite
    g.setComposite(originalComposite);
}
    
    // audio management
    private void updateAudio() {
        if (!audioInitialized) {
            initializeAudio();
        }
        
        // changes volume based on tier
        if (ambientDroneClip != null && ambientDroneClip.isOpen()) {
            FloatControl volume = (FloatControl) ambientDroneClip.getControl(FloatControl.Type.MASTER_GAIN);
            float gainValue = -40f + (knowledgeTier * 10f); // Gets louder with tier
            volume.setValue(Math.max(volume.getMinimum(), Math.min(gainValue, volume.getMaximum())));
        }
        
        // start/stop sounds based on tier
        if (knowledgeTier >= 1) {
            if (ambientDroneClip != null && !ambientDroneClip.isRunning()) {
                ambientDroneClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
        
        if (knowledgeTier >= 2) {
            if (whispersClip != null && !whispersClip.isRunning()) {
                whispersClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }
    
    private void initializeAudio() {
        
        // Temporary placeholder implementation need to add audio files next sprint (5)
        try {
            // load ambient drone sound
            File droneFile = new File("Resources/Sounds/drone.wav");
            if (droneFile.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(droneFile);
                ambientDroneClip = AudioSystem.getClip();
                ambientDroneClip.open(audioStream);
            }
            
            // load whispers sound
            File whispersFile = new File("Resources/Sounds/whispers.wav");
            if (whispersFile.exists()) {
                AudioInputStream audioStream2 = AudioSystem.getAudioInputStream(whispersFile);
                whispersClip = AudioSystem.getClip();
                whispersClip.open(audioStream2);
            }
            
            audioInitialized = true;
        } catch (Exception e) {
            System.out.println("Could not load audio files: " + e.getMessage());
            audioInitialized = true; 
        }
    }
    
    // cleanup audio
    public void cleanup() {
        if (ambientDroneClip != null) {
            ambientDroneClip.stop();
            ambientDroneClip.close();
        }
        if (whispersClip != null) {
            whispersClip.stop();
            whispersClip.close();
        }
    }
    
    public boolean shouldSpawnPhantom() {
        float chance = 0f;
        if (knowledgeTier >= 1) chance = 0.05f;
        if (knowledgeTier >= 2) chance = 0.15f;
        if (knowledgeTier >= 3) chance = 0.25f;
        if (knowledgeTier >= 4) chance = 1.0f; 
        return random.nextFloat() < chance;
    }
    
    public int getMaxPhantoms() {
        if (knowledgeTier >= 4) return 3;
        if (knowledgeTier >= 3) return 3;
        if (knowledgeTier >= 2) return 2;
        return 1;
    }
    
    public boolean areControlsReversed() {
        return controlsReversed;
    }
    
    public float getDesaturation() {
        if (knowledgeTier >= 4) return 0.7f;
        if (knowledgeTier >= 3) return 0.5f;
        if (knowledgeTier >= 2) return 0.3f;
        if (knowledgeTier >= 1) return 0.1f;
        return 0f;
    }
    
    public float getKnowledgeLevel() {
        return knowledgeLevel;
    }
    
    public int getKnowledgeTier() {
        return knowledgeTier;
    }
    
    public float getScreenShakeX() {
        return screenShakeX;
    }
    
    public float getScreenShakeY() {
        return screenShakeY;
    }
}