import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Bot extends TelegramLongPollingBot {

    Brigade[] brigades = new Brigade().getBrigades();
    String brigade = "";
    int key = 0;
    boolean isQuestion = false;
    int answer;
    int flag = -1;
    String text = "";
    int ball = 2;
    Keys[] keys = new Keys().getKeys();
    Keys keyObject;

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
                outMessage.setChatId(inMessage.getChatId());
                //Указываем текст сообщения

                switch (flag){
                    case -1:
                        text = "Введи название своего отряда. А я попробую его найти";
                        flag = 0;
                        break;
                    case 0:
                        String userBrigade = inMessage.getText();
                        for (Brigade item : brigades) {
                            if (item.name.contains(userBrigade)) {
                                brigade = item.name;
                                flag = 1;
                                break;
                            } else {
                                brigade = "";
                            }
                        }
                            if (brigade.isEmpty()) {
                                text = "Отряд не найден. Попробуй еще раз";
                                flag = 0;
                            } else {
                                text = "Твой отряд " + brigade + "? Введи да или нет";
                                flag = 1;
                            }

                        break;
                    case 1:
                        String decision = inMessage.getText();
                        if (decision.contains("да") || decision.contains("Да")){
                            flag = 2;
                        } else {
                            text = "Попробуй еще раз ввести название";
                            flag = 0;
                        }
                        break;
                    case 2:
                        text = "Введи ответ полученного ключа";
                        flag = 3;
                        break;
                    case 3:
                        int code = Integer.parseInt(inMessage.getText());
                        for (Keys value : keys) {
                            if (code == value.key) {
                                key = value.key;
                                keyObject = value;
                            }
                        }
                        if (key != 0){
                            text = "Код верный. Хотите ли доп.вопрос?";
                            flag = 4;
                        } else {
                            text = "Код неверный";
                        }
                        break;
                    case 4:
                        String decision1 = inMessage.getText();
                        if (decision1.contains("да")||decision1.contains("Да")){
                            text = keyObject.q;
                            isQuestion = true;
                            flag = 5;
                        } else {
                            text = "Ответ принят. Пока";
                            flag = 1;
                        }
                        break;
                    case 5:
                        String answer = inMessage.getText();
                        if(answer.equals(keyObject.trueA)){
                            ball *=2;
                            text = "Верно. Колдичество баллов за задание удвоено";
                        } else{
                            ball = 0;
                            text = "Неверно. Итого 0 баллов";
                        }

                }



                //outMessage.setText(inMessage.getText());

                outMessage.setText(text);

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
        return "";
    }
}
