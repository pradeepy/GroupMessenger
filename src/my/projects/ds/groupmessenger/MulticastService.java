package my.projects.ds.groupmessenger;

import java.util.Map;

/**
 * A mutlicast message delivery service.
 */
public interface MulticastService {
	
	/**
	 * Set the underlying unicast service that should be used to
	 * provide the multicast service.
	 * 
	 * @param service
	 */
	void setUnicastService (UnicastService service);
	
	/**
	 * Mutlicasts the message to the given list of hosts.
	 * 
	 * @param message The message to be delivered
	 * @param hosts The map of <Hostname(or IP), port> for all the end hosts. 
	 */
	void multicast (Message message, Map<String, Integer> hosts);

}
