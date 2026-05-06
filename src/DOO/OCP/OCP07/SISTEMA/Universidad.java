package DOO.OCP.OCP07.SISTEMA;

public class Universidad {
    public void procesarSolicitudBeca(Alumno alumno, EvaluadorBecas evaluador) {
        alumno.solicitarBeca(evaluador);
    }
}
