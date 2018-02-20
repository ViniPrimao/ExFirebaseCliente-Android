package com.example.qi.exfirebasecliente;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = "logMainActivity";

    private EditText etNome;
    private Spinner spCidade;
    private EditText etRenda;
    private Button btnOK;
    private RecyclerView rvClientes;
    //Adapter + ArrayList
    private ClienteAdapter adapter;
    private ArrayList<Cliente> clientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Refs.
        etNome = findViewById(R.id.ma_et_nome);
        spCidade = findViewById(R.id.ma_sp_cidade);
        etRenda = findViewById(R.id.ma_et_renda);
        btnOK = findViewById(R.id.ma_btn_ok);
        rvClientes = findViewById(R.id.ma_rv_clientes);

        clientes = new ArrayList<>();
        adapter = new ClienteAdapter(MainActivity.this,
                clientes);
        rvClientes.setAdapter(adapter);
        rvClientes.setHasFixedSize(true);
        rvClientes.setLayoutManager(
                new LinearLayoutManager(this));

        //Inicializar o Firebase
        FirebaseApp.initializeApp(MainActivity.this);
        final FirebaseDatabase db = FirebaseDatabase.getInstance();
        final DatabaseReference banco = db.getReference("clientes");

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cliente c = new Cliente(); /* esperando....*/
                c.setNome(etNome.getText().toString());
                c.setCidade(spCidade.getSelectedItem().toString());
                c.setRenda(
                        Double.parseDouble(etRenda.getText().toString()));
                //enviar para o firebase
                banco.push().setValue(c);

                Toast.makeText(
                        getBaseContext(),
                        "Cliente cadastrado com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        });//onclick

        banco.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                clientes.clear();
                for(DataSnapshot data: dataSnapshot.getChildren()) {
                    Cliente c = data.getValue(Cliente.class);
                    c.setKey(data.getKey());
                    clientes.add(c);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        adapter.setOnItemClickListener(new ClienteAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                //alterar
            }

            @Override
            public void onItemLongClick(int position, View v) {
                final int posSelec = position;

                AlertDialog.Builder msg =
                        new AlertDialog.Builder(MainActivity.this);
                msg.setTitle("Tem certeza?!");
                msg.setMessage("Você deseja realmente excluir?!");
                msg.setPositiveButton("sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Cliente c = clientes.get(posSelec);
                        banco.child(c.getKey()).removeValue();
                        clientes.remove(posSelec);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(
                                getBaseContext(),
                                "Cliente excluido com sucesso!",
                                Toast.LENGTH_SHORT).show();
                    }
                });
                msg.setNegativeButton("não",null);
                msg.show();
            }
        });

    }//fecha oncreate
}//fecha classe
