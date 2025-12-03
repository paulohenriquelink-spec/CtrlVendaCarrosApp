package br.ifsc.paulo.link.mobile.ctrlvendacarro.ui.carros;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import br.ifsc.paulo.link.mobile.ctrlvendacarro.databinding.FragmentConCarroBinding;
import br.ifsc.paulo.link.mobile.ctrlvendacarro.model.Carro;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Carro}.
 * TODO: Replace the implementation with code for your data type.
 */
public class CarroRecyclerViewAdapter extends RecyclerView.Adapter<CarroRecyclerViewAdapter.ViewHolder> {

    private final List<Carro> mValues;

    public CarroRecyclerViewAdapter(List<Carro> items) {
        mValues = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new ViewHolder(FragmentConCarroBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));

    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).getProduto());
        holder.mContentView.setText(String.valueOf(mValues.get(position).getPreco()));
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mIdView;
        public final TextView mContentView;
        public Carro mItem;

        public ViewHolder(FragmentConCarroBinding binding) {
            super(binding.getRoot());
            mIdView = binding.itemNumber;
            mContentView = binding.content;
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}