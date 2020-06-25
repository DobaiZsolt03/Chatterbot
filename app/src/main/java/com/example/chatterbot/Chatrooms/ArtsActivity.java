package com.example.chatterbot.Chatrooms;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.chatterbot.ChatActivity;
import com.example.chatterbot.Client;
import com.example.chatterbot.Fragments.ChatFragment;
import com.example.chatterbot.HomeActivity;
import com.example.chatterbot.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;



public class ArtsActivity extends AppCompatActivity {

    public static TextView textArea;
    private Client client;
    public ImageButton sendButton;
    public ImageButton disconnectbtn;
    public static EditText editText;
    FirebaseAuth firebaseAuth;
    public static String name;
    String userID;
    FirebaseFirestore fireStore;
    TextView dummy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arts);

        firebaseAuth = FirebaseAuth.getInstance();
        fireStore = FirebaseFirestore.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();
        dummy = findViewById(R.id.username);


        Connect();
        ThreadSendTask();
        textArea= (TextView)findViewById(R.id.textArea_arts);
        sendButton=findViewById(R.id.btn_send);
        editText=findViewById(R.id.sendMessage);
        disconnectbtn=findViewById(R.id.disconnectButton);

        textArea.setMovementMethod(new ScrollingMovementMethod());



        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.ThreadSendMessage_arts();
            }
        });

        disconnectbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                client.ThreadDisconnect();
                Intent intent = new Intent(ArtsActivity.this, ChatActivity.class);
                startActivity(intent);
            }
        });

    }

    @SuppressLint("StaticFieldLeak")
    void Connect(){
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                client = new Client("192.168.16.102",52864);
                return null;
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    void ThreadSendTask(){
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                client.ThreadSend();
                return null;
            }
        }.execute();
    }


    public static void printToConsole(String message){
        textArea.setText(textArea.getText()+message+"\n");

    }

}