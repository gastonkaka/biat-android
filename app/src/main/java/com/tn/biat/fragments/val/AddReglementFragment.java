package com.tn.biat.fragments.val;

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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.tn.biat.LoginActivity;
import com.tn.biat.R;
import com.tn.biat.utils.DataApplication;
import com.tn.biat.utils.Outils;
import com.tn.biat.utils.SingletonVolley;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class AddReglementFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddReglementFragment() {
        // Required empty public constructor
    }

    public static AddReglementFragment newInstance(String param1, String param2) {
        AddReglementFragment fragment = new AddReglementFragment();
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

    @Bind(R.id.montant_lettre)
    EditText _montant_lettre;
    @Bind(R.id.montant_chiffre)
    EditText _montant_chiffre;
    @Bind(R.id.devise)
    EditText _devise;
    @Bind(R.id.motif_payement)
    EditText _motif_payement;
    @Bind(R.id.frais_commission_BIAT)
    EditText _frais_commission_BIAT;
    @Bind(R.id.frais_correspondant)
    EditText _frais_correspondant;
    @Bind(R.id.type_payement)
    EditText _type_payement;
    @Bind(R.id.observation)
    EditText _observation;
    @Bind(R.id.titre_commerce_extrieurs)
    EditText _titre_commerce_extrieurs;
    @Bind(R.id.nom_donneur_dordre)
    EditText _nom_donneur_dordre;
    @Bind(R.id.adresse_donneur_dordre)
    EditText _adresse_donneur_dordre;
    @Bind(R.id.num_compte)
    EditText _num_compte;
    @Bind(R.id.code_devise)
    EditText _code_devise;
    @Bind(R.id.num_code_douane)
    EditText _num_code_douane;
    @Bind(R.id.num_registre_commerce)
    EditText _num_registre_commerce;
    @Bind(R.id.fournisseur)
    EditText _fournisseur;
    @Bind(R.id.submit)
    Button _submit;






    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_add_reglement, container, false);
        ButterKnife.bind(this,view);
        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    submitReglement();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }
    private JSONObject buildReglementData()throws JSONException{
        JSONObject data=new JSONObject();
        data.put("montant_lettre",_montant_lettre.getText().toString());
        data.put("montant_chiffre",_montant_chiffre.getText().toString());
        data.put("devise",_devise.getText().toString());
        data.put("motif_payement",_motif_payement.getText().toString());
        data.put("frais_commission_BIAT",_frais_commission_BIAT.getText().toString());
        data.put("frais_correspondant",_frais_correspondant.getText().toString());
        data.put("type_payement",_type_payement.getText().toString());
        data.put("observation",_observation.getText().toString());
        data.put("titre_commerce_extrieurs",_titre_commerce_extrieurs.getText().toString());
        data.put("nom_donneur_d'ordre",_nom_donneur_dordre.getText().toString());
        data.put("adresse_donneur_d'ordre",_adresse_donneur_dordre.getText().toString());
        data.put("num_compte",_num_compte.getText().toString());
        data.put("code_devise",_code_devise.getText().toString());
        data.put("num_code_douane",_num_code_douane.getText().toString());
        data.put("num_registre_commerce",_num_registre_commerce.getText().toString());
        data.put("fournisseur",_fournisseur.getText().toString());
        data.put("validation_entreprise", DataApplication.currentUSER.get("id_val").toString());
        return data;
    }



    private void submitReglement() throws JSONException{
        Outils.startLoading(getContext());
        JSONObject data=buildReglementData();
        String url = Uri.parse(DataApplication.URL_BASE + "/reglement.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Outils.stopLoading();
                Log.d("reglement",response.toString());
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
