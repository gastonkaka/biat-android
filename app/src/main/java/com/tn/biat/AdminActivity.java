package com.tn.biat;

import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tn.biat.fragments.admin.AddAuthorisateurFragment;
import com.tn.biat.fragments.admin.AddDepartementFragment;
import com.tn.biat.fragments.admin.AddValidateurFragment;
import com.tn.biat.fragments.admin.ListAuthorisateuFragment;
import com.tn.biat.fragments.admin.ListDepartementFragment;
import com.tn.biat.fragments.admin.ListValidateurFragment;
import com.tn.biat.fragments.admin.MenuFragment;
import com.tn.biat.fragments.agent.AddEntrepriseFragment;
import com.tn.biat.fragments.shared.ProfileFragment;
import com.tn.biat.fragments.val.ListReglementFragment;
import com.tn.biat.utils.DataApplication;
import com.tn.biat.utils.Outils;
import com.tn.biat.utils.SingletonVolley;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class AdminActivity extends AppCompatActivity implements
        MenuFragment.OnFragmentInteractionListener,
        ListDepartementFragment.OnFragmentInteractionListener,
        ListAuthorisateuFragment.OnFragmentInteractionListener,
        ListValidateurFragment.OnFragmentInteractionListener,
        AddDepartementFragment.OnFragmentInteractionListener,
        AddAuthorisateurFragment.OnFragmentInteractionListener,
        AddValidateurFragment.OnFragmentInteractionListener,
        ProfileFragment.OnFragmentInteractionListener
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        try {
            String val= DataApplication.currentUSER.getString("nom")+" "+DataApplication.currentUSER.getString("prenom");
            setTitle(val);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,new MenuFragment()).commit();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,new ProfileFragment()).commit();
            }
        });
    }

    @Override
    public void onBackPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,new MenuFragment()).commit();
    }

    @Override
    public void onProfileUpdated() {
        Snackbar.make(findViewById(R.id.fab), "Votre profile a été modifier ,Veuillez reconnecter avec vos nouveaux crédentials", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        Outils.deleteSavedUser();
        finish();
    }

    @Override
    public void onListValidateurInteraction(final JSONObject val) {
        final SweetAlertDialog alertDialog=new SweetAlertDialog(AdminActivity.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        alertDialog.setCustomImage(R.drawable.bg_login);
        alertDialog.setTitleText("Validateur");
        try {
            alertDialog.setContentText(val.get("nom").toString()+" "+ val.get("prenom").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        alertDialog.setConfirmText("Modifier profile");
        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                alertDialog.dismiss();
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,ProfileFragment.newInstance(val.toString())).commit();
            }
        });
        alertDialog.setCancelText("Supprimer");
        alertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                alertDialog.dismiss();
                try {
                    deleteValidateur(val);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }
    private void deleteValidateur(JSONObject val)throws JSONException{
        Outils.startLoading(AdminActivity.this);
        JSONObject data= new  JSONObject();
        data.put("table","`validateur entreprise`");
        data.put("nom_id","`id_val`");
        data.put("id",val.get("id_val").toString());
        String url = Uri.parse(DataApplication.URL_BASE + "/deleteUser.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Outils.stopLoading();
                Log.d("reglement",response.toString());
                Outils.switchLoadingToSuccess("Compte Supprimé","le compte  a été supprimer avec succé");
                onListValPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Outils.switchLoadingToError("Erreur connexion !!",error.getMessage());
            }
        });
        SingletonVolley.getInstance().addToRequestQueue(request);

    }

    @Override
    public void onListAuthrisateurInteraction(final JSONObject auth) {
        final SweetAlertDialog alertDialog=new SweetAlertDialog(AdminActivity.this,SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        alertDialog.setCustomImage(R.drawable.bg_login);
        alertDialog.setTitleText("Authorisateur");
        try {
            alertDialog.setContentText(auth.get("nom").toString()+" "+ auth.get("prenom").toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        alertDialog.setConfirmText("Modifier profile");
        alertDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                alertDialog.dismiss();
                getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,ProfileFragment.newInstance(auth.toString())).commit();
            }
        });
        alertDialog.setCancelText("Supprimer");
        alertDialog.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                alertDialog.dismiss();
                try {
                    deleteAuthorisateur(auth);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        alertDialog.setCancelable(true);
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();

    }
    private void deleteAuthorisateur(JSONObject auth)throws JSONException{
        Outils.startLoading(AdminActivity.this);
        JSONObject data= new  JSONObject();
        data.put("table","`authorisateur entreprise`");
        data.put("nom_id","`id_auth`");
        data.put("id",auth.get("id_auth").toString());
        String url = Uri.parse(DataApplication.URL_BASE + "/deleteUser.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Outils.stopLoading();
                Log.d("reglement",response.toString());
                Outils.switchLoadingToSuccess("Compte Supprimé","le compte  a été supprimer avec succé");
                onListAuthPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Outils.switchLoadingToError("Erreur connexion !!",error.getMessage());
            }
        });
        SingletonVolley.getInstance().addToRequestQueue(request);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_admin, menu);//Menu Resource, Menu
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                Outils.deleteSavedUser();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAddValPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,new AddValidateurFragment()).commit();

    }

    @Override
    public void onListValPressed() {

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,new ListValidateurFragment()).commit();
    }

    @Override
    public void onAddAuthPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,new AddAuthorisateurFragment()).commit();

    }

    @Override
    public void onListAuthPressed() {

        getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,new ListAuthorisateuFragment()).commit();
    }

    @Override
    public void onAddDepPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,new AddDepartementFragment()).commit();

    }

    @Override
    public void onListDepPressed() {
        getSupportFragmentManager().beginTransaction().replace(R.id.activity_admin,new ListDepartementFragment()).commit();
    }

    @Override
    public void onListDepartementInteraction(JSONObject dep) {

    }
}
