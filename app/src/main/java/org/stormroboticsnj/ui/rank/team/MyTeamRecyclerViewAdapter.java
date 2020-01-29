package org.stormroboticsnj.ui.rank.team;

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
import org.stormroboticsnj.models.Team;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.ui.rank.team.TeamListFragment.OnListFragmentInteractionListener;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * {@link RecyclerView.Adapter} that can team a  and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyTeamRecyclerViewAdapter extends RecyclerView.Adapter<MyTeamRecyclerViewAdapter.TeamViewHolder> {

    private final List<Team> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyTeamRecyclerViewAdapter(List<Team> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public TeamViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_team, parent, false);
        return new TeamViewHolder(view);
    }


    @Override
    public void onBindViewHolder(final TeamViewHolder holder, int position) {
        holder.mItem = mValues.get(position);

        //set text for team view
        holder.mTeamView.setText(String.format(Locale.US, "Team %d", holder.mItem.getTeam()));

        //set text for match view
        holder.mMatchView.setText(String.format(Locale.US, "%d Matches Played", holder.mItem.getMatches().size()));

        //set text for power cell views
        holder.mA3View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgAPowerCells3())); //inner auto
        holder.mT3View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgTPowerCells3())); //inner tele
        holder.mE3View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgEPowerCells3())); //inner end


    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class TeamViewHolder extends RecyclerView.ViewHolder {
        public final View mView;

        //create all the views
        public final TextView mTeamView, mMatchView, mA3View, mT3View, mE3View, mTot3View, mA2View, mT2View,
                mE2View, mTot2View, mA1View, mT1View, mE1View, mTot1View, mATotView, mTTotView,
                mETotView, mTotView, mAPickView, mRCView, mPCView, mParkView, mHangView, mLevelView,
                mDefView, mBSView, mFSView, mBWView, mFWView, mBLView, mFLView, mSZView;
        //public final View mBar;
        public Team mItem;

        public TeamViewHolder(View view) {
            super(view);

            //INITIALIZE ALL VIEWS

            mView = view;
            mTeamView = view.findViewById(R.id.team_team_number);
            mMatchView = view.findViewById(R.id.team_match_number);
            mA3View = view.findViewById(R.id.team_inner_auto);
            mT3View = view.findViewById(R.id.team_inner_teleop);
            mE3View = view.findViewById(R.id.team_inner_endgame);
            mTot3View = view.findViewById(R.id.team_inner_total);
            mA2View = view.findViewById(R.id.team_outer_auto);
            mT2View = view.findViewById(R.id.team_outer_teleop);
            mE2View = view.findViewById(R.id.team_outer_endgame);
            mTot2View = view.findViewById(R.id.team_outer_total);
            mA1View = view.findViewById(R.id.team_bottom_auto);
            mT1View = view.findViewById(R.id.team_bottom_teleop);
            mE1View = view.findViewById(R.id.team_bottom_endgame);
            mTot1View = view.findViewById(R.id.team_bottom_total);
            mATotView = view.findViewById(R.id.team_auto_total);
            mTTotView = view.findViewById(R.id.team_tele_total);
            mETotView = view.findViewById(R.id.team_end_total);
            mTotView = view.findViewById(R.id.team_total_total);
            mAPickView = view.findViewById(R.id.team_apick);
            mRCView = view.findViewById(R.id.team_rot_con);
            mPCView = view.findViewById(R.id.team_pos_con);
            mParkView = view.findViewById(R.id.team_park);
            mHangView = view.findViewById(R.id.team_hang);
            mLevelView = view.findViewById(R.id.team_level);
            mDefView = view.findViewById(R.id.team_defense);
            mBSView = view.findViewById(R.id.team_bs);
            mFSView = view.findViewById(R.id.team_fs);
            mBWView = view.findViewById(R.id.team_bw);
            mFWView = view.findViewById(R.id.team_fw);
            mBLView = view.findViewById(R.id.team_bl);
            mFLView = view.findViewById(R.id.team_fl);
            mSZView = view.findViewById(R.id.team_sz);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mTeamView.getText() + "'";
        }
    }
}
