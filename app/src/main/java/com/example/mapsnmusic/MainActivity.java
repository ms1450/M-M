package com.example.mapsnmusic;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.spotify.android.appremote.api.ConnectionParams;
import com.spotify.android.appremote.api.Connector;
import com.spotify.android.appremote.api.SpotifyAppRemote;
import com.spotify.protocol.client.Subscription;
import com.spotify.protocol.types.PlayerState;
import com.spotify.protocol.types.Track;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {
    private static final String CLIENT_ID = "2b24f4707b1442ceb90e2010b8d36d71";
    private static final String REDIRECT_URI ="http://com.mapsnmusic.login/callback";
    private SpotifyAppRemote mSpotifyAppRemote;



    private Button playbt;
    private String[] location_data;
    public String mood;
    private TextView song;
    public TextView cond;
    public TextView temp;

    public String[] high = {"spotify:playlist:37i9dQZF1DWWMOmoXKqHTD", "spotify:playlist:37i9dQZF1DX3rxVfibe1L0", "spotify:playlist:37i9dQZF1DX4fpCWaHOned", "spotify:playlist:37i9dQZF1DX6ziVCJnEm59"};
    public String[] med = {"spotify:playlist:37i9dQZF1DXdPec7aLTmlC", "spotify:playlist:37i9dQZF1DX7gIoKXt0gmx", "spotify:playlist:37i9dQZF1DX0UrRvztWcAU", "spotify:playlist:37i9dQZF1DWXmlLSKkfdAk", "37i9dQZF1DWSRc3WJklgBs"};
    public String[] low = {"spotify:playlist:37i9dQZF1DWSqmBTGDYngZ", "spotify:playlist:37i9dQZF1DX7KNKjOK0o75", "spotify:playlist:37i9dQZF1DX3YSRoSdA634"};
    private static final String TAG = "MainActivity";



    private static final int REQUEST_CODE =1337;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playbt = (Button) findViewById(R.id.playbtn); // Create Play Button
//       // loc = (TextView)findViewById(R.id.location);
//        img = (ImageView)findViewById(R.id.imageView2);
        song = (TextView) findViewById(R.id.song);
        temp = (TextView) findViewById(R.id.temp);
        cond = (TextView) findViewById(R.id.condition);
//
        playbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fetchdata background = new fetchdata();
                background.execute();
                connected();


            }
        });

        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                extract(location);


            }

            private void extract(Location location) {
                String templocation = location.toString();
                String s[];
                s = (String[]) templocation.split(" ");
                location_data = s[1].split(",");
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);


    }

    protected String runtemp(String[] temp) {
        int rnd = new Random().nextInt(temp.length);
        return temp[rnd];
    }

    @Override
    protected void onStart() {
        super.onStart();
        ConnectionParams connectionParams =
                new ConnectionParams.Builder(CLIENT_ID)
                        .setRedirectUri(REDIRECT_URI)
                        .showAuthView(true)
                        .build();


        SpotifyAppRemote.connect(this, connectionParams,
                new Connector.ConnectionListener() {


                    @Override
                    public void onConnected(SpotifyAppRemote spotifyAppRemote) {
                        mSpotifyAppRemote = spotifyAppRemote;
                        Log.d("MainActivity", "Connected! Yay!");
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        Log.d("MainActivity", "Failiure! ");
                        Log.e("MainActivity", throwable.getMessage(), throwable);

                    }
                });


    }

    private void connected() {
        // Then we will write some more code here.

        Log.d("MainActivity", "OKAYYYYYY SO THIS RUNSSS");
        if (mood == "high") {
            mSpotifyAppRemote.getPlayerApi().play(runtemp(high));//"spotify:playlist:37i9dQZF1DX6Rl8uES4jYu");//37i9dQZF1DX2sUQwD7tbmL

        } else if (mood == "medium") {
            mSpotifyAppRemote.getPlayerApi().play(runtemp(med));//"spotify:playlist:37i9dQZF1DX6Rl8uES4jYu");//37i9dQZF1DX2sUQwD7tbmL

        } else {
            mSpotifyAppRemote.getPlayerApi().play(runtemp(low));//"spotify:playlist:37i9dQZF1DX6Rl8uES4jYu");//37i9dQZF1DX2sUQwD7tbmL
        }
        mSpotifyAppRemote.getPlayerApi()
                .subscribeToPlayerState()
                .setEventCallback(playerState -> {
                    final Track track = playerState.track;
                    if (track != null) {
                        Log.d("MainActivity", track.name + " by " + track.artist.name);
                        song.setText(track.name + " by " + track.artist.name);

                    }
                });
    }


    @Override
    protected void onStop() {
        super.onStop();
        SpotifyAppRemote.disconnect(mSpotifyAppRemote);
    }


    class fetchdata extends AsyncTask<Void, Void, String[]> {
        String data;
        String line = "";
        String temperature;
        String music;
        String mood;
        String[] arr = new String[3];
        String idnum;


        private String getResponvce(URL url) throws IOException {

            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

            try{
                InputStream in = httpURLConnection.getInputStream();

                Scanner scanner = new Scanner(in);
                scanner.useDelimiter("\\A");

                boolean hasInput = scanner.hasNext();
                if(hasInput){
                    return scanner.next();
                }else{
                    return null;
                }
            } finally {
                httpURLConnection.disconnect();
            }
        }

        @Override
        public String[] doInBackground(Void... voids) {
            try {
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?lat=43.054304&lon=-77.606241&APPID=c2669574602fa7abeb8a87e07656a42b");

                String s = getResponvce(url);

                JSONObject jsonObject = new JSONObject(s);


                JSONArray array = jsonObject.getJSONArray("weather");
                System.out.println("hghghg " + array);
                JSONObject obj = array.getJSONObject(0);
                idnum = obj.getString("id");

                JSONObject jsonObject1 = jsonObject.getJSONObject("main");
                String var = jsonObject1.getString("temp");
                String highS = jsonObject1.getString("temp_max");
                String lowS = jsonObject1.getString("temp_min");


                double high = Double.parseDouble(highS);
                double low = Double.parseDouble(lowS);
                temperature = var;
                double temp = Double.parseDouble(temperature);
                if (temp > high) {
                    music = "High Temperature - Playing High Temperature Music";
                    mood = "high";
                } else if (temp < low) {
                    music = "Low Temperature - Playing Low Temperature Music";
                    mood = "medium";
                } else {
                    music = "Moderate Temperature - Playing Moderate Temperature Music";
                    mood = "low";
                }

                arr[0] = temperature;
                arr[1] = music;
                arr[2] = mood;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }


            return arr;
        }

        @Override
        protected void onPostExecute(String[] strings) {
            for (int i = 0; i < strings.length; i++) {
                this.mood = strings[2];
                this.music = strings[1];
                this.temperature = strings[0];
                System.out.println("Teststt " + mood + music + temperature);
                HashMap<Integer, String> dictionary = new HashMap<>();
                idtoConditions itc = new idtoConditions();
                itc.loadDictionary(dictionary);
                String condition = "Its " + itc.getWeather(Integer.parseInt(idnum), dictionary);

                cond.setText(condition);
                temp.setText(this.temperature + getString(R.string.Temp));

            }
        }
    }

}

