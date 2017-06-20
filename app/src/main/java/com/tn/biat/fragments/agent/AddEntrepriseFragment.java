package com.tn.biat.fragments.agent;

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


public class AddEntrepriseFragment extends Fragment {




    @Bind(R.id.nom_entreprise)
    EditText _nom_entreprise;
    @Bind(R.id.adresse)
    EditText _adresse;

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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddEntrepriseFragment() {

    }


    public static AddEntrepriseFragment newInstance(String param1, String param2) {
        AddEntrepriseFragment fragment = new AddEntrepriseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_entreprise, container, false);
        ButterKnife.bind(this,view);
        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    try {
                        submitEntreprise();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Outils.alertWarning("Champs manquants","Des infomations qui manque pour la creation du nouveau compte");
                }

            }
        });
        return view;
    }
    private boolean validate(){
        if (_nom_entreprise.getText().toString().equals("")) {
            return false;
        }
        if (_adresse.getText().toString().equals("")) {
            return false;
        }
        if (_nom.getText().toString().equals("") ) {
            return false;
        }
        if (_prenom.getText().toString().equals("") ) {
            return false;

        }
        if (_login.getText().toString().equals("")){
            return false;
        }
        if (_mdp.getText().toString().equals("") ) {
            return false;
        }

        return true;
    }


    private JSONObject buildEntrepriseData()throws JSONException{
        JSONObject data=new JSONObject();
        data.put("nom_entreprise",_nom_entreprise.getText().toString());
        data.put("adresse",_adresse.getText().toString());
        data.put("nom",_nom.getText().toString());
        data.put("prenom",_prenom.getText().toString());
        data.put("login",_login.getText().toString());
        data.put("mdp",_mdp.getText().toString());
        return data;
    }
    private void submitEntreprise() throws JSONException {
        Outils.startLoading(getContext());
        JSONObject data=buildEntrepriseData();
        String url = Uri.parse(DataApplication.URL_BASE + "/entreprise.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Outils.stopLoading();
                Log.d("reglement",response.toString());
                Outils.switchLoadingToSuccess("Compte ajouté","le compte de la nouvelle entreprise a été créer avec succé");
                mListener.onBackPressed();
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
    }
}
