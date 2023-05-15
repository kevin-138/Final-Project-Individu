package com.kevin.netkick.presentation.di

import com.kevin.netkick.network.di.DataComponent
import com.kevin.netkick.presentation.view.general.activity.AllPlayerInTeamsActivity
import com.kevin.netkick.presentation.view.general.activity.TeamDetailActivity
import com.kevin.netkick.presentation.view.home.news.activity.AllNewsListActivity
import com.kevin.netkick.presentation.view.home.news.activity.ArticleActivity
import com.kevin.netkick.presentation.view.home.popularteams.activity.PopularTeamsDetailActivity
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

  fun injectInto(mainActivity: MainActivity)

  fun injectInto(popularTeamsListActivity: PopularTeamsListActivity)

  fun injectInto(popularTeamsDetailActivity: PopularTeamsDetailActivity)

  fun injectInto(allNewsListActivity: AllNewsListActivity)

  fun injectInto(articleActivity: ArticleActivity)
  fun injectInto(teamDetailActivity: TeamDetailActivity)

  fun injectInto( allPlayerInTeamsActivity: AllPlayerInTeamsActivity)

//  fun injectInto(leagueSearchActivity: LeagueSearchActivity)

}