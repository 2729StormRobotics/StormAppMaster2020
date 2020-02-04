package org.stormroboticsnj.ui.display;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.stormroboticsnj.MainActivity;
import org.stormroboticsnj.R;
import org.stormroboticsnj.models.Whoosh;
import org.stormroboticsnj.ui.display.whoosh.WhooshListFragment;

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
        final CharSequence[] colNames = {"team_num", "match_num"};
        final CharSequence[] displayNames = {"Team Number", "Match Number"};
        final ArrayAdapter<CharSequence> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),  R.layout.spinner_item, displayNames);
        //adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        colSpinner.setAdapter(adapter);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*get field and search value, error if nonexistant */
                if (searchBox.getText().toString().equals("")) {
                    Toast t = new Toast(getContext());
                    t.setText("Please enter a value to search for");
                    t.show();
                    return;
                }
                int filterVal = Integer.parseInt(searchBox.getText().toString());
                onButtonPressed(colSpinner.getSelectedItemPosition() == 0, filterVal);
            }
        });
        return v;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(boolean t, int v) {
        if (mListener != null) {
            List<Whoosh> data = mListener.newSearch(t, v);
            WhooshListFragment frag = (WhooshListFragment) getChildFragmentManager().findFragmentById(R.id.frag1);
            if (data.isEmpty()) Toast.makeText(getContext(), "No Matches Found", Toast.LENGTH_LONG).show();
            frag.setWhooshList(data);
          
          //bad
//            svm = ViewModelProviders.of(this).get(SharedViewModel.class);
//            svm.select(data);

            svm = new ViewModelProvider(this).get(SharedViewModel.class);
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
        List<Whoosh> newSearch(boolean team, int val);
    }
}
