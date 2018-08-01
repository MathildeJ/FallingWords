# FallingWords
* Time invested: 5h15
* Time distribution:
  - 15 min to think about the UI (layout, interactions, different possible states of the UI) and the architecture of the app (Clean architecture + MVP)
  - 1h10 to set up the infrastructure layer (Dependency Injection, base classes)
  - 1h20 to add all the core classes (view, presenter and interactor) and implement the presentation logic
  - 1h to implement the business logic for the game mechanics: updating words at regular intervals, computing score
  - 30min for the view: layout, styling and animation of the falling word
  - 1h to write UI and instrumentation tests (and adjust/improve code when necessary)
* Decisions made to solve certain aspects of the game:
  - chose a reasonable update interval for the words (3s); this duration is used both to know when we should get a new pair of words and to animate the falling word
  - a new pair of words is chosen randomly each time and to make sure that words that are matching appear fairly frequently, a simple true/false generator is used
  - feedback is given as the number of correct answers over the number of pair of words that have gone through the screen; if the user doesn't respond in time, it is counted as a wrong answer
  - made the choice to limit the game to 30 pairs of words to keep it light and short (1min30 each game)
  - once an answer (yes or no) has been selected for the current pair of words, the buttons disappear so that the user can't change their mind or answer multiple times
* Decisions made because of restricted time:
  - there is no 'data' layer considering that in that specific case we're simply reading data from a small and local json file. Ideally, we should abstract the data source (repository pattern) to separate the concerns, improve performance and allow flexibility (for instance, easily switch the source to a database or a REST API)
  - in terms or architecture, MVVM might have been a bit more concise (especially if leveraging data-binding XML and/or architecture components), but I'm more familiar with MVP so it would have taken me a bit more time to use it
  - the UI is very simplistic. Not a lot of time was spent on the look & feel of the app, instead I focused on presenting the information and possible user actions in a clear and practical manner
  - didn't implement the possibility to pause/resume the game (so if the user leaves the app, the words will keep being updated)
* First thing to improve if there had been more time: create a data layer (not passing words from the View, but instead getting them directly from a Repository implementation that would be injected into the interactor)
  
  First thing to add if there had been more time: add 'home' screen from which we can start the game and see the highest score 
