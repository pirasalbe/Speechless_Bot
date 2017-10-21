package telegrambot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import com.google.inject.ConfigurationException;

import service.Configuration;
import service.IConfiguration;

public class Application {

	public static void main(String[] args) {
		
		IConfiguration config = new Configuration();

		String token = config.getToken();
		String username = config.getUsername();

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
