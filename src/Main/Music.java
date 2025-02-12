package Music;

import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.Clip
import javax.sound.sampled.LineUnavailableExcepetion
import javax.sound.sampled.UnsupportedAudioFileException
public class Track {

	
	public static void main (String[] args) {
		play("Track1.mp3");
	}
	
	
	public static void play(String filepath) {
		InputStream music;
		try {
			music=new FileInputStream(String filepath) {
				InputStream music;
				try {
					music= new FileInputStream(new File(filepath));
					AudioStream audios= new AudioStream(music);
					AudioPlayer.player.start(audios);
					
				}
				catch(Exception e) 
				{
					OptionPane.showMessageDialog(null,"Error");
					
				}
			}
		}
	}
}
