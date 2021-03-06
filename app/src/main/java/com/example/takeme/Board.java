//package com.example.takeme;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.core.app.NotificationCompat;
//import androidx.core.app.NotificationManagerCompat;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
//
//import android.app.NotificationChannel;
//import android.app.NotificationManager;
//import android.content.Intent;
//import android.os.Build;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.TextWatcher;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.firebase.ui.firestore.FirestoreRecyclerOptions;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.firestore.DocumentReference;
//import com.google.firebase.firestore.DocumentSnapshot;
//import com.google.firebase.firestore.Query;
//
//public class Board extends AppCompatActivity  {
//    public static final String TAG = "TAG";
//    private RecyclerView fireStoreTremps;
//    private FirestoreRecyclerAdapter adapter;
//    private EditText srcSearch,destSearch;
//    private Button search;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_board);
//        fireStoreTremps = findViewById(R.id.recycleTremp);
//        fireStoreTremps.setHasFixedSize(true);
//        srcSearch=(EditText)findViewById(R.id.srcText);
//        destSearch=(EditText)findViewById(R.id.destText);
//        search=(Button)findViewById(R.id.buttonSearch);
//
//
//
//        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
//            NotificationChannel channel2 = new NotificationChannel("My Notification2", "My Notification2", NotificationManager.IMPORTANCE_DEFAULT);
//            NotificationManager manager2 = getSystemService(NotificationManager.class);
//            manager2.createNotificationChannel(channel2);
//        }
//
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Query query = DataBase.search(srcSearch.toString(), destSearch.toString());
//                FirestoreRecyclerOptions<Tremp> options = new FirestoreRecyclerOptions.Builder<Tremp>()
//                        .setQuery(query, Tremp.class)
//                        .build();
//                adapter = new FirestoreRecyclerAdapter<Tremp, TrempViewHolder>(options) {
//                    @NonNull
//                    @Override
//                    public TrempViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_tremp ,parent, false);
//                        return new TrempViewHolder(view);
//                    }
//
//                    @Override
//                    protected void onBindViewHolder(@NonNull TrempViewHolder holder, int position, @NonNull Tremp model) {
//                        holder.driverId = model.driverId;
//                        holder.date.setText(model.getDate());
//                        holder.destCity.setText(model.getDest());
//                        holder.sourceCity.setText(model.getSrc());
//                        holder.hour.setText(model.getHour());
//                        holder.numberOfSeats.setText(String.valueOf(model.getSeats()));
//                        holder.position=holder.getAdapterPosition();
//                        Tremp tremp=options.getSnapshots().get(position);
//                        holder.tremp=tremp;
//                        holder.id=options.getSnapshots().getSnapshot(position).getId();
//                    }
//
//                };
//            }
//        });
//
////        FirestoreRecyclerOptions<Tremp> options = DataBase.Board("tremps");
//
//        fireStoreTremps.setHasFixedSize(true);
//        fireStoreTremps.setLayoutManager(new LinearLayoutManager(this));
//        fireStoreTremps.setAdapter(adapter);
//
//    }
//
//
//
//        class TrempViewHolder extends RecyclerView.ViewHolder {
//
//            private String driverId;
//            private TextView destCity;
//            private TextView sourceCity;
//            private TextView date;
//            private TextView hour;
//            private Button enrollment, message;
//
//            private TextView numberOfSeats;
//            int position;
//            Tremp tremp;
//            String id;
//
//            public TrempViewHolder(@NonNull View itemView) {
//                super(itemView);
//                destCity = itemView.findViewById(R.id.destCity);
//                sourceCity=itemView.findViewById(R.id.sourceCity);
//                date = itemView.findViewById(R.id.dateTremp);
//                hour = itemView.findViewById(R.id.hourTremp);
//                numberOfSeats = itemView.findViewById(R.id.numOfSeats);
//                message = itemView.findViewById(R.id.messageForDriver);
//                message.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Intent intent=new Intent(Board.this, MessageToDriver.class);
//                        intent.putExtra("driverId", driverId);
//                        startActivity(intent);
//                    }
//                });
//
//                enrollment = itemView.findViewById(R.id.registration);
//                enrollment.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Log.d("demo", "onClick: item clicked " + position + " tremp" + tremp.dest+"   "+id);
//                        DataBase.trempistJoinsTremp(id);
//
//                        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(Board.this, "My Notification2");
//                        builder2.setContentTitle("???????????? ??????????");
//                        builder2.setContentText(tremp.dest+" ?? "+tremp.src+"???????????? ?????????? ?? ");
//                        builder2.setSmallIcon(R.drawable.logo2);
//                        builder2.setAutoCancel(true);
//
//                        NotificationManagerCompat managerCompat2 = NotificationManagerCompat.from(Board.this);
//                        managerCompat2.notify(2,builder2.build());
//                    }
//                });
//            }
//        }
//
//    @Override
//    protected void onStop () {
//        super.onStop();
//        adapter.stopListening();
//    }
//
//    @Override
//    protected void onStart () {
//        super.onStart();
//        adapter.startListening();
//    }
//
//}


package com.example.takeme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class Board extends AppCompatActivity  {
    private RecyclerView fireStoreTremps;
    private FirestoreRecyclerAdapter adapter;
    private EditText srcSearch,destSearch;
    private Button search;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_board);
        fireStoreTremps = findViewById(R.id.recycleTremp);
        fireStoreTremps.setHasFixedSize(true);
        srcSearch=(EditText)findViewById(R.id.srcText);
        destSearch=(EditText)findViewById(R.id.destText);
        search=(Button)findViewById(R.id.buttonSearch);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel2 = new NotificationChannel("My Notification2", "My Notification2", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager2 = getSystemService(NotificationManager.class);
            manager2.createNotificationChannel(channel2);
        }

        search.setOnClickListener(new View.OnClickListener() {
            //On click function for the search button.
            @Override
            public void onClick(View view) {
                Query query = DataBase.search(srcSearch.getText().toString(), destSearch.getText().toString());
                FirestoreRecyclerOptions<Tremp> options = new FirestoreRecyclerOptions.Builder<Tremp>()
                        .setQuery(query, Tremp.class)
                        .build();
                adapter = new FirestoreRecyclerAdapter<Tremp, TrempViewHolder>(options) {
                    @NonNull
                    @Override
                    public TrempViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_tremp ,parent, false);
                        return new TrempViewHolder(view);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull TrempViewHolder holder, int position, @NonNull Tremp model) {
                        holder.driverId = model.driverId;
                        holder.date.setText(model.getDate());
                        holder.destCity.setText(model.getDest());
                        holder.sourceCity.setText(model.getSrc());
                        holder.hour.setText(model.getHour());
                        holder.numberOfSeats.setText(String.valueOf(model.getEmptySeats()));
                        holder.position=holder.getAdapterPosition();
                        Tremp tremp=options.getSnapshots().get(position);
                        holder.tremp=tremp;
                        holder.id=options.getSnapshots().getSnapshot(position).getId();
                    }

                };
                fireStoreTremps.setHasFixedSize(true);
                fireStoreTremps.setLayoutManager(new LinearLayoutManager(Board.this));
                fireStoreTremps.setAdapter(adapter);
                onStart();
            }
        });


        FirestoreRecyclerOptions<Tremp> options = DataBase.Board("tremps");
        adapter = new FirestoreRecyclerAdapter<Tremp, TrempViewHolder>(options) {
            @NonNull
            @Override
            public TrempViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_list_tremp ,parent, false);
                return new TrempViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull TrempViewHolder holder, int position, @NonNull Tremp model) {
                holder.driverId = model.driverId;
                holder.date.setText(model.getDate());
                holder.destCity.setText(model.getDest());
                holder.sourceCity.setText(model.getSrc());
                holder.hour.setText(model.getHour());
                holder.numberOfSeats.setText(String.valueOf(model.getEmptySeats()));
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


    class TrempViewHolder extends RecyclerView.ViewHolder {

        private String driverId;
        private TextView destCity;
        private TextView sourceCity;
        private TextView date;
        private TextView hour;
        private Button enrollment, message;

        private TextView numberOfSeats;
        int position;
        Tremp tremp;
        String id;



        public TrempViewHolder(@NonNull View itemView) {
            super(itemView);
            destCity = itemView.findViewById(R.id.destCity);
            sourceCity=itemView.findViewById(R.id.sourceCity);
            date = itemView.findViewById(R.id.dateTremp);
            hour = itemView.findViewById(R.id.hourTremp);
            numberOfSeats = itemView.findViewById(R.id.numOfSeats);
            message = itemView.findViewById(R.id.messageForDriver);
            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(Board.this, MessageToDriver.class);
                    intent.putExtra("driverId", driverId);
                    startActivity(intent);
                }
            });

            enrollment = itemView.findViewById(R.id.registration);
            enrollment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("demo", "onClick: item clicked " + position + " tremp" + tremp.dest+"   "+id);
                    DataBase.trempistJoinsTremp(id);
                    NotificationCompat.Builder builder2 = new NotificationCompat.Builder(Board.this, "My Notification2");
                    builder2.setContentTitle("???????????? ??????????");
                    builder2.setContentText(tremp.dest+" ?? "+tremp.src+"???????????? ?????????? ?? ");
                    builder2.setSmallIcon(R.drawable.logo2);
                    builder2.setAutoCancel(true);

                    NotificationManagerCompat managerCompat2 = NotificationManagerCompat.from(Board.this);
                    managerCompat2.notify(2,builder2.build());
                }
            });
                enrollment = itemView.findViewById(R.id.registration);
                enrollment.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.d("demo", "onClick: item clicked " + position + " tremp" + tremp.dest+"   "+id);
                        DataBase.trempistJoinsTremp(id);
                        NotificationCompat.Builder builder2 = new NotificationCompat.Builder(Board.this, "My Notification2");
                        NotificationManagerCompat managerCompat2 = NotificationManagerCompat.from(Board.this);
                        DataBase.setNotification(builder2,managerCompat2, tremp);
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

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseFirestore fStore = FirebaseFirestore.getInstance();

        switch (item.getItemId()){
            case R.id.nav_home:
                Intent intent=new Intent(getApplicationContext(), DriverOrTrempist.class);
                startActivity(intent);
                return true;
            case R.id.nav_profile:
                fStore.collection("users").document(DataBase.getID()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.contains("myCar"))
                        {
                            startActivity(new Intent(getApplicationContext(), DriverProfile.class));
                        }
                        else {
                            startActivity(new Intent(getApplicationContext(), Profile.class));
                        }

                    }
                });
                return true;
            case R.id.nav_find:
                Intent intent3=new Intent(getApplicationContext(), Board.class);
                startActivity(intent3);
            case R.id.nav_logout:
                DataBase.logout();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();

        }
        return super.onOptionsItemSelected(item);
    }

}
