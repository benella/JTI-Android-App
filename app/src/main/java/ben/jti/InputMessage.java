package ben.jti;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class InputMessage extends AppCompatActivity {

    private EditText message_input;
    private Button send_button;
    private ImageButton speak;
    private ImageView logo;
    private ImageView rect;

    private String final_contact;

    private Button CS1;
    private Button CS2;
    private Button CS3;
    private Button CS4;
    private Button CS5;

    private JSONObject from_server;
    private JSONObject to_server;
    private String message;

    private String server_url = "http://10.0.2.2:8888/search";


    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_message);

        message_input = (EditText) findViewById(R.id.inputMessage);
        send_button = (Button) findViewById(R.id.sendButton);
        logo = (ImageView) findViewById(R.id.whologo);
        rect = (ImageView) findViewById(R.id.rectimage);
        speak = (ImageButton) findViewById(R.id.speach);

        CS1 = (Button) findViewById(R.id.CS1);
        CS2 = (Button) findViewById(R.id.CS2);
        CS3 = (Button) findViewById(R.id.CS3);
        CS4 = (Button) findViewById(R.id.CS4);
        CS5 = (Button) findViewById(R.id.CS5);

        send_button.setVisibility(View.INVISIBLE);


        // CS1 button
        View.OnClickListener CS1_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CS1.setTextColor(getResources().getColor(R.color.selected));
                CS2.setTextColor(getResources().getColor(R.color.choice2));
                CS3.setTextColor(getResources().getColor(R.color.choice3));
                CS4.setTextColor(getResources().getColor(R.color.choice4));
                CS5.setTextColor(getResources().getColor(R.color.choice5));
                final_contact = CS1.getText().toString();
            }
        };
        CS1.setOnClickListener(CS1_listener);


        // CS2 button
        View.OnClickListener CS2_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CS1.setTextColor(getResources().getColor(R.color.choice1));
                CS2.setTextColor(getResources().getColor(R.color.selected));
                CS3.setTextColor(getResources().getColor(R.color.choice3));
                CS4.setTextColor(getResources().getColor(R.color.choice4));
                CS5.setTextColor(getResources().getColor(R.color.choice5));
                final_contact = CS2.getText().toString();
            }
        };
        CS2.setOnClickListener(CS2_listener);


        // CS3 button
        View.OnClickListener CS3_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CS1.setTextColor(getResources().getColor(R.color.choice1));
                CS2.setTextColor(getResources().getColor(R.color.choice2));
                CS3.setTextColor(getResources().getColor(R.color.selected));
                CS4.setTextColor(getResources().getColor(R.color.choice4));
                CS5.setTextColor(getResources().getColor(R.color.choice5));
                final_contact = CS3.getText().toString();
            }
        };
        CS3.setOnClickListener(CS3_listener);


        // CS4 button
        View.OnClickListener CS4_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CS1.setTextColor(getResources().getColor(R.color.choice1));
                CS2.setTextColor(getResources().getColor(R.color.choice2));
                CS3.setTextColor(getResources().getColor(R.color.choice3));
                CS4.setTextColor(getResources().getColor(R.color.selected));
                CS5.setTextColor(getResources().getColor(R.color.choice5));
                final_contact = CS4.getText().toString();
            }
        };
        CS4.setOnClickListener(CS4_listener);


        // CS5 button
        View.OnClickListener CS5_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CS1.setTextColor(getResources().getColor(R.color.choice1));
                CS2.setTextColor(getResources().getColor(R.color.choice2));
                CS3.setTextColor(getResources().getColor(R.color.choice3));
                CS4.setTextColor(getResources().getColor(R.color.choice4));
                CS5.setTextColor(getResources().getColor(R.color.selected));
                final_contact = CS5.getText().toString();
            }
        };
        CS5.setOnClickListener(CS5_listener);




        // Speech button
        View.OnClickListener speak_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Dictate");
                Intent dict_message = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                dict_message.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                dict_message.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                dict_message.putExtra(RecognizerIntent.EXTRA_PROMPT,"Dictate your message");

                try {
                    startActivityForResult(dict_message, 100);
                } catch (ActivityNotFoundException e){
                    Toast.makeText(InputMessage.this, "This device doesn't support dictation", Toast.LENGTH_SHORT).show();
                }
            }
        };

        speak.setOnClickListener(speak_listener);



        // Contact suggestions
        final Handler h = new Handler();
        int delay = 1000; //milliseconds

        h.postDelayed(new Runnable() {
            int delay = 1000; //milliseconds
            int last_length = 0;
            JSONArray CS = null;

            // Runs every second
            public void run() {
                message = message_input.getText().toString();

                // If message length changed
                if (last_length != message.length()) {
                    last_length = message.length();

                    if (last_length != 0) {

                        // Getting data from server
                        try {

                            // Getting date and time
                            DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm:ss");
                            Calendar calendar = Calendar.getInstance();

                            to_server = new JSONObject("{\"msg\":\"" + message + "\",\"time\":\"" + df.format(calendar.getTime()) + "\"}");

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        new JSONTask().execute(server_url);


                    } else {

                        // Empty message - reset
                        CS1.setText("");
                        CS2.setText("");
                        CS3.setText("");
                        CS4.setText("");
                        CS5.setText("");
                        logo.setVisibility(View.VISIBLE);
                        rect.setVisibility(View.VISIBLE);
                        send_button.setVisibility(View.INVISIBLE);

                        CS1.setTextColor(getResources().getColor(R.color.choice1));
                        CS2.setTextColor(getResources().getColor(R.color.choice2));
                        CS3.setTextColor(getResources().getColor(R.color.choice3));
                        CS4.setTextColor(getResources().getColor(R.color.choice4));
                        CS5.setTextColor(getResources().getColor(R.color.choice5));
                    }
                }

                h.postDelayed(this, delay);
            }
        }, delay);


        // Send button
        View.OnClickListener send_listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Sending: " + message + ", To: " + final_contact);

                Uri uri = Uri.parse("smsto:0800000123");
                Intent it = new Intent(Intent.ACTION_SENDTO, uri);
                it.putExtra("sms_body", message);
                startActivity(it);
            }
        };

        send_button.setOnClickListener(send_listener);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


//    public String getPhoneNumber(String name, Context context) {
//        String ret = null;
//        String selection = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME+" like'%" + name +"%'";
//        String[] projection = new String[] { ContactsContract.CommonDataKinds.Phone.NUMBER};
//        Cursor c = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
//                projection, selection, null, null);
//        if (c.moveToFirst()) {
//            ret = c.getString(0);
//        }
//        c.close();
//        if(ret==null)
//            ret = "Unsaved";
//        return ret;
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case 100: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    message_input.setText(result.get(0));
                }
                break;
            }

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "InputMessage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ben.jti/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "InputMessage Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://ben.jti/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }


    public class JSONTask extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {

            HttpURLConnection connection = null;

            try{

                // Setting connection
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                connection.setRequestMethod("GET");
                connection.connect();

                // Sending output
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());

                String output = to_server.toString();
                System.out.println("Sending: " + output);
                writer.write(output);
                writer.flush();
                writer.close();

                // Getting respond
                InputStream input = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                StringBuilder result = new StringBuilder();
                String line;

                while((line = reader.readLine()) != null){
                    result.append(line);
                }

                return result.toString();

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(connection != null){
                    connection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            JSONArray CS = null;
            final Button [] CSBArray = {CS1, CS2, CS3, CS4, CS5};

            try {
                System.out.println("Got back: " + result);

                if (message.length() != 0) {

                    if (result != null) {

                        from_server = new JSONObject(result);

                        try {
                            if (from_server != null) {
                                CS = from_server.getJSONArray("result");
                                System.out.println(CS);

                                // Hiding logo
                                logo.setVisibility(View.INVISIBLE);
                                rect.setVisibility(View.INVISIBLE);

                                // Setting CS
                                int l = CS.length();
                                for (int i = 0; i < l; i++) {
                                    CSBArray[i].setText(CS.get(i) + "");
                                }

                                for (int i = l; i < 5; i++) {
                                    CSBArray[i].setText("");
                                }

                                send_button.setVisibility(View.VISIBLE);

                                CS1.setTextColor(getResources().getColor(R.color.selected));
                                CS2.setTextColor(getResources().getColor(R.color.choice2));
                                CS3.setTextColor(getResources().getColor(R.color.choice3));
                                CS4.setTextColor(getResources().getColor(R.color.choice4));
                                CS5.setTextColor(getResources().getColor(R.color.choice5));
                                final_contact = CS1.getText().toString();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        from_server = null;
                    }
                }

                else {

                    // Empty message - reset
                    CS1.setText("");
                    CS2.setText("");
                    CS3.setText("");
                    CS4.setText("");
                    CS5.setText("");

                    logo.setVisibility(View.VISIBLE);
                    rect.setVisibility(View.VISIBLE);
                    send_button.setVisibility(View.INVISIBLE);

                    CS1.setTextColor(getResources().getColor(R.color.choice1));
                    CS2.setTextColor(getResources().getColor(R.color.choice2));
                    CS3.setTextColor(getResources().getColor(R.color.choice3));
                    CS4.setTextColor(getResources().getColor(R.color.choice4));
                    CS5.setTextColor(getResources().getColor(R.color.choice5));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}


