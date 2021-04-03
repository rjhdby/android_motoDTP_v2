package motocitizen.domain.usecases

import motocitizen.domain.model.accident.AccidentPush
import motocitizen.domain.repos.AppRepo
import javax.inject.Inject

class PushUseCase @Inject constructor(
    private val appRepo: AppRepo
) {

    fun saveAccidentPush(accidentPush: AccidentPush) = appRepo.saveAccidentPush(accidentPush)
}