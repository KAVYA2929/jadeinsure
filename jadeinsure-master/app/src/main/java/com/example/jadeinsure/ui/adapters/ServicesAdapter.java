package com.example.jadeinsure.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.jadeinsure.R;
import com.example.jadeinsure.model.Service;
import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ServiceViewHolder> {

    private List<Service> services;
    private OnServiceClickListener listener;

    public interface OnServiceClickListener {
        void onServiceClick(Service service);
    }

    public ServicesAdapter(List<Service> services, OnServiceClickListener listener) {
        this.services = services;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_service, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceViewHolder holder, int position) {
        holder.bind(services.get(position));
    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public void updateServices(List<Service> newServices) {
        this.services = newServices;
        notifyDataSetChanged();
    }

    class ServiceViewHolder extends RecyclerView.ViewHolder {
        private ImageView iconView;
        private TextView nameView;
        private TextView descriptionView;

        ServiceViewHolder(View itemView) {
            super(itemView);
            iconView = itemView.findViewById(R.id.service_icon);
            nameView = itemView.findViewById(R.id.service_name);
            descriptionView = itemView.findViewById(R.id.service_description);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    listener.onServiceClick(services.get(position));
                }
            });
        }

        void bind(Service service) {
            nameView.setText(service.getName());
            descriptionView.setText(service.getDescription());

            Glide.with(itemView.getContext())
                    .load(service.getIconUrl())
                    .placeholder(R.drawable.ic_service_placeholder)
                    .error(R.drawable.ic_service_error)
                    .into(iconView);
        }
    }
}