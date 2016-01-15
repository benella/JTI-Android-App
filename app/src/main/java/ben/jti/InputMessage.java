package ben.jti;

import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class InputMessage extends AppCompatActivity {

    private EditText message_input;
    private Button send_button;

    private String final_contact;

    private Button CS1;
    private Button CS2;
    private Button CS3;
    private Button CS4;
    private Button CS5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_message);

        message_input = (EditText) findViewById(R.id.inputMessage);
        send_button = (Button) findViewById(R.id.sendButton);

        CS1 = (Button) findViewById(R.id.CS1);
        CS2 = (Button) findViewById(R.id.CS2);
        CS3 = (Button) findViewById(R.id.CS3);
        CS4 = (Button) findViewById(R.id.CS4);
        CS5 = (Button) findViewById(R.id.CS5);


        // CS1 button
        View.OnClickListener CS1_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CS1.setTextColor(Color.RED);
                CS2.setTextColor(Color.BLACK);
                CS3.setTextColor(Color.BLACK);
                CS4.setTextColor(Color.BLACK);
                CS5.setTextColor(Color.BLACK);
                final_contact = CS1.getText().toString();
            }
        };
        CS1.setOnClickListener(CS1_listener);


        // CS2 button
        View.OnClickListener CS2_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CS1.setTextColor(Color.BLACK);
                CS2.setTextColor(Color.RED);
                CS3.setTextColor(Color.BLACK);
                CS4.setTextColor(Color.BLACK);
                CS5.setTextColor(Color.BLACK);
                final_contact = CS2.getText().toString();
            }
        };
        CS2.setOnClickListener(CS2_listener);


        // CS3 button
        View.OnClickListener CS3_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CS1.setTextColor(Color.BLACK);
                CS2.setTextColor(Color.BLACK);
                CS3.setTextColor(Color.RED);
                CS4.setTextColor(Color.BLACK);
                CS5.setTextColor(Color.BLACK);
                final_contact = CS3.getText().toString();
            }
        };
        CS3.setOnClickListener(CS3_listener);


        // CS4 button
        View.OnClickListener CS4_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CS1.setTextColor(Color.BLACK);
                CS2.setTextColor(Color.BLACK);
                CS3.setTextColor(Color.BLACK);
                CS4.setTextColor(Color.RED);
                CS5.setTextColor(Color.BLACK);
                final_contact = CS4.getText().toString();
            }
        };
        CS4.setOnClickListener(CS4_listener);


        // CS5 button
        View.OnClickListener CS5_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CS1.setTextColor(Color.BLACK);
                CS2.setTextColor(Color.BLACK);
                CS3.setTextColor(Color.BLACK);
                CS4.setTextColor(Color.BLACK);
                CS5.setTextColor(Color.RED);
                final_contact = CS5.getText().toString();
            }
        };
        CS5.setOnClickListener(CS5_listener);


        // Contact suggestions
        final Handler h = new Handler();
        int delay = 1000; //milliseconds

        h.postDelayed(new Runnable() {
            int delay = 1000; //milliseconds
            int i = 0;
            int last_length = 0;

            // Runs every second
            public void run() {
                String message = message_input.getText().toString();

                // If message length changed
                if (last_length != message.length()) {
                    last_length = message.length();

                    if (last_length != 0) {
                        // Setting CS
                        CS1.setText((i) + "");
                        CS2.setText((i + 1) + "");
                        CS3.setText((i + 2) + "");
                        CS4.setText((i + 3) + "");
                        CS5.setText((i + 4) + "");
                        i++;
                    }

                    else {
                        CS1.setText("");
                        CS2.setText("");
                        CS3.setText("");
                        CS4.setText("");
                        CS5.setText("");
                    }
                }

                h.postDelayed(this, delay);
            }
        }, delay);


        // Send button
        View.OnClickListener send_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        send_button.setOnClickListener(send_listener);
    }
}
