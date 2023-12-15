package zhurasem.project.api.exceptions;

public class DuplicateSignException extends RuntimeException {

    public DuplicateSignException() {}

    public <E> DuplicateSignException(E entity) {
        super("Duplicate of sign" + entity);
    }

}
