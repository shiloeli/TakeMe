package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class TrempList extends AppCompatActivity {
    private RecyclerView fStoreTDriver;
    private FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tremp_list);
        fStoreTDriver = findViewById(R.id.recycleDriver);
        FirestoreRecyclerOptions<Tremp> options = DataBase.trempList("tremps");
        adapter = new FirestoreRecyclerAdapter<Tremp, TrempList.DriverViewHolder>(options) {

            @NonNull
            @Override
            public TrempList.DriverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_tremp_driver ,parent, false);
                return new TrempList.DriverViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TrempList.DriverViewHolder holder, int position, @NonNull Tremp model) {
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
        fStoreTDriver.setHasFixedSize(true);
        fStoreTDriver.setLayoutManager(new LinearLayoutManager(this));
        fStoreTDriver.setAdapter(adapter);

    }
    public void onClickReturn(View view) {
        startActivity(new Intent(getApplicationContext(), DriverDashboard.class));
    }



    class DriverViewHolder extends RecyclerView.ViewHolder {
        private TextView destCity;
        private TextView date;
        private TextView hour;
        private TextView day;
        private TextView numberOfSeats;
        int position;
        Tremp tremp;
        String id;


        public DriverViewHolder(@NonNull View itemView) {
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