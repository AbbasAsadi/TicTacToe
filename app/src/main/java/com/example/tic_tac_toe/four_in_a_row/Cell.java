package com.example.tic_tac_toe.four_in_a_row;

import java.io.Serializable;

public class Cell implements Serializable {
  public boolean empty;
  public Board.Turn player;

  public Cell() {
    empty = true;
  }

  public void setPlayer(Board.Turn player) {
    this.player = player;
    empty = false;
  }

}
