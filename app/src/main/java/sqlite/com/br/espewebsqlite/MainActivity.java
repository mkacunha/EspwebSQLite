package sqlite.com.br.espewebsqlite;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private AlunosService alunosService;

    private TextView edCurso;
    private TextView edNome;
    private TextView edCidade;

    private ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        alunosService = new AlunosService(getBaseContext());

        FloatingActionButton btSalvar = (FloatingActionButton) findViewById(R.id.fab);
        btSalvar.setOnClickListener(this);


        edCurso = (TextView) findViewById(R.id.edCurso);
        edNome = (TextView) findViewById(R.id.edNome);
        edCidade = (TextView) findViewById(R.id.edCidade);

        listView = (ListView) findViewById(R.id.listView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void salvarAluno() {
        Aluno aluno = new Aluno(0, edCurso.getText().toString(), edNome.getText().toString(), edCidade.getText().toString());

        boolean isSalvou = alunosService.salvar(aluno);

        if (isSalvou) {
            imprimeMensagem("Registro salvo com sucesso !");
            limparCampos();
            buscarDados();
        } else {
            imprimeMensagem("Não foi possível salvar o registro !");
        }
    }

    private void imprimeMensagem(String mensagem) {
        Toast.makeText(getApplicationContext(), mensagem, Toast.LENGTH_LONG).show();
    }

    private void buscarDados() {
        List<Aluno> alunos = alunosService.buscar();

        listView.setAdapter(new ArrayAdapter<>(MainActivity.
                this, android.R.layout.simple_list_item_1, alunos));
    }

    private void limparCampos() {
        edCurso.setText("");
        edNome.setText("");
        edCidade.setText("");
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
}
