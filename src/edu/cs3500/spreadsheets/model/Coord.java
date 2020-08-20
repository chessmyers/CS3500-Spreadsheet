package edu.cs3500.spreadsheets.model;

import java.util.Objects;


/**
 * A value type representing coordinates in a {@link WorksheetModel}.
 */
public class Coord {
  public final int row;
  public final int col;

  /**
   * Constructs a new Coordinate object holding given row and col.
   * @param col the column of the coordinate
   * @param row the row of the coordinate
   */
  public Coord(int col, int row) {
    if (row < 1 || col < 1) {
      throw new IllegalArgumentException("Coordinates should be strictly positive");
    }
    this.row = row;
    this.col = col;
  }

  /**
   * Converts from the A-Z column naming system to a 1-indexed numeric value.
   * @param name the column name
   * @return the corresponding column index
   */
  public static int colNameToIndex(String name) {
    name = name.toUpperCase();
    int ans = 0;
    for (int i = 0; i < name.length(); i++) {
      ans *= 26;
      ans += (name.charAt(i) - 'A' + 1);
    }
    return ans;
  }

  /**
   * Converts a 1-based column index into the A-Z column naming system.
   * @param index the column index
   * @return the corresponding column name
   */
  public static String colIndexToName(int index) {
    StringBuilder ans = new StringBuilder();
    while (index > 0) {
      int colNum = (index - 1) % 26;
      ans.insert(0, Character.toChars('A' + colNum));
      index = (index - colNum) / 26;
    }
    return ans.toString();
  }

  /**
   * Converts a cell name (such as P43) to a {@link Coord}.
   * @param cellName the name of the cell (ex B12)
   * @return a {@link Coord} representing the given cell
   */
  public static Coord coordStringToCoord(String cellName) {
    if (cellName == null) {
      throw new IllegalArgumentException("Null string given.");
    }
    if (!cellName.matches("^[a-zA-Z0-9]*$")) {
      throw new IllegalArgumentException("Malformed symbol given");
    }
    int row;
    int col;
    String[] colLetter = cellName.split("[1-9]");
    col = Coord.colNameToIndex(colLetter[0]);
    try {
      String[] rowLetter = cellName.split("[a-z+A-Z+]");
      row = Integer.parseInt(rowLetter[rowLetter.length - 1]);
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid row given");
    }

    return new Coord(col, row);
  }

  @Override
  public String toString() {
    return colIndexToName(this.col) + this.row;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return row == coord.row
        && col == coord.col;
  }

  @Override
  public int hashCode() {
    return Objects.hash(col, row);
  }
}
