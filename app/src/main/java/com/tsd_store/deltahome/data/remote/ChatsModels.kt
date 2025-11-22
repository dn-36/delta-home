package com.tsd_store.deltahome.data.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ChatsResponse(
    val name: String? = null,
    val ui: String? = null,
    val count_new_message: Int? = null,
    val image: String? = null,
    val users: List<User>? = listOf(),
    val project: Project? = null,
    val lastMessageUi: String? = null,
    val user_ui: String? = null,
    val user_image: String? = null,
    val message: String? = null,
    val data: Long? = null,
    val created_at: String? = null
)

@Serializable
data class User(
    val phone: String? = null,
    val name: String? = null,
    val ui: String? = null,
    val active: Int? = null,
    val image: String? = null,
    val id: Int? = null,
    val laravel_through_key: Int? = null
)

@Serializable
data class Project(
    val id: Int? = null,
    val name: String? = null,
    val creater_id: Int? = null,
    val company_id: Int? = null,
    val created_at: String? = null,
    val updated_at: String? = null,
    val entity_id: Int? = null,
    val active: Int? = null,
    val entity_client_id: Int? = null
)


@Serializable
data class NewMessageDto(
    @SerialName("chat_ui")
    val chatUi: String,
    @SerialName("feedback_ui")
    val feedbackUi: String,
    @SerialName("text")
    val text: String? = null,
    @SerialName("image")
    val image: String? = null, // base64-encoded image
    @SerialName("filesmobile")
    val files: List<FileItem>? = null
)

@Serializable
data class FileItem(
    @SerialName("data")
    val file: String,  // base64-encoded file
    @SerialName("type")
    val type:String
)