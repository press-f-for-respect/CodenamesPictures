package com.pressfforrespect.codenamespictures.network;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.pressfforrespect.codenamespictures.game.Board;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SpyMasterServer extends AsyncTask {

    private Board board;
    private Gson gson;

    public SpyMasterServer(Board board) {
        this.board = board;
        gson = new Gson();
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            System.out.println("Server Started");
            Socket client = serverSocket.accept();
            InputStream inputStream = client.getInputStream();
            OutputStream outputStream = client.getOutputStream();
//            System.out.println("Server" + inputStream.read());
            String boardJson = gson.toJson(board);
            PrintWriter printWriter = new PrintWriter(outputStream, true);
            printWriter.println(boardJson);
        } catch (IOException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
    }
}
