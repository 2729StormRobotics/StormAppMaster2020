package org.stormroboticsnj.ui.rank.team;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.stormroboticsnj.R;
import org.stormroboticsnj.models.Team;
import org.stormroboticsnj.ui.rank.team.TeamListFragment.OnListFragmentInteractionListener;

import java.util.List;
import java.util.Locale;

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
    public void onBindViewHolder(@NonNull final TeamViewHolder holder, int position) {
        if (mValues != null && mValues.size() > 0) {
            holder.mItem = mValues.get(position);

            //set text for team view
            holder.mTeamView.setText(String.format(Locale.US, "Team %d", holder.mItem.getTeam()));

            //set text for match view
            holder.mMatchView.setText(String.format(Locale.US, "%d Matches Played", holder.mItem.getMatches().size()));

            //set text for power cell views
            holder.mA3View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgAPowerCells3())); //inner auto
            holder.mT3View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgTPowerCells3())); //inner tele
            holder.mE3View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgEPowerCells3())); //inner end
            holder.mTot3View.setText(String.format(Locale.US, "%.2f", (holder.mItem.getAvgAPowerCells3()
                    + holder.mItem.getAvgTPowerCells3()
                    + holder.mItem.getAvgEPowerCells3())));

            holder.mA2View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgAPowerCells2())); //inner auto
            holder.mT2View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgTPowerCells2())); //inner auto
            holder.mE2View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgEPowerCells2())); //inner auto
            holder.mTot2View.setText(String.format(Locale.US, "%.2f", (holder.mItem.getAvgAPowerCells2()
                    + holder.mItem.getAvgTPowerCells2()
                    + holder.mItem.getAvgEPowerCells2())));

            holder.mA1View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgAPowerCells1())); //inner auto
            holder.mT1View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgTPowerCells1())); //inner auto
            holder.mE1View.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgEPowerCells1())); //inner auto
            holder.mTot1View.setText(String.format(Locale.US, "%.2f", (holder.mItem.getAvgAPowerCells1()
                    + holder.mItem.getAvgTPowerCells1()
                    + holder.mItem.getAvgEPowerCells1())));

            holder.mATotView.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgAutoPowerCells())); //inner auto
            holder.mTTotView.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgTeleopPowerCells())); //inner auto
            holder.mETotView.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgEndgamePowerCells())); //inner auto
            holder.mTotView.setText(String.format(Locale.US, "%.2f", (holder.mItem.getAvgAutoPowerCells()
                    + holder.mItem.getAvgTeleopPowerCells()
                    + holder.mItem.getAvgEndgamePowerCells())));

            holder.mCycleView.setText(String.format(Locale.US, "Average %.2f Offense Seconds Per Power Cell", holder.mItem.getAvgOffenseSecondsPerPC()));

            holder.mAPickView.setText(String.format(Locale.US, "%.2f", holder.mItem.getAvgAutoPowerCells()));

            holder.mRCView.setText(String.format(Locale.US, "Rotation Control %d/%d Matches", holder.mItem.getRotationControlMatchesCount(),
                    (holder.mItem.getMatches().size())));

            holder.mPCView.setText(String.format(Locale.US, "Position Control %d/%d Matches", holder.mItem.getPositionControlMatchesCount(),
                    (holder.mItem.getMatches().size())));

            holder.mPCView.setText(String.format(Locale.US, "Park %d/%d Matches", holder.mItem.getParkMatchesCount(),
                    (holder.mItem.getMatches().size())));

            holder.mPCView.setText(String.format(Locale.US, "Park %d/%d Matches", holder.mItem.getParkMatchesCount(),
                    (holder.mItem.getMatches().size())));

            holder.mPCView.setText(String.format(Locale.US, "Hang %d/%d Matches", holder.mItem.getHangMatchesCount(),
                    (holder.mItem.getMatches().size())));

            holder.mPCView.setText(String.format(Locale.US, "Level %d/%d Matches", holder.mItem.getLevelMatchesCount(),
                    (holder.mItem.getMatches().size())));

            int[] locationsCount = holder.mItem.getLocationsFrequency(); //format: {BS, FS, BW, FW, BL, FL, SZ}
            holder.mBSView.setText(String.format(Locale.US, "BS %d", locationsCount[0]));
            holder.mFSView.setText(String.format(Locale.US, "BS %d", locationsCount[1]));
            holder.mBWView.setText(String.format(Locale.US, "BS %d", locationsCount[2]));
            holder.mFWView.setText(String.format(Locale.US, "BS %d", locationsCount[3]));
            holder.mBLView.setText(String.format(Locale.US, "BS %d", locationsCount[4]));
            holder.mFLView.setText(String.format(Locale.US, "BS %d", locationsCount[5]));
            holder.mSZView.setText(String.format(Locale.US, "BS %d", locationsCount[6]));
        }
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
                mCycleView, mBSView, mFSView, mBWView, mFWView, mBLView, mFLView, mSZView;
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
            mCycleView = view.findViewById(R.id.team_cycle_time);
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
