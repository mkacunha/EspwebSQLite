package sqlite.com.br.espewebsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by maiko on 07/05/16.
 */
public class AlunosService {

    private SQLiteDatabase db;
    private CriaBanco banco;

    public AlunosService(Context context) {
        banco = new CriaBanco(context);
    }

    public boolean salvar(Aluno aluno) {

        ContentValues valores;
        long resultado;

        db = banco.getWritableDatabase();
        valores = new ContentValues();

        valores.put(CriaBanco.CURSO, aluno.getCurso());
        valores.put(CriaBanco.NOME, aluno.getNome());
        valores.put(CriaBanco.CIDADE, aluno.getCidade());

        resultado = db.insert(CriaBanco.TABELA, null, valores);
        db.close();
        return resultado != -1;
    }

    public List<Aluno> buscar(){
        Cursor dados;
        List<Aluno> alunos = new ArrayList<>();
        String[] campos =  {CriaBanco.ID,CriaBanco.CURSO, CriaBanco.NOME, CriaBanco.CIDADE};

        db = banco.getReadableDatabase();
        dados = db.query(banco.TABELA, campos, null, null, null, null, null, null);

        if(dados!=null){
            dados.moveToFirst();

            do {
                alunos.add(new Aluno(dados.getInt(0), dados.getString(1), dados.getString(2), dados.getString(3)));
            } while (dados.moveToNext());
        }

        db.close();
        return alunos;
    }
}
