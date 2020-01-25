package org.stormroboticsnj.ui.whoosh;

import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.stormroboticsnj.R;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.ui.whoosh.WhooshListFragment.OnListFragmentInteractionListener;

import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can whoosh a  and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyWhooshRecyclerViewAdapter extends RecyclerView.Adapter<MyWhooshRecyclerViewAdapter.ViewHolder> {

    private final List<Whoosh> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyWhooshRecyclerViewAdapter(List<Whoosh> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_whoosh, parent, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Whoosh w = mValues.get(position);
        holder.mItem = w;
        //set and format text for team and match number
        holder.mTeamView.setText(String.format(Locale.US, "%d Match %d", w.getTeam(), w.getMatch()));
        holder.mTeamView.setTextColor(w.isAlliance() ? Color.rgb(255, 0, 0) : Color.rgb(0,0,255));
        //set text for auto information
        holder.mAView.setText(String.format(Locale.US, "Auto (Lower, Inner, Outer): %d %d %d Auto Pickup: %d",
                w.getAPowerCell1(), w.getAPowerCell2(), w.getAPowerCell3(), w.getAPowerCellPickup()));
        //set text for teleop information
        holder.mTView.setText(String.format(Locale.US, "Teleop (Lower, Inner, Outer): %d %d %d",
                w.getTPowerCell1(), w.getTPowerCell2(), w.getTPowerCell3()));
        //set text for control panel information
        String controlPanelInfo = "None";
        if (w.isPositionControl()) {
            if (w.isRotationControl()) controlPanelInfo = "RC and PC";
            else controlPanelInfo = "PC";
        } else if (w.isRotationControl()) controlPanelInfo = "RC";

        holder.mCPView.setText(String.format(Locale.US, "Control Panel: %s", controlPanelInfo));

        holder.mEView.setText(String.format(Locale.US, "Endgame (Lower, Inner, Outer): %d %d %d",
                w.getEPowerCell1(), w.getEPowerCell2(), w.getEPowerCell3()));

        switch (mValues.get(position).getHang()) {
            case 0:
                holder.mHView.setText("P");
                break;
            case 1:
                holder.mHView.setText("H");
                break;
            case 2:
                holder.mHView.setText("LH");
                break;
            default:
                holder.mHView.setText("");
        }

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    //mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mTeamView, mAView, mTView, mCPView, mEView, mHView;
        public Whoosh mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTeamView = view.findViewById(R.id.whoosh_team_number);
            mAView = view.findViewById(R.id.whoosh_apc);
            mTView = view.findViewById(R.id.whoosh_tpc);
            mCPView = view.findViewById(R.id.whoosh_cp);
            mEView = view.findViewById(R.id.whoosh_epc);
            mHView = view.findViewById(R.id.whoosh_hang);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTeamView.getText() + "'";
        }
    }
}
