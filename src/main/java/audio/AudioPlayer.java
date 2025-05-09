package audio;

import java.net.URL;
import java.util.Random;
import java.io.IOException;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.BooleanControl;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class AudioPlayer {
	public static int MENU_1 = 0;
	public static int Level_1 = 1;
	public static int Level_2 = 2;

	public static int DIE = 0;
	public static int JUMP = 1;
	public static int GAMEOVER = 2;
	public static int LVL_COMPLETED = 3;
	public static int ATTACK_ONE = 4;
	public static int ATTACK_TWO = 5;
	public static int ATTACK_THREE = 6;

	private Clip[] songs, effects;
	private int currentSongId;
	private float volume = 0.55f;
	private boolean songMute, effectMute;
	private Random rand = new Random();

	public AudioPlayer() {
		loadSongs();
		loadEffects();
		playSong(MENU_1);
	}

	private void loadSongs() {
		String[] names = { "menu", "level1", "level2" };
		songs = new Clip[names.length];
		for (int i = 0; i < songs.length; i++)
			songs[i] = getClip(names[i]);
	}

	private void loadEffects() {
		String[] effectNames = { "die", "jump", "gameover", "lvlcompleted", "attack1", "attack2", "attack3" };
		effects = new Clip[effectNames.length]; // FIXED: was songs = ...
		for (int i = 0; i < effects.length; i++)
			effects[i] = getClip(effectNames[i]);

		updateEffectsVolume();
	}

	private Clip getClip(String name) {
		URL url = getClass().getResource("/" + name + ".wav");
		try {
			AudioInputStream audio = AudioSystem.getAudioInputStream(url);
			Clip c = AudioSystem.getClip();
			c.open(audio);
			return c;
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void setVolume(float volume) {
		this.volume = volume;
		updateSongVolume();
		updateEffectsVolume();
	}

	public void stopSong() {
		if (songs[currentSongId].isActive())
			songs[currentSongId].stop();
	}
	
	//Make sure this is uncommented later for the level songs to work once we have actual levels
		public void setLevelSong(int lvlIndex) {
			if (lvlIndex % 2 == 0)
				playSong(Level_1); // FIXED typo: playsong -> playSong
			else
				playSong(Level_2);
		}

	public void lvlCompleted() {
		stopSong();
		playEffect(LVL_COMPLETED); // FIXED typo: playeEffect -> playEffect
	}

	public void playAttackSound() {
		int start = 4;
		start += rand.nextInt(3);
		playEffect(start);
	}

	public void playEffect(int effect) {
		effects[effect].setMicrosecondPosition(0);
		effects[effect].start();
	}

	public void playSong(int song) {
			stopSong();
	
			currentSongId = song;
			updateSongVolume();
			songs[currentSongId].setMicrosecondPosition(0);
			songs[currentSongId].loop(Clip.LOOP_CONTINUOUSLY);

	}

	public void toggleSongMute() {
		this.songMute = !songMute;
		for (Clip c : songs) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(songMute);
		}
	}

	public void toggleEffectMute() {
		this.effectMute = !effectMute;
		for (Clip c : effects) {
			BooleanControl booleanControl = (BooleanControl) c.getControl(BooleanControl.Type.MUTE);
			booleanControl.setValue(effectMute);
		}
		if (!effectMute)
			playEffect(JUMP);
	}

	private void updateSongVolume() {
		if (songs[currentSongId] != null) {
			FloatControl gainControl = (FloatControl) songs[currentSongId].getControl(FloatControl.Type.MASTER_GAIN);
			float range = gainControl.getMaximum() - gainControl.getMinimum();
			float gain = (range * volume) + gainControl.getMinimum();
			gainControl.setValue(gain);
		}
	}

	private void updateEffectsVolume() {
		for (Clip c : effects) {
			if (c != null) {
				FloatControl gainControl = (FloatControl) c.getControl(FloatControl.Type.MASTER_GAIN);
				float range = gainControl.getMaximum() - gainControl.getMinimum();
				float gain = (range * volume) + gainControl.getMinimum();
				gainControl.setValue(gain);
			}
		}
	}
}
