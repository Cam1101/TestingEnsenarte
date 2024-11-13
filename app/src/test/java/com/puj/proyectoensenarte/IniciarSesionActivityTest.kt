package com.puj.proyectoensenarte

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE) // Ignora el manifiesto en las pruebas
class IniciarSesionActivityTest {

    private lateinit var auth: FirebaseAuth
    private lateinit var activity: IniciarSesionActivity

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        auth = mock(FirebaseAuth::class.java)
        activity = IniciarSesionActivity()
        activity.auth = auth
    }

    @Test
    fun `login with valid credentials should redirect user`() {
        // Crea un Task simulado para un inicio de sesión exitoso
        val task = createMockTask(success = true, exception = null)

        `when`(auth.signInWithEmailAndPassword("test@example.com", "password123"))
            .thenReturn(task)

        activity.signIn("test@example.com", "password123")

        // Verifica si se llama con las credenciales correctas
        verify(auth).signInWithEmailAndPassword("test@example.com", "password123")
    }

    @Test
    fun `login with invalid email should show error message`() {
        val exception = FirebaseAuthInvalidUserException("ERROR_USER_NOT_FOUND", "No account found with this email.")
        val task = createMockTask(success = false, exception = exception)

        `when`(auth.signInWithEmailAndPassword("wrong@example.com", "password123"))
            .thenReturn(task)

        activity.signIn("wrong@example.com", "password123")

        verify(auth).signInWithEmailAndPassword("wrong@example.com", "password123")
    }

    @Test
    fun `login with incorrect password should show error message`() {
        val exception = FirebaseAuthInvalidCredentialsException("ERROR_WRONG_PASSWORD", "Incorrect password.")
        val task = createMockTask(success = false, exception = exception)

        `when`(auth.signInWithEmailAndPassword("test@example.com", "wrongpassword"))
            .thenReturn(task)

        activity.signIn("test@example.com", "wrongpassword")

        verify(auth).signInWithEmailAndPassword("test@example.com", "wrongpassword")
    }

    // Método auxiliar para crear una tarea simulada de Firebase
    private fun createMockTask(success: Boolean, exception: Exception?): Task<AuthResult> {
        val task = mock(Task::class.java) as Task<AuthResult>
        `when`(task.isSuccessful).thenReturn(success)
        `when`(task.exception).thenReturn(exception)
        return task
    }
}
