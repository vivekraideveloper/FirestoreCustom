package com.vijayjaidewan01vivekrai.firestorecustom_github;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import javax.annotation.Nullable;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDes, editTextPriority;
    private Button save, load;
    private TextView textView;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference collectionReference = db.collection("NoteBook");
    private DocumentReference documentReference = db.collection("NoteBook").document("First Note");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTitle = findViewById(R.id.edit_text_title);
        editTextDes = findViewById(R.id.edit_text_des);
        editTextPriority = findViewById(R.id.edit_text_priority);
        save = findViewById(R.id.save);
        load = findViewById(R.id.load);
        textView = findViewById(R.id.text_view);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String titleNotebook = editTextTitle.getText().toString();
                String descriptionNotebook = editTextDes.getText().toString();
                int priorityValue = Integer.parseInt(editTextPriority.getText().toString());

                if (editTextPriority.length() == 0){
                    editTextPriority.setText("");
                }

                Note note = new Note(titleNotebook, descriptionNotebook, priorityValue);
                collectionReference.add(note).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {

                        Toast.makeText(MainActivity.this, "Saved Successfully!", Toast.LENGTH_SHORT).show();

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(MainActivity.this, "Some error occured!", Toast.LENGTH_SHORT).show();
                    }
                });



            }
        });


        load.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectionReference.whereEqualTo("priority", 4).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = "";

                        for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots){
                            Note note = documentSnapshots.toObject(Note.class);

                            String title = note.getTitle();
                            String description = note.getDescription();
                            int priorityValue = note.getPriority();

                            data+= "Title: "+title+"\nDescription: "+description+"\nPriority: "+priorityValue+"\n\n";

                        }

                        textView.setText(data);
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                if (e != null){
                    Toast.makeText(MainActivity.this, "Error occured", Toast.LENGTH_SHORT).show();
                    return;
                }

                String data = "";

                for (QueryDocumentSnapshot documentSnapshots : queryDocumentSnapshots){
                    Note note = documentSnapshots.toObject(Note.class);

                    String title = note.getTitle();
                    String description = note.getDescription();
                    int priorityValue = note.getPriority();

                    data+= "Title: "+title+"\nDescription: "+description+"\nPriority: "+priorityValue+"\n\n";

                }

                textView.setText(data);


            }
        });



    }
}
