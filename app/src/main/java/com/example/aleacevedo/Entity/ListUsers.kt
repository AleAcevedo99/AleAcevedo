package com.example.aleacevedo.Entity

class ListUsers{

    fun add(user: EntityUser): Int{
        var answer: Int = -1
        if(!existsEmailFilter(user.email)){
            listUsers.add(user)
            answer = listUsers.size - 1
        }
        return answer
    }

    fun existsEmailFilter(email: String):Boolean{
        return listUsers.filter { it.email == email }.isNotEmpty()
    }

    fun existsUser(user: EntityUser):Int{
        var answer: Int = -1
        for((index,element) in listUsers.withIndex()){
            if(element.email == user.email && element.password == user.password){
                answer = index
                break
            }
        }
        return answer
    }

    companion object{
        private var listUsers= arrayListOf<EntityUser>()
    }
}
