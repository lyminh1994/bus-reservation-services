package vn.com.minhlq.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * @author Minh Ly Quang
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class BaseException extends RuntimeException {

	private Integer code;
	private String message;
	private Object data;

	public BaseException(HttpStatus status) {
		super(status.getReasonPhrase());
		this.code = status.value();
		this.message = status.getReasonPhrase();
	}

	public BaseException(HttpStatus status, Object data) {
		this(status);
		this.data = data;
	}

	public BaseException(Integer code, String message) {
		super(message);
		this.code = code;
		this.message = message;
	}

	public BaseException(Integer code, String message, Object data) {
		this(code, message);
		this.data = data;
	}

}
