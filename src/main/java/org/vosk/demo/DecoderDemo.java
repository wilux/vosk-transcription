package org.vosk.demo;

import java.io.*;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import org.vosk.LogLevel;
import org.vosk.Recognizer;
import org.vosk.LibVosk;
import org.vosk.Model;

public class DecoderDemo {

    public static void main(String[] argv) throws IOException, UnsupportedAudioFileException {
        LibVosk.setLogLevel(LogLevel.DEBUG);

        Mp3ToWav mp3ToWav = new Mp3ToWav();
        String file = "C:\\Users\\nesto\\Downloads\\vosk-api-master\\java\\demo\\audio\\audio.mp3";
        mp3ToWav.convert(file);

        try (Model model = new Model("models/vosk-model-small-en-us-0.15");
                    InputStream ais = AudioSystem.getAudioInputStream(new BufferedInputStream(new FileInputStream("audio/demo.wav")));
                    Recognizer recognizer = new Recognizer(model, 44050)) {

            int nbytes;
            byte[] b = new byte[4096];
            while ((nbytes = ais.read(b)) >= 0) {
                if (recognizer.acceptWaveForm(b, nbytes)) {
                   // System.out.println(recognizer.getResult());
                } else {
                   // System.out.println(recognizer.getPartialResult());
                }
            }

            System.out.println(recognizer.getFinalResult());
        }
    }
}
