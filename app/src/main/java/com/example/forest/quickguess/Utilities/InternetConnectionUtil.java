package com.example.forest.quickguess.Utilities;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.example.forest.quickguess.APIsInterface.APIRanks;
import com.example.forest.quickguess.Adapters.RanksAdapter;
import com.example.forest.quickguess.RecyclerView.Ranks;
import com.example.forest.quickguess.Services.WebService.RanksResponse;
import com.example.forest.quickguess.Services.WebService.RanksService;
import com.sdsmdg.tastytoast.TastyToast;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;


public class InternetConnectionUtil {
    private String TAG = "check_internet";
    private final OkHttpClient client = new OkHttpClient();
    private long startTime;
    private long endTime;
    private long fileSize;

    // Bandwidth range in kbps copied from FBConnect Class
    private int POOR_BANDWIDTH = 150;
    private int AVERAGE_BANDWIDTH = 550;
    private int GOOD_BANDWIDTH = 2000;
    public boolean isFast = false;

    public InternetConnectionUtil downloadInfo(Context context)
    {



      /*  final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Checking internet connection");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        Request request = new Request.Builder()
                .url("https://res.cloudinary.com/dpcxcsdiw/image/upload/v1535740442/playquickbackgrounds/video-gradient.png") // replace image url
                .addHeader("Cache-Control", "no-cache")
                .build();
        startTime = System.currentTimeMillis();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                isFast = false;
                e.printStackTrace();
                progressDialog.dismiss();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    isFast = false;
                    progressDialog.dismiss();
                    throw new IOException("Unexpected code " + response);
                }

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    Log.d(TAG, responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }
                InputStream input = response.body().byteStream();
                try {
                    ByteArrayOutputStream bos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];

                    while (input.read(buffer) != -1) {
                        bos.write(buffer);
                    }
                    byte[] docBuffer = bos.toByteArray();
                    fileSize = bos.size();

                } finally {
                    input.close();
                }
                endTime = System.currentTimeMillis();
                // calculate how long it took by subtracting endtime from starttime
                 double timeTakenMills = Math.floor(endTime - startTime);  // time taken in milliseconds
                 double timeTakenInSecs = timeTakenMills / 1000;  // divide by 1000 to get time in seconds
                 int kilobytePerSec = (int) Math.round(1024 / timeTakenInSecs);
                 double speed = Math.round(fileSize / timeTakenMills);

                Log.d(TAG, "Time taken in secs: " + timeTakenInSecs);
                Log.d(TAG, "Kb per sec: " + kilobytePerSec);
                Log.d(TAG, "Download Speed: " + speed);
                Log.d(TAG, "File size in kb: " + fileSize);

                if (kilobytePerSec > POOR_BANDWIDTH && kilobytePerSec <= AVERAGE_BANDWIDTH){
                    // Average connection
                    isFast = true;
                } else if (kilobytePerSec > AVERAGE_BANDWIDTH && kilobytePerSec <= GOOD_BANDWIDTH){
                    // Fast connection
                    isFast = true;
                }
            }
        });
        progressDialog.dismiss();*/
        return this;
    }
}
