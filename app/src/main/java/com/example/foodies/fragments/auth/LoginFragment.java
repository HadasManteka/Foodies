package com.example.foodies.fragments.auth;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import com.example.foodies.MainActivity;
import com.example.foodies.R;
import com.example.foodies.databinding.FragmentLoginBinding;
import com.example.foodies.enums.AuthenticationEnum;
import com.example.foodies.model.user.User;
import com.example.foodies.model.user.UserModel;
import com.example.foodies.util.ProgressDialog;

public class LoginFragment extends Fragment {

    FragmentLoginBinding binding;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        MainActivity activity = (MainActivity) getActivity();
        activity.enableNavigationIcon(false);

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        if (User.getUser() != null) {
            NavHostFragment.findNavController(this).navigate(
                    LoginFragmentDirections.actionLoginFragmentToHomePageFragment());
        } else {

            binding.registerBtn.setOnClickListener(view1 -> {
                Navigation.findNavController(binding.getRoot()).navigate(
                        LoginFragmentDirections.actionLoginFragmentToRegisterFragment());

            });

            binding.loginBtn.setOnClickListener(view1 -> {
                if (!validateLinkForm()) {
                    return;
                }

                ProgressDialog.showProgressDialog(getContext(), getString(R.string.loading));
                String email = binding.emailEt.getText().toString();
                String password = binding.passwordEt.getText().toString();

                UserModel.instance().login(email, password, (response) -> {
                    if (AuthenticationEnum.AUTHORIZED.equals(response)) {
                        UserModel.instance().getUser(email, (user) -> {
                            ProgressDialog.hideProgressDialog();
                            User.setUser(user);
                            getActivity().invalidateOptionsMenu();
                            Navigation.findNavController(binding.getRoot()).navigate(
                                    LoginFragmentDirections.actionLoginFragmentToHomePageFragment());

                        });
                        ProgressDialog.hideProgressDialog();
                    } else {
                        ProgressDialog.hideProgressDialog();
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
                        alertDialog.setTitle("Sorry,");
                        alertDialog.setMessage("you are unauthorized.");
                        alertDialog.show();
                    }
                });
            });
        }

        return view;
    }

    private boolean validateLinkForm() {
        if (TextUtils.isEmpty(binding.emailEt.getText().toString())) {
            binding.emailEt.setError("Required.");
            return false;
        } else if (TextUtils.isEmpty(binding.passwordEt.getText().toString())) {
            binding.passwordEt.setError("Required.");
            return false;
        } else {
            return true;
        }
    }
}