package org.stormroboticsnj.ui.whoosh;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.stormroboticsnj.MainActivity;
import org.stormroboticsnj.R;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.ui.display.SharedViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class WhooshListFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private OnListFragmentInteractionListener mListener;
    private List<Whoosh> whooshList = new ArrayList<Whoosh>();
    // FOR TESTING PURPOSES ONLY, A BUNCH OF DEFAULT DATA

    MyWhooshRecyclerViewAdapter adapter;
    private SharedViewModel svm;


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public WhooshListFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static WhooshListFragment newInstance() {
        WhooshListFragment fragment = new WhooshListFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    public void setWhooshList(List<Whoosh> wl) {
        whooshList.clear();
        whooshList.addAll(wl);
        adapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_whoosh_list, container, false);
        /* MainActivity act = (MainActivity) getActivity();
        whooshList = act.getData("team_num", 2729); */
        //whooshList = mListener.newSearchWL("team_num", 2729);
        //FOR TESTING PURPOSES ONLY, A BUNCH OF DEFAULT DATA
        for(int i =0; i < 10; ++i) {
            String h = "";
            switch (i % 4) {
                case 0:
                    h = "L";
                    break;
                case 1:
                    h = "H";
                    break;
                case 2:
                    h = "P";
                    break;
                default:
            }
            Whoosh w = new Whoosh(2729, i+1, (Math.round((Math.random() * i)) % 2 == 0), 7, 4, 1,
                    5, 8, 5, 2, (i%2==0), (i%3==0),
                    9, 6, 3, h, "FW.BL.BS.SZ", (int) Math.round((Math.random() * 135)));
            whooshList.add(w);
        }
        adapter = new MyWhooshRecyclerViewAdapter(whooshList, mListener);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            final RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(adapter);

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        List<Whoosh> newSearchWL(String col, int val);
    }
}
