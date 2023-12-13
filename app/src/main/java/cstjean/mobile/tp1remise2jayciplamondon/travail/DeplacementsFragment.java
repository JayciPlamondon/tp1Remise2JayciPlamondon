package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import cstjean.mobile.tp1remise2jayciplamondon.R;
import java.util.ArrayList;

/**
 * La classe DeplacementsFragment représente le fragment qui affiche la liste des déplacements.
 *
 * @author Jayci Plamondon
 */
public class DeplacementsFragment extends Fragment {

    /**
     * Représente l'instance du jeu Damier.
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

        Button resumeGameButton = view.findViewById(R.id.resumeGameButton);
        resumeGameButton.setOnClickListener(v -> {

            // Redémarre le DamierFragment pour continuer la partie
            Fragment damierFragment = new DamierFragment();
            FragmentTransaction fm = requireActivity().getSupportFragmentManager().beginTransaction();
            fm.replace(R.id.fragment_container, damierFragment).commit();
        });

        RecyclerView recyclerViewCours = view.findViewById(R.id.recycler_view_cours);
        recyclerViewCours.setLayoutManager(new LinearLayoutManager(getActivity()));

        ArrayList<String> logsList = singletonDamier.getLogsList();

        LogsListAdapter adapterLogs = new LogsListAdapter(logsList);
        recyclerViewCours.setAdapter(adapterLogs);

        return view;
    }
}