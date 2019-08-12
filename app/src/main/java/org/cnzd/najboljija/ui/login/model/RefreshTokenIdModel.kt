package org.cnzd.najboljija.ui.login.model

data class NewTokenIdRequest(var grant_type: String = "refresh_token", val refresh_token: String )

data class NewTokenIdResponse(var expires_in: String = "",
                              var token_type: String = "",
                              var refresh_token: String = "",
                              var id_token: String = "",
                              var user_id: String = "",
                              var project_id: String = "")