package com.verocoda.datagramtester;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void run100_100(View view) {
        UDP_Client client = new UDP_Client();
        client.context = getApplicationContext();
        client.iterations = 8929;
        client.sleep_time = 350;
        client.run_time = 100;
        client.send();
    }

    public void run100_50(View view) {
        UDP_Client client = new UDP_Client();
        client.context = getApplicationContext();
        client.iterations = 8929;
        client.sleep_time = 350;
        client.run_time = 50;
        client.send();
    }


    public void run50_100(View view) {
        UDP_Client client = new UDP_Client();
        client.context = getApplicationContext();
        client.iterations = 4465;
        client.sleep_time = 650;
        client.run_time = 100;
        client.send();
    }


    public void run50_50(View view) {
        UDP_Client client = new UDP_Client();
        client.context = getApplicationContext();
        client.iterations = 4465;
        client.sleep_time = 650;
        client.run_time = 50;
        client.send();
    }

    public void run20_100(View view) {
        UDP_Client client = new UDP_Client();
        client.context = getApplicationContext();
        client.iterations = 1786;
        client.sleep_time = 850;
        client.run_time = 100;
        client.send();
    }

    public void run20_50(View view) {
        UDP_Client client = new UDP_Client();
        client.context = getApplicationContext();
        client.iterations = 1786;
        client.sleep_time = 850;
        client.run_time = 50;
        client.send();
    }

    public void run5_100(View view) {
        UDP_Client client = new UDP_Client();
        client.context = getApplicationContext();
        client.iterations = 447;
        client.sleep_time = 950;
        client.run_time = 100;
        client.send();
    }

    public void run5_50(View view) {
        UDP_Client client = new UDP_Client();
        client.context = getApplicationContext();
        client.iterations = 447;
        client.sleep_time = 950;
        client.run_time = 50;
        client.send();
    }
}


class UDP_Client {
    private AsyncTask<Void, Void, Void> async_cient;
    public int sleep_time;
    public int iterations;
    public int run_time;
    public Context context;

    @SuppressLint("NewApi")
    public void send() {
        async_cient = new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                DatagramSocket ds = null;

                try {
                    ds = new DatagramSocket();
                    long time = System.currentTimeMillis();
                    Log.d("UDP_Client","current time " + System.currentTimeMillis());
                    byte[] data = new byte[1470];
                    DatagramPacket dp = new DatagramPacket(data, 1470, InetAddress.getLocalHost(), 50002);
                    Log.d("UDP_Client", "DP contents " + dp.getLength());
                    for(int j=0;j<run_time;j++) {
                        for (int i = 0; i < iterations ; i++) {
                            //ds.setBroadcast(true);
                            ds.send(dp);
                            //Log.d("UDP_Client", "dp to string " + dp.toString());
                        }
                        Thread.sleep(sleep_time);
                    }
                    Log.d("UDP_Client","current time " + System.currentTimeMillis());
                    Log.d("UDP_Client","difference time " + (System.currentTimeMillis() - time));
                    publishProgress();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (ds != null) {
                        ds.close();
                    }
                }
                return null;
            }

            @Override
            protected void onProgressUpdate(Void... values) {
                super.onProgressUpdate(values);
                Toast.makeText(context,"Completed test",Toast.LENGTH_LONG).show();
            }

            protected void onPostExecute(Void result) {
                super.onPostExecute(result);
            }
        };

        if (Build.VERSION.SDK_INT >= 11)
            async_cient.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        else async_cient.execute();
    }
}
