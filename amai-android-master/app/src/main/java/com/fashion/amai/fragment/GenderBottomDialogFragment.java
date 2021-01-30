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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fashion.amai.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class GenderBottomDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {

  public static final String TAG = "ActionBottomDialog";
  private boolean maleRadio = false;
  private boolean femaleRadio = false;
  private boolean allRadio = false;
  private RadioButton radio_btn_male;
  private RadioButton radio_btn_female;
  private RadioButton radio_btn_all;

  private ItemClickListener mListener;

  public static GenderBottomDialogFragment newInstance() {
    return new GenderBottomDialogFragment();
  }

  @Nullable @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                           @Nullable Bundle savedInstanceState) {
    return inflater.inflate(R.layout.bottom_sheet_gender, container, false);

  }

  @Override
  public void onCancel(DialogInterface dialog) {
    super.onCancel(dialog);

    Toast.makeText(getContext(), "CANCEL", Toast.LENGTH_SHORT).show();

  }

  @Override public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

    LinearLayout male_linearlayout = (LinearLayout) view.findViewById(R.id.male_linearlayout);
     radio_btn_male = (RadioButton) view.findViewById(R.id.radio_btn_male);
     radio_btn_female = (RadioButton) view.findViewById(R.id.radio_btn_female);
     radio_btn_all = (RadioButton) view.findViewById(R.id.radio_btn_all);
    LinearLayout female_linearlayout = (LinearLayout) view.findViewById(R.id.female_linearlayout);
    LinearLayout all_linearlayout = (LinearLayout) view.findViewById(R.id.all_linearlayout);

    male_linearlayout.setOnClickListener(this);
    female_linearlayout.setOnClickListener(this);
    all_linearlayout.setOnClickListener(this);

    radio_btn_male.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        boolean isMaleChecked = radio_btn_male.isChecked();

        if (isMaleChecked){
          mListener.onItemClick("male");

          unCheckAll();
          maleRadio = true;
          radio_btn_male.setChecked(true);

        } else {
          maleRadio = false;
        }
      }
    });

    radio_btn_female.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        boolean isFemaleChecked = radio_btn_female.isChecked();

        if (isFemaleChecked){
          mListener.onItemClick("female");

          unCheckAll();
          femaleRadio = true;
          radio_btn_female.setChecked(true);

        } else {
          femaleRadio = false;
        }
      }
    });

    radio_btn_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

        boolean isAllChecked = radio_btn_all.isChecked();

        if (isAllChecked){
          mListener.onItemClick("all");
          unCheckAll();
          allRadio = true;
          radio_btn_all.setChecked(true);
        } else {
          allRadio = false;
        }

      }
    });


    RadioGroup rGroup = (RadioGroup) view.findViewById(R.id.radioGroup1);
    rGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
    {
      public void onCheckedChanged(RadioGroup group, int checkedId)
      {
        RadioButton checkedRadioButton = (RadioButton)group.findViewById(checkedId);
        boolean isChecked = checkedRadioButton.isChecked();
        if (isChecked)
        {
          Toast.makeText(getContext(), checkedRadioButton.getText(), Toast.LENGTH_SHORT).show();
        }
      }
    });

  }


  private void unCheckAll(){
    if (maleRadio){
      radio_btn_male.setChecked(false);
      maleRadio = false;
    }

    if (femaleRadio){
      radio_btn_female.setChecked(false);
      femaleRadio = false;
    }

    if (allRadio){
      radio_btn_all.setChecked(false);
      allRadio = false;
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
      case R.id.male_linearlayout:

        mListener.onItemClick("male");

        if (radio_btn_male.isChecked()){
          unCheckAll();
        } else {
          radio_btn_male.setChecked(true);

        }

        break;
      case R.id.female_linearlayout:
        mListener.onItemClick("female");

        if (radio_btn_female.isChecked()){
          unCheckAll();
        } else {
          radio_btn_female.setChecked(true);
        }

        break;
      case R.id.all_linearlayout:
        mListener.onItemClick("all");

        if (radio_btn_all.isChecked()){
          unCheckAll();
        } else {
          radio_btn_all.setChecked(true);
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
