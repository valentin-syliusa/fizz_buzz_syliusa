# What I've Done

I created a basic FizzBuzz Android application, split into two modules.


The first one, domain module, only contains business logic & our use case. In our case, business logic is limited to an Input model and a use case that'll take an Input, perform our business logic, and return a result as a List of Strings.
This use case is processed in IO thread to avoid blocking UI thread and improve performances.
We could have improved this part by creating a dedicated "Result" model with a "value" parameter that'd be a List of Strings, but I thought it wasn't really needed.
This domain module also contains one unit test, that tests our use case, key part of our business logic in this project.


The second one, app module, contains essentially our presentation layer. This app don't really uses a repository and doesn't communicate with a back-end/webservice so the main part is this presentation part.
I used Navigation Components to have a smooth, easy to understand and easy to improve flow. I also took advantage of the Safe Args plug-in to pass values between Fragments in a more readable and secure way.
Because I used Navigation Components, my presentation layer contains a MainActivity that essentially includes the NavHostFragment and two Fragments, representing the two screens of our flow.


The Input one displays a form with 5 inputs (I developed a custom View to avoid duplicating code here, the custom view acting as an input view with some parameters to customize it the way we want), check if data is valid (not null for an Int field or not null AND not empty for a String field) and generate a View State. If all the fields are valid validation button is enabled and we can go to the next step. If it's not the case, the validation button is disabled and we can't go to the next Fragment to ensure that our flow is secure. We also have, for each field, a state TextView that notify which fields are valid or invalid.


The result one displays a list of results, through a simple RecyclerView, by calling the dedicated use case and generating a View State with the results. If we encounter an OutOfMemory error, we notify user that limit is too high and let him go back to previous screen and re-type its inputs, and a new limit.


I voluntarily didn't used Jetpack Compose in this project.


Please note that Dark Mode is supported and app is translated in French & English.

# Architecture

I tried to implement a Clean Architecture in this project, by splitting presentation & domain layers.


On the presentation side, I used MVVM pattern with a ViewModel asociated to each Fragment. The ViewModel is responsible for the logic part and generate a State and Effects observed by the Fragment that can update its UI following this State's value and perform actions following the Effects received. I used StateFlow & SharedFlow because they allow more "multiplaform" code than LiveData and have no flaws, in our case, compared to the old-LiveData style.

# Improvements

I could have added a color code for input view to detect if input is valid or invalid, instead of displaying a simple state text. The error management could also have been improved far more by checking which is the error, and not only notifying that there is an error.


I could have tried to define which is the memory size's limit and use this limit for limit field, but it'd take too much time for this test.


I also could have improved the input Fragment by re-displaying the proper values when we go back to this Fragment.


I also could've created a dedicated UI Input model that'd be passed between Fragments (but must have been a Parcelable or Serializable so I avoided this to avoid adding useless libraries to the project) instead of passing the 5 arguments.


I also could've made the input screen more "flexible" and use a RecyclerView instead.

In the Gradle part of the project, I could've improved the code by creating global variables for libraries versions codes to avoid duplicating definitions and avoid using different libraries in different modules of the same project.