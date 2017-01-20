package com.example.deepakrattan.threadsdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView txtMsg;
    private Button btnClick;
    private EditText edtMsg;


    //Handling empty message from separate thread
   /* Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            txtMsg.setText("Button Pressed");
        }
    };*/


    //Handling message from separate thread

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Bundle bundle = msg.getData();
            String message = bundle.getString("message");
            txtMsg.setText(message);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //findViewByID
        txtMsg = (TextView) findViewById(R.id.txtMsg);
        btnClick = (Button) findViewById(R.id.btnClick);
        edtMsg = (EditText) findViewById(R.id.edtMsg);


      /*  btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Performing long running operations on the main thread
                // which is not desirable for Android Application Development
                long endTime = System.currentTimeMillis() + 20 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime - System.currentTimeMillis());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //After 20 seconds text will be set on the text view
                txtMsg.setText("Button pressed");
            }
        });*/

        //Performing long running task on separate thread
  /*      btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new thread

                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {
                        long endTime = System.currentTimeMillis() + 20 * 1000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }//end of while
                            handler.sendEmptyMessage(0);
                        }

                    }
                };//end of runnable

                Thread thread = new Thread(runnable);
                thread.start();
            }
        });*/

        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new thread
                Runnable runnable = new Runnable() {
                    @Override
                    public void run() {

                        long endTime = System.currentTimeMillis() + 20 * 1000;
                        while (System.currentTimeMillis() < endTime) {
                            synchronized (this) {
                                try {
                                    wait(endTime - System.currentTimeMillis());
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }//end of while

                        //Sending message to handler

                        Message message = handler.obtainMessage();
                        Bundle bundle = new Bundle();
                        bundle.putString("message","this is message from separate thread");
                        message.setData(bundle);
                        handler.sendMessage(message);

                    }
                };
                Thread thread = new Thread(runnable);
                thread.start();
            }
        });
    }
}
