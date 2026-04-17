package unicsul.itinerario.tempoamigo.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "contatos_emergencia")
public class ContatoEmergencia {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public final String numero;
    public final String nome;
    public final String mensagemInicial;

    public ContatoEmergencia(String numero, String nome, String mensagemInicial) {
        this.numero = numero;
        this.nome = nome;
        this.mensagemInicial = mensagemInicial;
    }
}