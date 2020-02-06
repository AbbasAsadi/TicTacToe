package com.example.tic_tac_toe;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.tic_tac_toe.four_in_a_row.FourInRowFragment;
import com.example.tic_tac_toe.tic_tac_toe.TicTacToeFragment;

public class MainActivity extends AppCompatActivity {
    private Button ticTacToeButton;
    private Button fourInRowButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initUi();

        ticTacToeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.tic_tac_toe_fragment);
                if (fragment == null) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.myContainer, new TicTacToeFragment())
                            .commit();

                }
            }
        });

        fourInRowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment fragment = fragmentManager.findFragmentById(R.id.four_in_row_fragment);
                if (fragment == null) {
                    fragmentManager
                            .beginTransaction()
                            .replace(R.id.myContainer, new FourInRowFragment())
                            .commit();
                }
            }
        });
    }


    private void initUi() {
        this.ticTacToeButton = findViewById(R.id.tic_tac_toe_button);
        this.fourInRowButton = findViewById(R.id.four_in_row_button);
    }
}
