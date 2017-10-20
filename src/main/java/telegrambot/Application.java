package telegrambot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.google.inject.ConfigurationException;

import service.Configuration;

public class Application {

	public static void main(String[] args) {

		String token = Configuration.getToken();
		String username = Configuration.getUsername();

		ApiContextInitializer.init();
		TelegramBotsApi telegramBotsApi = new TelegramBotsApi();

		Controller telegramBot = new Controller(token, username);

		try {
			telegramBotsApi.registerBot(telegramBot);
		} catch (TelegramApiException | ConfigurationException e) {
			System.out.println("Impossibile inizializzare Telegram Bot" + e);
		}

		System.out.println("Bot avviato");
	}

}
