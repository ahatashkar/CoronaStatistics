package my.amir.corona.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import my.amir.corona.JsonClasses.Countries.Country;
import my.amir.corona.R;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.myViewHolder> {


    List<Country> list;
    CountryAdapter.onMoreClickListener onMoreClickListener;
    Context context;

//    public int mSelectedItem = -1;


    public List<Country> getList()
    {
        return this.list;
    }

    public CountryAdapter(List<Country> list) {
        this.list = list;
    }


    @Override
    public CountryAdapter.myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        context = parent.getContext();

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recyclerview_country, parent, false);

        return new CountryAdapter.myViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountryAdapter.myViewHolder holder, int position) {

        Country currentItem = list.get(position);

        holder.name_textView.setText(currentItem.getCountry_name());
        holder.cases_textView.setText(currentItem.getCases());
        holder.recovered_textView.setText(currentItem.getTotal_recovered());
        holder.death_textView.setText(currentItem.getDeaths());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name_textView;
        TextView cases_textView;
        TextView recovered_textView;
        TextView death_textView;
        Button more_button;

        myViewHolder(View itemView) {
            super(itemView);

            name_textView = itemView.findViewById(R.id.name_textView);
            cases_textView = itemView.findViewById(R.id.cases_textView);
            recovered_textView = itemView.findViewById(R.id.recovered_textView);
            death_textView = itemView.findViewById(R.id.death_textView);
            more_button = itemView.findViewById(R.id.more_button);

            more_button.setOnClickListener(this);

//            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {

            if(v.getId() == more_button.getId()){
                onMoreClickListener.onClickListener(v, getAdapterPosition());
            }

        }
    }

    public interface onMoreClickListener {
        void onClickListener(View view, int position);
    }

    public void setOnItemMoreClickListener(final CountryAdapter.onMoreClickListener clickListener){
        this.onMoreClickListener = clickListener;
    }


    public void updateList(List<Country> list){

        int i = getItemCount();
        this.list.addAll(i,list);
        notifyDataSetChanged();

    }
}
