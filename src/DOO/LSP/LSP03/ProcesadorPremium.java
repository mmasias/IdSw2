package DOO.LSP.LSP03;

class ProcesadorPremium extends ProcesadorPedido {

    @Override
    public double procesar(double importe) {
        return importe * 0.60;
    }
}