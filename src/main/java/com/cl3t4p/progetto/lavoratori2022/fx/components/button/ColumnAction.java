package com.cl3t4p.progetto.lavoratori2022.fx.components.button;

/***
 *  Enum for the type of action a columnButton should perform.
 */
public enum ColumnAction {
    /**
     * This action will remove the row from the table and if it is the last row, it will show a error message.
     */
    LAST_STANDING,
    /**
     * This action will remove the row from the table.
     */
    REMOVE,
    /**
     * This action will do nothing.
     */
    NOTHING
}
