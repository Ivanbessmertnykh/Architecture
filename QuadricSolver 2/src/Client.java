import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private Client() {}

    public static void main(String[] args) {
        try {

            //Достаем реестр
            Registry registry = LocateRegistry.getRegistry(null);

            //ищем в реестре удаленный объект (созданный на сервере) Solver
            Solver stub = (Solver) registry.lookup("Solver");

            //Решаем квадратное уравнение
            System.out.println(stub.solve());
        } catch (RemoteException | NotBoundException e) {

            //Вывод ошибок
            System.err.println("Произошла ошибка ".concat(e.getMessage()));
            e.printStackTrace();
        }
    }
}
