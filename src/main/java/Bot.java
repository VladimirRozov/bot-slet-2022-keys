import com.sun.istack.internal.logging.Logger;
import org.apache.commons.logging.Log;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {
    Logic logic;
    Keys keys;
    Result result = new Result("","","", false, "",0,-1,"", null);
    @Override
    public void onUpdateReceived(Update update) {
        try {
        //проверяем есть ли сообщение и текстовое ли оно
        if (update.hasMessage() && update.getMessage().hasText()) {
            //Извлекаем объект входящего сообщения
            Message inMessage = update.getMessage();
            //Создаем исходящее сообщение
            SendMessage outMessage = new SendMessage();
            //Указываем в какой чат будем отправлять сообщение
            //(в тот же чат, откуда пришло входящее сообщение)

            String message = inMessage.getText();
            String chatId = inMessage.getChatId().toString();
            logic = new Logic();
            if(chatId.equals(result.chatId)){
                    result = logic.action(message, result);
            } else {
                result.text = "Подожди.";
                if(result.flag == 5 || result.flag == -1) {
                    result.chatId = chatId;
                    result = logic.action(message, result);
                }
            }
            outMessage.setChatId(result.chatId);
            //Указываем текст сообщения
            outMessage.setText(result.text);
            //Отправляем сообщение
            execute(outMessage);
        }
    } catch (TelegramApiException e) {
        e.printStackTrace();
    }
}

    @Override
    public String getBotUsername() {
        return "keysspbso2022Bot";
    }

    @Override
    public String getBotToken() {
        return "5394225555:AAFYAqW4Un1Bl7WY6X32uyJxX13XYheT3qk";
    }
}