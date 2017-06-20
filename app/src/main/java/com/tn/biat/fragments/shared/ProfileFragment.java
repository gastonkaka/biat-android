package com.tn.biat.fragments.shared;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tn.biat.R;
import com.tn.biat.utils.DataApplication;
import com.tn.biat.utils.Outils;
import com.tn.biat.utils.SingletonVolley;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ProfileFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";


    private String mProfileJsonString;

    @Bind(R.id.nom)
    EditText _nom;
    @Bind(R.id.prenom)
    EditText _prenom;
    @Bind(R.id.login)
    EditText _login;
    @Bind(R.id.mdp)
    EditText _mdp;
    @Bind(R.id.submit)
    Button _submit;

    boolean isCurrentProfile;
    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
        mProfileJsonString=DataApplication.currentUSER.toString();
        isCurrentProfile=true;
    }

    public static ProfileFragment newInstance(String param1) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mProfileJsonString = getArguments().getString(ARG_PARAM1);
            isCurrentProfile=false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this,view);
        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    submitProfile();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        try {
            renderProfile();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void renderProfile()throws JSONException{
        JSONObject profile=new JSONObject(mProfileJsonString);
        _nom.setText(profile.get("nom").toString());
        _prenom.setText(profile.get("prenom").toString());
        _login.setText(profile.get("login").toString());
        _mdp.setText(profile.get("mdp").toString());
    }
    private JSONObject buildProfileData()throws JSONException{
        JSONObject profile=new JSONObject(mProfileJsonString);
        profile.put("nom",_nom.getText().toString());
        profile.put("prenom",_prenom.getText().toString());
        profile.put("login",_login.getText().toString());
        profile.put("mdp",_mdp.getText().toString());

        if(profile.has("id_admin")){
            profile.put("table","`administrateur`");
            profile.put("nom_id","`id_admin`");
            profile.put("id",profile.get("id_admin").toString());
        }else if(profile.has("id_val")){
            profile.put("table","`validateur entreprise`");
            profile.put("nom_id","`id_val`");
            profile.put("id",profile.get("id_val").toString());
        }else if(profile.has("id_auth")){
            profile.put("table","`authorisateur entreprise`");
            profile.put("nom_id","`id_auth`");
            profile.put("id",profile.get("id_auth").toString());
        }else if(profile.has("id_agent")){
            profile.put("table","`agent`");
            profile.put("nom_id","`id_agent`");
            profile.put("id",profile.get("id_agent").toString());
        }


        return profile;
    }
    private void submitProfile()throws JSONException{
        Outils.startLoading(getContext());
        JSONObject data=buildProfileData();
        String url = Uri.parse(DataApplication.URL_BASE + "/profile.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Outils.stopLoading();
                Log.d("departement",response.toString());
                 Outils.switchLoadingToSuccess("Compte modifié","le profile a été modifier avec sucée ");
                if (isCurrentProfile){
                    mListener.onProfileUpdated();
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

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {

        void onBackPressed();
        void onProfileUpdated();
    }
}
