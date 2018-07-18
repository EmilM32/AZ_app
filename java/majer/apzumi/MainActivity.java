package majer.apzumi;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    ArrayList<RepositoryData> arrayList;
    ListView lv;
    CheckBox checkBox;
    MyAdapter adapter;

    private static final String urlGithub  = "https://api.github.com/repositories";
    private static final String urlBitbucket  = "https://api.bitbucket.org/2.0/repositories?fields=values.name,values.owner,values.description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        arrayList = new ArrayList<>();
        lv = findViewById(R.id.listView);
        checkBox = findViewById(R.id.checkBox);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadGithub().execute(urlGithub);
                new ReadBitbucket().execute(urlBitbucket);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object objName = arrayList.get(position).getName();
                Object objRep = arrayList.get(position).getRep();
                Object objDescription = arrayList.get(position).getDescription();
                Object objAvatar = arrayList.get(position).getImage();

                Intent intent = new Intent(MainActivity.this, User.class);
                intent.putExtra("name", objName.toString());
                intent.putExtra("rep", objRep.toString());
                intent.putExtra("description", objDescription.toString());
                intent.putExtra("avatar", objAvatar.toString());
                startActivity(intent);
            }

        });

        boolean online = isOnline();
        if(!online){
            createNoInternetDialog();
        }

    }
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    class ReadGithub extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONArray jsonArray =  new JSONArray(content);

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = (JSONObject) jsonArray.get(i);
                    JSONObject owner = c.getJSONObject("owner");
                    arrayList.add(new RepositoryData(
                            c.getString("name"),
                            owner.getString("avatar_url"),
                            c.getString("description"),
                            "Github"
                    ));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            adapter = new MyAdapter(getApplicationContext(), R.layout.row_list, arrayList);
            lv.setAdapter(adapter);
            sortListByName(adapter);
        }
    }

    class ReadBitbucket extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            return readURL(params[0]);
        }

        @Override
        protected void onPostExecute(String content) {
            try {
                JSONObject jsonObject = new JSONObject(content);
                JSONArray jsonArray = jsonObject.getJSONArray("values");

                for(int i = 0; i < jsonArray.length(); i++){
                    JSONObject c = (JSONObject) jsonArray.get(i);
                    JSONObject owner = c.getJSONObject("owner");
                    JSONObject links = owner.getJSONObject("links");
                    JSONObject avatar = links.getJSONObject("avatar");
                    arrayList.add(new RepositoryData(
                            owner.getString("display_name"),
                            avatar.getString("href"),
                            c.getString("description"),
                            "Bitbucket"
                    ));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            final MyAdapter adapter = new MyAdapter(getApplicationContext(), R.layout.row_list, arrayList);
            lv.setAdapter(adapter);
            sortListByName(adapter);

            checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked)
                        sortListByRep(adapter);
                    else {
                        sortListByName(adapter);
                    }
                }
            });

        }
    }

    private static String readURL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public void sortListByRep(ArrayAdapter adapter){
            Collections.sort(arrayList, new Comparator<RepositoryData>() {
                @Override
                public int compare(RepositoryData r1, RepositoryData r2) {
                    return r1.getRep().compareTo(r2.getRep());
                }
            });

            adapter.notifyDataSetChanged();
    }

    public void sortListByName(ArrayAdapter adapter){
        Collections.sort(arrayList, new Comparator<RepositoryData>() {
            @Override
            public int compare(RepositoryData r1, RepositoryData r2) {
                return r2.getName().compareTo(r1.getName());
            }
        });

        adapter.notifyDataSetChanged();
    }

    public void createNoInternetDialog()
    {
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Informacja")
                .setMessage("Nie mogę wykryć połączenia z Internetem.")
                .setIcon(R.mipmap.ic_launcher)
                .setCancelable(false)
                .setPositiveButton("Ok, sprawdzę to!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

}

