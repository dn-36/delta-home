package com.module.core.network.chats

import kotlinx.serialization.Serializable


@Serializable
data class ChatResponseMessages(
    val id: Int,
    val name: String,
    val ui: String?,
    val image: String?,
    val text: String?,
    val creater_id: Int,
    val created_at: String,
    val updated_at: String?,
    val project_id: Int?,
    val messages: Messages?,
    val users: List<UserMessages>?,
    val project: ProjectMessages?
)

@Serializable
data class Messages(
    val current_page: Int?,
    val data: List<MessageData>?,
    val first_page_url: String?,
    val from: Int?,
    val last_page: Int?,
    val last_page_url: String?,
    val links: List<Link>?,
    val next_page_url: String?,
    val path: String?,
    val per_page: Int?,
    val prev_page_url: String?,
    val to: Int?,
    val total: Int?
)

@Serializable
data class MessageData(
    val id: Int = 0,
    val chat_id: Int?,
    val user_id: Int?,
    val ui: String?,
    val image: String?,
    val text: String?,
    val created_at: String?,
    val updated_at: String?,
    val feedback_id: Int?,
    val is_send_users: Int?,
    val status_view: Int?,
    val data: Long?,
    val status: Int?,
    val user: UserMessages?,
    val view: View?,
    val files: List<FileData>?,
    val feedback: Feedback?
)

@Serializable
data class Feedback(
    val id: Int?,
    val chat_id: Int?,
    val user_id: Int?,
    val text: String?,
    val image: String?,
    val ui: String?
)

@Serializable
data class FileData(
    val id: Int?,
    val message_id: Int?,
    val url: String?
)

@Serializable
data class UserMessages(
    val name: String?,
    val phone: String?,
    val id: Int?,
    val ui: String?,
    val image: String?
)

@Serializable
data class View(
    val id: Int?,
    val message_id: Int?,
    val user_id: Int?,
    val status: Int?,
    val created_at: String?,
    val updated_at: String?
)

@Serializable
data class Link(
    val url: String?,
    val label: String?,
    val active: Boolean?
)

@Serializable
data class ProjectMessages(
    val id: Int?,
    val name: String?,
    val creater_id: Int?,
    val company_id: Int?,
    val created_at: String?,
    val updated_at: String?,
    val entity_id: Int?,
    val active: Int?,
    val entity_client_id: Int?
)

