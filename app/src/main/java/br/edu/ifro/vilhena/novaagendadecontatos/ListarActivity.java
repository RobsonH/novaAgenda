package br.edu.ifro.vilhena.novaagendadecontatos;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import br.edu.ifro.vilhena.novaagendadecontatos.dao.ContatoDAO;
import br.edu.ifro.vilhena.novaagendadecontatos.model.Contato;

public class ListarActivity extends AppCompatActivity {

    private ListView listarContatos;
    private FloatingActionButton listarAcao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        listarContatos = findViewById(R.id.listar_contatos);
        listarAcao = findViewById(R.id.listar_acao);

        listarAcao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ListarActivity.this, FormularioActivity.class);
                startActivity(intent);
            }
        });

        registerForContextMenu(listarContatos);

        listarContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> lista, View item, int posicao, long id) {

                Contato contato = (Contato) lista.getItemAtPosition(posicao);

                Intent intent = new Intent(ListarActivity.this, FormularioActivity.class);
                intent.putExtra("contato", contato);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        carregarLista();
    }

    private void carregarLista() {
        ContatoDAO contatoDAO = new ContatoDAO(this);
        List<Contato> contatos = contatoDAO.listar();
        contatoDAO.close();

       // ArrayAdapter<Contato> adapter = new ArrayAdapter<Contato>(this, android.R.layout.simple_list_item_1, contatos);
        AdapterPersonalizado adapter = new AdapterPersonalizado(contatos, this);
        listarContatos.setAdapter(adapter);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {

        MenuItem menuDeletar = menu.add("Deletar");
        MenuItem menuLigar = menu.add("Ligar");
        MenuItem menuCompartilhar = menu.add("Compartilhar");
        MenuItem menuEmail = menu.add("Enviar email");


        menuDeletar.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

                Contato contato = (Contato) listarContatos.getItemAtPosition(info.position);

                ContatoDAO contatoDAO = new ContatoDAO(ListarActivity.this);
                contatoDAO.deletar(contato);
                contatoDAO.close();

                carregarLista();

                return false;
            }
        });

    }
}










