import java.rmi.Remote;
import java.rmi.RemoteException;

//интерфейс удаленного объекта
public interface Solver extends Remote {

    String solve() throws RemoteException;

    void saveCoeffs(double a, double b, double c) throws RemoteException;
}
