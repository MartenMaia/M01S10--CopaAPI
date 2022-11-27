package tech.devinhouse.copadomundoapi.exception;

public class RegisterNotFoundException extends RuntimeException{

    public RegisterNotFoundException(String model, String id){
        super("Objetivo com identificador: " + id + ", não encontrado na base: "+ model);
    }

    public RegisterNotFoundException(String nomeRecurso, Integer id) {
        this(nomeRecurso, String.valueOf(id));
    }

}
