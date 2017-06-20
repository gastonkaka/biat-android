package com.tn.biat.fragments.admin;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.tn.biat.R;
import com.tn.biat.adapters.DepartementAdapter;
import com.tn.biat.adapters.UserAdapter;
import com.tn.biat.utils.DataApplication;
import com.tn.biat.utils.Outils;
import com.tn.biat.utils.SingletonVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ListAuthorisateuFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;
    @Bind(R.id.list)
    RecyclerView _lista;
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnFragmentInteractionListener mListener;

    public ListAuthorisateuFragment() {
        // Required empty public constructor
    }
    public static ListAuthorisateuFragment newInstance(String param1, String param2) {
        ListAuthorisateuFragment fragment = new ListAuthorisateuFragment();
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

        View view = inflater.inflate(R.layout.fragment_list_authorisateu, container, false);
        ButterKnife.bind(this,view);
        if (mColumnCount <= 1) {
            _lista.setLayoutManager(new LinearLayoutManager(view.getContext()));
        } else {
            _lista.setLayoutManager(new GridLayoutManager(view.getContext(), mColumnCount));
        }
        try {
            getAuthorisateurs();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private void getAuthorisateurs() throws JSONException {
        Outils.startLoading(getContext());
        JSONObject data=new JSONObject();
        data.put("entreprise", DataApplication.currentUSER.get("entreprise"));
        String url = Uri.parse(DataApplication.URL_BASE + "/authorisateurs.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Outils.stopLoading();
                _lista.setAdapter(new UserAdapter(response, mListener));
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
        void onListAuthrisateurInteraction(JSONObject auth);
    }
}
