package com.tn.biat.fragments.agent;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.tn.biat.AuthorisateurActivity;
import com.tn.biat.R;
import com.tn.biat.utils.DataApplication;
import com.tn.biat.utils.Outils;
import com.tn.biat.utils.SingletonVolley;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class RejetReglementFragment extends Fragment {

    @Bind(R.id.text)
    TextView _text;
    @Bind(R.id.motif)
    EditText _motif;
    @Bind(R.id.cancel)
    Button _cancel;
    @Bind(R.id.valider)
    Button _valider;

    private static final String ARG_PARAM1 = "param1";

    private String mReglementJsonString;

    private OnFragmentInteractionListener mListener;

    public RejetReglementFragment() {

    }

    public static RejetReglementFragment newInstance(String param1) {
        RejetReglementFragment fragment = new RejetReglementFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mReglementJsonString = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_rejet_reglement, container, false);
        ButterKnife.bind(this,view);
        try {
            final JSONObject reg=new JSONObject(mReglementJsonString);
            String msg="Ëtes-vous sure de vouloire rejeter ce réglement de facture au nom du donneur d'ordre "+
                    reg.get("nom_donneur_d'ordre").toString()+" et sous le numéro de registre de commerce "+
                    reg.get("num_registre_commerce").toString()+" ?\n Veuillez laisser un motif de rejet si oui ";
            _text.setText(msg);
            _cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mListener.onBackPressed();
                }
            });
            _valider.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        submitRejet(reg);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
    private void submitRejet(JSONObject reg)throws JSONException{
        Outils.startLoading(getContext());
        JSONObject data=new JSONObject();
        data.put("reg",reg.get("id_reglement").toString());
        data.put("id", DataApplication.currentUSER.get("id_agent"));
        data.put("action","rejet");
        data.put("motif_rejet",_motif.getText().toString());
        String url = Uri.parse(DataApplication.URL_BASE + "/confirmation.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Outils.stopLoading();
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
