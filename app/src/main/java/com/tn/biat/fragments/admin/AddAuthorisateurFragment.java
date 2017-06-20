package com.tn.biat.fragments.admin;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.tn.biat.Biat;
import com.tn.biat.R;
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


public class AddAuthorisateurFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private String mParam1;
    private String mParam2;

    @Bind(R.id.spinner)
    Spinner _spinner;
    JSONArray _deps=new JSONArray();

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

    private OnFragmentInteractionListener mListener;

    public AddAuthorisateurFragment() {
        // Required empty public constructor
    }

    public static AddAuthorisateurFragment newInstance(String param1, String param2) {
        AddAuthorisateurFragment fragment = new AddAuthorisateurFragment();
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
        View view= inflater.inflate(R.layout.fragment_add_authorisateur, container, false);
        ButterKnife.bind(this,view);
        _submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validate()) {
                    try {
                        submitAuthorisateur();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Outils.alertWarning("Champs manquants","Des infomations qui manque pour la creation du nouveau compte");
                }

            }
        });
        try {
            getDepatements();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return view;
    }

    private boolean validate(){
        if (_nom.getText().toString().equals("")) {
            return false;
        }
        if (_prenom.getText().toString().equals("")) {
            return false;
        }
        if (_login.getText().toString().equals("")) {
            return false;
        }
        if (_mdp.getText().toString().equals("")) {
            return false;
        }

        return true;
    }
    private JSONObject buildAuthorisateurData()throws JSONException{
        JSONObject data=new JSONObject();
        data.put("nom",_nom.getText().toString());
        data.put("prenom",_prenom.getText().toString());
        data.put("login",_login.getText().toString());
        data.put("mdp",_mdp.getText().toString());
        data.put("departement",_deps.getJSONObject(_spinner.getSelectedItemPosition()).get("id_departement").toString());
        return data;
    }
    private void submitAuthorisateur() throws JSONException {
        Outils.startLoading(getContext());
        JSONObject data=buildAuthorisateurData();
        String url = Uri.parse(DataApplication.URL_BASE + "/authorisateur.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Outils.stopLoading();
                Log.d("departement",response.toString());
                // Outils.switchLoadingToSuccess("Compte ajouté","le compte de la nouvelle entreprise a été créer avec succé");
                mListener.onListAuthPressed();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Outils.switchLoadingToError("Erreur connexion !!",error.getMessage());
            }
        });
        SingletonVolley.getInstance().addToRequestQueue(request);
    }

    private void getDepatements() throws JSONException {
        Outils.startLoading(getContext());
        JSONObject data=new JSONObject();
        data.put("entreprise", DataApplication.currentUSER.get("entreprise"));
        String url = Uri.parse(DataApplication.URL_BASE + "/departements.php")
                .buildUpon()
                .appendQueryParameter("data", data.toString())
                .build().toString();
        JsonArrayRequest request=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Outils.stopLoading();
                _deps=response;
                _spinner.setAdapter(new AddAuthorisateurFragment.MySpinnerAdapter(response));
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
        void onListAuthPressed();
    }

    public class MySpinnerAdapter implements SpinnerAdapter {
        private LayoutInflater inflater;
        private List<String> deps;
        public MySpinnerAdapter(JSONArray items) {
            this.inflater = (LayoutInflater) Biat.getAppContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            deps=new ArrayList<String>();
            for (int i = 0; i < items.length(); i++) {
                try {
                    deps.add(items.getJSONObject(i).get("nom_departement").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
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
            return deps.size();
        }

        @Override
        public Object getItem(int i) {
            return deps.get(i);
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
