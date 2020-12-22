package cl.hector.arquetipo_api.modelo;

import java.io.Serializable;

public class ApiMessage implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer id;
	private String message;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer vId) {
		this.id = vId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String vMessage) {
		this.message = vMessage;
	}
}