package org.cnzd.najboljija.ui.photo_challenge.view_holder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import org.cnzd.najboljija.R
import org.cnzd.najboljija.R2
import org.cnzd.najboljija.common.epoxy.KotlinHolder
import org.cnzd.najboljija.common.utils.toInvisible
import org.cnzd.najboljija.common.utils.toVisible

@EpoxyModelClass(layout = R2.layout.cell_photo_challenge_task)
abstract class PhotoChallengeTaskHolderModel : EpoxyModelWithHolder<PhotoChallengeTaskHolder>(){

    @EpoxyAttribute
    lateinit var task: String
    @EpoxyAttribute
    var completed: Boolean = false
    @EpoxyAttribute
    var enabled: Boolean = false
    @EpoxyAttribute
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: PhotoChallengeTaskHolder) {
        holder.taskText.text = task
        if(enabled){
            holder.questionMark.toInvisible()
            holder.taskText.toVisible()
        }
        if(completed){
            holder.tick.toVisible()
        }
        holder.container.setOnClickListener {
            if(enabled
            ){
                clickListener.onClick(it)
            }
        }
    }

    override fun unbind(holder: PhotoChallengeTaskHolder) {
        holder.tick.toInvisible()
        holder.questionMark.toVisible()
        holder.taskText.toInvisible()
    }
}

class PhotoChallengeTaskHolder : KotlinHolder(){
    val container by bind<ConstraintLayout>(R.id.clContainer)
    val taskText by bind<TextView>(R.id.tvChallengeText)
    val tick by bind<ImageView>(R.id.ivTaskCompletedTick)
    val questionMark by bind<ImageView>(R.id.ivQuestionMark)
}