# Air Quality App

This is an Android app for checking air quality and PM2.5 of different areas in Taiwan.

It uses the "aqx_p_432" API provided by the Environmental Protection Administration.

## Main Functions

* Display the air quality and PM2.5 readings of AQI sites across Taiwan.

* Sites with lower PM2.5 readings (<=5) are displayed on the top horizontal scrolling list. 
* The rest is displayed in a vertical scrolling list.
* A search function is provided to search for the county or the area you want to check.
* The app will check for data changes for every 10 minutes.
* The user can also swipe down on the vertical list to manually update the data.

## Development Milestones
**Note:** the initial commit is mistakenly committed and pushed using my other account.

### MVC version
May 23, 2022 [Initial commit.](https://github.com/yuan-an-chen/air_quality_app/commit/c4eeb23b4de782ae2b7038d06aa9c75338c717c1)

May 27, 2022 [Completed all requested functions in the spec using the MVC architecture.](https://github.com/yuan-an-chen/air_quality_app/commit/e47c0b8eb0c74615dec85123d4c7c63c87bb1aa8)

Jun 2, 2022 [Added additional data refresh function.](https://github.com/yuan-an-chen/air_quality_app/commit/ff7ce56944a36b1b525bbd1d4d366d395e5c22d3)

### MVVM version
Jun 10, 2022 Created a experiment branch to try rewriting the app in the MVVM architecture.

Jun 11, 2022 [Introduced Retrofit, Coroutine and Flow into the project.](https://github.com/yuan-an-chen/air_quality_app/commit/7021a17d5392f8469faf2b8668b97dc6b2ecf45a)

Jun 13, 2022 [Finished rewriting all previous functions in the MVVM architecture.](https://github.com/yuan-an-chen/air_quality_app/commit/7021a17d5392f8469faf2b8668b97dc6b2ecf45a)

### Final version
Jun 13, 2022 Merged the **mvvm_experiment_branch** into the master branch.





