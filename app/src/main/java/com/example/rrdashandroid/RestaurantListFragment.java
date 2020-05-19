package com.example.rrdashandroid;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {

    public RestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_restaurant_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ListView restaurantListView = (ListView) getActivity().findViewById(R.id.restaurant_list);
        restaurantListView.setAdapter(new BaseAdapter() {
                                          @Override
                                          public int getCount() {
                                              return 3;
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
                                              return LayoutInflater.from(getActivity()).inflate(R.layout.list_item_restaurant, null);
                                          }
                                      }

        );


    }
}
