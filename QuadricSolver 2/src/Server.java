import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server extends SolverImpl {

    public Server() {}

    public static void main(String[] args) {
        try {

            //Создаем объект класса, отвечающего за решение квадратного уравнения
            SolverImpl solver = new SolverImpl();

            //Сохраняем значения коэффициентов, переданных в качестве параметров при вызове сервера
            //Пример параметра: -DaCoeff={значение коэфф. a}
            solver.saveCoeffs(
                    Double.parseDouble(System.getProperty("aCoeff")),
                    Double.parseDouble(System.getProperty("bCoeff")),
                    Double.parseDouble(System.getProperty("cCoeff"))
            );

            //Экспортируем интерфейс solver (SolverImpl имплементируется от Solver) в процесс сервера
            Solver stub = (Solver) UnicastRemoteObject.exportObject(solver, 0);

            //Достаем реестр
            Registry registry = LocateRegistry.getRegistry();

            //Значению "Solver" сопоставляем интерфейс Solver
            registry.bind("Solver", stub);
        } catch (Exception e) {

            //Вывод ошибки
            System.err.println("Произошла ошибка ".concat(e.getMessage()));
            e.printStackTrace();
        }
    }
}
