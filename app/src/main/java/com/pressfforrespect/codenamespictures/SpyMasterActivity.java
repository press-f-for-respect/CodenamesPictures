package com.pressfforrespect.codenamespictures;

import android.os.Bundle;

import com.pressfforrespect.codenamespictures.game.Board;

public class SpyMasterActivity extends GameActivity{

    private Board board;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        board = new Board();
        super.changeColor(board.getStarter());

    }

    @Override
    void gamePause() {

    }

    @Override
    void endTurn() {

    }
}
