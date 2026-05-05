package DOO.LSP.LSP03;

public class Cliente {

    static double cerrarCaja(ProcesadorPedido procesador, double[] pedidos) {
        double total = 0;
        for (double importe : pedidos) {
            total += procesador.procesar(importe);
        }
        return total;
    }

    public static void main(String[] args) {
        double[] pedidos = { 100.0, 200.0, 50.0, 300.0 };

        double totalBase = cerrarCaja(new ProcesadorPedido(), pedidos);
        double totalPremium = cerrarCaja(new ProcesadorPremium(), pedidos);
        double totalDeudores = cerrarCaja(new ProcesadorDeudores(), pedidos);
        double totalMayorista = cerrarCaja(new ProcesadorMayorista(), pedidos);
        double totalPromocion = cerrarCaja(new ProcesadorPromocion(), pedidos);

        System.out.println("Cierre de caja");
        System.out.println("Base:       " + (int) totalBase + " EUR");
        System.out.println("Premium:    " + (int) totalPremium + " EUR");
        System.out.println("Deudores:   " + (int) totalDeudores + " EUR");
        System.out.println("Mayorista:  " + (int) totalMayorista + " EUR");
        System.out.println("Promocion:  " + (int) totalPromocion + " EUR");
        System.out.println();
        System.out.println("Caja cerrada. Buen dia.");
    }
}
