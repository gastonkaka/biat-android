package com.tn.biat.fragments.auth;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.tn.biat.R;
import com.tn.biat.adapters.ReglementAdapter;
import com.tn.biat.utils.DataApplication;
import com.tn.biat.utils.Outils;
import com.tn.biat.utils.SingletonVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ListReglementFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(R.id.list)
    RecyclerView _lista;

    private String mParam1;
    private String mParam2;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnFragmentInteractionListener mListener;

    public ListReglementFragment() {

    }

    public static ListReglementFragment newInstance(String param1, String param2) {
        ListReglementFragment fragment = new ListReglementFragment();
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

      View view=   inflater.inflate(R.layout.fragment_reglement_list, container, false);
        ButterKnife.bind(this,view);
        if (mColumnCount <= 1) {
            _lista.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            _lista.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }
        try {
            getReglements();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
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
    private void getReglements() throws JSONException {
        Outils.startLoading(getContext());
        JSONObject data=new JSONObject();
        data.put("type","auth");
        data.put("id", DataApplication.currentUSER.get("id_auth"));
        data.put("dep",DataApplication.currentUSER.get("departement"));
        String url = Uri.parse(DataApplication.URL_BASE + "/reglements.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        StringRequest request=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Outils.stopLoading();
                Log.d("reglement",response);
                try {
                    JSONArray data=new JSONArray(response);
                    _lista.setAdapter(new ReglementAdapter(data, mListener));
                } catch (JSONException e) {
                    e.printStackTrace();
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onAuthorisateurListReglementInteraction(JSONObject reg);
    }
}
