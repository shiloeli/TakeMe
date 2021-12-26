package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TrempistTrempsList extends AppCompatActivity {
    private RecyclerView fStoreTrempist;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trempist_tremps_list);
        fStoreTrempist = findViewById(R.id.recycleTrempist);
        FirestoreRecyclerOptions<Tremp> options = DataBase.trempistTremps("tremps");
        adapter = new FirestoreRecyclerAdapter<Tremp,TrempistViewHolder>(options) {

            @NonNull
            @Override
            public TrempistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_tremp_trempist ,parent, false);
                return new TrempistViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TrempistViewHolder holder, int position, @NonNull Tremp model) {
                holder.date.setText(model.getDate());
                holder.destCity.setText(model.getDest());
                holder.hour.setText(model.getHour());
                holder.numberOfSeats.setText(String.valueOf(model.getSeats()));
                holder.position=holder.getAdapterPosition();
                Tremp tremp=options.getSnapshots().get(position);
                holder.tremp=tremp;
                holder.id=options.getSnapshots().getSnapshot(position).getId();
            }

        };
        fStoreTrempist.setHasFixedSize(true);
        fStoreTrempist.setLayoutManager(new LinearLayoutManager(this));
        fStoreTrempist.setAdapter(adapter);

    }



    public void onClickReturn(View view) {
        Intent intent=new Intent(TrempistTrempsList.this, TrempistDashboard.class);
        startActivity(intent);
    }


    class TrempistViewHolder extends RecyclerView.ViewHolder {
        private TextView destCity;
        private TextView date;
        private TextView hour;
        private TextView numberOfSeats;
        int position;
        Tremp tremp;
        String id;


        public TrempistViewHolder(@NonNull View itemView) {
            super(itemView);
            destCity = itemView.findViewById(R.id.destCity);
            date = itemView.findViewById(R.id.dateTremp);
            hour = itemView.findViewById(R.id.hourTremp);
            numberOfSeats = itemView.findViewById(R.id.numOfSeats);
        }
    }

    @Override
    protected void onStop () {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart () {
        super.onStart();
        adapter.startListening();
    }

}