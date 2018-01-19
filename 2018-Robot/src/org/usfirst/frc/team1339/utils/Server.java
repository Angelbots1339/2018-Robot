package org.usfirst.frc.team1339.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import org.json.simple.JSONObject;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

	private int port;
	private String modesJSON;
	
	public AutonomousModeSelector autonomousSelector;
	public ValueDisplay valueDisplay;
	

	public Server(int port){
		this.port = port;
		autonomousSelector = new AutonomousModeSelector();
		valueDisplay = new ValueDisplay();
	}

	public boolean start(){
		JSONObject obj = new JSONObject();
		String[] modes = autonomousSelector.getNames();
		for(int i = 0; i < modes.length; i++){
			obj.put(i, modes[i]);
		}
		modesJSON = obj.toJSONString();
		
		HttpServer server;
		boolean success = false;
		try {
			server = HttpServer.create(new InetSocketAddress(port), 0);
			server.createContext("/", new RootHandler());
			server.createContext("/setMode", new PostModeHandler());
			server.createContext("/getModes", new GetModesHandler());
			server.createContext("/getValues", new GetValuesHandler());
			server.start();

			success = true;
			System.out.println("Server starting...");
		} catch (IOException e) {
			System.out.println("Server start failure.");
		}
		return success;
	}

	class RootHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			String path = t.getRequestURI().toString();
			File file;
			if(path.equals("/"))
				path += "index.html";
			file = new File("/home/lvuser/web_resources" + path);
			Headers h = t.getResponseHeaders();
			h.set("Content-Type", "text/html");

			t.sendResponseHeaders(200, 0);
			OutputStream os = t.getResponseBody();
			FileInputStream fs = new FileInputStream(file);
			final byte[] buffer = new byte[0x10000];
			int count = 0;
			while ((count = fs.read(buffer)) >= 0) {
				os.write(buffer,0,count);
			}
			fs.close();
			os.close();
		}
	}

	class GetModesHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			t.sendResponseHeaders(200, modesJSON.length());
			OutputStream os = t.getResponseBody();
			os.write(modesJSON.getBytes());
			os.close();
		}
	}
	
	class GetValuesHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			String values = valueDisplay.getJSON();
			t.sendResponseHeaders(200, values.length());
			OutputStream os = t.getResponseBody();
			os.write(values.getBytes());
			os.close();
		}
	}

	class PostModeHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange t) throws IOException {
			//Reading data
			int auto = -1;
			String response = "Failure to set auto...";
			BufferedReader in = new BufferedReader(new InputStreamReader(t.getRequestBody()));
			String line = "";
			while((line = in.readLine()) != null){
				if(line.contains("auto")){
					auto = Integer.parseInt(line.substring(line.indexOf("=") + 1));
				}
			}
			in.close();
			
			if(auto != -1){
				autonomousSelector.setCurrentMode(auto);
				response = "Success setting auto!";
			}
			
			t.sendResponseHeaders(200, response.length());
			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}
	}
}
