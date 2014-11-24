package com.aldobo.simple.sqlite.sample.fragments;

import android.app.ProgressDialog;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import com.aldobo.simple.sqlite.sample.R;


public class BaseFragment extends Fragment {

    private ProgressDialog mProgressDialog;

    protected void showProgressDialog()
    {
        if(mProgressDialog==null)
            initProgressDialog();
        mProgressDialog.show();

    }
    protected void hideProgressDialog()
    {
        if(mProgressDialog!=null && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }
    protected void initProgressDialog()
    {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setTitle("");
        mProgressDialog.setMessage(getString(R.string.loading));
    }
    protected void toastError(String error)
    {
        Toast.makeText(getActivity().getApplicationContext(),error,Toast.LENGTH_LONG).show();
    }
    protected void toastInfo(String info)
    {
        Toast.makeText(getActivity().getApplicationContext(),info,Toast.LENGTH_SHORT).show();
    }
    protected void showFragment(int frameLayout,Fragment fragment)
    {
        getFragmentManager().beginTransaction().replace(frameLayout,fragment).addToBackStack(null).commit();
    }


}
