package org.stormroboticsnj.ui.display.whoosh;

import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.stormroboticsnj.R;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.ui.display.whoosh.WhooshListFragment.OnListFragmentInteractionListener;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

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
        //set and format text for team number
        holder.mTeamView.setText(String.format(Locale.US, "Team %d", w.getTeam()));
        holder.mTeamView.setTextColor(w.isAlliance() ? Color.rgb(255, 0, 0) : Color.rgb(0,0,255));

        //set text for match number
        holder.mMatchView.setText(String.format(Locale.US, "Match %d", w.getMatch()));

        //set text for power cell scoring
        holder.mA3View.setText(String.format(Locale.US, "%d", w.getAPowerCell3())); //inner auto
        holder.mT3View.setText(String.format(Locale.US, "%d", w.getTPowerCell3())); //inner tele
        holder.mE3View.setText(String.format(Locale.US, "%d", w.getEPowerCell3())); //inner end
        holder.mTot3View.setText(String.format(Locale.US, "%d", (w.getAPowerCell3() + w.getTPowerCell3() + w.getEPowerCell3()))); //inner total

        holder.mA2View.setText(String.format(Locale.US, "%d", w.getAPowerCell2())); //outer auto
        holder.mT2View.setText(String.format(Locale.US, "%d", w.getTPowerCell2())); //outer tele
        holder.mE2View.setText(String.format(Locale.US, "%d", w.getEPowerCell2())); //outer end
        holder.mTot2View.setText(String.format(Locale.US, "%d", (w.getAPowerCell2() + w.getTPowerCell2() + w.getEPowerCell2()))); //outer total


        holder.mA1View.setText(String.format(Locale.US, "%d", w.getAPowerCell1())); //bottom auto
        holder.mT1View.setText(String.format(Locale.US, "%d", w.getTPowerCell1())); //bottom tele
        holder.mE1View.setText(String.format(Locale.US, "%d", w.getEPowerCell1())); //bottom end
        holder.mTot1View.setText(String.format(Locale.US, "%d", (w.getAPowerCell1() + w.getTPowerCell1() + w.getEPowerCell1()))); //bottom total

        holder.mATotView.setText(String.format(Locale.US, "%d", (w.getAPowerCell1() + w.getAPowerCell2() + w.getAPowerCell3()))); //auto total
        holder.mTTotView.setText(String.format(Locale.US, "%d", (w.getTPowerCell1() + w.getTPowerCell2() + w.getTPowerCell3()))); //tele total
        holder.mETotView.setText(String.format(Locale.US, "%d", (w.getEPowerCell1() + w.getEPowerCell2() + w.getEPowerCell3()))); //end total
        holder.mTotView.setText(String.format(Locale.US, "%d",
                (w.getAPowerCell1() + w.getAPowerCell2() + w.getAPowerCell3() +
                 w.getTPowerCell1() + w.getTPowerCell2() + w.getTPowerCell3() +
                 w.getEPowerCell1() + w.getEPowerCell2() + w.getEPowerCell3())
        )); //end total

        //set text for auto pickup
        holder.mAPickView.setText(String.format(Locale.US, "%d", w.getAPowerCellPickup()));
        if (w.getAPowerCellPickup() > 0) holder.mAPickView.setTypeface(null, Typeface.BOLD);

        //set text formatting for control panel info
        if (w.isRotationControl()) {
            holder.mRCView.setTextColor(Color.parseColor("#000000"));
            holder.mRCView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
        } else {
            holder.mRCView.setTextColor(Color.parseColor("#888888"));
            holder.mRCView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        }
        if (w.isPositionControl()) {
            holder.mPCView.setTextColor(Color.parseColor("#000000"));
            holder.mPCView.setBackground(ContextCompat.getDrawable(holder.mRCView.getContext(), R.drawable.greencell));
        } else {
            holder.mPCView.setTextColor(Color.parseColor("#888888"));
            holder.mPCView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        }



        //set text formatting for hang info
        holder.mParkView.setTextColor(Color.parseColor("#888888"));
        holder.mParkView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        holder.mHangView.setTextColor(Color.parseColor("#888888"));
        holder.mHangView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        holder.mLevelView.setTextColor(Color.parseColor("#888888"));
        holder.mLevelView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        switch (w.getEndgameOutcome()) {
            case "P":
                holder.mParkView.setTextColor(Color.parseColor("#000000"));
                holder.mParkView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
                break;
            case "H":
                holder.mHangView.setTextColor(Color.parseColor("#000000"));
                holder.mHangView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
                break;
            case "L":
                holder.mHangView.setTextColor(Color.parseColor("#000000"));
                holder.mHangView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
                holder.mLevelView.setTextColor(Color.parseColor("#000000"));
                holder.mLevelView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
                break;
            default:
                //did not participate
        }

        //set text for defense info
        holder.mDefView.setText(String.format(Locale.US, "%d seconds defense played.", w.getDefenseSecs()));
        //draw bar to indicate portion of match spent defending
        Drawable clip = holder.mDefView.getBackground();
        if (clip instanceof ClipDrawable) {
            clip.setLevel((int) Math.round(w.getDefenseSecs() / 135.0 * 10000.0));
        }

        //set text formatting for map info
        holder.mBSView.setTextColor(Color.parseColor("#888888"));
        holder.mBSView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        holder.mFSView.setTextColor(Color.parseColor("#888888"));
        holder.mFSView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        holder.mBWView.setTextColor(Color.parseColor("#888888"));
        holder.mBWView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        holder.mFWView.setTextColor(Color.parseColor("#888888"));
        holder.mFWView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        holder.mBLView.setTextColor(Color.parseColor("#888888"));
        holder.mBLView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        holder.mFLView.setTextColor(Color.parseColor("#888888"));
        holder.mFLView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));
        holder.mSZView.setTextColor(Color.parseColor("#888888"));
        holder.mSZView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.transcell));

        List<String> mapLocations = Arrays.asList(w.getLocations().split(Pattern.quote(".")));
        if (mapLocations.contains("BS")) {
            holder.mBSView.setTextColor(Color.parseColor("#000000"));
            holder.mBSView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
        }
        if (mapLocations.contains("FS")) {
            holder.mFSView.setTextColor(Color.parseColor("#000000"));
            holder.mFSView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
        }
        if (mapLocations.contains("BW")) {
            holder.mBWView.setTextColor(Color.parseColor("#000000"));
            holder.mBWView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
        }
        if (mapLocations.contains("FW")) {
            holder.mFWView.setTextColor(Color.parseColor("#000000"));
            holder.mFWView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
        }
        if (mapLocations.contains("BL")) {
            holder.mBLView.setTextColor(Color.parseColor("#000000"));
            holder.mBLView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
        }
        if (mapLocations.contains("FL")) {
            holder.mFLView.setTextColor(Color.parseColor("#000000"));
            holder.mFLView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
        }
        if (mapLocations.contains("SZ")) {
            holder.mSZView.setTextColor(Color.parseColor("#000000"));
            holder.mSZView.setBackground(ContextCompat.getDrawable(holder.mView.getContext(), R.drawable.greencell));
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
        public final TextView mTeamView, mMatchView, mA3View, mT3View, mE3View, mTot3View, mA2View, mT2View,
                mE2View, mTot2View, mA1View, mT1View, mE1View, mTot1View, mATotView, mTTotView,
                mETotView, mTotView, mAPickView, mRCView, mPCView, mParkView, mHangView, mLevelView,
                mDefView, mBSView, mFSView, mBWView, mFWView, mBLView, mFLView, mSZView;
        //public final View mBar;
        public Whoosh mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTeamView = view.findViewById(R.id.whoosh_team_number);
            mMatchView = view.findViewById(R.id.whoosh_match_number);
            mA3View = view.findViewById(R.id.whoosh_inner_auto);
            mT3View = view.findViewById(R.id.whoosh_inner_teleop);
            mE3View = view.findViewById(R.id.whoosh_inner_endgame);
            mTot3View = view.findViewById(R.id.whoosh_inner_total);
            mA2View = view.findViewById(R.id.whoosh_outer_auto);
            mT2View = view.findViewById(R.id.whoosh_outer_teleop);
            mE2View = view.findViewById(R.id.whoosh_outer_endgame);
            mTot2View = view.findViewById(R.id.whoosh_outer_total);
            mA1View = view.findViewById(R.id.whoosh_bottom_auto);
            mT1View = view.findViewById(R.id.whoosh_bottom_teleop);
            mE1View = view.findViewById(R.id.whoosh_bottom_endgame);
            mTot1View = view.findViewById(R.id.whoosh_bottom_total);
            mATotView = view.findViewById(R.id.whoosh_auto_total);
            mTTotView = view.findViewById(R.id.whoosh_tele_total);
            mETotView = view.findViewById(R.id.whoosh_end_total);
            mTotView = view.findViewById(R.id.whoosh_total_total);
            mAPickView = view.findViewById(R.id.whoosh_apick);
            mRCView = view.findViewById(R.id.whoosh_rot_con);
            mPCView = view.findViewById(R.id.whoosh_pos_con);
            mParkView = view.findViewById(R.id.whoosh_park);
            mHangView = view.findViewById(R.id.whoosh_hang);
            mLevelView = view.findViewById(R.id.whoosh_level);
            mDefView = view.findViewById(R.id.whoosh_defense);
            mBSView = view.findViewById(R.id.whoosh_bs);
            mFSView = view.findViewById(R.id.whoosh_fs);
            mBWView = view.findViewById(R.id.whoosh_bw);
            mFWView = view.findViewById(R.id.whoosh_fw);
            mBLView = view.findViewById(R.id.whoosh_bl);
            mFLView = view.findViewById(R.id.whoosh_fl);
            mSZView = view.findViewById(R.id.whoosh_sz);

            //mBar = view.findViewById(R.id.whoosh_defense_bar);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTeamView.getText() + "'";
        }
    }
}
