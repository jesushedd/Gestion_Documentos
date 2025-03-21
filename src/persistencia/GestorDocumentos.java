package persistencia;


import gestion_documentos.Documento;
import gestion_documentos.Estado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class GestorDocumentos {
    public void agregarDocumento(Documento documentoAgregar){
        String query = "INSERT INTO documentos (numero_expediente, fecha, tipo, remitente, estado)" +
                        "VALUES (?,?,?,?,?)";

        try (PreparedStatement statement = ConexionDB.getConexion().prepareStatement(query)){
            statement.setObject(1,documentoAgregar.getNumeroExpediente());
            statement.setObject(2, documentoAgregar.getFecha());
            statement.setString(3, documentoAgregar.getTipo());
            statement.setString(4, documentoAgregar.getRemitente());
            statement.setObject(5, documentoAgregar.getEstado().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Documento> obtenerDocumentos(){
        List<Documento> documentos = new ArrayList<>();
        ResultSet resultQuery = null;
        String query = "SELECT * FROM documentos";
        try (Statement statement = ConexionDB.getConexion().createStatement()){
            resultQuery = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("No se puedo obtener los documentos", e);
        }

        try {
            while (resultQuery.next()){
                int id = resultQuery.getInt(1);
                int numero_expediente = resultQuery.getInt(2);
                LocalDate fecha = resultQuery.getObject(3, LocalDate.class);
                String tipo = resultQuery.getString(4);
                String remitente = resultQuery.getString(5);


                Estado estado = Estado.valueOf(resultQuery.getString(6).toUpperCase());

                documentos.add(new Documento(id,numero_expediente, fecha,tipo,remitente,estado));

            }
            return documentos;
        } catch (SQLException e){
            throw new RuntimeException("Error al leer los documentos guardados", e);
        }
    }

    public void cambiarEstado(int id, Estado nuevoEstado) throws DocumentoNoEnconctradoException {
        String query = "UPDATE documentos SET estado = ? WHERE id = ?";

        try (PreparedStatement statement = ConexionDB.getConexion().prepareStatement(query)){
            statement.setString(1, nuevoEstado.toString());
            statement.setInt(2, id);
            if (statement.executeUpdate() == 0){
                throw new DocumentoNoEnconctradoException("No existe el documento con el id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el estado del documento",e);
        }

    }




    public static void main(String[] args) {
        GestorDocumentos gd = new GestorDocumentos();

        //gd.agregarDocumento(new Documento(Estado.RECHAZADO));
        //gd.agregarDocumento(new Documento(Estado.EN_REVISION));
    }


}
