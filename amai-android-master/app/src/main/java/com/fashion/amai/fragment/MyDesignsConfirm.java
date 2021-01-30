package com.fashion.amai.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fashion.amai.adapter.AdapterMyDesignsTab3;
import com.fashion.amai.model.MyDataModal;
import com.fashion.amai.R;

import java.util.ArrayList;
import java.util.List;

public class MyDesignsConfirm extends BaseFragment {
    private RecyclerView recyclerAppointments;
    private LinearLayoutManager linearLayoutManager;
    private AdapterMyDesignsTab3 adapterMyDesignsTab3;
    private List<MyDataModal> myDataModalList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_3_my_designes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        myDataModalList = new ArrayList<>();
        recyclerAppointments = view.findViewById(R.id.recycler_confirmed_designs);
        linearLayoutManager = new LinearLayoutManager(getActivity());
        adapterMyDesignsTab3 = new AdapterMyDesignsTab3(getActivity(), myDataModalList);
        recyclerAppointments.setLayoutManager(linearLayoutManager);
        recyclerAppointments.setAdapter(adapterMyDesignsTab3);
        initData();
    }

    private void initData() {
        myDataModalList.add(new MyDataModal("https://i.ebayimg.com/images/g/lXUAAOSwsFtZ5GAo/s-l400.jpg", "Men Ethnic Jacket, Kurta and Dhoti Pant Set", "Dec 09, 2019", "35"));
        myDataModalList.add(new MyDataModal("https://medias.utsavfashion.com/media/catalog/product/cache/1/image/1000x/040ec09b1e35df139433887a97daa66f/m/p/mpc793.jpg", "Sky Bluem Kurta and Dhoti Pant Set", "Nov 18, 2019", "75"));
        myDataModalList.add(new MyDataModal("https://n4.sdlcdn.com/imgs/e/s/z/Fanzi-Blue-Cotton-Dhoti-Kurta-SDL352449337-1-6332e.jpg", "Men Ethnic Jacket, Kurta and Dhoti Pant Set", "Nov 20, 2019", "55"));
        myDataModalList.add(new MyDataModal("https://n3.sdlcdn.com/imgs/a/8/v/Amg-Gold-Silk-Dhoti-Kurta-SDL800696728-1-b6981.jpg", "Kurta and Dhoti Pant Set", "Dec 17, 2019", "63"));
        myDataModalList.add(new MyDataModal("https://n3.sdlcdn.com/imgs/a/8/v/Amg-White-Silk-Dhoti-Kurta-SDL829604889-3-da03e.jpg", "Silver Shine Kurta and Dhoti Pant Set",  "Nov 19, 2019", "83"));
        myDataModalList.add(new MyDataModal("https://i.ebayimg.com/images/g/lXUAAOSwsFtZ5GAo/s-l400.jpg", "Men Ethnic Jacket, Kurta and Dhoti Pant Set", "Dec 09, 2019", "55"));
        myDataModalList.add(new MyDataModal("https://medias.utsavfashion.com/media/catalog/product/cache/1/image/1000x/040ec09b1e35df139433887a97daa66f/m/p/mpc793.jpg", "Sky Bluem Kurta and Dhoti Pant Set", "Nov 18, 2019", "45"));
        myDataModalList.add(new MyDataModal("https://n4.sdlcdn.com/imgs/e/s/z/Fanzi-Blue-Cotton-Dhoti-Kurta-SDL352449337-1-6332e.jpg", "Men Ethnic Jacket, Kurta and Dhoti Pant Set", "Nov 20, 2019", "30"));
        myDataModalList.add(new MyDataModal("https://n3.sdlcdn.com/imgs/a/8/v/Amg-Gold-Silk-Dhoti-Kurta-SDL800696728-1-b6981.jpg", "Kurta and Dhoti Pant Set", "Dec 17, 2019", "59"));
        myDataModalList.add(new MyDataModal("https://n3.sdlcdn.com/imgs/a/8/v/Amg-White-Silk-Dhoti-Kurta-SDL829604889-3-da03e.jpg", "Silver Shine Kurta and Dhoti Pant Set",  "Nov 19, 2019", "83"));

    }
}
