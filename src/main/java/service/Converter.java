package service;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Converter {

	int nDefaultSampleSizeInBits = 16;
	float fDefaultSampleRate = 16000;
	AudioFormat.Encoding defaultEncoding = AudioFormat.Encoding.PCM_SIGNED;
	AudioFileFormat.Type defaultFileType = AudioFileFormat.Type.WAVE;
	boolean bDefaultBigEndian = false;
	int nDefaultChannels = 1;
	float frameRate = 16000;
	int frameSize = 2;
	AudioFormat defaultFormat = null;
	File inputFile = null;
	File outputFile = null;

	public Converter(File file) {

		try {
			// Standard Format
			defaultFormat = new AudioFormat(defaultEncoding, fDefaultSampleRate, nDefaultSampleSizeInBits,
					nDefaultChannels, frameSize, frameRate, bDefaultBigEndian);
			inputFile = file;
			outputFile = file;
			convert();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected void convert() throws UnsupportedAudioFileException, IOException {

		AudioFileFormat inputFileFormat = AudioSystem.getAudioFileFormat(inputFile);
		AudioFormat inputFormat = inputFileFormat.getFormat();

		if (inputFormat.matches(defaultFormat)) {
			System.out.println("No Change");
			return;
		}

		AudioInputStream sourceStream = AudioSystem.getAudioInputStream(inputFile);
		int nWrittenBytes = 0;
		AudioInputStream stream = AudioSystem.getAudioInputStream(defaultFormat, sourceStream);
		nWrittenBytes = AudioSystem.write(stream, defaultFileType, outputFile);
		System.out.println("Written bytes: " + nWrittenBytes);
	}

	public File getOutputFile() {
		return outputFile;
	}
}
