package com.tn.biat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.tn.biat.R;
import com.tn.biat.fragments.val.ListReglementFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class ReglementAdapter extends RecyclerView.Adapter<ReglementAdapter.ViewHolder> {

    private final JSONArray mValues;
    private final ListReglementFragment.OnListFragmentInteractionListener mListener;
    private final com.tn.biat.fragments.auth.ListReglementFragment.OnFragmentInteractionListener mListener2;
    private final com.tn.biat.fragments.agent.ListReglementFragment.OnFragmentInteractionListener mListener3;

    public ReglementAdapter(JSONArray items, ListReglementFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener2=null;
        mListener3=null;
        mListener = listener;
    }
    public ReglementAdapter(JSONArray items, com.tn.biat.fragments.auth.ListReglementFragment.OnFragmentInteractionListener listener) {
        mValues = items;
        mListener2=listener;
        mListener = null;
        mListener3=null;
    }
    public ReglementAdapter(JSONArray items, com.tn.biat.fragments.agent.ListReglementFragment.OnFragmentInteractionListener listener) {
        mValues = items;
        mListener2=null;
        mListener = null;
        mListener3=listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            holder.mItem = mValues.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String line0="Statut : "+mValues.getJSONObject(position).get("statut").toString();
            holder.mLine0View.setText(line0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String line1="Date validation : "+mValues.getJSONObject(position).get("date_validation").toString();

            if(!holder.mItem.isNull("date_authorisation")){
                line1+="\n Date authorisation : "+mValues.getJSONObject(position).get("date_authorisation").toString();
            }
            if(!holder.mItem.isNull("date_confirmation_agence")){
                line1+="\n Date confirmation : "+mValues.getJSONObject(position).get("date_confirmation_agence").toString();
            }
            holder.mIdView.setText(line1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String line2="Montant : "+mValues.getJSONObject(position).get("montant chiffre").toString();
            line2+="\n"+mValues.getJSONObject(position).get("montant lettre").toString();
            if(!holder.mItem.isNull("nom_donneur_d'ordre"))
            {line2+="\n Nom donneur d'ordre : \n"+mValues.getJSONObject(position).get("nom_donneur_d'ordre").toString();}
            if(!holder.mItem.isNull("num_registre_commerce"))
            {line2+="\n Num registre commerce : \n"+mValues.getJSONObject(position).get("num_registre_commerce").toString();}
            holder.mContentView.setText(line2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onValidateuListReglementInteraction(holder.mItem);
                }else if(null != mListener2){
                    mListener2.onAuthorisateurListReglementInteraction(holder.mItem);
                }else if(null != mListener3){
                    mListener3.onAgentListReglementInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView mLine0View;
        public JSONObject mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.line1);
            mContentView = (TextView) view.findViewById(R.id.line2);

            mLine0View = (TextView) view.findViewById(R.id.line0);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
