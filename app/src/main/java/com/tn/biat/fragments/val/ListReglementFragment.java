package com.tn.biat.fragments.val;

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
import com.tn.biat.adapters.ReglementAdapter;
import com.tn.biat.utils.DataApplication;
import com.tn.biat.utils.Outils;
import com.tn.biat.utils.SingletonVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class ListReglementFragment extends Fragment {


    @Bind(R.id.list)
    RecyclerView _lista;

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    public ListReglementFragment() {

    }


    public static ListReglementFragment newInstance(int columnCount) {
        ListReglementFragment fragment = new ListReglementFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reglement_list, container, false);
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
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void getReglements() throws JSONException {
        Outils.startLoading(getContext());
        JSONObject data=new JSONObject();
        data.put("type","val");
        data.put("id",DataApplication.currentUSER.get("id_val"));
        String url = Uri.parse(DataApplication.URL_BASE + "/reglements.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Outils.stopLoading();
                _lista.setAdapter(new ReglementAdapter(response, mListener));
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Outils.switchLoadingToError("Erreur connexion !!",error.getMessage());
            }
        });
        SingletonVolley.getInstance().addToRequestQueue(request);
    }



    public interface OnListFragmentInteractionListener {
        void onValidateuListReglementInteraction(JSONObject item);
    }
}
