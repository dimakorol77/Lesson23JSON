import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class CurrencyExchange {
    public static void main(String[] args) {
        try {
            // URL для получения курсов валют
            URL url = new URL("https://www.cbr-xml-daily.ru/daily_json.js");

            // Отправляем GET запрос и получаем ответ в формате JSON
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Парсим JSON и выводим данные в консоль
            JSONObject json = new JSONObject(response.toString());
            System.out.println("Дата: " + json.getString("Date"));
            System.out.println("Курсы валют:");
            JSONObject rates = json.getJSONObject("Valute");
            for (String code : rates.keySet()) {
                JSONObject currency = rates.getJSONObject(code);
                String name = currency.getString("Name");
                double value = currency.getDouble("Value");
                System.out.println(code + " - " + name + ": " + value);
            }

        } catch (Exception e) {
            System.out.println("Произошла ошибка: " + e.getMessage());
        }
    }
}
