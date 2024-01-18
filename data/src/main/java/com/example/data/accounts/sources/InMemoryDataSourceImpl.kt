package com.example.data.accounts.sources

import com.example.data.accounts.entity.AccountDataEntity
import com.example.data.accounts.exceptions.AccountDoesNotExistException
import com.example.data.accounts.exceptions.IncorrectLoginException
import com.example.data.accounts.exceptions.WrongPasswordException
import java.util.UUID

class InMemoryDataSourceImpl(

) : AccountsDataSource {

    private val accounts = mutableListOf(
        FullAccountDataEntity(
            0,
            "admin",
            "admin",
            "123"
        )
    )

    private val tokens = mutableListOf<Pair<Long, String>>()

    override suspend fun signIn(login: String, password: String): Pair<String, AccountDataEntity> {
        val index = accounts.indexOfFirst {
            it.login == login
        }
        if (index == -1)
            throw IncorrectLoginException()

        if (accounts[index].password == password) {
            val token = UUID.randomUUID().toString()
            tokens.add(Pair(accounts[index].id, token))
            return Pair(token, accounts[index].toAccountDataEntity())
        }
        throw WrongPasswordException()
    }

    override suspend fun getAccount(id: Long): AccountDataEntity {
        val index = accounts.indexOfFirst {
            it.id == id
        }
        if (index == -1)
            throw AccountDoesNotExistException()
        return accounts[index].toAccountDataEntity()
    }

    override suspend fun setAccountUsername(accountWithNewName: AccountDataEntity) {
        val index = accounts.indexOfFirst {
            it.id == accountWithNewName.id
        }
        if (index == -1)
            throw AccountDoesNotExistException()
        accounts[index].name = accountWithNewName.name
    }
}

data class FullAccountDataEntity(
    val id: Long,
    var name: String,
    val login: String,
    val password: String
) {
    fun toAccountDataEntity() = AccountDataEntity (
        id = id,
        name = name,
        login = login
    )
}