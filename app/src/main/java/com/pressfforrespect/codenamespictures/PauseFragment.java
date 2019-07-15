package com.pressfforrespect.codenamespictures;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class PauseFragment extends Fragment {

    private Button goMenu;
    private GameActivity game;

    public PauseFragment(GameActivity game){
        this.game = game;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View fragmentView = inflater.inflate(R.layout.fragment_pause, container, false);

        goMenu = fragmentView.findViewById(R.id.back_menu);
        goMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                game.finishGame();
            }
        });
        return fragmentView;
    }
}
