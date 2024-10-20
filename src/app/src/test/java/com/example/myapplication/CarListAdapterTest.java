package com.example.myapplication;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

public class CarListAdapterTest {
    private CarListAdapter adapter;
    private List<CarInfo> carInfoList;

    @Mock
    ViewGroup mockParent;
    @Mock
    LayoutInflater mockInflater;
    @Mock
    View mockView;
    @Mock
    CarListAdapter.onItemClickListener mockListener;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        adapter = new CarListAdapter();
        carInfoList = new ArrayList<>();
        carInfoList.add(new CarInfo("Tesla Model S", "Electric Sedan", "Electric", "https://www.tesla.com/models"));
        carInfoList.add(new CarInfo("Chevrolet Bolt", "Compact Electric Car", "Electric", "https://www.chevrolet.com/bolt"));
        adapter.setCarInfoList(carInfoList);
        adapter.setmOnItemClickListener(mockListener);

        when(mockParent.getContext()).thenReturn(RuntimeEnvironment.systemContext);
        when(mockInflater.inflate(eq(R.layout.car_list_item), any(ViewGroup.class), eq(false))).thenReturn(mockView);
    }

    @Test
    public void itemCount_ShouldBeCorrect() {
        assertEquals(2, adapter.getItemCount());
    }

    @Test
    public void onCreateViewHolder_ShouldInflateView() {
        LayoutInflater layoutInflater = LayoutInflater.from(mockParent.getContext());
        when(mockParent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).thenReturn(layoutInflater);

        CarListAdapter.MyHolder holder = adapter.onCreateViewHolder(mockParent, 0);
        assertNotNull(holder);
    }

    @Test
    public void onBindViewHolder_ShouldBindDataCorrectly() {
        CarListAdapter.MyHolder mockHolder = mock(CarListAdapter.MyHolder.class);
        mockHolder.product_title = mock(TextView.class);
        mockHolder.product_price = mock(TextView.class);
        mockHolder.product_count = mock(TextView.class);
        mockHolder.product_date = mock(TextView.class);

        adapter.onBindViewHolder(mockHolder, 0);

        verify(mockHolder.product_title).setText("Tesla Model S");
        verify(mockHolder.product_price).setText("Electric Sedan");
        verify(mockHolder.product_count).setText("Electric");
        verify(mockHolder.product_date).setText("https://www.tesla.com/models");
    }
}
