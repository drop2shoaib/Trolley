package com.trolley;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vamsi on 3/29/2015.
 */
public class Info extends Activity {

    private ListView infoList;
    private ProgressBar infoProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.info);
        infoList = (ListView) findViewById(R.id.info_list);
        infoProgressBar = (ProgressBar) findViewById(R.id.info_progress_bar);

        this.retrieveNamesFromParse();
    }

    private void retrieveNamesFromParse(){
        this.loadingInformation();
        ParseQuery<ParseObject> getAllNamesQuery = ParseQuery.getQuery("items");

        getAllNamesQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> parseObjects, ParseException exception) {
                List<String> itemNames = new ArrayList<String>();
                if (exception == null) {
                    for(int i = 0; i < parseObjects.size(); i++){
                        ParseObject p = parseObjects.get(i);
                        itemNames.add(i, p.getString("name"));
                    }
                    ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                            Info.this,
                            R.layout.list_white_text, R.id.list_content,
                            itemNames );
                    infoList.setAdapter(arrayAdapter);
                    Info.this.showInformation();
                } else {
                    Toast.makeText(Info.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void loadingInformation(){
        this.infoList.setVisibility(View.GONE);
        this.infoProgressBar.setVisibility(View.VISIBLE);
    }

    private void showInformation(){
        this.infoList.setVisibility(View.VISIBLE);
        this.infoProgressBar.setVisibility(View.GONE);
    }
}
