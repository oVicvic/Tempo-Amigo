package unicsul.itinerario.tempoamigo.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import unicsul.itinerario.tempoamigo.model.ContatoEmergencia;

@Dao
public interface ContatoEmergenciaDao {

    @Insert
    void inserir(ContatoEmergencia contato);

    @Update
    void atualizar(ContatoEmergencia contato);

    @Delete
    void deletar(ContatoEmergencia contato);

    @Query("SELECT * FROM contatos_emergencia")
    List<ContatoEmergencia> listarTodos();

    @Query("SELECT * FROM contatos_emergencia ORDER BY id DESC LIMIT 1")
    ContatoEmergencia buscarUltimo();

    @Query("SELECT * FROM contatos_emergencia WHERE id = :id")
    ContatoEmergencia buscarPorId(int id);
}