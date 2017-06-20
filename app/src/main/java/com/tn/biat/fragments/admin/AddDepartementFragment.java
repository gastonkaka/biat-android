package com.tn.biat.fragments.admin;

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


public class AddDepartementFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(R.id.nom_departement)
    EditText _nom_departement;
    @Bind(R.id.submit)
    Button _submit;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddDepartementFragment() {

    }

    public static AddDepartementFragment newInstance(String param1, String param2) {
        AddDepartementFragment fragment = new AddDepartementFragment();
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
        View view= inflater.inflate(R.layout.fragment_add_departement, container, false);
        ButterKnife.bind(this,view);
        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    try {
                        submitDepartement();
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
        if (_nom_departement.getText().toString().equals("")) {
            return false;
        }

        return true;
    }

    private JSONObject buildDepatementData()throws JSONException{
        JSONObject data=new JSONObject();
        data.put("nom_departement",_nom_departement.getText().toString());
        data.put("entreprise",DataApplication.currentUSER.get("entreprise"));
        return data;
    }
    private void submitDepartement() throws JSONException {
        Outils.startLoading(getContext());
        JSONObject data=buildDepatementData();
        String url = Uri.parse(DataApplication.URL_BASE + "/departement.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Outils.stopLoading();
                Log.d("departement",response.toString());
               // Outils.switchLoadingToSuccess("Compte ajouté","le compte de la nouvelle entreprise a été créer avec succé");
                mListener.onListDepPressed();
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

        void onListDepPressed();
    }
}
