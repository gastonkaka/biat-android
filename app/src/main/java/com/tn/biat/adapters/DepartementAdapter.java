package com.tn.biat.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tn.biat.R;
import com.tn.biat.fragments.admin.ListDepartementFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mednaceur on 09/05/2017.
 */

public class DepartementAdapter extends RecyclerView.Adapter<DepartementAdapter.ViewHolder> {

    private final JSONArray mValues;

    private final ListDepartementFragment.OnFragmentInteractionListener mListener;

    public DepartementAdapter(JSONArray mValues, ListDepartementFragment.OnFragmentInteractionListener mListener) {
        this.mValues = mValues;
        this.mListener = mListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_card, parent, false);
        return new DepartementAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        try {
            holder.mItem = mValues.getJSONObject(position);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String line0=mValues.getJSONObject(position).getJSONObject("entreprise").get("nom_entreprise").toString();
            holder.mLine0View.setText(line0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
            String line1="Département : ";
            holder.mIdView.setText(line1);
        try {
            String line2=mValues.getJSONObject(position).get("nom_departement").toString();
             holder.mContentView.setText(line2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListDepartementInteraction(holder.mItem);
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
