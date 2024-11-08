public class Main {
    public static void main(String[] args) {

        Matrix matrix1 = new Matrix(2, 2);
        matrix1.set(0, 0, new Complex(1, 2));
        matrix1.set(0, 1, new Complex(5, 4));
        matrix1.set(1, 0, new Complex(5, 6));
        matrix1.set(1, 1, new Complex(7, 4));

        Matrix matrix2 = new Matrix(2, 2);
        matrix2.set(0, 0, new Complex(9, 10));
        matrix2.set(0, 1, new Complex(16, 12));
        matrix2.set(1, 0, new Complex(13, 5));
        matrix2.set(1, 1, new Complex(13, 16));

        System.out.println("Матрица 1:");
        System.out.println(matrix1);
        System.out.println("Матрица 2:");
        System.out.println(matrix2);

        System.out.println("Результат сложения:");
        Matrix result = matrix1.add(matrix2);
        System.out.println(result);

        System.out.println("Результат вычитания:");
        result = matrix1.subtract(matrix2);
        System.out.println(result);

        System.out.println("Результат умножения:");
        result = matrix1.multiply(matrix2);
        System.out.println(result);

        System.out.println("Результат деления:");
        result = matrix1.divide(matrix2);
        System.out.println(result);

        System.out.println("Детерминант первой матрицы:");
        Complex res = Matrix.determinant(matrix1);
        System.out.println(res);

    }
}