package service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.api.StreamSpeechRecognizer;
import edu.cmu.sphinx.result.WordResult;

public class Service {

	public static String getText(File file) {
		String msg = "";

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
		} catch (IOException e) {
			System.out.println("File non trovato");
		}
		Configuration configuration = new Configuration();

		configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		configuration.setLanguageModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us.lm.bin");
		configuration.setSampleRate(6000);

		StreamSpeechRecognizer recognizer = null;

		try {
			recognizer = new StreamSpeechRecognizer(configuration);
		} catch (IOException e) {
			System.out.println("Impossibile inizializzare");
		}

		if (inputStream == null || recognizer == null)
			return "";

		recognizer.startRecognition(inputStream);
		SpeechResult result;
		while ((result = recognizer.getResult()) != null) {
			System.out.println("List of recognized words and their times:");
			for (WordResult r : result.getWords()) {
				System.out.println(r);
				msg += r.getWord().getSpelling() + " ";
			}
		}
		recognizer.stopRecognition();

		return msg;
	}
}
