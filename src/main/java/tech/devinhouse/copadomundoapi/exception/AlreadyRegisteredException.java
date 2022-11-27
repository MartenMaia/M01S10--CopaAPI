package tech.devinhouse.copadomundoapi.exception;

public class AlreadyRegisteredException extends RuntimeException{

    public AlreadyRegisteredException(String model, String id){
        super("Objetivo com identificador: " + id + ", já cadastrada na base de dados: "+ model);
    }

    public AlreadyRegisteredException(String nomeRecurso, Integer id) {
        this(nomeRecurso, String.valueOf(id));
    }

}
