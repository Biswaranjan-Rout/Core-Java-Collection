package com.fashion.amai.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fashion.amai.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class SortBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

  public static final String TAG = "ActionBottomDialog";
  private boolean popularityRadio = false;
  private boolean low_to_highRadio = false;
  private boolean high_to_lowRadio = false;
  private boolean newest_first_Radio = false;
  private boolean nearbyRadio = false;
  private RadioButton radio_btn_popularity;
  private RadioButton radio_btn_low_to_high;
  private RadioButton radio_btn_high_to_low;
  private RadioButton radio_btn_newest_first;
  private RadioButton radio_btn_nearby;

  private ItemClickListener mListener;

  public static SortBottomDialogFragment newInstance() {
    return new SortBottomDialogFragment();
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.bottom_sheet_sort, container, false);

  }

  @Override
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);

    Toast.makeText(getContext(), "CANCEL", Toast.LENGTH_SHORT).show();

  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    LinearLayout linearlayout_popular = (LinearLayout) view.findViewById(R.id.linearlayout_popular);
     radio_btn_popularity = (RadioButton) view.findViewById(R.id.radio_btn_popularity);
     radio_btn_low_to_high = (RadioButton) view.findViewById(R.id.radio_btn_low_to_high);
     radio_btn_high_to_low = (RadioButton) view.findViewById(R.id.radio_btn_high_to_low);
    radio_btn_newest_first = (RadioButton) view.findViewById(R.id.radio_btn_newest_first);
    radio_btn_nearby = (RadioButton) view.findViewById(R.id.radio_btn_nearby);
    LinearLayout linearlayout_low_high = (LinearLayout) view.findViewById(R.id.linearlayout_low_high);
    LinearLayout linearlayout_high_low = (LinearLayout) view.findViewById(R.id.linearlayout_high_low);
    LinearLayout linearlayout_newest = (LinearLayout) view.findViewById(R.id.linearlayout_newest);
    LinearLayout linearlayout_nearBy = (LinearLayout) view.findViewById(R.id.linearlayout_nearBy);

    linearlayout_popular.setOnClickListener(this);
    linearlayout_low_high.setOnClickListener(this);
    linearlayout_high_low.setOnClickListener(this);
    linearlayout_newest.setOnClickListener(this);
    linearlayout_nearBy.setOnClickListener(this);



    radio_btn_nearby.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        boolean isMaleChecked = radio_btn_nearby.isChecked();

        if (isMaleChecked){
          mListener.onItemClick("Nearby");

          unCheckAll();
          nearbyRadio = true;
          radio_btn_nearby.setChecked(true);

        } else {
          nearbyRadio = false;
        }
      }
    });

    radio_btn_newest_first.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        boolean isMaleChecked = radio_btn_newest_first.isChecked();

        if (isMaleChecked){
          mListener.onItemClick("Newest");

          unCheckAll();
          newest_first_Radio = true;
          radio_btn_newest_first.setChecked(true);

        } else {
          newest_first_Radio = false;
        }
      }
    });

    radio_btn_popularity.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        boolean isMaleChecked = radio_btn_popularity.isChecked();

        if (isMaleChecked){
          mListener.onItemClick("popular");

          unCheckAll();
          popularityRadio = true;
          radio_btn_popularity.setChecked(true);

        } else {
          popularityRadio = false;
        }
      }
    });

    radio_btn_low_to_high.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        boolean isFemaleChecked = radio_btn_low_to_high.isChecked();

        if (isFemaleChecked){
          mListener.onItemClick("lowHigh");

          unCheckAll();
          low_to_highRadio = true;
          radio_btn_low_to_high.setChecked(true);

        } else {
          low_to_highRadio = false;
        }
      }
    });

    radio_btn_high_to_low.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        boolean isAllChecked = radio_btn_high_to_low.isChecked();

        if (isAllChecked){
          mListener.onItemClick("highlow");
          unCheckAll();
          high_to_lowRadio = true;
          radio_btn_high_to_low.setChecked(true);
        } else {
          high_to_lowRadio = false;
        }

      }
    });

  }

  private void unCheckAll(){
    if (popularityRadio){
      radio_btn_popularity.setChecked(false);
      popularityRadio = false;
    }

    if (low_to_highRadio){
      radio_btn_low_to_high.setChecked(false);
      low_to_highRadio = false;
    }

    if (high_to_lowRadio){
      radio_btn_high_to_low.setChecked(false);
      high_to_lowRadio = false;
    }

    if (newest_first_Radio){
      radio_btn_newest_first.setChecked(false);
      newest_first_Radio = false;
    }

    if (nearbyRadio){
      radio_btn_nearby.setChecked(false);
      nearbyRadio = false;
    }


  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof ItemClickListener) {
      mListener = (ItemClickListener) context;
    } else {
      throw new RuntimeException(context.toString()
              + " must implement ItemClickListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }


  @Override public void onClick(View view) {

    switch (view.getId()){
      case R.id.linearlayout_popular:

        mListener.onItemClick("popular");

        if (radio_btn_popularity.isChecked()){
          unCheckAll();
        } else {
          radio_btn_popularity.setChecked(true);

        }

        break;
      case R.id.linearlayout_low_high:
        mListener.onItemClick("lowhigh");

        if (radio_btn_low_to_high.isChecked()){
          unCheckAll();
        } else {
          radio_btn_low_to_high.setChecked(true);
        }

        break;
      case R.id.linearlayout_high_low:
        mListener.onItemClick("highlow");

        if (radio_btn_high_to_low.isChecked()){
          unCheckAll();
        } else {
          radio_btn_high_to_low.setChecked(true);
        }

        break;

      case R.id.linearlayout_newest:
        mListener.onItemClick("newest");

        if (radio_btn_newest_first.isChecked()){
          unCheckAll();
        } else {
          radio_btn_newest_first.setChecked(true);
        }

        break;
      case R.id.linearlayout_nearBy:
        mListener.onItemClick("nearby");

        if (radio_btn_nearby.isChecked()){
          unCheckAll();
        } else {
          radio_btn_nearby.setChecked(true);
        }

        break;


    }
   /* TextView tvSelected = (TextView) view;

    mListener.onItemClick(tvSelected.getText().toString());

    dismiss();

    */
  }

  public interface ItemClickListener {
    void onItemClick(String item);
  }
}
