package com.example.tic_tac_toe.four_in_a_row;


import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.tic_tac_toe.R;
import com.google.android.material.snackbar.Snackbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class FourInRowFragment extends Fragment {

    private int redPlayerScore = 0;
    private int yellowPlayerScore = 0;
    private ImageView[][] cells;
    private View boardView;
    private Board board;
    private ViewHolder viewHolder;
    private static int numberOfRows = 6;
    private static int numberOfColumns = 7;
    private Snackbar snackbar;

    public FourInRowFragment() {
        // Required empty public constructor
    }

    private class ViewHolder {
        public TextView redPlayerWinTextView;
        public TextView yellowPlayerWinTextView;
        public ImageView turnIndicatorImageView;
        public Button resetButton;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_four_in_row, container, false);
        board = new Board(numberOfRows, numberOfColumns);
        viewHolder = new ViewHolder();
        initUi(view);
        buildCells();
        boardView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_POINTER_UP:
                    case MotionEvent.ACTION_UP: {
                        int col = colAtX(motionEvent.getX());
                        if (col != -1)
                            drop(col);
                    }
                }
                return true;
            }
        });

        viewHolder.redPlayerWinTextView.setText("0");
        viewHolder.yellowPlayerWinTextView.setText("0");
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());

        viewHolder.resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (snackbar != null){
                    snackbar.dismiss();
                }
                reset();
            }
        });
        return view;
    }

    private void initUi(View view) {
        boardView = view.findViewById(R.id.game_board_four_in_a_row);
        viewHolder.redPlayerWinTextView = view.findViewById(R.id.score_red_player_four_in_a_row);
        viewHolder.yellowPlayerWinTextView = view.findViewById(R.id.score_yellow_player_four_in_a_row);
        viewHolder.turnIndicatorImageView = view.findViewById(R.id.turn_image_four_in_a_row);
        viewHolder.resetButton = view.findViewById(R.id.reset_button_four_in_a_row);
    }

    private void buildCells() {
        cells = new ImageView[numberOfRows][numberOfColumns];
        for (int r = 0; r < numberOfRows; r++) {
            ViewGroup row = (ViewGroup) ((ViewGroup) boardView).getChildAt(r);
            row.setClipChildren(false);
            for (int c = 0; c < numberOfColumns; c++) {
                ImageView imageView = (ImageView) row.getChildAt(c);
                imageView.setImageResource(android.R.color.transparent);
                cells[r][c] = imageView;
            }
        }
    }

    private void drop(int col) {
        if (board.winCondition)
            return;
        int row = board.lastAvailableRow(col);
        if (row == -1) {
            return;
        }
        final ImageView cell = cells[row][col];
        float move = -(cell.getHeight() * row + cell.getHeight());
        cell.setY(move);
        cell.setImageResource(resourceForTurn());

        TranslateAnimation anim = new TranslateAnimation(0, 0, 0, Math.abs(move));
        anim.setDuration(200);
        anim.setFillAfter(true);
        cell.startAnimation(anim);

        board.occupyCell(row, col);

        if (board.checkForWin()) {
            win();
        } else if (board.noWinner()) {
            showSnackBar(Color.LTGRAY , "No winner!!!");
        } else {
            changeTurn();
        }
    }


    private void win() {
        int color;
        if (board.turn == Board.Turn.RED_PLAYER) {
            color = Color.RED;
            redPlayerScore++;
        } else {
            color = Color.YELLOW;
            yellowPlayerScore++;
        }
        String temp = redPlayerScore + "";
        viewHolder.redPlayerWinTextView.setText(temp);
        temp = yellowPlayerScore + "";
        viewHolder.yellowPlayerWinTextView.setText(temp);
        if (board.turn == Board.Turn.RED_PLAYER) {
            showSnackBar(color , "Red won!!!");
        } else {
            showSnackBar(color , "Yellow won");
        }

    }

    private void changeTurn() {
        board.toggleTurn();
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
    }

    private int colAtX(float x) {
        float colWidth = cells[0][0].getWidth();
        int col = (int) x / (int) colWidth;
        if (col < 0 || col > 6)
            return -1;
        return col;
    }

    private int resourceForTurn() {
        switch (board.turn) {
            case RED_PLAYER:
                return R.drawable.red_bead;
            case YELLOW_PLAYER:
                return R.drawable.yellow_bead;
        }
        return R.drawable.red_bead;
    }

    private void reset() {
        board.reset();
        viewHolder.turnIndicatorImageView.setImageResource(resourceForTurn());
        for (int i = 0; i < numberOfRows; i++) {
            for (int j = 0; j < numberOfColumns; j++) {
                cells[i][j].setImageResource(android.R.color.transparent);
            }
        }
    }

    private void showSnackBar(int color, String message) {
        snackbar = Snackbar.make(getActivity().findViewById(android.R.id.content), message, Snackbar.LENGTH_INDEFINITE);
        snackbar.setAction("reset", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reset();
            }
        });
        snackbar.setActionTextColor(color);
        snackbar.show();
    }

}
