/**
 * Author: Archer Reilly
 * Date: 08/Apr/2015
 * File: Client.java
 * Desc: the main for rtsp client
 *
 * Produced By CSRGXTU.
 */
public class Client {
  public static void main(String args[]) {
    String host = "192.168.10.93";
    int port = 1234;
    RtspClient client = new RtspClient(host, port);
    client.options();
    client.close();
  }
}
