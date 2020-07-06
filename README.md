# Architecture Overview

The architecture solution for this project is an MVVM-inspired approach, built with Google Jetpack Architecture Components. 

As stated in the project description, the solution has to be maintainable, easily extendable, and testable. MVVM architecture is able to fulfill all these requirements. The following graph illustrates the approach:

<img src="/architecture.png" alt="architecture" title="architecture" width="1000" height="300" />

MVVM architecture is a modern approach to Android app development that has the following advantages: 
 - Clear separation of responsibilities between components. This separation allows for an easier understanding and maintenance of the codebase.
 - It allows developers to build and modify components independently. Changes are typically done within one layer and have minimal influence on other parts of the codebase.
 - Better modularity. Enables developers to split the app into different independent modules, which can be developed and extended with few, if any, changes spanning across multiple modules.
 - Easier testing. Well-defined boundaries between components allow developers to test them in isolation (unit testing), as well as to test the system as a whole (integration testing).

The barebones example of this approach is demonstrated in the current demo project.

As the project grows, this architecture is to be extended to the layered Clean Architecture. The existing version of the architecture is designed with future growth in mind.

## A list of technologies I chose for this project
Shown in the current demo project:
- Kotlin
- RxJava - used for threading and networking 
- Retrofit/OkHttp - networking
- Koin - dependency injection
- Jetpack LiveData & ViewModels 
- Gson for JSON parsing
- View and Data-binding

Potentially to be used as the project grows:
- Firebase for push messages and crash-analytics;
- Firebase Remote Config;
- Google Room for the local database (most likely would have some local data encryption involved).

## Flow Diagram implemented in this project

<img src="/user-flow.png" alt="architecture" title="architecture" width="740" height="710" />

## Technical questions

##### **Q**: How long did you spend on the coding test? What would you add to your solution if you had more time? 
If you didn't spend much time on the coding test then use this as an opportunity to explain what you would add.

**A**: First of all, I'd like to point out that the task asks to provide the full architecture for the MVP app, backed up by a demo project demonstrating the selected approach. I do not think that this is feasible to do in the given timeframe of two days, therefore I needed to set my priorities and make some sacrifices.  

From this perspective, I believe that I managed to implement a solid barebones demo app. It can be used to start an MVP app development and has all that is needed to grow it further to a production-ready app.

Among the features that I'd suggest for an MVP app (in no particular order) would be:
 - a lock screen with PIN-code and/or biometric (fingerprint) protection;
 - I'm not a UI/UX designer, therefore the app definitely needs major improvements in these areas;
 - I expect the real app would be using the bank's own design guidebook and colors;
 - the currently used cleartext server communication protocol is certainly not suitable for the banking app;
 - better filtering/grouping capabilities for transactions list;
 - redesign the dashboard to achieve "at a glance" look and feel;
 - ability to add (and maybe order) bank cards and accounts;
 - some customer support features: a web chat, ATMs & offices map, Q&A section etc;
 - bank legal information & privacy policy;
 - bank news, ads messages (possibly with push-messaging from the server);
 - deep linking and Android App Links;
 - restore access/password function;
 - remote app configuration (Firebase Remote Config?).

#####  Q: What was the most useful feature that was added to the latest version of your chosen language? Please include a snippet of code that shows how you've used it.

**A**: My language of choice is Kotlin, therefore the latest feature of it would be Flow for coroutines. Flow (with coroutines) can be used instead of the currently used RxJava for background work and threading purposes.

As for the code snippet, I'd rather speak of another feature of Kotlin I'm really fond of. This feature is Kotlin DSL. It enabled developers to write code in a declarative style and using the domain-level keywords and names. Such code is highly readable and is a great tool for the declaration of various data structures.

The good example from the current project would be the Koin module declaration:

```kotlin
val appModule = module {
    single<Repository> {
        RepositoryImpl(get())
    }
    scope(named(Scopes.AUTH_ACTIVITY.name)) {
        viewModel { AuthViewModel(get()) }
        viewModel { SignInViewModel(get(), androidContext()) }
        viewModel { SignUpViewModel(get()) }
    }
    scope(named(Scopes.INFORMATION_ACTIVITY.name)) {
        viewModel { InformationViewModel() }
        viewModel { TransactionsViewModel(get()) }
        viewModel { DashboardViewModel(get()) }
    }
}
```

##### Q: How would you track down a performance issue in production? Have you ever had to do this?
**A**: If the issue is reproducible on devices available for developers, we have a range of tools available to track down the issue - from simple debugging tools to benchmarking and profiling solutions. This is what I had to do multiple times in the past and I'm most familiar with it.

In production, I think there's only one option: to gathering statistics via common services like Firebase, Google Analytics, Yandex Metrics. In my opinion, a good tool to start would be Firebase Performance Monitoring. 

##### Q: How would you debug issues related to API usage? Can you give us an example?
**A**: This issue can (and probably should) be addressed from both sides - client and server end.

If the issue is related to the high API usage, the client's solution probably would be to use caching (with proper encryption, we're building a banking app here). On the contrary, the server may have to implement some kind of throttling.

Another issue that is related to API would be validating the app's handling of all possible server responses. In this case, we may use local unit-tests with stored responses; some middleware (ie Charles) to mock server responses.

Last, but not least, the latest Android Studio update enables good Network profiling capabilities that can help to deal with API related issues.

##### Q: How would you improve the Node server’s API that you just used?

**A**: The **critical** issue I see with the current API is the cleartext format (HTTP), which is not suitable for a banking app that is expected to follow high-security standards.

Here's a list of API-related things I highlighted during the development of the demo project:
- the https format should be used for server communication, possibly with some additional data protection measures;
- I suggest using some tool for API documentation (swagger is what I worked with the most);
- Error states and statuses are not defined, a clear error resolution contract between a server and the app should be defined;
- Too much work regarding sorting and filtering data is happening on the client (accounts and transaction), I believe such logic should be done on the server's end;
- because of the above issue, much more data than needed is transferred to the mobile app, which is a potential security threat;
- I do not like having amounts in string format because it leaves too much space for possible incorrect values that need handling in the client;
- two completely different formats are used for DateTime representation; 
- the auth endpoint just responds with static data, in practice I'd expect it to return some kind of authorization token;
- the sign up possibly can be paired with an automatic sign-in, returning the aforementioned auth token.

##### Q: Please describe yourself using JSON.
**A**: 
```json
{
    "positiion" : "Lead / Senior Developer",
    "technologies": [ "Android", "Kotlin", "Java" ],
    "spoken_languages": [
        {
            "language" : "Russian",
            "level" : "Native"
        },
        {
            "language" : "English",
            "level" : "C1"
        }
    ],
    "hard_skills": [
        { 
            "Common" : ["Software Design", "Version Control Systems", "Tast Estimation", "Development metodologies"]
        },
        { 
            "Java" : ["Java basics", "Java concurency", "Java collections", "JVM memory structure"]
        },
        { 
            "Kotlin" : ["Kotlin basics", "Kotlin advanced practices"]
        },
        { 
            "Android" : [ 
                "Fundamentals", "App Components", "Views", "State handling",
                "Background work", "Notifications", "Animations", "Multimedia"
            ]
        },
        { 
            "Libraries" : [ 
                "Retrofit", "OkHttp", "RxJava", "Realm", "SQL", "Room", 
                "Architecture Components", "Dagger", "Koin", "Moxy"
            ]
        },
        { 
            "App Architecture" : [ 
                "Basic Theory", "MVP", "MVVM", "Clean architecture"
            ]
        }, 
        { 
            "Other" : [ 
                "Unit-test", "UI-tests"
            ]
        }
    ],
    "soft_skills": [
        "Empathy", "Communication", "Patience", "Open-mindedness", "Teamwork"
    ]
}
```