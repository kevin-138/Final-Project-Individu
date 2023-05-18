package com.kevin.netkick.presentation.di

import com.kevin.netkick.network.di.DataComponent
import com.kevin.netkick.presentation.view.general.activity.AllPlayerInTeamsActivity
import com.kevin.netkick.presentation.view.general.activity.LeagueSearchActivity
import com.kevin.netkick.presentation.view.general.activity.TeamDetailActivity
import com.kevin.netkick.presentation.view.home.news.activity.AllNewsListActivity
import com.kevin.netkick.presentation.view.home.news.activity.ArticleActivity
import com.kevin.netkick.presentation.view.home.popularteams.activity.PopularTeamsListActivity
import com.kevin.netkick.presentation.view.main.activity.MainActivity
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

//  fun injectInto(leagueSearchActivity: LeagueSearchActivity)

}