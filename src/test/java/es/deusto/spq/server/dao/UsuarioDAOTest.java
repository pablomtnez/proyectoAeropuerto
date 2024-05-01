package es.deusto.spq.server.dao;

import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.Query;
import javax.jdo.Transaction;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import es.deusto.spq.server.dao.UsuarioDAO;
import es.deusto.spq.server.jdo.Usuario;

public class UsuarioDAOTest {

    private UsuarioDAO usuarioDAO;
    private PersistenceManager pmMock;
    private Transaction txMock;
    private Extent<Usuario> extentMock;

    @Before
    public void setUp() {
        usuarioDAO = new UsuarioDAO();
        pmMock = mock(PersistenceManager.class);
        txMock = mock(Transaction.class);
        extentMock = mock(Extent.class);

        usuarioDAO.pmf = mock(PersistenceManagerFactory.class);
        when(usuarioDAO.pmf.getPersistenceManager()).thenReturn(pmMock);
        when(pmMock.currentTransaction()).thenReturn(txMock);
    }

    @Test
    public void testGetInstance() {
        UsuarioDAO instance1 = UsuarioDAO.getInstance();
        UsuarioDAO instance2 = UsuarioDAO.getInstance();
        
        assertSame(instance1, instance2);
    }

    @Test
    public void testSave() {
        Usuario usuario = new Usuario();

        usuarioDAO.save(usuario);

        verify(pmMock).makePersistent(usuario);
    }

    @Test
    public void testDelete() {
        Usuario usuario = new Usuario();

        usuarioDAO.delete(usuario);

        verify(pmMock).deletePersistent(usuario);
    }

    @Test
    public void testGetAll() {
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario());

        when(pmMock.getExtent(Usuario.class, true)).thenReturn(extentMock);
        when(extentMock.iterator()).thenReturn(usuarios.iterator());

        List<Usuario> result = usuarioDAO.getAll();

        assertEquals(usuarios.size(), result.size());
    }

    @Test
    public void testFind() {
        String email = "test@example.com";
        Usuario usuario = new Usuario();
        usuario.setEmail(email);

        Query queryMock = mock(Query.class);

        when(pmMock.newQuery(Usuario.class)).thenReturn(queryMock);
        when(queryMock.execute(email)).thenReturn(usuario);

        Usuario result = usuarioDAO.find(email);

        assertEquals(usuario, result);
    }
}

