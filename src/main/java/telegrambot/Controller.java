package telegrambot;

import org.telegram.telegrambots.api.methods.GetFile;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Audio;
import org.telegram.telegrambots.api.objects.File;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.Voice;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import service.Service;

public class Controller extends TelegramLongPollingBot {

	private String token;
	private String username;

	public Controller(String token, String username) {
		this.token = token;
		this.username = username;
	}

	public void onUpdateReceived(Update update) {
		System.out.println("Ricevuto messaggio");

		String chatId = "";
		Message message = null;
		Audio audio = null;
		Voice voice = null;
		String fileId = "";
		if (update != null)
			message = update.getMessage();

		if (message != null) {
			chatId = message.getChatId().toString();
			audio = message.getAudio();
			voice = message.getVoice();
		}

		if (chatId.isEmpty())
			return;

		if (audio != null)
			fileId = audio.getFileId();
		else if (voice != null)
			fileId = voice.getFileId();
		
		// get file
		if (fileId.isEmpty()) {
			sendError(chatId);
			return;
		}

		java.io.File file = getFile(fileId);

		if (file == null) {
			sendError(chatId);
			return;
		}

		String result = Service.getText(file);
		
		sendResponse(result, chatId);
	}

	private void sendResponse(String response, String chatId) {
		SendMessage sendMessage = new SendMessage(chatId, response);

		try {
			sendApiMethod(sendMessage);
		} catch (TelegramApiException e) {
			System.out.println("Impossibile rispondere");
		}
	}

	private void sendError(String chatId) {
		sendResponse("Nessun file da analizzare", chatId);
	}

	private java.io.File getFile(String fileId) {
		GetFile getFile = new GetFile();
		getFile.setFileId(fileId);

		File file = null;
		java.io.File result = null;

		try {
			file = sendApiMethod(getFile);
			result = downloadFile(file);
		} catch (TelegramApiException e) {
			System.out.println("Impossibile rispondere");
		}

		return result;
	}

	public String getBotUsername() {
		return username;
	}

	@Override
	public String getBotToken() {
		return token;
	}

}
