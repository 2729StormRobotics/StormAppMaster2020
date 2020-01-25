package org.stormroboticsnj.ui.display;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import org.stormroboticsnj.MainActivity;
import org.stormroboticsnj.R;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.ui.whoosh.WhooshListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DisplayFragment.OnSearchListener} interface
 * to handle interaction events.
 * Use the {@link DisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DisplayFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    private OnSearchListener mListener;
    private SharedViewModel svm;
    public DisplayFragment() {
        // Required empty public constructor
    }

    public static DisplayFragment newInstance() {
        DisplayFragment fragment = new DisplayFragment();
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
        View v = inflater.inflate(R.layout.fragment_display, container, false);
        final Button searchButton = v.findViewById(R.id.btnSearch);
        final Spinner colSpinner = v.findViewById(R.id.spinnerCols);
        final EditText searchBox = v.findViewById(R.id.txtVal);

       // final View testView = v.findViewById(R.id.frag1);


        final MainActivity act = (MainActivity) getActivity(); //this might throw exception if (getActivity() instanceOf MainActivity) is false
        final CharSequence[] colNames = act.getColNames();
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getActivity().getApplicationContext(),  android.R.layout.simple_spinner_item, colNames);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colSpinner.setAdapter(adapter);



        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*get field and search value, error if nonexistant */
                if (searchBox.getText().equals("")) {return;} //TODO: Add error message
                int filterVal = Integer.parseInt(searchBox.getText().toString());
                onButtonPressed(colNames[colSpinner.getSelectedItemPosition()].toString(), filterVal);
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(String c, int v) {
        if (mListener != null) {
            List<Whoosh> data = mListener.newSearch(c, v);
            WhooshListFragment frag = (WhooshListFragment) getChildFragmentManager().findFragmentById(R.id.frag1);
            frag.setWhooshList(data);
            svm = ViewModelProviders.of(this).get(SharedViewModel.class);
            svm.select(data);
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
        List<Whoosh> newSearch(String col, int val);
    }
}
