import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.*;
import java.io.InputStreamReader;

public class CurrencyConverterGUI extends JFrame {
    final private Font mainFont = new Font("segoe print", Font.BOLD, 18);
    JTextField jfFirstCurrency, jfNextCurrency;
    JLabel LBwelcome;
    BufferedReader reader;
    String line;
    StringBuffer responseContent = new StringBuffer();
    private static HttpURLConnection connection;
    public void initialize(){
                        /*** FORM PANNEL ***/
        JLabel lbFirstCurrency = new JLabel("Currency you want to convert from");
        lbFirstCurrency.setFont(mainFont);
        lbFirstCurrency.setForeground(Color.WHITE);

        jfFirstCurrency = new JTextField();
        jfFirstCurrency.setFont(mainFont);

        JLabel lbNextCurrency = new JLabel("Currency you want to convert to");
        lbNextCurrency.setFont(mainFont);
        lbNextCurrency.setForeground(Color.WHITE);

        jfNextCurrency = new JTextField();
        jfNextCurrency.setFont(mainFont);


        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(4, 1, 5, 5));
        formPanel.setOpaque(false);
        formPanel.add(lbFirstCurrency);
        formPanel.add(jfFirstCurrency);
        formPanel.add(lbNextCurrency);
        formPanel.add(jfNextCurrency);
        
                        /*** WELCOME LABEL ***/
        LBwelcome = new JLabel();
        LBwelcome.setFont(mainFont);
                        /*** Buttons Panel ***/
        JButton btnOK = new JButton("OK");
        btnOK.setFont(mainFont);
        btnOK.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent a) {
                String firstCurrency = jfFirstCurrency.getText();
                String nextCurrency = jfNextCurrency.getText();
                try{URL url = new URL("https://cdn.jsdelivr.net/gh/fawazahmed0/currency-api@1/latest/currencies/"+ firstCurrency + "/" + nextCurrency+ ".json");
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
                LBwelcome.setForeground(Color.white);
                LBwelcome.setText(requestResponse + " "+ nextCurrency);
            }catch(Exception e){
                System.out.println(e.getMessage());
            }
            }
            
        });

        JButton btnClear = new JButton("Clear");
        btnClear.setFont(mainFont);
        btnClear.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                // TODO Auto-generated method stub
                jfFirstCurrency.setText("");
                jfNextCurrency.setText("");
                LBwelcome.setText("");
            }
            
        });

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(1, 2, 5, 5));
        buttonsPanel.setOpaque(false);
        buttonsPanel.add(btnOK);
        buttonsPanel.add(btnClear);



        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(new Color(26, 31, 51));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(LBwelcome, BorderLayout.CENTER);
        mainPanel.add(buttonsPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setTitle("Currency Converter");
        setSize(500,600);
        setMinimumSize(new Dimension(300,400));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args){
        CurrencyConverterGUI myFrame = new CurrencyConverterGUI();
        myFrame.initialize();
    }
}
