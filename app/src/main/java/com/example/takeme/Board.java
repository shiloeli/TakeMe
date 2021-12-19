package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.google.firebase.firestore.Query;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.firestore.FirebaseFirestore;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class Board extends AppCompatActivity  {
    private RecyclerView fireStoreTremps;
    private FirestoreRecyclerAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        fireStoreTremps = findViewById(R.id.recycleTremp);

        FirestoreRecyclerOptions<Tremp> options = DataBase.Board("tremps", "date");
        adapter = new FirestoreRecyclerAdapter<Tremp, TrempViewHolder>(options) {
            @NonNull
            @Override
            public TrempViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_tremp ,parent, false);
                return new TrempViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TrempViewHolder holder, int position, @NonNull Tremp model) {
                holder.date.setText(model.getDate());
                holder.destCity.setText(model.getDest());
                holder.hour.setText(model.getHour());
                holder.day.setText(model.getDay());
                holder.numberOfSeats.setText(String.valueOf(model.getSeats()));
                holder.position=holder.getAdapterPosition();
                Tremp tremp=options.getSnapshots().get(position);
                holder.tremp=tremp;
                holder.id=options.getSnapshots().getSnapshot(position).getId();
            }

        };
        fireStoreTremps.setHasFixedSize(true);
        fireStoreTremps.setLayoutManager(new LinearLayoutManager(this));
        fireStoreTremps.setAdapter(adapter);
    }


        private class TrempViewHolder extends RecyclerView.ViewHolder {
            private TextView destCity;
            private TextView date;
            private TextView hour;
            private TextView day;
            private TextView numberOfSeats;
            int position;
            Tremp tremp;
            String id;


            public TrempViewHolder(@NonNull View itemView) {
                super(itemView);
                destCity = itemView.findViewById(R.id.destCity);
                date = itemView.findViewById(R.id.dateTremp);
                hour = itemView.findViewById(R.id.hourTremp);
                day = itemView.findViewById(R.id.driverDay);
                numberOfSeats = itemView.findViewById(R.id.numOfSeats);
                itemView.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        Log.d("demo", "onClick: item clicked " + position + " tremp" + tremp.dest+"   "+id);

                    }
                });
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
