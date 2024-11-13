package com.puj.proyectoensenarte;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.assertTrue;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@RunWith(AndroidJUnit4.class)
public class FirebaseAuthIntegrationTest {

    private FirebaseAuth auth;

    @Before
    public void setup() {
        auth = FirebaseAuth.getInstance();
    }

    @After
    public void teardown() {
        auth.signOut();
    }

    @Test
    public void createNewAccountSucceeds() throws InterruptedException, ExecutionException, TimeoutException {
        String email = "newuser@example.com";
        String password = "newpassword123";

        Task<AuthResult> resultTask = auth.createUserWithEmailAndPassword(email, password);
        AuthResult result = Tasks.await(resultTask, 30, TimeUnit.SECONDS); // Espera hasta 30s

        assertNotNull(result.getUser());

        // Limpia la cuenta después de la prueba
        if (result.getUser() != null) {
            result.getUser().delete();
        }
    }

    @Test
    public void signInWithValidCredentialsFails() throws InterruptedException,
            ExecutionException, TimeoutException {
        String email = "invalid@example.com";
        String password = "wrongpassword";

        Task<AuthResult> resultTask = auth.signInWithEmailAndPassword(email, password);

        try {
            Tasks.await(resultTask, 30, TimeUnit.SECONDS);
            fail("La autenticación debería haber fallado");
        } catch (ExecutionException e) {
            assertTrue(e.getCause() instanceof FirebaseAuthException);
        }
    }

    @Test
    public void sendPasswordResetEmailSucceeds() throws InterruptedException,
            ExecutionException, TimeoutException {
        String email = "juankparris@gmail.com";

        Task<Void> resultTask = auth.sendPasswordResetEmail(email);
        Tasks.await(resultTask, 30, TimeUnit.SECONDS);
    }


    @Test
    public void signInWithValidCredentialsSucceeds() throws InterruptedException,
            ExecutionException, TimeoutException {
        String email = "juankparris@gmail.com";
        String password = "Camilo.123";

        Task<AuthResult> resultTask = auth.signInWithEmailAndPassword(email, password);
        AuthResult result = Tasks.await(resultTask, 30, TimeUnit.SECONDS);

        // Verifica que el usuario se haya autenticado correctamente
        assertNotNull("La autenticación debería ser exitosa y " +
                "devolver un usuario", result.getUser());
    }


}
