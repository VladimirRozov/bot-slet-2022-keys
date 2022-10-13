import java.io.IOException;

public class Logic {
    Brigade[] brigades = new Brigade().getBrigades();
    String brigade = "";
    String key = "";
    boolean isQuestion = false;
    String answer;
    int flag = -1;
    String text = "";
    int ball = 2;
    Keys[] keys = new Keys().getKeys();
    Keys keyObject = new Keys();
    Result result;

    Logic(){};

    Logic(String chatID){
        result = new Result(chatID, brigade,key,false,answer,0, -1, text, keyObject);
    }

    public Result action(String inputTxt, Result result) {

        switch (result.flag) {
            case -1:
                text = "Введи название своего отряда. А я попробую его найти";
                flag = 0;
                break;
            case 0:
                for (Brigade item : brigades) {
                    if (item.name.contains(inputTxt)) {
                        brigade = item.name;
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
                    result.brigade = brigade;
                }
                break;
            case 1:
                if (inputTxt.contains("да") || inputTxt.contains("Да")) {
                    result.flag = flag;
                    flag = 3;
                    text = "Введи ответ полученного ключа";
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
                Result[] results = Result.getResult();
                for (Keys value : keys) {
                    try {
                        for (Result item : results) {
                            if (!item.key.equals(inputTxt) && !item.brigade.equals(brigade)) {
                                if (inputTxt.contains(value.key)) {
                                    key = value.key;
                                    keyObject = value;
                                }
                            }
                        }
                    } catch (NullPointerException e){
                        if (inputTxt.contains(value.key)) {
                            key = value.key;
                            keyObject = value;
                        }
                    }

                }
                if (!key.isEmpty()) {
                    text = "Код верный. Хотите ли доп.вопрос?";
                    flag = 4;
                    result.keys = keyObject;
                    result.key= key;
                } else {
                    text = "Код неверный";
                    flag = 3;
                }
                break;
            case 4:
                if (inputTxt.contains("да") || inputTxt.contains("Да")) {
                    text = result.keys.q + "\n" + result.keys.a1+ "\n" + result.keys.a2 + "\n" +
                            result.keys.a3 + "\n" + result.keys.a4 + "\n" + "Введи номер варианта!";
                    isQuestion = true;
                    result.q = isQuestion;
                    result.ball = ball;
                    flag = 5;
                } else {
                    text = "Ответ принят. Пока";
                    flag = -1;
                }
                break;
            case 5:
                answer = inputTxt;
                if (answer.equals(result.keys.trueA)) {
                    ball *= 2;
                    text = "Верно. Количество баллов за задание удвоено";
                    flag = -1;
                } else {
                    ball = 0;
                    text = "Неверно. Итого 0 баллов";
                    flag = -1;
                }
                result.ball = ball;
                try {
                    Result.setResult(result);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }
        result.text = text;
        result.flag = flag;
        try {
            Result.setResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
