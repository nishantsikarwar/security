package com.example.kira.security;

import android.content.Context;
import android.icu.text.StringPrepParseException;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.PicassoProvider;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PipedInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class JsonData extends AsyncTask<Void, Void, Void> {

    String single_parsed = "";
    String image_url = "";
    String line = "";
    String data = "";
//    Context context;
    boolean flag =false;


    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://thomso.in/api/mobile/security/media/"+MainActivity.qr_value.getText().toString());

            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
//            urlConnection.setRequestMethod("GET");
            InputStream stream = urlConnection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));


            String line = "";

            while(line!=null)
            {
                line = reader.readLine();
                data = data+line;
            }


            JSONObject jsonObject = new JSONObject(data);
            jsonObject  = (JSONObject) jsonObject.get("body");

            single_parsed ="NAME   :" + jsonObject.get("name") + "\n"+
                    "EMAIL  :" + jsonObject.get("email")+"\n"+
                    "CONTACT :"+jsonObject.get("contact")+"\n"+
                    "ORGANIZATION :" +jsonObject.get("organization");

            image_url = jsonObject.get("image").toString();

            int responseCode = urlConnection.getResponseCode();

            if (responseCode == HttpsURLConnection.HTTP_OK) {

                line = "Successs:"+Integer.toString(responseCode)+urlConnection.getResponseMessage();


            }else{
                line = "false:"+Integer.toString(responseCode)+urlConnection.getResponseMessage();

            }

//                single_parsed = "USER BLOCKED";

        } catch (MalformedURLException e) {
            flag = true;
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            flag = true;
            e.printStackTrace();
        }



        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Log.d("Securuty",line);
       // Picasso.get().setIndicatorsEnabled(false);
        MainActivity.fetched_data.setText(single_parsed);
        Picasso.get().load("https://thomso.in/uploads/img/MediaImage/"+image_url).memoryPolicy(MemoryPolicy.NO_CACHE,MemoryPolicy.NO_STORE).networkPolicy(NetworkPolicy.NO_CACHE,NetworkPolicy.NO_STORE).into(MainActivity.profile_image);
        if(flag)
        MainActivity.users_media.setText("USERS_NOT_FOUND");

    }
}
