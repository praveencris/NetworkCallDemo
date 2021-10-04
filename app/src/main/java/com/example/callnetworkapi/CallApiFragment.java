package com.example.callnetworkapi;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.callnetworkapi.databinding.CallApiFragmentBinding;

public class CallApiFragment extends Fragment {

    private CallApiViewModel mViewModel;
    private CallApiFragmentBinding binding;

    public static CallApiFragment newInstance() {
        return new CallApiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.call_api_fragment, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CallApiViewModel.class);


        binding.resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewModel.callNetworkApi("");
            }
        });

        mViewModel.response.observe(getViewLifecycleOwner(), new Observer<Result<String>>() {
            @Override
            public void onChanged(Result<String> result) {
                if(result instanceof Result.Success){
                   binding.resultText.setText(((Result.Success<String>) result).data);
                }else {
                  binding.resultText.setText(((Result.Error<String>)result).exception.getMessage());
                }
            }
        });
    }
}