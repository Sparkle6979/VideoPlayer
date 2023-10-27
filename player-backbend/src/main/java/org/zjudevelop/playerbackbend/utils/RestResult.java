package org.zjudevelop.playerbackbend.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RestResult<T> {
    private String message;
    private int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;

    private RestResult(String message, int code, T data) {
        this.message = message;
        this.code = code;
        this.data = data;
    }

    public static RestResult<Object> success() {
        return new RestResult<>(OK_MESSAGE, OK_CODE, null);
    }

    public static <T> RestResult<T> success(T data) {
        return new RestResult<>(OK_MESSAGE, OK_CODE, data);
    }

    public static <T> RestResult<T> success(T data, String message) {
        return new RestResult<>(message, OK_CODE, data);
    }

    public static <T> RestResult<T> fail() {
        return new RestResult<>(NOT_OK_MESSAGE, NOT_OK_CODE, null);
    }

    public static <T> RestResult<T> fail(String message) {
        return new RestResult<>(message, NOT_OK_CODE, null);
    }

    public static <T> RestResult<T> fail(String message, T data) {
        return new RestResult<>(message, NOT_OK_CODE, data);
    }

    public static <T> RestResult<T> fail(String message, int code ) {
        return new RestResult<>(message, code, null);
    }

    public static <T> RestResult<T> fail(String message, int code, T data) {
        return new RestResult<>(message, code, data);
    }

    class Builder {
        private String message;
        private int code;
        private T data;

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public Builder code(int code) {
            this.code = code;
            return this;
        }

        public Builder data(T data) {
            this.data = data;
            return this;
        }

        public RestResult<T> build() {
            return new RestResult<>(message, code, data);
        }
    }

    private static final String NOT_OK_MESSAGE = "ERROR";
    private static final int NOT_OK_CODE = -1;
    private static final String OK_MESSAGE = "OK";
    private static final int OK_CODE = 200;
}
