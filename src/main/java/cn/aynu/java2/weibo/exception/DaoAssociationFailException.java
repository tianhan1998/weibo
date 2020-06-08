package cn.aynu.java2.weibo.exception;

/**
 * @author tianh
 */
public class DaoAssociationFailException extends Exception{
    public DaoAssociationFailException() {
        super();
    }

    public DaoAssociationFailException(String message) {
        super(message);
    }
}
