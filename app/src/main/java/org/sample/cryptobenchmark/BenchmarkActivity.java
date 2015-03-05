package org.sample.cryptobenchmark;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


public class BenchmarkActivity extends ActionBarActivity {
    private Button benchmarkButton, resultsButton;
    private TextView textViewMD5, textViewSHA1, textViewSHA2;
    private ProgressBar progressBarMD5;
    private ProgressBar progressBarSHA1;
    private ProgressBar progressBarSHA2;
    private static final int PROGRESS_BAR_MAX = 100;
    private static final int KEYSPACE_SIZE = 52 * 52 * 52;
    private byte[] lowerCaseASCII = new byte[]{
            0x41, 0x42, 0x43, 0x44, 0x45, 0x46, 0x47, 0x48, 0x49,
            0x4A, 0x4B, 0x4C, 0x4D, 0x4E, 0x4F, 0x50, 0x51, 0x52,
            0x53, 0x54, 0x55, 0x56, 0x57, 0x58, 0x59, 0x5A,
            0x61, 0x62, 0x63, 0x64, 0x65, 0x66, 0x67, 0x68, 0x69,
            0x6A, 0x6B, 0x6C, 0x6D, 0x6E, 0x6F, 0x70, 0x71, 0x72,
            0x73, 0x74, 0x75, 0x76, 0x77, 0x78, 0x79, 0x7A
    };

    private ByteCharset lowerCase = new ByteCharset(lowerCaseASCII);
    private int sampleMD5[] = new int[]{0xf3, 0xab, 0xb8, 0x6b, 0xd3, 0x4c, 0xf4, 0xd5,
            0x26, 0x98, 0xf1, 0x4c, 0x0d, 0xa1, 0xdc, 0x60
    }; // MD5(zzz)
    private int sampleSHA1[] = new int[]{0x40, 0xfa, 0x37, 0xec, 0x00, 0xc7, 0x61, 0xc7,
            0xdb, 0xb6, 0xeb, 0xde, 0xe6, 0xd4, 0xa2, 0x60,
            0xb9, 0x22, 0xf5, 0xf4}; // SHA1(zzz)
    private int sampleSHA2[] = new int[]{0x17, 0xf1, 0x65, 0xd5, 0xa5, 0xba, 0x69, 0x5f,
            0x27, 0xc0, 0x23, 0xa8, 0x3a, 0xa2, 0xb3, 0x46,
            0x3e, 0x23, 0x81, 0x0e, 0x36, 0x0b, 0x75, 0x17,
            0x12, 0x7e, 0x90, 0x16, 0x1e, 0xeb, 0xab, 0xda
    }; // SHA2(zzz)
    boolean inProgress = false;

    private class BenchmarkTask extends AsyncTask<Void, Integer, Void> {
        private long start[] = new long[3];
        private long end[] = new long[3];

        private long performance(int index) {
            return (long) (((float) (KEYSPACE_SIZE)) / (end[index] - start[index]) * 1000);
        }

        private void performTest(int index, String algorithm, int[] sample, ProgressBar progressBar) {
            start[index] = System.currentTimeMillis();
            new BruteForcer(lowerCase, 3).crack(algorithm, sample, progressBar);
            end[index] = System.currentTimeMillis();
        }

        @Override
        protected void onPostExecute(Void v) {

            textViewMD5.setText("MD5 [H/s]: " + performance(0));
            textViewMD5.setText("SHA1 [H/s]: " + performance(1));
            textViewMD5.setText("SHA2 [H/s]: " + performance(2));

            benchmarkButton.setText(getResources().getString(R.string.run));
        }

        @Override
        protected Void doInBackground(Void... params) {

            performTest(0, "MD5", sampleMD5, progressBarMD5);
            performTest(0, "SHA1", sampleSHA1, progressBarSHA1);
            performTest(0, "SHA2", sampleSHA2, progressBarSHA2);

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
        benchmarkButton = (Button) findViewById(R.id.button);
        benchmarkButton.setOnClickListener(new BenchmarkButtonClickListener());

        resultsButton = (Button) findViewById(R.id.button3);
        resultsButton.setOnClickListener(new ResultsButtonClickListener());

        textViewMD5 = (TextView) findViewById(R.id.textView);
        textViewSHA1 = (TextView) findViewById(R.id.textView3);
        textViewSHA2 = (TextView) findViewById(R.id.textView4);

        progressBarMD5 = (ProgressBar) findViewById(R.id.progressBar);
        progressBarMD5.setMax(PROGRESS_BAR_MAX);

        progressBarSHA1 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBarSHA1.setMax(PROGRESS_BAR_MAX);

        progressBarSHA2 = (ProgressBar) findViewById(R.id.progressBar3);
        progressBarSHA2.setMax(PROGRESS_BAR_MAX);


    }

    class BenchmarkButtonClickListener implements Button.OnClickListener {
        @Override
        public void onClick(View v) {

            if (!inProgress) {
                new BenchmarkTask().execute(null, null, null);
                benchmarkButton.setText(getResources().getString(R.string.stop));
            } else {
                benchmarkButton.setText(getResources().getString(R.string.run));
            }
            inProgress = !inProgress;
        }
    }

    class ResultsButtonClickListener implements Button.OnClickListener {

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(BenchmarkActivity.this, ResultsActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
