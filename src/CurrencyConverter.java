import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Scanner;

public class CurrencyConverter {

    public static void main(String[] args) throws IOException {

        // HashMap maps integers to currency codes.
        HashMap<Integer, String> currencyCodes = new HashMap<Integer, String>();

        // Add currency codes
        currencyCodes.put(1, "USD");
        currencyCodes.put(2, "CAD");
        currencyCodes.put(3, "EUR");
        currencyCodes.put(4, "HKD");
        currencyCodes.put(5, "INR");
        currencyCodes.put(6, "JPY");  // Corrected the currency code for Japanese Yen

        // set up variables
        
        String fromCode, toCode;
        double amount;

        Scanner sc = new Scanner(System.in);

        // Start the script
        System.out.println("We convert monies here.");

        // choose presented currency
        System.out.println("What do you have?");
        System.out.println("1:USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro) \t 4:HKD (Hong Kong Dollar) \t 5:INR (Indian Rupee) \t 6:JPY (Japanese Yen)");
        fromCode = currencyCodes.get(sc.nextInt());

        // choose desired currency
        System.out.println("What do you want?");
        System.out.println("1:USD (US Dollar) \t 2:CAD (Canadian Dollar) \t 3:EUR (Euro) \t 4:HKD (Hong Kong Dollar) \t 5:INR (Indian Rupee) \t 6:JPY (Japanese Yen)");
        toCode = currencyCodes.get(sc.nextInt());

        // select currency amount
        System.out.println("How much you got?");
        amount = sc.nextDouble();

        sendHttpGETRequest(fromCode, toCode, amount);

        System.out.println("Thanks. Come again.");
    }

    private static void sendHttpGETRequest(String fromCode, String toCode, double amount) throws IOException {
        String GET_URL = "https://api.exchangerate-api.com/v4/latest/" + fromCode;
        URL url = new URL(GET_URL);
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
        httpURLConnection.setRequestMethod("GET");
        int responseCode = httpURLConnection.getResponseCode();

        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject obj = new JSONObject(response.toString());
            Double exchangeRate = obj.getJSONObject("rates").getDouble(toCode);
            System.out.println(obj.getJSONObject("rates"));
            System.out.println(exchangeRate);
            System.out.println();
            System.out.println(amount + " " + fromCode + " = " + amount * exchangeRate + " " + toCode);
        } else {
            System.out.println("Get request failed!");
        }
    }
}
