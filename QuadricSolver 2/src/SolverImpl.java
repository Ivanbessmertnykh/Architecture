import java.rmi.RemoteException;
import java.text.DecimalFormat;

//Реализация интерфейса Solver
public class SolverImpl implements Solver {

    private final static DecimalFormat RESULT_FORMAT = new DecimalFormat("#0.000000000");

    private double a = 0;
    private double b = 0;
    private double c = 0;

    //Сохраняем коэффициенты квадратного уравнения
    @Override
    public void saveCoeffs(double a, double b, double c) throws RemoteException {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    //Метод решения уравнения (здесь проверяются случаи, когда уравнение не является квадратным)
    //Если условия не выполняются, то решаем квадратное уравнение
    @Override
    public String solve() throws RemoteException {
        if (a == 0 && b == 0) {
            return "Equation does not exist";
        }

        if (a == 0 && b != 0 && c != 0) {
            double x = -1 * c / b;
            return RESULT_FORMAT.format(x);
        }

        return solveQuadraticEquation();
    }

    private String solveQuadraticEquation() {
        double d = b * b - 4 * a * c;
        if (d > 0) { //дискриминант > 0 - два корня
            double x1 = (-1 * b + Math.sqrt(d)) / (2 * a);
            double x2 = (-1 * b - Math.sqrt(d)) / (2 * a);
            return "X1 = ".concat(RESULT_FORMAT.format(x1)).concat("\nX2 = ".concat(RESULT_FORMAT.format(x2)));
        } else if (d == 0) { // дискриминант = 0 - один корень
            double x = (-1 * b) / 2 * a;
            return "X = ".concat(RESULT_FORMAT.format(x));
        } else { // дискриминант < 0 - два мнимых корня
            double realPart = (-1 * b) / 2 * a; // действительная часть решения
            double imPart = Math.sqrt(-1 * d) / (2 * a); //мнимая часть решения
            return "X1 = ".concat(RESULT_FORMAT.format(realPart))
                    .concat("+")
                    .concat(RESULT_FORMAT.format(imPart))
                    .concat("i")
                    .concat("\nX2 = ".concat(RESULT_FORMAT.format(realPart))
                            .concat("-")
                            .concat(RESULT_FORMAT.format(imPart))
                            .concat("i"));
        }
    }
}
