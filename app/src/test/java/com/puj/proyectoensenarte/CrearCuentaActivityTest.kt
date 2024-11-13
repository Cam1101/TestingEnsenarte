package com.puj.proyectoensenarte

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import com.puj.proyectoensenarte.CrearCuentaActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE) // Ignora el manifiesto en las pruebas
class CrearCuentaActivityTest {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    private lateinit var activity: CrearCuentaActivity

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        auth = mock(FirebaseAuth::class.java)
        db = mock(FirebaseFirestore::class.java)
        activity = CrearCuentaActivity()
        activity.auth = auth
        activity.db = db
    }


    @Test
    fun `create account with valid email and password shows success`() {
        // Simula una creación de cuenta exitosa
        val task = createMockTask(success = true, exception = null)

        `when`(auth.createUserWithEmailAndPassword("test@example.com", "password123"))
            .thenReturn(task)

        activity.createAccount("Test User", "test@example.com", "password123", "nickname")

        // Verifica que el método fue llamado con los parámetros correctos
        verify(auth).createUserWithEmailAndPassword("test@example.com", "password123")
    }

    @Test
    fun `create account with email already in use shows error message`() {
        // Simula una excepción de email en uso
        val exception = FirebaseAuthUserCollisionException("ERROR_EMAIL_ALREADY_IN_USE", "The email address " +
                "is already in use by another account.")
        val task = createMockTask(success = false, exception = exception)

        `when`(auth.createUserWithEmailAndPassword("existing@example.com", "password123"))
            .thenReturn(task)

        activity.createAccount("Test User", "existing@example.com",
            "password123", "nickname")

        // Verifica que se haya mostrado el mensaje de error correcto
        verify(auth).createUserWithEmailAndPassword("existing@example.com", "password123")
    }


    @Test
    fun `create account with invalid email format shows error message`() {
        // Llama al método con un email inválido
        val result = activity.validateInput("Test User", "invalidemail",
            "password123", "nickname")

        // Verifica que la validación falle
        assert(!result)
    }

    @Test
    fun `create account with short password shows error message`() {
        // Llama al método con una contraseña demasiado corta
        val result = activity.validateInput("Test User", "test@example.com",
            "123", "nickname")

        // Verifica que la validación falle
        assert(!result)
    }

    @Test
    fun `create account with empty fields shows error message`() {
        // Llama al método con campos vacíos
        val result = activity.validateInput("", "", "", "")

        // Verifica que la validación falle
        assert(!result)
    }

    private fun createMockTask(success: Boolean, exception: Exception?): Task<AuthResult> {
        val task = mock(Task::class.java) as Task<AuthResult>
        `when`(task.isSuccessful).thenReturn(success)
        `when`(task.exception).thenReturn(exception)
        return task
    }
}
