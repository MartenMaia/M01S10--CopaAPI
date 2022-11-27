package tech.devinhouse.copadomundoapi.exception;

public class RegistroInvalidoException extends RuntimeException{

    RegistroInvalidoException(String model, String id){
        super("Objetivo com identificador: " + id + ", configurado de forma invalida para a base: "+ model);
    }

    public RegistroInvalidoException(String nomeRecurso, Integer id) {
        this(nomeRecurso, String.valueOf(id));
    }

}
