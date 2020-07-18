package org.cnzd.najboljija.ui.photo_challenge.view_model

data class PhotoChallengeTask(var text: String, var completed: Boolean, var enabled: Boolean = false, var taskText:String? = "")

fun providePhotoChallengeUI(response: Map<String, Boolean>): List<PhotoChallengeTask>{
    val list = mutableListOf<PhotoChallengeTask>()
    for(i in response){
        list.add(PhotoChallengeTask(text = i.key, completed =  i.value))
    }
    return list
}