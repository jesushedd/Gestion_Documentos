package modelo.persistencia;


import modelo.Documento;
import modelo.Estado;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DocumentoDAO {
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
            throw new RuntimeException("Error al guardar documento en BD",e);
        }
    }

    public List<Documento> obtenerDocumentos() throws DocumentoNoEncontradoException {
        List<Documento> documentos = new ArrayList<>();
        ResultSet resultQuery = null;
        String query = "SELECT * FROM documentos";
        try (Statement statement = ConexionDB.getConexion().createStatement()){
            resultQuery = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("No se pudo obtener los documentos", e);
        }

        try {
            while (resultQuery.next()){
                documentos.add(construirDocumento(resultQuery));
            }

            if (documentos.isEmpty()){
                throw new DocumentoNoEncontradoException("Aún no hay documentos guardados");
            }
            return documentos;
        } catch (SQLException e){
            throw new RuntimeException("Error al procesar el result Set", e);
        }
    }


    public List<Integer> obtenerNumerosExpedientes() throws DocumentoNoEncontradoException {
        List<Integer> expedientes = new ArrayList<>();
        ResultSet resultQuery;
        String query = "SELECT DISTINCT numero_expediente FROM documentos " +
                       "WHERE numero_expediente IS NOT NULL";

        try (Statement statement = ConexionDB.getConexion().createStatement()){
            resultQuery = statement.executeQuery(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener numero de expedientes", e);
        }

        try {
            while (resultQuery.next()){
                expedientes.add(resultQuery.getInt("numero_expediente"));
            }

            if (expedientes.isEmpty()){
                throw new DocumentoNoEncontradoException("Aún no hay documentos guardados con ese numero de expediente");
            }
            return expedientes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Documento construirDocumento(ResultSet resultQuery){
        try (resultQuery){
            int id = resultQuery.getInt(1);
            int numero_expediente = resultQuery.getInt(2);
            LocalDate fecha = resultQuery.getObject(3, LocalDate.class);
            String tipo = resultQuery.getString(4);
            String remitente = resultQuery.getString(5);


            Estado estado = Estado.valueOf(resultQuery.getString(6).toUpperCase());

            return new Documento(id, numero_expediente, fecha, tipo, remitente, estado);

        } catch (SQLException e){
            throw new RuntimeException("Error al construir documento");
        }


    }

    public List<Documento> obtenerDocumentosPorExpediente(int numExpediente) throws DocumentoNoEncontradoException {
        List<Documento> documentos = new ArrayList<>();
        ResultSet resultQuery;
        String query = "SELECT * FROM documentos WHERE numero_expediente = ?";
        try (PreparedStatement statement = ConexionDB.getConexion().prepareStatement(query)){
            statement.setInt(1, numExpediente);
            resultQuery = statement.executeQuery();
        }catch (SQLException e){
            throw new RuntimeException("No se pudo obtener los documentos",e);
        }

        try {
            while (resultQuery.next()){
                documentos.add(construirDocumento(resultQuery));
            }

            if (documentos.isEmpty()){
                throw new DocumentoNoEncontradoException("No existe el número de expediente");
            }
            return documentos;

        }catch (SQLException e){
            throw new RuntimeException("Error al leer los documentos guardados", e);
        }


    }

    public void cambiarEstado(int id, Estado nuevoEstado) throws DocumentoNoEncontradoException {
        String query = "UPDATE documentos SET estado = ? WHERE id = ?";

        try (PreparedStatement statement = ConexionDB.getConexion().prepareStatement(query)){
            statement.setString(1, nuevoEstado.toString());
            statement.setInt(2, id);
            if (statement.executeUpdate() == 0){
                throw new DocumentoNoEncontradoException("No existe el documento con el id: " + id);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al actualizar el estado del documento",e);
        }

    }

    public void eliminarDocumento(int id) throws  DocumentoNoEncontradoException{
        String query = "DELETE FROM documentos WHERE id = ?";
        try (PreparedStatement statement = ConexionDB.getConexion().prepareStatement(query)){
            statement.setInt(1, id);
            if (statement.executeUpdate() == 0){
                throw  new  DocumentoNoEncontradoException("El documento a eliminar no existe");
            }
        }catch (SQLException e){
            throw new RuntimeException("Error al eliminar documento",e);
        }
    }

    public void eliminarExpediente(int numeroExpediente) throws DocumentoNoEncontradoException {
        String query = "DELETE FROM documentos WHERE numero_expediente = ?";

        try (PreparedStatement statement = ConexionDB.getConexion().prepareStatement(query)){
            statement.setInt(1, numeroExpediente);
            if (statement.executeUpdate() == 0){
                throw new  DocumentoNoEncontradoException("No existen documentos con numero expediente: " + numeroExpediente);
            }
        } catch (SQLException e){
            throw new RuntimeException("Error al eliminar documentos por expediente");

        }
    }




    public static void main(String[] args) throws DocumentoNoEncontradoException {
        DocumentoDAO gd = new DocumentoDAO();
        Documento doc = new Documento(Estado.EN_REVISION);


    }


}
