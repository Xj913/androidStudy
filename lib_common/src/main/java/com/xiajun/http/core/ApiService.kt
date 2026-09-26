package com.example.net

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.xiajun.http.core.NetClient

/** 业务 API 定义（示例，根据实际接口修改） */
object ApiService {

    private const val BASE_URL = "https://api.example.com"

    /** GET 请求示例 */
    suspend fun getUser(userId: String): NetResult<User> {
        val request = NetClient.get(
            "$BASE_URL/user",
            mapOf("id" to userId)
        )
        val type = object : TypeToken<ApiResponse<User>>() {}.type
        return NetRequest.execute(request, type)
    }

    /** POST JSON 示例 */
    suspend fun login(account: String, password: String): NetResult<Token> {
        val json = """{"account":"$account","password":"$password"}"""
        val request = NetClient.postJson("$BASE_URL/login", json)
        val type = object : TypeToken<ApiResponse<Token>>() {}.type
        return NetRequest.execute(request, type)
    }

    /** POST Form 示例 */
    suspend fun uploadForm(name: String, age: Int): NetResult<String> {
        val request = NetClient.postForm(
            "$BASE_URL/upload",
            mapOf("name" to name, "age" to age.toString())
        )
        val type = object : TypeToken<ApiResponse<String>>() {}.type
        return NetRequest.execute(request, type)
    }

    /** PUT 示例 */
    suspend fun updateUser(user: User): NetResult<User> {
        val json = Gson().toJson(user)
        val request = NetClient.putJson("$BASE_URL/user", json)
        val type = object : TypeToken<ApiResponse<User>>() {}.type
        return NetRequest.execute(request, type)
    }

    /** DELETE 示例 */
    suspend fun deleteUser(userId: String): NetResult<String> {
        val request = NetClient.delete("$BASE_URL/user", """{"id":"$userId"}""")
        val type = object : TypeToken<ApiResponse<String>>() {}.type
        return NetRequest.execute(request, type)
    }
}

/** 示例数据模型（根据实际替换） */
data class User(val id: String, val name: String)
data class Token(val token: String, val expiresIn: Long)
