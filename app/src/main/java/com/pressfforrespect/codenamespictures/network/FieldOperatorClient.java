package com.pressfforrespect.codenamespictures.network;

import com.google.gson.Gson;
import com.pressfforrespect.codenamespictures.FieldOperatorActivity;
import com.pressfforrespect.codenamespictures.game.Board;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class FieldOperatorClient extends Thread {

    private String host;
    private int port;
    private FieldOperatorActivity fieldOperatorActivity;
    Gson gson;

    public FieldOperatorClient(String host, int port, FieldOperatorActivity fieldOperatorActivity) {
        this.host = host;
        this.port = port;
        this.fieldOperatorActivity = fieldOperatorActivity;
        gson = new Gson();
    }

    @Override
    public void run() {
        Socket socket = new Socket();
        try {
            socket.bind(null);
            socket.connect((new InetSocketAddress(host, port)), 5000);
            System.out.println("connected");
            OutputStream outputStream = socket.getOutputStream();
            InputStream inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            Board board = gson.fromJson(reader.readLine(), Board.class);
            System.out.println(board.getPicNums());
            fieldOperatorActivity.setBoard(board);

            fieldOperatorActivity.onReceivedBoard();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
