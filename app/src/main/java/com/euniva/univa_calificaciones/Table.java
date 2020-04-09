package com.euniva.univa_calificaciones;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.ArrayList;

public class Table {
    private TableLayout tableLayout;
    private Context context;
    private String[] header;
    public ArrayList<String[]> data;
    private TableRow tableRow;
    private TextView txtCell;
    private int indexC, indexR;
    private boolean multiColor = false;
    int firstColor, secondColor;

    public Table(TableLayout tableLayout, Context context) {
        this.tableLayout = tableLayout;
        this.context = context;
    }

    //Recibimos un arreglo para insertar el encabezado de la tabla
    public void addHeader(String[] header) {
        this.header = header;
        createHeader();
    }

    //Recibimos los datos que se van a insertar a la tabla
    public void addData(ArrayList<String[]> data) {
        this.data = data;
        createDataTable();
    }

    //Crear las filas
    private void newRow() {
        tableRow = new TableRow(context);
    }

    //Crear las columnas
    private void newCell() {
        txtCell = new TextView(context);
        txtCell.setGravity(Gravity.CENTER);
        txtCell.setTextSize(18);
    }

    //Agregar los datos dentro de nuestro header
    private void createHeader() {
        indexC = 0; //Inicializamos la fila en la posición 0
        newRow(); //Llamamos el método para crear una nueva fila
        while (indexC < header.length) { //Obtenemos el total de datos que se encuentran dentro del arreglo
            newCell(); //Llamamos el método para crear una columna
            txtCell.setText(header[indexC++]);//Agregar el texto y sumamos para que vaya recorriendo el arreglo
            tableRow.addView(txtCell, newTableRowParams());//Agregamos la celda y los parametros para las lineas de separación
        }
        tableLayout.addView(tableRow); //Agregamos la fila
    }

    private void createDataTable() {
        int datos = data.size();
        String info;
        if (datos <= 5) {
            for (indexR = 1; indexR <= data.size(); indexR++) { //Recorrer las filas y se inicializa en 1 porque la fila 0 es el encabezado
                newRow();
                for (indexC = 0; indexC < header.length; indexC++) {
                    newCell();
                    String[] row = data.get(indexR - 1);
                    info = (indexC < row.length) ? row[indexC] : ""; //Hacemos una comparación para verificar que sean los mismo valores en filas y columnas y sino lo son que se agreguen campo vacio
                    txtCell.setText(info);
                    tableRow.addView(txtCell, newTableRowParams());
                }
                tableLayout.addView(tableRow);
            }
        } else {
            for (indexR = 1; indexR <= 5; indexR++) { //Recorrer las filas y se inicializa en 1 porque la fila 0 es el encabezado
                newRow();
                for (indexC = 0; indexC < header.length; indexC++) {
                    newCell();
                    String[] row = data.get(indexR - 1);
                    info = (indexC < row.length) ? row[indexC] : ""; //Hacemos una comparación para verificar que sean los mismo valores en filas y columnas y sino lo son que se agreguen campo vacio
                    txtCell.setText(info);
                    tableRow.addView(txtCell, newTableRowParams());
                }
                tableLayout.addView(tableRow);
            }
        }
    }

    //Crear las líneas de separación en la tabla
    private TableRow.LayoutParams newTableRowParams() {
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.setMargins(1, 1, 1, 1);
        params.weight = 1;
        return params;
    }

    //Agregar una nueva fila de información
    public void addItems(String[] item) {
        String info;
        data.add(item);
        indexC = 0;
        newRow();
        while (indexC < header.length) {
            newCell();
            info = (indexC < item.length) ? item[indexC++] : "";
            txtCell.setText(info);
            tableRow.addView(txtCell, newTableRowParams());
        }
        tableLayout.addView(tableRow, data.size() - 1);

        reColoring();
    }

    public void backgroundHeader(int color) {
        indexC = 0;
        while (indexC < header.length) {
            txtCell = getCell(0, indexC++);
            txtCell.setBackgroundColor(color);
            txtCell.setTextColor(Color.WHITE);
            txtCell.setTypeface(Typeface.DEFAULT_BOLD);
        }
    }

    public void backgroundData(int firstColor, int secondColor) {
        for (indexR = 1; indexR <= header.length; indexR++) {
            multiColor = !multiColor;
            for (indexC = 0; indexC < header.length; indexC++) {
                txtCell = getCell(indexR, indexC);
                txtCell.setBackgroundColor((multiColor) ? firstColor : secondColor);
            }
        }
        this.firstColor=firstColor;
        this.secondColor=secondColor;
    }
    public void reColoring() {
        indexC = 0;
        multiColor=!multiColor;
        while (indexC < header.length) {
            txtCell = getCell(data.size()-1, indexC++);
            txtCell.setBackgroundColor((multiColor) ? firstColor : secondColor);

        }
    }

    //Encontrar una fila para cambiarle el color
    private TableRow getRow(int index) {
        return (TableRow) tableLayout.getChildAt(index);
    }

    //Encontrar la celda para cambiarle el color
    private TextView getCell(int rowIndex, int columIndex) {
        tableRow = getRow(rowIndex);
        return (TextView) tableRow.getChildAt(columIndex);
    }
}
