package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cstjean.mobile.tp1remise2jayciplamondon.R;

public class DeplacementsFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    /**
     * Instance du jeu Damier.
     */
    private final SingletonDamier singletonDamier = SingletonDamier.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deplacements, container, false);

        System.out.println(singletonDamier.getLogsList());

        Button resumeGameButton = view.findViewById(R.id.resumeGameButton);
        resumeGameButton.setOnClickListener(v -> {
            // Redémarre le DamierFragment pour continuer la partie
            Fragment damierFragment = new DamierFragment();
            FragmentTransaction fm = requireActivity().getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.fragment_container, damierFragment).commit();
        });

        return view;
    }
}