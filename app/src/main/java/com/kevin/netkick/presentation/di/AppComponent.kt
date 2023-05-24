package com.kevin.netkick.presentation.di

import com.kevin.netkick.network.di.DataComponent
import com.kevin.netkick.presentation.view.explore.activity.FixturesActivity
import com.kevin.netkick.presentation.view.explore.activity.FixturesDetailActivity
import com.kevin.netkick.presentation.view.explore.activity.LeagueStandingsActivity
import com.kevin.netkick.presentation.view.general.activity.AllPlayerInTeamsActivity
import com.kevin.netkick.presentation.view.explore.activity.LeagueSearchActivity
import com.kevin.netkick.presentation.view.general.activity.TeamDetailActivity
import com.kevin.netkick.presentation.view.home.news.activity.AllNewsListActivity
import com.kevin.netkick.presentation.view.home.news.activity.ArticleActivity
import com.kevin.netkick.presentation.view.home.popularteams.activity.PopularTeamsListActivity
import com.kevin.netkick.presentation.view.main.activity.MainActivity
import com.kevin.netkick.presentation.view.trophies.activity.coach.CoachDetailActivity
import com.kevin.netkick.presentation.view.trophies.activity.coach.CoachSearchActivity
import com.kevin.netkick.presentation.view.trophies.activity.players.PlayersAchievementActivity
import com.kevin.netkick.presentation.view.trophies.activity.players.PlayersSearchActivity
import com.kevin.netkick.presentation.view.trophies.activity.players.TeamsSearchActivity
import com.kevin.netkick.presentation.view.trophies.activity.topscorer.LeagueTopScorerActivity
import com.kevin.netkick.presentation.view.trophies.activity.topscorer.LeagueTopScorerSearchActivity
import dagger.Component

@AppScope
@Component(
  dependencies = [DataComponent::class],
  modules = [UseCaseModule::class]
)
interface AppComponent {

  @Component.Factory
  interface Factory {
    fun create(dataComponent: DataComponent): AppComponent
  }

  fun injectIntoMainActivity(mainActivity: MainActivity)
  fun injectIntoPopularTeamsListActivity(popularTeamsListActivity: PopularTeamsListActivity)
  fun injectIntoAllNewsListActivity(allNewsListActivity: AllNewsListActivity)
  fun injectIntoArticleActivity(articleActivity: ArticleActivity)
  fun injectIntoTeamDetailActivity(teamDetailActivity: TeamDetailActivity)
  fun injectIntoAllPlayerInTeamsActivity(allPlayerInTeamsActivity: AllPlayerInTeamsActivity)
  fun injectIntoLeagueSearchActivity(leagueSearchActivity: LeagueSearchActivity)
  fun injectIntoLeagueStandingActivity(leagueStandingActivity: LeagueStandingsActivity)
  fun injectIntoLeagueTopScorerSearchActivity(leagueTopScorerSearchActivity: LeagueTopScorerSearchActivity)
  fun injectIntoLeagueTopScorerActivity(leagueTopScorerActivity: LeagueTopScorerActivity)
  fun injectIntoCoachSearchActivity(coachSearchActivity: CoachSearchActivity)
  fun injectIntoCoachDetailActivity(coachDetailActivity: CoachDetailActivity)
  fun injectIntoTeamsSearchActivity(teamsSearchActivity: TeamsSearchActivity)
  fun injectIntoPlayersSearchActivity(playersSearchActivity: PlayersSearchActivity)
  fun injectIntoPlayersAchievementActivity(playersAchievementActivity: PlayersAchievementActivity)
  fun injectIntoLeagueMatches(fixturesActivity: FixturesActivity)
  fun injectIntoStatisticActivity(fixturesDetailActivity: FixturesDetailActivity)
}