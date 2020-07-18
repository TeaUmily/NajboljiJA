package org.cnzd.najboljija.ui.login.model



data class LoginRequest(var email: String = "",
                        var password: String = "",
                        var returnSecureToken: Boolean = true)

data class LoginResponse(val localId: String = "",
                         val email: String = "",
                         val displayName: String = "",
                         val idToken: String = "",
                         val registered: Boolean = false,
                         val refreshToken: String = "",
                         val expiresIn: String = ""
)

