import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class CurrencyConverter{
    private static HttpURLConnection connection;
    public static void main(String[]args){
        Scanner scnr = new Scanner(System.in);
        BufferedReader reader;
        String line;
        StringBuffer responseContent = new StringBuffer();
        String currency1;
        String currency2;
        Double amount;

        System.out.println("Hello! This is a currency converter using the Free Currency Rates API");
        System.out.println("To get a full list of all the currencies converted and their 3 letter acronym go to: https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies.json");
        System.out.println("Link to Free Currency Rates API used in this code: https://github.com/fawazahmed0/currency-api");
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------");

        System.out.println("Enter 3 letter acronym of currency you want to convert");
        currency1 = scnr.next();
        System.out.println("Enter amount you want converted");
        amount = scnr.nextDouble();
        System.out.println("Enter 3 letter acronym of currency that you want it converted to");
        currency2 = scnr.next();

        try{URL url = new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/"+ currency1 + "/" + currency2+ ".json");
                connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                int status = connection.getResponseCode();
                //System.out.println(status);

                if(status > 299){
                    reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                    while((line = reader.readLine()) != null){
                        responseContent.append(line);
                    }
                    reader.close();
                }else{
                    reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    while((line = reader.readLine()) != null){
                        responseContent.append(line);
                    }
                    reader.close();
                }
                //System.out.println(responseContent.toString());

                String requestResponse = responseContent.substring(37, responseContent.length() - 1);
                connection.disconnect();
                Double requestDouble = Double.parseDouble(requestResponse);
                double finalAmount = amount * requestDouble;
                System.out.println(amount + " " + currency1 + " = " + finalAmount + " " + currency2);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            scnr.close();
    }
}