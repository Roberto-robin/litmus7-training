package com.litmus7.Response;
public class Response<T> {

    private final T data;      
    private final Status status;


    public enum Status {
    COMPLETED(200),
    FAILED(500),
    PROCESSED(202);

   private final int code;

        Status(int code) {
            this.code = code;
        }

        public int getCode() {
            return code;
        }
    }

    public Response(T data, Status status) {
        this.data = data;
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public Status getStatus() {
        return status;
    }

    public int getStatusCode() {
        return status.getCode();
    }
}

