package com.kevin.netkick.network.model.fixtures

import com.google.gson.annotations.SerializedName
import com.kevin.netkick.domain.entity.fixtures.ResponseF
import com.kevin.netkick.network.model.fixtures.subfixture.FixtureModel
import com.kevin.netkick.network.model.fixtures.subfixture.StatusModel
import com.kevin.netkick.network.model.fixtures.subfixture.VenueFModel
import com.kevin.netkick.network.model.fixtures.subscore.ScoreModel
import com.kevin.netkick.network.model.fixtures.subscore.SubScoreModel
import com.kevin.netkick.network.model.fixtures.subteams.TeamsFModel
import com.kevin.netkick.network.model.fixtures.subteams.TeamsSubFModel

data class ResponseFModel(
    @SerializedName("fixture")
    val fixture: FixtureModel?,
    @SerializedName("league")
    val league: LeagueFModel?,
    @SerializedName("teams")
    val teams: TeamsFModel?,
    @SerializedName("goals")
    val goals: GoalsFModel?,
    @SerializedName("score")
    val score: ScoreModel?
) {
    companion object {
        fun transformToListEntity(item: List<ResponseFModel?>): List<ResponseF> {
            return item.map {
                transformToEntity(
                    it ?: ResponseFModel(
                        fixture = FixtureModel(
                            0, "", "", "", VenueFModel("", ""), StatusModel("", "")
                        ),
                        league = LeagueFModel(
                            0, "", "", "", 0, ""
                        ),
                        teams = TeamsFModel(
                            TeamsSubFModel(0, "", "", false), TeamsSubFModel(0, "", "", false)
                        ),
                        goals = GoalsFModel(0, 0),
                        score = ScoreModel(SubScoreModel(0, 0), SubScoreModel(0, 0))
                    )
                )
            }
        }

        fun transformToEntity(it: ResponseFModel): ResponseF {
            return ResponseF(
                fixture = FixtureModel.transformToEntity(
                    it.fixture ?: FixtureModel(
                        0,
                        "",
                        "",
                        "",
                        VenueFModel("", ""),
                        StatusModel("", "")
                    )
                ),
                league = LeagueFModel.transformToEntity(
                    it.league ?: LeagueFModel(0, "", "", "", 0, "")
                ),
                teams = TeamsFModel.transformToEntity(
                    it.teams ?: TeamsFModel(
                        TeamsSubFModel(0, "", "", false),
                        TeamsSubFModel(0, "", "", false)
                    )
                ),
                goals = GoalsFModel.transformToEntity(it.goals ?: GoalsFModel(0, 0)),
                score = ScoreModel.transformToEntity(
                    it.score ?: ScoreModel(SubScoreModel(0, 0), SubScoreModel(0, 0))
                )
            )
        }
    }
}
