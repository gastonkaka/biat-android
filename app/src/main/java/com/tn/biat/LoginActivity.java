package com.tn.biat;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tn.biat.utils.DataApplication;
import com.tn.biat.utils.Outils;
import com.tn.biat.utils.SingletonVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.content)
    LinearLayout _content;
    @Bind(R.id.login)
    Button _login;
    @Bind(R.id.username)
    EditText _username;
    @Bind(R.id.password)
    EditText _password;
    @Bind(R.id.spinner)
    Spinner _spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        _spinner.setAdapter(new MySpinnerAdapter());
        _login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    doLogin();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
        if(Outils.isUserExist()){
            try {
                skipLogin();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private void skipLogin() throws JSONException {
             JSONObject user=new JSONObject(Outils.getSavedUser());
             Intent intent=new Intent(LoginActivity.this,LoginActivity.class);
             if (user.has("id_val")){
                 intent=new Intent(LoginActivity.this,ValidateurActivity.class);
             }else if(user.has("id_auth")){
                 intent=new Intent(LoginActivity.this,AuthorisateurActivity.class);
             }else if(user.has("id_agent")){
                 intent=new Intent(LoginActivity.this,AgentActivity.class);
             }else if(user.has("id_admin")){
                 intent=new Intent(LoginActivity.this,AdminActivity.class);
             }
            DataApplication.currentUSER=user;
            startActivity(intent);

    }
    private String getRole(){
        switch (_spinner.getSelectedItemPosition()){
            case 0:
                return "`administrateur`";
            case 1:
                return "`agent`";
            case 2:
                return "`validateur entreprise`";
            case 3:
                return "`authorisateur entreprise`";
            case 4:
                return "`agent`";
            default:
                return "`administrateur`";
        }
    }
    private void navigateToRole(){
        Intent intent=new Intent();
        switch (_spinner.getSelectedItemPosition()){
            case 0:
                intent=new Intent(LoginActivity.this,AdminActivity.class);
               break;
            case 1:
                intent=new Intent(LoginActivity.this,AgentActivity.class);
                break;
            case 2:
                intent=new Intent(LoginActivity.this,ValidateurActivity.class);
                break;
            case 3:
                intent=new Intent(LoginActivity.this,AuthorisateurActivity.class);
                break;
            case 4:
                intent=new Intent(LoginActivity.this,AgentActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    private void doLogin() throws JSONException {
        Outils.startLoading(LoginActivity.this);
        JSONObject data =new JSONObject();
        data.put("table",getRole());
        data.put("login",_username.getText().toString());
        data.put("pwd",_password.getText().toString());
        String url = Uri.parse(DataApplication.URL_BASE + "/login.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        JsonArrayRequest request =new JsonArrayRequest(Request.Method.GET, url,null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.d("login",response.toString());
                if (response.length() == 0) {
                    Outils.switchLoadingToError("Erreur !!","Invalide Login \\ mot de passe");
                }else{
                    Outils.stopLoading();
                    try {
                        DataApplication.currentUSER=response.getJSONObject(0);
                        Outils.saveUser(DataApplication.currentUSER);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    _username.setText("");_password.setText("");
                    navigateToRole();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Outils.switchLoadingToError("Erreur connexion !!",error.getMessage());
            }
        });
        SingletonVolley.getInstance().addToRequestQueue(request);

    }

    public class MySpinnerAdapter implements SpinnerAdapter {
        private LayoutInflater inflater;
        private List<String> roles;
        public MySpinnerAdapter() {
            this.inflater = (LayoutInflater)Biat.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        roles=new ArrayList<String>();roles.add("Admin");roles.add("Agent");roles.add("Validateur");roles.add("Authorisateur");
            roles.add("SuperAgent");
        }

        @Override
        public View getDropDownView(int i, View convertView, ViewGroup parent) {
            View rowView = convertView;
                rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent,false);
                //Configuration du ViewHolder
            TextView text=(TextView) rowView.findViewById(android.R.id.text1);
            text.setTextColor(Color.BLACK);
            text.setText(getItem(i).toString());
            return rowView;
        }

        @Override
        public void registerDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

        }

        @Override
        public int getCount() {
            return roles.size();
        }

        @Override
        public Object getItem(int i) {
            return roles.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getView(int i, View convertView, ViewGroup parent) {
            View rowView = convertView;
            rowView = inflater.inflate(android.R.layout.simple_list_item_1, parent,false);
            //Configuration du ViewHolder
            TextView text=(TextView) rowView.findViewById(android.R.id.text1);
            text.setText(getItem(i).toString());
            text.setTextColor(Color.BLACK);
            return rowView;
        }

        @Override
        public int getItemViewType(int i) {
            return 0;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }
}
