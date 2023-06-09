package com.kevin.netkick.domain.entity.fixtures

import com.kevin.netkick.domain.entity.fixtures.subfixture.Fixture
import com.kevin.netkick.domain.entity.fixtures.subscore.Score
import com.kevin.netkick.domain.entity.fixtures.subteams.TeamsF


data class ResponseF(
    val fixture: Fixture,
    val league: LeagueF,
    val teams: TeamsF,
    val goals: GoalsF,
    val score: Score
)
