package com.example.service

import com.example.entity.user.User
import com.example.entity.user.by
import com.example.repository.UserRepository
import org.babyfish.jimmer.sql.kt.fetcher.newFetcher
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun queryUserWithRolePermission(userId: Long) =
        userRepository.findUserRolePermissionBy(userId, USER_ROLE_PERMISSION_FETCHER)


    companion object {
        private val USER_ROLE_PERMISSION_FETCHER = newFetcher(User::class).by {
            allScalarFields()
            roles {
                code()
                permissions {
                    code()
                }
            }
        }
    }
}