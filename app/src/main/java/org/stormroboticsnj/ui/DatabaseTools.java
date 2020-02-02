package org.stormroboticsnj.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import org.stormroboticsnj.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DatabaseTools.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DatabaseTools#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatabaseTools extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public DatabaseTools() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DatabaseTools.
     */
    // TODO: Rename and change types and number of parameters
    public static DatabaseTools newInstance(String param1, String param2) {
        DatabaseTools fragment = new DatabaseTools();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_database_tools, container, false);

        final Button dump = v.findViewById(R.id.btnDump);
        final Button clean = v.findViewById(R.id.btnClear);

        clean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity()) //confirm with user
                        .setTitle("Clear Data")
                        .setMessage("Are you sure you want to completely clear the local database? This action is permanent.")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                new AlertDialog.Builder(getActivity()) //confirm with user
                                        .setTitle("Confirm")
                                        .setMessage("The local database will be permanently cleared.")
                                        .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //clear database table
                                                mListener.clearDatabase();
                                                Toast.makeText(getActivity(), "Database cleared", Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                //do nothing
                                            }
                                        })
                                        .setIcon(R.mipmap.ic_launcher)
                                        .show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //do nothing
                            }
                        })
                        .setIcon(R.mipmap.ic_launcher)
                        .show();
            }
        });

        dump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View s) {
                if(mListener.dumpDatabase() != null)
                    Toast.makeText(getActivity(), "Database exported to csv file", Toast.LENGTH_SHORT)
                            .show();
                else
                    Toast.makeText(getActivity(), "Database dump failed", Toast.LENGTH_SHORT)
                            .show();
            }
        });

        return v; // Inflate the layout for this fragment
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void clearDatabase();

        String dumpDatabase();
    }

}
