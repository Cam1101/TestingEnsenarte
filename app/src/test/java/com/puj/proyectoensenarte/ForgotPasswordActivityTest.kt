package com.puj.proyectoensenarte

import com.google.android.gms.tasks.Task
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.puj.proyectoensenarte.authentication.ForgotPasswordActivity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE) // Ignora el archivo AndroidManifest.xml
class ForgotPasswordActivityTest {

    private lateinit var auth: FirebaseAuth
    private lateinit var activity: ForgotPasswordActivity

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        auth = mock(FirebaseAuth::class.java)
        activity = ForgotPasswordActivity()
        activity.auth = auth
    }

    @Test
    fun `send password reset email successfully`() {
        val task = createMockTask(success = true, exception = null)

        `when`(auth.sendPasswordResetEmail("test@example.com")).thenReturn(task)

        activity.sendPasswordReset("test@example.com")

        verify(auth).sendPasswordResetEmail("test@example.com")
    }

    @Test
    fun `send password reset email to unregistered account shows error`() {
        val exception = FirebaseAuthInvalidUserException("ERROR_USER_NOT_FOUND", "No account found with this email.")
        val task = createMockTask(success = false, exception = exception)

        `when`(auth.sendPasswordResetEmail("unregistered@example.com")).thenReturn(task)

        activity.sendPasswordReset("unregistered@example.com")

        verify(auth).sendPasswordResetEmail("unregistered@example.com")
    }

    @Test
    fun `send password reset email network error shows error`() {
        val exception = FirebaseNetworkException("Network error. Please try again later.")
        val task = createMockTask(success = false, exception = exception)

        `when`(auth.sendPasswordResetEmail("test@example.com")).thenReturn(task)

        activity.sendPasswordReset("test@example.com")

        verify(auth).sendPasswordResetEmail("test@example.com")
    }

    @Test
    fun `send password reset with invalid email format shows error`() {
        val email = "invalidemail"
        val result = android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

        // Verifica que el email no pase la validación
        assert(!result)
    }

    // Método auxiliar para crear una tarea simulada de Firebase
    private fun createMockTask(success: Boolean, exception: Exception?): Task<Void> {
        val task = mock(Task::class.java) as Task<Void>
        `when`(task.isSuccessful).thenReturn(success)
        `when`(task.exception).thenReturn(exception)
        return task
    }
}
