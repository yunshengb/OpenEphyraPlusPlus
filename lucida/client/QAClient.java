package lucida.client;

// Thrift client-side code
import lucida.qastubs.QAService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/** This is a question-answering client that make requests.
 */
public class QAClient {
  public static void main(String [] args) {
    try {
      TTransport transport;
     
      transport = new TSocket("localhost", 9090);
      transport.open();

      TProtocol protocol = new TBinaryProtocol(transport);
      QAService.Client client = new QAService.Client(protocol); // create the client

      perform(client);

      transport.close();
    } catch (TException x) {
      x.printStackTrace();
    } 
  }

  /**
   * Make requests.
   * @param client the thrift client.
   */
  private static void perform(QAService.Client client) throws TException
  {
  	// ask OE a question and print the answer
    String result = client.askFactoidThrift("What is the speed of light?");
    System.out.println("OE returns the answer: " + result);
  }
}
