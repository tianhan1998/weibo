package cn.aynu.java2.weibo.exception;

public class UploadFailException extends Exception{
    public UploadFailException() {
        super();
    }

    public UploadFailException(String message) {
        super(message);
    }
}
