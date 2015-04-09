/**
 * Author: Archer Reilly
 * Date: 08/Apr/2015
 * File: RtspClient.java
 * Desc: rtsp client
 *
 * Produced By CSRGXTU
 */
import java.io.IOException;
import java.io.OutputStream;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class RtspClient {
  // rtsp server address
  public String host = null;

  // rtsp server port
  public int port = 0;

  // socket that will be used through the session
  Socket socket = null;

  PrintWriter socketOut = null;

  BufferedReader socketIn = null;

  public RtspClient(String host, int port) {
    this.host = host;
    this.port = port;

    try {
      this.socket = new Socket(host, port);
      this.socketOut = new PrintWriter(
        this.socket.getOutputStream(), true);
      this.socketIn = new BufferedReader(
        new InputStreamReader(this.socket.getInputStream()));
    } catch (IOException e) {
      System.exit(1);
    }
  }

  public void close() {
    try {
      this.socket.close();
    } catch (IOException e) {
      System.exit(1);
    }
  }

  public String options() {
    String options = "OPTIONS rtsp://" + this.host + "/" + this.port
      + " RTSP/1.0\nCSeq: 1\n\n";

    System.out.println(options);

    return null;
  }

  public String describe() {
    return null;
  }

  public String setup() {
    return null;
  }

  public String play() {
    return null;
  }

  public String pause() {
    return null;
  }

  public String record() {
    return null;
  }

  public String announce() {
    return null;
  }

  public String teardown() {
    return null;
  }

  public String getParameter() {
    return null;
  }

  public String setParameter() {
    return null;
  }

  public String redirect() {
    return null;
  }

  public String getHost() {
    return this.host;
  }

  public int getPort() {
    return this.port;
  }

  public void setHost(String host) {
    this.host = host;
  }

  public void setPort(int port) {
    this.port = port;
  }

}
