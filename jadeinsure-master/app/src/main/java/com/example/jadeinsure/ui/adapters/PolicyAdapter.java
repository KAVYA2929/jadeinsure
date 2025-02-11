package com.example.jadeinsure.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.jadeinsure.R;
import com.example.jadeinsure.model.Policy;
import com.google.android.material.chip.Chip;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class PolicyAdapter extends RecyclerView.Adapter<PolicyAdapter.PolicyViewHolder> {
    private List<Policy> policies;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());

    public PolicyAdapter(List<Policy> policies) {
        this.policies = policies;
    }

    @NonNull
    @Override
    public PolicyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_policy, parent, false);
        return new PolicyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PolicyViewHolder holder, int position) {
        Policy policy = policies.get(position);
        holder.bind(policy);
    }

    @Override
    public int getItemCount() {
        return policies.size();
    }

    public void updatePolicies(List<Policy> newPolicies) {
        this.policies = newPolicies;
        notifyDataSetChanged();
    }

    class PolicyViewHolder extends RecyclerView.ViewHolder {
        private final TextView policyType;
        private final TextView policyId;
        private final TextView policyDates;
        private final TextView policyPremium;
        private final Chip policyStatus;

        PolicyViewHolder(View itemView) {
            super(itemView);
            policyType = itemView.findViewById(R.id.policy_type);
            policyId = itemView.findViewById(R.id.policy_id);
            policyDates = itemView.findViewById(R.id.policy_dates);
            policyPremium = itemView.findViewById(R.id.policy_premium);
            policyStatus = itemView.findViewById(R.id.policy_status);
        }

        void bind(Policy policy) {
            policyType.setText(policy.getType());
            policyId.setText(String.format("Policy ID: %s", policy.getId()));
            policyDates.setText(String.format("%s - %s",
                    dateFormat.format(policy.getStartDate()),
                    dateFormat.format(policy.getEndDate())));
            policyPremium.setText(String.format(Locale.getDefault(),
                    "Premium: $%.2f", policy.getPremium()));
            policyStatus.setText(policy.getStatus());
        }
    }
}