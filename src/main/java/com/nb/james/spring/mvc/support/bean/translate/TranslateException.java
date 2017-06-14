package com.nb.james.spring.mvc.support.bean.translate;

/**
 * 
 * @copyright(C) 2006-2012 James
 * @author James
 */
public class TranslateException extends Exception {

    /**
	 * 
	 */
    private static final long serialVersionUID = -5676399898848577947L;

    public TranslateException(String message) {
        super(message);
    }

    public TranslateException(String message, Throwable cause) {
        super(message, cause);
    }

    public TranslateException(Throwable cause) {
        super(cause);
    }

}
