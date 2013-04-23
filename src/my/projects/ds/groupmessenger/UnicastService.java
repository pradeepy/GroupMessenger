package my.projects.ds.groupmessenger;

/**
 * A unicast message delivery service.
 */
public interface UnicastService {
	
	/**
	 * Delivers the message to the given host.
	 * 
	 * @param message
	 * @param host
	 * @param port
	 */
	void send (Message message, String host, int port);

}
