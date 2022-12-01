package com.gangoffour2.monopoly;

import com.gangoffour2.monopoly.squares.Square;

@FunctionalInterface
public interface SquarePredicate {
    boolean exec(Square square);
}
