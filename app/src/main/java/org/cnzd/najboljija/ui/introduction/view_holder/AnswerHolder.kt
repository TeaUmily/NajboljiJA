package org.cnzd.najboljija.ui.introduction.view_holder


import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import com.airbnb.epoxy.EpoxyAttribute
import com.airbnb.epoxy.EpoxyModelClass
import com.airbnb.epoxy.EpoxyModelWithHolder
import org.cnzd.najboljija.R2
import org.cnzd.najboljija.common.epoxy.KotlinHolder
import org.cnzd.najboljija.common.utils.onTextChanged
import org.cnzd.najboljija.common.utils.toInvisible
import org.cnzd.najboljija.common.utils.toVisible


@EpoxyModelClass(layout = R2.layout.cell_introduction_question_answer)
abstract class IntroductionQAModel : EpoxyModelWithHolder<QAndAHolder>() {

    @EpoxyAttribute
    lateinit var questionLabel: String
    @EpoxyAttribute
    lateinit var answer: String
    @EpoxyAttribute(hash = false)
    lateinit var clickListener: View.OnClickListener

    override fun bind(holder: QAndAHolder) {
        holder.questionLabel.text = questionLabel
        holder.answerEditText.setText(answer)
        holder.doneBtn.toInvisible()

        initListeners(holder)

        if (answer.isNotEmpty()) {
            holder.sideLine.background = getDrawable(holder.sideLine.context, org.cnzd.najboljija.R.drawable.epoxy_cell_side_line_green)
        }

        holder.answerEditText.onTextChanged {
            holder.answerEditText.isCursorVisible = true
            if (it.isNotEmpty()) {
                holder.doneBtn.toVisible()
                holder.sideLine.background = getDrawable(holder.sideLine.context, org.cnzd.najboljija.R.drawable.epoxy_cell_side_line_green)
            } else {
                holder.doneBtn.toInvisible()
                holder.sideLine.background = getDrawable(holder.sideLine.context, org.cnzd.najboljija.R.drawable.epoxy_cell_side_line_red)
            }
        }
    }

    private fun initListeners(holder: QAndAHolder) {
        holder.doneBtn.setOnClickListener {
            clickListener.onClick(it)
            holder.doneBtn.toInvisible()
            holder.answerEditText.isCursorVisible = false
            holder.answerEditText.clearFocus()
        }
    }
}


class QAndAHolder : KotlinHolder() {
    val questionLabel by bind<TextView>(org.cnzd.najboljija.R.id.tvQuestionLabel)
    val answerEditText by bind<EditText>(org.cnzd.najboljija.R.id.etAnswer)
    val sideLine by bind<ImageView>(org.cnzd.najboljija.R.id.ivSideLine)
    val doneBtn by bind<ImageView>(org.cnzd.najboljija.R.id.ivSaveAnswer)

}