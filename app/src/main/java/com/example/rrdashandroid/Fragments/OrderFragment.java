package com.example.rrdashandroid.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.rrdashandroid.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OrderFragment extends Fragment {

    public OrderFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView listView = (ListView) getActivity().findViewById(R.id.tray_list);
        listView.setAdapter(new BaseAdapter() {
                                @Override
                                public int getCount() {
                                    return 4;
                                }

                                @Override
                                public Object getItem(int position) {
                                    return null;
                                }

                                @Override
                                public long getItemId(int position) {
                                    return 0;
                                }

                                @Override
                                public View getView(int position, View convertView, ViewGroup parent) {
                                    return LayoutInflater.from(getActivity()).inflate(R.layout.list_item_tray, null);
                                }
                            }
        );



    }
}
