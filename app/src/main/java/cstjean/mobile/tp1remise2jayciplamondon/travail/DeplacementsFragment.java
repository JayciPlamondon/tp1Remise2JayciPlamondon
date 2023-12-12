package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import cstjean.mobile.tp1remise2jayciplamondon.R;

public class DeplacementsFragment extends Fragment {

    /**
     * Représente l'instance du jeu Damier.
     */
    private final SingletonDamier singletonDamier = SingletonDamier.getInstance();

    /**
     * Représente l'instance de RecyclerView.
     */
    private RecyclerView recyclerViewCours;

    /**
     * ente l'instance de LogsListAdapter.
     */
    private LogsListAdapter adapterLogs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deplacements, container, false);

        Button resumeGameButton = view.findViewById(R.id.resumeGameButton);
        resumeGameButton.setOnClickListener(v -> {

            // Redémarre le DamierFragment pour continuer la partie
            Fragment damierFragment = new DamierFragment();
            FragmentTransaction fm = requireActivity().getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.fragment_container, damierFragment).commit();
        });

        recyclerViewCours = view.findViewById(R.id.recycler_view_cours);
        recyclerViewCours.setLayoutManager(new LinearLayoutManager(getActivity()));
        ArrayList<String> logsList = singletonDamier.getLogsList();
        adapterLogs = new LogsListAdapter(logsList);
        recyclerViewCours.setAdapter(adapterLogs);

        return view;
    }
}