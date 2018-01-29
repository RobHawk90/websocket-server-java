package robhawk.java.websocket_server.models;

import org.json.JSONObject;

public class Notificacao {

	private String sistema;
	private String usuario;
	private String mensagem;

	public Notificacao(String json) {
		JSONObject jsobj = new JSONObject(json);
		this.sistema = jsobj.getString("sistema");
		this.usuario = jsobj.getString("usuario");
		this.mensagem = jsobj.getString("mensagem");
	}

	public Notificacao(String sistema, String usuario, String mensagem) {
		this.sistema = sistema;
		this.usuario = usuario;
		this.mensagem = mensagem;
	}

	public String getSistema() {
		return sistema;
	}

	public void setSistema(String sistema) {
		this.sistema = sistema;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setCliente(String cliente) {
		this.usuario = cliente;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public String toJson() {
		JSONObject json = new JSONObject();
		json.append("sistema", sistema);
		json.append("usuario", usuario);
		json.append("mensagem", mensagem);
		return json.toString();
	}

}