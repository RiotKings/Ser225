package Engine;

import javax.sound.sampled.*;
import java.io.File;
import java.net.URL;


public class SoundEffect {
    
    /**
     * Play a sound effect once at default volume.
     * @param fileName Path to the sound file
     */
    public static void play(String fileName) {
        play(fileName, 1.0f);
    }
    
    /**
     * Play a sound effect once with specified volume.
     * @param fileName Path to the sound file
     * @param volume Volume level (0.0 to 1.0)
     */
    public static void play(String fileName, float volume) {
        new Thread(() -> {
            try {
                URL url = new File(fileName).toURI().toURL();
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                
                AudioFormat baseFormat = audioIn.getFormat();
                AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
                );
                AudioInputStream decodedAudioIn = AudioSystem.getAudioInputStream(decodedFormat, audioIn);
                
                Clip clip = AudioSystem.getClip();
                clip.open(decodedAudioIn);
                
                //  volume
                if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                    FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    float dB = (float) (Math.log(Math.max(0.0001, volume)) / Math.log(10.0) * 20.0);
                    gainControl.setValue(Math.max(gainControl.getMinimum(), Math.min(dB, gainControl.getMaximum())));
                }
                
                clip.addLineListener(event -> {
                    if (event.getType() == LineEvent.Type.STOP) {
                        clip.close();
                        try {
                            decodedAudioIn.close();
                            audioIn.close();
                        } catch (Exception ignored) {}
                    }
                });
                
                clip.start();
                
            } catch (Exception e) {
                System.err.println("Error playing sound: " + fileName);
                e.printStackTrace();
            }
        }).start();
    }
}