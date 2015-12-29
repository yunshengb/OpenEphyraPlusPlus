package lucida.server;

// Thrift java libraries 
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;

// Thrift client-side code for registering w/ sirius
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;

// Generated code
import lucida.qastubs.QAService;
import lucida.qastubs.QAServiceHandler;
//import lucida.cmdcenterstubs.CommandCenter;
//import lucida.cmdcenterstubs.MachineData;

/**
 * Starts the question-answer server and listens for requests.
 */
public class QADaemon {
  /** 
   * An object whose methods are implementations of the question-answer thrift
   * interface.
   */
  public static QAServiceHandler handler;

  /**
   * An object responsible for communication between the handler
   * and the server. It decodes serialized data using the input protocol,
   * delegates processing to the handler, and writes the response
   * using the output protocol.
   */
  public static QAService.Processor<QAServiceHandler> processor;

  /** 
   * Entry point for question-answer.
   */
  public static void main(String [] args) {
    try {
      handler = new QAServiceHandler();
      processor = new QAService.Processor(handler);

      Runnable simple = new Runnable() {
        public void run() {
          simple(processor);
        }
      };      
     
      new Thread(simple).start();
    } catch (Exception x) {
      x.printStackTrace();
    }
  }

  /**
   * Listens for requests and forwards request information
   * to handler.
   * @param processor the thrift processor that will handle serialization
   * and communication with the handler once a request is received.
   */
  public static void simple(QAService.Processor processor) {
    try {
      TServerTransport serverTransport = new TServerSocket(9090);
      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

      System.out.println("Starting the simple server...");
      server.serve();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
