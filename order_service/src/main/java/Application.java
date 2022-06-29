import org.moran.api.OrderInterface;
import org.moran.api.impl.OrderInterfaceImpl;
import org.moran.util.Context;
import org.moran.util.Server;
import org.moran.util.handler.Dispatcher;

public class Application {

	public static void main(String[] args) {
		Dispatcher dispatcher = new Dispatcher();
		dispatcher.register(OrderInterface.class,"orderInterfaceImpl",new OrderInterfaceImpl());
		Server server = new Server(new Context(dispatcher,9090));
		server.runServer();
	}
}
