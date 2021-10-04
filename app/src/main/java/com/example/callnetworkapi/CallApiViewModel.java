package com.example.callnetworkapi;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;

public class CallApiViewModel extends AndroidViewModel {
    private ExecutorService executor;

    private final MutableLiveData<Result<String>> _response = new MutableLiveData<>();
    public LiveData<Result<String>> response = _response;


    public CallApiViewModel(@NonNull Application application) {
        super(application);
        executor = ((CallApiApplication) application).executorService;
    }


    public void callNetworkApi(String input) {
        /* String jsonBody = "{ username: \"" + username + "\", token: \"" + token + "\" }";*/
        executor.execute(new Runnable() {
            @Override
            public void run() {
                makeSynchronousNetworkRequest(input, new ResponseCallback<String>() {
                    @Override
                    public void onComplete(Result<String> result) {
                        _response.postValue(result);
                    }
                });
            }
        });
    }


    public void makeSynchronousNetworkRequest(String jsonBody, ResponseCallback<String> callback) {
        try {
            URL url = new URL("https://jsonplaceholder.typicode.com/todos/5");
            HttpURLConnection httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            Log.d("TAG", httpConnection.getResponseCode() + "");
            InputStream in = new BufferedInputStream(httpConnection.getInputStream());
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String read;
            while ((read = br.readLine()) != null) {
                //System.out.println(read);
                sb.append(read);
            }
            br.close();

            String response = sb.toString();
            Result<String> result = new Result.Success<String>(response);
            callback.onComplete(result);
        } catch (Exception e) {
            Result<String> errorResult = new Result.Error<String>(e);
            callback.onComplete(errorResult);
        }
    }

    interface ResponseCallback<T> {
        void onComplete(Result<T> result);
    }

}

