package com.tn.biat;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tn.biat.fragments.agent.AddEntrepriseFragment;
import com.tn.biat.fragments.agent.ListReglementFragment;
import com.tn.biat.fragments.agent.RejetReglementFragment;
import com.tn.biat.fragments.shared.ProfileFragment;
import com.tn.biat.utils.DataApplication;
import com.tn.biat.utils.Outils;
import com.tn.biat.utils.SingletonVolley;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AgentActivity extends AppCompatActivity implements
        ListReglementFragment.OnFragmentInteractionListener,
        AddEntrepriseFragment.OnFragmentInteractionListener,
        RejetReglementFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener
   {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent);
        try {
            String val=DataApplication.currentUSER.getString("nom")+" "+DataApplication.currentUSER.getString("prenom");
            setTitle(val);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_agent,new ListReglementFragment()).commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_agent,new ProfileFragment()).commit();
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
       public void onBackPressed() {
           getSupportFragmentManager().beginTransaction().replace(R.id.activity_agent,new ListReglementFragment()).commit();
       }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            if(DataApplication.currentUSER.get("is_super").toString().equals("1")){
                getMenuInflater().inflate(R.menu.menu_super_agent, menu);
            }else{
                getMenuInflater().inflate(R.menu.menu_agent, menu);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        // Inflate the menu; this adds items to the action bar if it is present.
        //Menu Resource, Menu
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_agent,new AddEntrepriseFragment()).commit();
               return true;
            case R.id.logout:
                Outils.deleteSavedUser();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

       @Override
       public void onAgentListReglementInteraction(final JSONObject item) {
           try {
               if(item.get("statut").toString().equals("authorise")){
                   final SweetAlertDialog alertDialog=new SweetAlertDialog(AgentActivity.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
                   alertDialog.setCustomImage(R.drawable.bg_login);
                   alertDialog.setTitleText("Confirmation");
                   try {
                       alertDialog.setContentText("Ëtes-vous sure de vouloire confirmer ce réglement de facture au nom du donneur d'ordre "+
                               item.get("nom_donneur_d'ordre").toString()+" et sous le numéro de registre de commerce "+
                               item.get("num_registre_commerce").toString()+" ?");
                   } catch (JSONException e) {
                       e.printStackTrace();
                   }
                   alertDialog.setConfirmText("Confirme");
                   alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                       @Override
                       public void onClick(SweetAlertDialog sweetAlertDialog) {
                           alertDialog.dismiss();
                           try {
                               confirmerReglement(item);
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }
                       }
                   });
                   alertDialog.setCancelText("rejet");
                   alertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                       @Override
                       public void onClick(SweetAlertDialog sweetAlertDialog) {
                           alertDialog.dismiss();
                           getSupportFragmentManager().beginTransaction().replace(R.id.activity_agent,RejetReglementFragment.newInstance(item.toString())).commit();
                       }
                   });
                   alertDialog.setCancelable(true);
                   alertDialog.setCanceledOnTouchOutside(true);
                   alertDialog.show();

               }
           } catch (JSONException e) {
               e.printStackTrace();
           }
       }
       private void confirmerReglement(JSONObject item)throws JSONException{
           Outils.startLoading(AgentActivity.this);
           JSONObject data=new JSONObject();
           data.put("reg",item.get("id_reglement").toString());
           data.put("id", DataApplication.currentUSER.get("id_agent"));
           data.put("action","confirm");
           String url = Uri.parse(DataApplication.URL_BASE + "/confirmation.php")
                   .buildUpon()
                   .appendQueryParameter("data", data.toString())
                   .build().toString();
           StringRequest request=new StringRequest(url, new Response.Listener<String>() {
               @Override
               public void onResponse(String response) {
                   Outils.stopLoading();
                   onBackPressed();
               }
           }, new Response.ErrorListener() {
               @Override
               public void onErrorResponse(VolleyError error) {
                   Outils.switchLoadingToError("Erreur connexion !!",error.getMessage());
               }
           });
           SingletonVolley.getInstance().addToRequestQueue(request);
       }
   }
