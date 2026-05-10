import java.lang.reflect.Array;

public class Task4 {

    public static void main(String[] args) {

        Object intArray = createArray(int.class, 5);
        System.out.println(arrayToString(intArray)); // пустий

        Array.set(intArray, 0, 10);
        Array.set(intArray, 1, 20);
        Array.set(intArray, 2, 30);

        System.out.println(arrayToString(intArray)); // заповнений

        Object newArray = resizeArray(intArray, 8); // збільшений
        System.out.println(arrayToString(newArray));

        Object stringArray = createArray(String.class, 3);
        Array.set(stringArray, 0, "one");
        Array.set(stringArray, 1, "two");

        System.out.println(arrayToString(stringArray)); // строковий

        Object matrix = createMatrix(int.class, 3, 4);

        for (int i = 0; i < 3; i++) {
            Object row = Array.get(matrix, i);

            for (int j = 0; j < 4; j++) {
                Array.set(row, j, i * 10 + j);
            }
        }

        System.out.println(matrixToString(matrix)); // матриця

        Object newMatrix = resizeMatrix(matrix, 4, 6);
        System.out.println(matrixToString(newMatrix)); // збільшена матриця
    }

    public static Object createArray(Class<?> type, int size) {
        return Array.newInstance(type, size);
    }

    public static Object createMatrix(Class<?> type, int rows, int cols) {
        return Array.newInstance(type, rows, cols);
    }

    public static Object resizeArray(Object oldArray, int newSize) {
        Class<?> type = oldArray.getClass().getComponentType();

        int oldSize = Array.getLength(oldArray);

        Object newArray = Array.newInstance(type, newSize);
        System.arraycopy(oldArray, 0, newArray, 0, Math.min(oldSize, newSize));
        return newArray;
    }

    public static Object resizeMatrix(Object oldMatrix, int newRows, int newCols) {
        Class<?> rowType = oldMatrix.getClass().getComponentType();
        Class<?> elementType = rowType.getComponentType();
        int oldRows = Array.getLength(oldMatrix);
        Object newMatrix = Array.newInstance(elementType, newRows, newCols);
        for (int i = 0; i < Math.min(oldRows, newRows); i++) {
            Object oldRow = Array.get(oldMatrix, i);
            Object newRow = Array.get(newMatrix, i);

            int oldCols = Array.getLength(oldRow);
            System.arraycopy(oldRow, 0, newRow, 0, Math.min(oldCols, newCols));
        }
        return newMatrix;
    }

    public static String arrayToString(Object array) {
        StringBuilder sb = new StringBuilder();
        Class<?> type = array.getClass().getComponentType();

        sb.append(type.getName()).append("[").append(Array.getLength(array)).append("] = {");

        for (int i = 0; i < Array.getLength(array); i++) {
            sb.append(Array.get(array, i));
            if (i < Array.getLength(array) - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");

        return sb.toString();
    }

    public static String matrixToString(Object matrix) {
        StringBuilder sb = new StringBuilder();
        Class<?> elementType = matrix.getClass().getComponentType().getComponentType();

        int rows = Array.getLength(matrix);
        int cols = Array.getLength(Array.get(matrix, 0));

        sb.append(elementType.getName()).append("[").append(rows).append("][").append(cols).append("] = {");

        for (int i = 0; i < rows; i++) {
            Object row = Array.get(matrix, i);
            sb.append("{");
            for (int j = 0; j < cols; j++) {
                sb.append(Array.get(row, j));
                if (j < cols - 1) {
                    sb.append(", ");
                }
            }
            sb.append("}");
            if (i < rows - 1) {
                sb.append(", ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}