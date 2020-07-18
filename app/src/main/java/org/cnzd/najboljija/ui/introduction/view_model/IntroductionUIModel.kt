package org.cnzd.najboljija.ui.introduction.view_model

data class IntroductionAnswersResponse(
        val hobiji: String,
        val idealno_zanimanje: String,
        val ljubimci: String,
        val moji_uzori: String,
        val nadimak: String,
        val najdraze_jelo: String,
        val najdraze_sjecanje: String,
        val najdrazi_skolski_predmet: String,
        val najdrazi_sport: String,
        val nisam_nikad: String,
        val omiljena_boja: String,
        val osobina_koju_volim_kod_sebe: String,
        val slobodno_vrijeme_provodim: String,
        val tv_izbor: String
)

fun provideAnswersListFromData(response: IntroductionAnswersResponse): MutableList<String> {
    val list = mutableListOf<String>()
    list.apply {
        add(response.hobiji)
        add(response.idealno_zanimanje)
        add(response.ljubimci)
        add(response.moji_uzori)
        add(response.nadimak)
        add(response.najdraze_jelo)
        add(response.najdraze_sjecanje)
        add(response.najdrazi_skolski_predmet)
        add(response.najdrazi_sport)
        add(response.nisam_nikad)
        add(response.omiljena_boja)
        add(response.osobina_koju_volim_kod_sebe)
        add(response.slobodno_vrijeme_provodim)
        add(response.tv_izbor)
    }
    return list
}

fun provideAnswersDataFromList(list: MutableList<String>)
        = IntroductionAnswersResponse(list[0], list[1], list[2], list[3], list[4], list[5], list[6], list[7], list[8], list[9], list[10], list[11], list[12], list[13])