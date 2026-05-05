package DOO.LSP.LSP03;

class ProcesadorDeudores extends ProcesadorPedido {

    @Override
    public double procesar(double importe) {
        return importe * 1.20;
    }
}