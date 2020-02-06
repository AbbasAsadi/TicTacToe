package com.example.tic_tac_toe.tic_tac_toe;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.tic_tac_toe.R;
import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class TicTacToeFragment extends Fragment implements View.OnClickListener {

    private static final int YELLOW_CODE = 0;
    private static final int RED_CODE = 1;
    private static final int NOT_PLAYED = 2;
    private static final int NO_WINNER = -1;
    private int activePlayer = RED_CODE;
    private int winner = NO_WINNER;
    private int redPlayerScore = 0;
    private int yellowPlayerScore = 0;
    private LinearLayout bord;
    private ImageView house1;
    private ImageView house2;
    private ImageView house3;
    private ImageView house4;
    private ImageView house5;
    private ImageView house6;
    private ImageView house7;
    private ImageView house8;
    private ImageView house9;
    private ImageView turnImageView;
    private Snackbar snackbar;
    private Button resetButton;
    private TextView scoreRedPlayerTextView;
    private TextView scoreYellowPlayerTextView;

    int[] gameState = {NOT_PLAYED, NOT_PLAYED, NOT_PLAYED,
            NOT_PLAYED, NOT_PLAYED, NOT_PLAYED,
            NOT_PLAYED, NOT_PLAYED, NOT_PLAYED};

    int[][] winningPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};


    public TicTacToeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tic_tac_toe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initUi(view);

        setOnClickUi();

        super.onViewCreated(view, savedInstanceState);

    }

    private void initUi(@NonNull View view) {
        house1 = view.findViewById(R.id.house1_tic_tac_toe);
        house2 = view.findViewById(R.id.house2_tic_tac_toe);
        house3 = view.findViewById(R.id.house3_tic_tac_toe);
        house4 = view.findViewById(R.id.house4_tic_tac_toe);
        house5 = view.findViewById(R.id.house5_tic_tac_toe);
        house6 = view.findViewById(R.id.house6_tic_tac_toe);
        house7 = view.findViewById(R.id.house7_tic_tac_toe);
        house8 = view.findViewById(R.id.house8_tic_tac_toe);
        house9 = view.findViewById(R.id.house9_tic_tac_toe);
        turnImageView = view.findViewById(R.id.turn_image_tic_tac_toe);
        scoreRedPlayerTextView = view.findViewById(R.id.score_red_player_tic_tac_toe);
        scoreYellowPlayerTextView = view.findViewById(R.id.score_yellow_player_tic_tac_toe);
        resetButton = view.findViewById(R.id.reset_button_tic_tac_toe);
        bord = view.findViewById(R.id.bord_tic_tac_toe);
    }

    private void setOnClickUi() {
        house1.setOnClickListener(this);
        house2.setOnClickListener(this);
        house3.setOnClickListener(this);
        house4.setOnClickListener(this);
        house5.setOnClickListener(this);
        house6.setOnClickListener(this);
        house7.setOnClickListener(this);
        house8.setOnClickListener(this);
        house9.setOnClickListener(this);
        resetButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.reset_button_tic_tac_toe) {
            if (snackbar != null) {
                snackbar.dismiss();
            }
            reset(null);

        } else {
            int tag = Integer.parseInt((String) view.getTag());
            if (winner != NO_WINNER || gameState[tag] != NOT_PLAYED) {
                return;
            }
            ImageView bead = (ImageView) view;
            bead.setScaleX(0.2f);
            bead.setScaleY(0.2f);
            if (activePlayer == RED_CODE) {
                bead.setImageResource(R.drawable.red_bead);
                gameState[tag] = RED_CODE;
                activePlayer = YELLOW_CODE;
                turnImageView.setImageResource(R.drawable.yellow_bead);
            } else if (activePlayer == YELLOW_CODE) {
                bead.setImageResource(R.drawable.yellow_bead);
                gameState[tag] = YELLOW_CODE;
                activePlayer = RED_CODE;
                turnImageView.setImageResource(R.drawable.red_bead);
            }
            bead.animate().scaleX(1f).scaleY(1f).setDuration(150);

            //Check Winner
            winnerMsg();
        }
    }

    public void winnerMsg() {
        winner = checkWinner();
        if (winner != NO_WINNER || filled()) {
            if (winner == NO_WINNER) {
                showSnackBar(Color.GRAY, "No Winner");
            } else if (winner == RED_CODE) {
                redPlayerScore++;

                showSnackBar(Color.RED, "Red Player won");
            } else if (winner == YELLOW_CODE) {
                yellowPlayerScore++;
                showSnackBar(Color.YELLOW, "Yellow Player won");
            }
            scoreRedPlayerTextView.setText(redPlayerScore + "");
            scoreYellowPlayerTextView.setText(yellowPlayerScore + "");
        }

    }

    //no winner : -1
    //red : RED_CODE
    //yellow : YELLOW_CODE
    public int checkWinner() {
        for (int[] positions : winningPositions) {
            if (gameState[positions[0]] == gameState[positions[1]] &&
                    gameState[positions[1]] == gameState[positions[2]] &&
                    gameState[positions[0]] != NOT_PLAYED) {
                return gameState[positions[0]];
            }
        }
        return NO_WINNER;
    }

    public Boolean filled() {
        for (int i = 0; i < gameState.length; i++) {
            if (gameState[i] == NOT_PLAYED) {
                return false;
            }
        }
        return true;
    }


    public void reset(View view) {
        //active player
        activePlayer = RED_CODE;
        turnImageView.setImageResource(R.drawable.red_bead);
        //winner
        winner = NO_WINNER;
        //gameState
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = NOT_PLAYED;
        }

        //playground
        for (int i = 0; i < bord.getChildCount(); i++) {
            LinearLayout row = (bord.getChildAt(i) instanceof LinearLayout) ?
                    (LinearLayout) bord.getChildAt(i) : null;
            if (row == null) return;
            for (int j = 0; j < row.getChildCount(); j++) {
                ImageView iv = (row.getChildAt(j) instanceof ImageView) ?
                        (ImageView) row.getChildAt(j) : null;
                if (iv == null) return;
                iv.setImageResource(0);
            }
        }
    }

    private void showSnackBar(int color, String message) {
        bord.setEnabled(false);
        snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("reset", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset(null);
            }
        });
        snackbar.setActionTextColor(color);
        snackbar.show();
    }


}
