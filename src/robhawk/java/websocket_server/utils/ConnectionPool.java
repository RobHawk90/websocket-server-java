package robhawk.java.websocket_server.utils;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

import org.java_websocket.WebSocket;

import robhawk.java.websocket_server.models.Cliente;
import robhawk.java.websocket_server.models.Notificacao;

public class ConnectionPool {

	private ConcurrentMap<String, ConcurrentLinkedQueue<Cliente>> sistemas;

	public ConnectionPool() {
		this.sistemas = new ConcurrentHashMap<String, ConcurrentLinkedQueue<Cliente>>();
	}

	public void add(String sistema, Cliente conexao) {
		if (sistemas.containsKey(sistema)) {
			ConcurrentLinkedQueue<Cliente> conexoes = sistemas.get(sistema);
			if (!conexoes.contains(conexao))
				conexoes.add(conexao);
		} else {
			ConcurrentLinkedQueue<Cliente> collection = new ConcurrentLinkedQueue<Cliente>();
			collection.add(conexao);
			sistemas.put(sistema, collection);
		}
	}

	public void remove(WebSocket conexao) {
		Collection<ConcurrentLinkedQueue<Cliente>> sistemas = this.sistemas.values();

		for (ConcurrentLinkedQueue<Cliente> sistema : sistemas)
			for (Cliente cliente : sistema)
				if (conexao.equals(cliente.getConexao()))
					sistema.remove(cliente);
	}

	public void send(Notificacao notificacao) {
		ConcurrentLinkedQueue<Cliente> conexoes = sistemas.get(notificacao.getSistema());

		for (Cliente cliente : conexoes) {
			boolean naoFiltraCliente = notificacao.getUsuario().isEmpty();
			boolean mesmoUsuarioNotificacao = cliente.getUsuario().equals(notificacao.getUsuario());

			if (naoFiltraCliente || mesmoUsuarioNotificacao)
				cliente.envia(notificacao);
		}
	}

	public int sizeSistemas() {
		return sistemas.size();
	}

	public int sizeConexoes() {
		int conexoes = 0;
		for (ConcurrentLinkedQueue<Cliente> sistema : sistemas.values())
			conexoes += sistema.size();
		return conexoes;
	}
}
