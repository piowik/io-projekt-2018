package io.almp.flatmanager;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShoppingMainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShoppingMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShoppingMainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private ListView mUserBalances;
    private List<UserBalance> userBalancesList;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ShoppingMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShoppingMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShoppingMainFragment newInstance(String param1, String param2) {
        ShoppingMainFragment fragment = new ShoppingMainFragment();
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
        userBalancesList = new LinkedList<>();
        userBalancesList.add(new UserBalance("1", "Artur", 20));
        userBalancesList.add(new UserBalance("2", "Piotr", -20));
        userBalancesList.add(new UserBalance("3", "Mati", 15.4));
        userBalancesList.add(new UserBalance("4", "Laura", -13.3));

        //TODO real data needed from server

        View rootView = inflater.inflate(R.layout.fragment_shopping_main, container, false);
        mUserBalances = rootView.findViewById(R.id.all_flatmates_balances);
        BalancesAdapter balancesAdapter = new BalancesAdapter(this.getActivity(), userBalancesList);
        mUserBalances.setAdapter(balancesAdapter);

        //TODO adapter for recent shopping history

        return rootView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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
        void onFragmentInteraction(Uri uri);
    }
}
