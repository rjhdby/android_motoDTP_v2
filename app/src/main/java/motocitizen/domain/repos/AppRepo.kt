package motocitizen.domain.repos

import motocitizen.domain.model.accident.AccidentPush

interface AppRepo {
    fun saveAccidentPush(push: AccidentPush)
}