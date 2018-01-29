package robhawk.java.websocket_server.models;

import org.java_websocket.WebSocket;

public class Cliente {

	private String sistema;
	private String usuario;
	private WebSocket conexao;

	public Cliente(String sistema, String usuario, WebSocket conexao) {
		this.sistema = sistema;
		this.usuario = usuario;
		this.conexao = conexao;
	}

	public void envia(Notificacao notificacao) {
		conexao.send(notificacao.getMensagem());
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

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public WebSocket getConexao() {
		return conexao;
	}

	public void setConexao(WebSocket conexao) {
		this.conexao = conexao;
	}

}
