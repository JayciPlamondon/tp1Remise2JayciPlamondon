package cstjean.mobile.tp1remise2jayciplamondon.travail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import cstjean.mobile.tp1remise2jayciplamondon.R;
import java.util.ArrayList;

/**
 * La classe LogsListAdapter représente l'adapter pour le RecyclerView du logsList.
 *
 * @author Jayci Plamondon
 */
class LogsListAdapter extends RecyclerView.Adapter<LogsViewHolder> {
    /**
     * Représente la liste des déplacements sous forme Manoury.
     */
    private final ArrayList<String> logsList;

    /**
     * Constructeur de la classe LogsListAdapter.
     *
     * @param logsList Représente la liste des déplacements sous forme Manoury.
     */
    public LogsListAdapter(ArrayList<String> logsList) {
        this.logsList = logsList;
    }

    @NonNull
    @Override
    public LogsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.fragment_deplacement_item, parent, false);
        return new LogsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LogsViewHolder holder, int position) {
        holder.bindCoursSession(logsList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return logsList.size();
    }
}

/**
 * La classe LogsViewHolder représente le ViewHolder pour le RecyclerView du logsList.
 *
 * @author Jayci Plamondon
 */
class LogsViewHolder extends RecyclerView.ViewHolder {
    /**
     * Représente un seul déplacement en notation Manoury.
     */
    private final TextView deplacement;

    /**
     * Le constructeur de la classe LogsViewHolder.
     *
     * @param itemView Le déplacement en notation Manoury.
     */
    LogsViewHolder(@NonNull View itemView) {
        super(itemView);
        deplacement = itemView.findViewById(R.id.item_txt_deplacement);
    }

    /**
     * Modifie la valeur du texte de l'item dans le RecyclerView pour la valeur du déplacement
     * en fonction de la position donné en argument.
     *
     * @param log Représente un seul déplacement en notation Manoury.
     * @param position Représente la position du déplacement dans la logsList.
     */
    void bindCoursSession(String log, int position) {
        String formattedLog = itemView.getContext().getString(R.string.log_item_format, position + 1, log);
        deplacement.setText(formattedLog);
    }

}