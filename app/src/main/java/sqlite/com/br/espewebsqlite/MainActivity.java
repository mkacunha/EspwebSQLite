package sqlite.com.br.espewebsqlite;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlunosService alunosService;

    private TextView edCurso;
    private TextView edNome;
    private TextView edCidade;

    private ListView listView;

    private List<Aluno> alunos;
    private GoogleApiClient client;

    private boolean isEditandoAluno;
    private Aluno alunoSelecionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        alunosService = new AlunosService(getBaseContext());

        FloatingActionButton btSalvar = (FloatingActionButton) findViewById(R.id.fab);
        btSalvar.setOnClickListener(this);


        edCurso = (TextView) findViewById(R.id.edCurso);
        edNome = (TextView) findViewById(R.id.edNome);
        edCidade = (TextView) findViewById(R.id.edCidade);

        listView = (ListView) findViewById(R.id.listView);
        registerForContextMenu(listView);

        buscarDados();

        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.listView) {
            getMenuInflater().inflate(R.menu.menu_edicao, menu);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        alunoSelecionado = alunos.get(info.position);

        switch (item.getItemId()) {
            case R.id.menu_editar_item:
                editarAluno();
                return true;
            case R.id.menu_remover_item:
                removerAluno();
                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    private void editarAluno() {
        isEditandoAluno = true;
        preencherCampos(alunoSelecionado);
    }


    private void salvarAluno() {
        if (!isEditandoAluno) alunoSelecionado = new Aluno();

        alunoSelecionado.setCurso(edCurso.getText().toString());
        alunoSelecionado.setNome(edNome.getText().toString());
        alunoSelecionado.setCidade(edCidade.getText().toString());

        boolean isSalvou = alunosService.salvar(alunoSelecionado);

        if (isSalvou) {
            imprimeMensagem("Registro salvo com sucesso !");
            limparCampos();
            buscarDados();
        } else {
            imprimeMensagem("Não foi possível salvar o registro !");
        }
    }

    private void removerAluno() {
        if (alunosService.remover(alunoSelecionado.getId())) {
            buscarDados();
            imprimeMensagem("Aluno removido com sucesso !");
        }
    }

    private void imprimeMensagem(String mensagem) {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    private void buscarDados() {
        alunos = alunosService.buscar();

        listView.setAdapter(new ArrayAdapter<>(MainActivity.
                this, android.R.layout.simple_list_item_1, alunos));
    }

    private void limparCampos() {
        edCurso.setText("");
        edNome.setText("");
        edCidade.setText("");
    }

    private void preencherCampos(Aluno aluno) {
        edCurso.setText(aluno.getCurso());
        edNome.setText(aluno.getNome());
        edCidade.setText(aluno.getCidade());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab: {
                salvarAluno();
                break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://sqlite.com.br.espewebsqlite/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://sqlite.com.br.espewebsqlite/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
