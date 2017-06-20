package com.tn.biat;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.tn.biat.fragments.shared.ProfileFragment;
import com.tn.biat.fragments.val.AddReglementFragment;
import com.tn.biat.fragments.val.ListReglementFragment;
import com.tn.biat.utils.DataApplication;
import com.tn.biat.utils.Outils;

import org.json.JSONException;
import org.json.JSONObject;

public class ValidateurActivity extends AppCompatActivity implements
        ListReglementFragment.OnListFragmentInteractionListener,
        AddReglementFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validateur);
        Log.d("currentUser", DataApplication.currentUSER.toString());
        try {
            String val=DataApplication.currentUSER.getString("nom")+" "+DataApplication.currentUSER.getString("prenom");
            setTitle(val);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_validateur,new ListReglementFragment()).commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_validateur,new ProfileFragment()).commit();
            }
        });
    }
    @Override
    public void onProfileUpdated() {
        Snackbar.make(findViewById(R.id.fab), "Votre profile a été modifier ,Veuillez reconnecter avec vos nouveaux crédentials", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Outils.deleteSavedUser();
        finish();
    }

    @Override
    public void onValidateuListReglementInteraction(JSONObject item) {
        Log.d("reglement",item.toString());

    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_validateur,new ListReglementFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_val, menu);//Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_validateur,new AddReglementFragment()).commit();
                return true;
            case R.id.logout:
                Outils.deleteSavedUser();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
