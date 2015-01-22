package org.sample.cryptobenchmark;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    private Button button;
    private TextView textView;
    private static long start, end;


    private class BenchmarkTask extends AsyncTask<Void, Integer, Void> {

        @Override
        protected void onPostExecute(Void v) {
            textView.setText("Result [H/s]: " + (long) ((Math.pow(26, 5) / (end - start)) * 1000));
        }

        @Override
        protected Void doInBackground(Void... params) {
            start = System.currentTimeMillis();
            new BruteForcer().crack();
            end = System.currentTimeMillis();
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            setProgress(progress[0]);
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new ButtonClickListener());

        textView = (TextView) findViewById(R.id.textView);
    }

    class ButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {
            new BenchmarkTask().execute(null, null, null);
            textView.setText("clicked");
        }
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
}
