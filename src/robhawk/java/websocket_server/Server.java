package robhawk.java.websocket_server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collection;

import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import robhawk.java.websocket_server.models.Cliente;
import robhawk.java.websocket_server.models.Notificacao;
import robhawk.java.websocket_server.utils.ConnectionPool;

public class Server extends WebSocketServer {

	private ConnectionPool pool = new ConnectionPool();

	public Server(InetSocketAddress address) {
		super(address);
	}

	public Server(int port) {
		super(new InetSocketAddress(port));
	}

	@Override
	public void onClose(WebSocket conexao, int codigo, String motivo, boolean remoto) {
		pool.remove(conexao);
		System.out.println("<< sistemas conectados: " + pool.sizeSistemas() + " / total de clientes: " + pool.sizeConexoes());
	}

	@Override
	public void onError(WebSocket conexao, Exception erro) {
		if (erro != null)
			System.out.println("erro: " + erro.getMessage());
		
		if (conexao != null && erro != null)
			conexao.send("erro: " + erro.getMessage());
	}

	@Override
	public void onMessage(WebSocket conexao, String json) {
		Notificacao notificacao = new Notificacao(json);
		pool.send(notificacao);
	}

	@Override
	public void onOpen(WebSocket conexao, ClientHandshake request) {
		String[] params = request.getResourceDescriptor().split("/");
		String usuario = "";
		String sistema = "";

		if (params.length > 1)
			sistema = params[1];
		if (params.length > 2)
			usuario = params[2];

		pool.add(sistema, new Cliente(sistema, usuario, conexao));
		System.out.println(">> sistemas conectados: " + pool.sizeSistemas() + " / total de clientes: " + pool.sizeConexoes());
	}

	public void sendToAll(String text) {
		Collection<WebSocket> conections = connections();

		synchronized (conections) {
			for (WebSocket conexao : conections)
				conexao.send(text);
		}
	}

	public static void main(String[] args) throws InterruptedException, IOException {
		//				WebSocketImpl.DEBUG = true;
		Server server = new Server(8282);
		server.start();
		System.out.println("WebSocket iniciado na porta: " + server.getPort());
	}

}
