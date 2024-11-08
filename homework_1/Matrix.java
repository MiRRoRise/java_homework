public class Matrix {
    private final Complex[][] data;
    private final int rows;
    private final int cols;

    public Matrix(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
    }

    public Matrix(int rows, int cols, double val) {
        this.rows = rows;
        this.cols = cols;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                Complex a = new Complex(val, 0);
                this.data[i][j] = a;
            }
        }
    }

    public Matrix(Matrix other) {
        this.rows = other.rows;
        this.cols = other.cols;
        this.data = new Complex[rows][cols];
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] = other.get(i, j);
            }
        }
    }

    public void set(int rows, int cols, Complex val) {
        this.data[rows][cols] = val;
    }

    public Complex get(int rows, int cols) {
        return this.data[rows][cols];
    }

    public int get_rows() {
        return this.rows;
    }

    public int get_cols() {
        return this.cols;
    }

    public Matrix add(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.set(i, j, this.get(i, j).add(other.get(i, j)));
            }
        }
        return result;
    }

    public Matrix subtract(Matrix other) {
        if (this.rows != other.rows || this.cols != other.cols) {
            throw new IllegalArgumentException("Размеры матриц не совпадают");
        }
        Matrix result = new Matrix(this.rows, this.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                result.set(i, j, this.get(i, j).subtract(other.get(i, j)));
            }
        }
        return result;
    }

    public Matrix multiply(Matrix other) {
        if (this.cols != other.rows) {
            throw new IllegalArgumentException("Количество столбцов первой матрицы неравно количеству строк второй матрицы");
        }
        Matrix result = new Matrix(this.rows, other.cols);
        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < other.cols; j++) {
                Complex res = new Complex(0, 0);
                for (int k = 0; k < this.cols; k++) {
                    res = res.add(this.get(i, k).multiply(other.get(k, j)));
                }
                result.set(i, j, res);
            }
        }
        return result;
    }

    public static Matrix Identity(int rows, int cols) {
        Matrix result = new Matrix(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == j) result.set(i, j, new Complex(1, 0));
                else result.set(i, j, new Complex(0, 0));
            }
        }
        return result;
    }

    public static Matrix Inverse(Matrix other) {
        Matrix id = new Matrix(other);
        Matrix result = Matrix.Identity(other.get_rows(), other.get_cols());
        Complex res;
        int val = other.get_rows();
        for (int k = 0; k < val; k++) {
            res = id.get(k, k);
            for (int j = 0; j < val; j++) {
                id.set(k, j, id.get(k, j).divide(res));
                result.set(k, j, result.get(k, j).divide(res));
            }
            for (int i = k + 1; i < val; i++) {
                res = id.get(i, k);
                for (int j = 0; j < val; j++) {
                    id.set(i, j, id.get(i, j).subtract(id.get(k, j).multiply(res)));
                    result.set(i, j, result.get(i, j).subtract(result.get(k, j).multiply(res)));
                }
            }
        }
        for (int k = val - 1; k > 0; k--) {
            for (int i = k - 1; i >= 0; i--) {
                res = id.get(i, k);
                for (int j = 0; j < val; j++) {
                    id.set(i, j, id.get(i, j).subtract(id.get(k, j).multiply(res)));
                    result.set(i, j, result.get(i, j).subtract(result.get(k, j).multiply(res)));
                }
            }
        }
        return result;
    }

    public Matrix divide(Matrix other) {
        if (other.get_rows() != other.get_cols()) {
            throw new IllegalArgumentException("Матрица должна быть квадратной");
        }
        Complex det = determinant(other);
        if (det.equals(new Complex(0, 0))) {
            throw new IllegalArgumentException("Определитель матрицы равен нулю, нахождение обратной невозможно");
        }
        Matrix inverse = Inverse(other);
        Matrix result = new Matrix(this.get_rows(), other.get_cols(), 0);
        for (int i = 0; i < this.get_rows(); i++) {
            for (int j = 0; j < other.get_cols(); j++) {
                for (int k = 0; k < this.get_cols(); k++) {
                    result.set(i, j, result.get(i, j).add(this.get(i, k).multiply(inverse.get(k, j))));
                }
            }
        }
        return result;
    }

    public static Complex determinant(Matrix other) {
        if (other.rows!= other.cols) {
            throw new IllegalArgumentException("Матрица не квадратная");
        }
        Matrix result = new Matrix(other);
        Complex val = new Complex(0, 0);
        Complex res = new Complex(1, 0);
        for (int k = 0; k < other.rows - 1; k++) {
            for (int i = k + 1; i < other.rows; i++) {
                val = result.get(i, k).divide(result.get(k, k)).multiply(new Complex(-1, 0));
                for (int j = 0; j < other.cols; j++) {
                    result.set(i, j, result.get(i, j).add(result.get(k, j).multiply(val)));
                }
            }
        }
        for (int i = 0; i < other.rows; i++) {
            res = res.multiply(result.get(i, i));
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                s.append(data[i][j].toString()).append(" ");
            }
            s.append("\n");
        }
        return s.toString();
    }
}