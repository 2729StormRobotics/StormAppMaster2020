package org.stormroboticsnj.ui.rank;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import org.stormroboticsnj.MainActivity;
import org.stormroboticsnj.R;
import org.stormroboticsnj.models.Team;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.ui.rank.team.TeamListFragment;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RankFragment.OnSearchListener} interface
 * to handle interaction events.
 * Use the {@link RankFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RankFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private OnSearchListener mListener;

    //private SharedViewModel svm;
    public RankFragment() {
        // Required empty public constructor
    }

    public static RankFragment newInstance() {
        RankFragment fragment = new RankFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_rank, container, false);
        final Button searchButton = v.findViewById(R.id.btnRank);
        final Spinner colSpinner = v.findViewById(R.id.rankSpinner);

        // final View testView = v.findViewById(R.id.frag1);

        final MainActivity act = (MainActivity) getActivity(); //this might throw exception if (getActivity() instanceOf MainActivity) is false
        final RankEnum[] colNames = {
                RankEnum.POWER_CELLS_BOTTOM_AUTO,
                RankEnum.POWER_CELLS_OUTER_AUTO,
                RankEnum.POWER_CELLS_INNER_AUTO,
                RankEnum.POWER_CELLS_AUTO,
                RankEnum.POWER_CELL_PICKUP,

                RankEnum.POWER_CELLS_BOTTOM_TELEOP,
                RankEnum.POWER_CELLS_OUTER_TELEOP,
                RankEnum.POWER_CELLS_INNER_TELEOP,
                RankEnum.POWER_CELLS_TELEOP,
                RankEnum.CYCLE_TIME,

                RankEnum.POWER_CELLS_BOTTOM_ENDGAME,
                RankEnum.POWER_CELLS_OUTER_ENDGAME,
                RankEnum.POWER_CELLS_INNER_ENDGAME,
                RankEnum.POWER_CELLS_ENDGAME,

                RankEnum.ROTATION_CONTROL,
                RankEnum.POSITION_CONTROL,

                RankEnum.ENDGAME_HANG,
                RankEnum.ENDGAME_LEVEL_HANG};
        final CharSequence[] displayNames = {
                "Auto Bottom PC",
                "Auto Outer PC",
                "Auto Inner PC",
                "Auto Total PC",
                "Auto PC Pickup",
                "Teleop Bottom PC",
                "Teleop Outer PC",
                "Teleop Inner PC",
                "Teleop Total PC",
                "OFFENSE SECONDS PER PC",
                "Endgame Bottom PC",
                "Endgame Outer PC",
                "Endgame Inner PC",
                "Endgame Total PC",
                "Rotation Control Frequency",
                "Position Control Frequency",
                "Hang Frequency",
                "Level Hang Frequency"};
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(), R.layout.spinner_item, displayNames);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colSpinner.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*get search column*/
                onButtonPressed(colNames[colSpinner.getSelectedItemPosition()]);
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(RankEnum rank) {
        if (mListener != null) {
            List<Whoosh> data = mListener.getAll();
            List<Team> teams = new ArrayList<>();
            if (data.size() > 1) {

                int lastSavedIndex = 0;

                for (int i = 1; i < data.size(); ++i) {
                    if (data.get(i).getTeam() != data.get(i - 1).getTeam()) {
                        Team t = new Team();
                        t.setMatches(data.subList(lastSavedIndex, i));
                        teams.add(t);
                        lastSavedIndex = i;
                    }
                }

                final RankEnum r = rank;

                Collections.sort(teams, new Comparator<Team>() {
                    @Override
                    public int compare(Team t0, Team t1) {
                        double avt0 = 0.0;
                        double avt1 = 0.0;
                        switch (r) {
                            case POWER_CELLS_BOTTOM_AUTO:
                                avt0 = t0.getAvgAPowerCells1();
                                avt1 = t1.getAvgAPowerCells1();
                                break;
                            case POWER_CELLS_OUTER_AUTO:
                                avt0 = t0.getAvgAPowerCells2();
                                avt1 = t1.getAvgAPowerCells2();
                                break;
                            case POWER_CELLS_INNER_AUTO:
                                avt0 = t0.getAvgAPowerCells3();
                                avt1 = t1.getAvgAPowerCells3();
                                break;
                            case POWER_CELLS_AUTO:
                                avt0 = t0.getAvgAutoPowerCells();
                                avt1 = t1.getAvgAutoPowerCells();
                                break;
                            case POWER_CELL_PICKUP:
                                avt0 = t0.getAvgPowerCellPickup();
                                avt1 = t1.getAvgPowerCellPickup();
                                break;
                            case POWER_CELLS_BOTTOM_TELEOP:
                                avt0 = t0.getAvgTPowerCells1();
                                avt1 = t1.getAvgTPowerCells1();
                                break;
                            case POWER_CELLS_OUTER_TELEOP:
                                avt0 = t0.getAvgTPowerCells2();
                                avt1 = t1.getAvgTPowerCells2();
                                break;
                            case POWER_CELLS_INNER_TELEOP:
                                avt0 = t0.getAvgTPowerCells3();
                                avt1 = t1.getAvgTPowerCells3();
                                break;
                            case POWER_CELLS_TELEOP:
                                avt0 = t0.getAvgTeleopPowerCells();
                                avt1 = t1.getAvgTeleopPowerCells();
                                break;
                            case CYCLE_TIME:
                                avt0 = t0.getAvgOffenseSecondsPerPC();
                                avt1 = t1.getAvgOffenseSecondsPerPC();
                                break;
                            case POWER_CELLS_BOTTOM_ENDGAME:
                                avt0 = t0.getAvgEPowerCells1();
                                avt1 = t1.getAvgEPowerCells1();
                                break;
                            case POWER_CELLS_OUTER_ENDGAME:
                                avt0 = t0.getAvgEPowerCells2();
                                avt1 = t1.getAvgEPowerCells2();
                                break;
                            case POWER_CELLS_INNER_ENDGAME:
                                avt0 = t0.getAvgEPowerCells3();
                                avt1 = t1.getAvgEPowerCells3();
                                break;
                            case POWER_CELLS_ENDGAME:
                                avt0 = t0.getAvgEndgamePowerCells();
                                avt1 = t1.getAvgEndgamePowerCells();
                                break;
                            case ROTATION_CONTROL:
                                avt0 = t0.getRotationControlFrequency();
                                avt1 = t1.getRotationControlFrequency();
                                break;
                            case POSITION_CONTROL:
                                avt0 = t0.getPositionControlFrequency();
                                avt1 = t1.getPositionControlFrequency();
                                break;
                            case ENDGAME_HANG:
                                avt0 = t0.getHangFrequency();
                                avt1 = t1.getHangFrequency();
                                break;
                            case ENDGAME_LEVEL_HANG:
                                avt0 = t0.getLevelHangFrequency();
                                avt1 = t1.getLevelHangFrequency();
                                break;
                            default:
                                // nothing
                        }
                        if (avt0 == avt1) return 0;
                        else if (avt0 > avt1) return 1;
                        else {return -1;}
                    }
                });

            }

            TeamListFragment frag = (TeamListFragment) getChildFragmentManager().findFragmentById(R.id.frag2);
            frag.setTeamList(teams);

            //svm = ViewModelProviders.of(this).get(SharedViewModel.class);
            //svm.select(teams);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSearchListener) {
            mListener = (OnSearchListener) context;
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

    public interface OnSearchListener {
        // TODO: Update argument type and name
        List<Whoosh> getAll();
    }
}
