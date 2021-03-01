package il.ac.shenkar.costmanager.model;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * This enum represents the currencies on our cost manager program.
 *
 */
public enum Currency {
    ILS, USD, EUR, GBP;

    private static final String API_URL = "https://api.ratesapi.io/api/latest?base=";

    private JSONObject data = null;

    /**
     * API call according to the currency type, the data of all the rates according the currency type
     * will be saved inside JSONObject for continuous use.
     * @throws CostManagerException For any connection issue.
     */
    public void setExchangeRate() throws CostManagerException
    {
        try {

            URL url = new URL(API_URL+  this.name());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            // Getting the response code
            int responseCode = conn.getResponseCode();

            if (responseCode != 200)
            {
                throw new CostManagerException("Error using rates API - Set Exchange Rate.");
            }
            else
                {

                StringBuilder inline = new StringBuilder();
                Scanner scanner = new Scanner(url.openStream());

                // Write all the JSON data into a string using a scanner
                while (scanner.hasNext()) {
                    inline.append(scanner.nextLine());
                }

                // Close the scanner
                scanner.close();

                // Using the JSON simple library parse the string into a json object
                JSONParser parse = new JSONParser();
                data = (JSONObject) parse.parse(inline.toString());
                data = (JSONObject) data.get("rates");

            }

        } catch (Exception e) {
            throw new CostManagerException("Error using rates API - Set Exchange Rate.",e);
        }
    }

    /**
     * Convert currencies
     * @param price Represent the price that need to be converted
     * @param currency Represent the currency that need to be converted according to the received data
     * @return converted price (double)
     * @throws CostManagerException For any connection issue, no data received.
     */
    public double convert(double price, Currency currency) throws CostManagerException
    {
        if(data != null)
            return price / (Double) data.get(currency.name());
        else
            throw new CostManagerException("Error using rates API - Convert Currency.");
    }
}

