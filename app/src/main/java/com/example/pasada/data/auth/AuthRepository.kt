package com.example.pasada.data.auth

import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.providers.builtin.Email
import io.github.jan.supabase.auth.status.SessionStatus
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val sessionStatus: Flow<SessionStatus>
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(email: String, password: String): Result<Unit>
    suspend fun signOut(): Result<Unit>
}

class AuthRepositoryImpl(
    private val client: SupabaseClient
) : AuthRepository {

    override val sessionStatus: Flow<SessionStatus>
        get() = client.auth.sessionStatus

    override suspend fun signIn(email: String, password: String): Result<Unit> {
        return runCatching {
            client.auth.signInWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    override suspend fun signUp(email: String, password: String): Result<Unit> {
        return runCatching {
            client.auth.signUpWith(Email) {
                this.email = email
                this.password = password
            }
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return runCatching {
            client.auth.signOut()
        }
    }
}
