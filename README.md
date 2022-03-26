### Currency

Currency is a proof-of-concept Android app designed to help customers to hand different currency conversions.

---------

## Table of contents

- [Features](#features)
- [Implementation Details](#implementation-details--technologies-used)
- [API](#api)
- [Why Modularization](#why-modular-architecture-)
- [App Modules](#app-modules)
- [App Architecture](#app-architecture)
- [Preview](#preview)
- [Demo](#demo)
- [Credits](#credits)
- [License](#license)

---------

### Features

- User can convert currencies form different bases to any currency.
- User can swap between FROM - TO currencies and accordingly converted data
  changed.
- User can view list of his conversion history for the last 3 days (day by day).
- User can see a list of rates of 10 most popular currencies form his selected base.
- User can make any conversion offline mode with the last cached rates fetched from Rest Api.

--------

### Implementation Details & technologies used

- Kotlin
- Modular Architecture
- Clean Architecture
- MVVM pattern
- Repository Pattern
- Observer Pattern
- Kotlin Dsl Gradle
- Dependency Injection using Dagger Hilt
- Unit testing
- Kotlin Coroutines
- Kotlin Flow
- Android Jetpack
- Android-KTX
- Room Database
- Navigation Component
- ViewBinding
- Jetpack Compose
- Retrofit & OkHttp
- Gson
- Java ThreeTen


<br/>

---------

### Api

- [Fixer Api](http://data.fixer.io/api/)

---------

### Why Modular Architecture ?

![Alt text](https://github.com/MhmoudAlim/Currency/blob/master/blob/modules.png?raw=true)

- Clean, Testable, Maintainable and extendable Codebase
- Apply high cohesion and low coupling
- Faster Gradle Build Time
- Easier Team-working on same project
- High Usability allowance of Codes
- Fine-grained dependency control.
- Improve reusability across other apps.
- Improves the ownership & the quality of the codebase.
- Stricter boundaries when compared to packages.
- Makes Instant Apps & Dynamic Features possible (improving discoverability).

<br/>

---------

### App Modules

- _App_ :  contains views, state holders - represent presentation Logic (UI) Layer.
- _Data_ :  contains data, repos , use-cases, database - represent business logic (domain-data) Layers.
- _Core_ :  contains helper classes and shared claes with all modules.


<br/>

---------

### App Architecture

![Alt text](https://github.com/MhmoudAlim/Currency/blob/master/blob/architecture.png?raw=true)

---------

### Preview

1            |  2
:-------------------------:|:-------------------------:
![screenshot](https://github.com/MhmoudAlim/Currency/blob/master/blob/Screenshot_1.jpg?raw=true)  |   ![](https://github.com/MhmoudAlim/Currency/blob/master/blob/Screenshot_2.jpg?raw=true)

<br/>

---------

### Demo

Download App sample : [Currency](https://github.com/MhmoudAlim/Currency/raw/master/blob/app-release.apk)

------

### Credits

- [Guide to app architecture](https://developer.android.com/jetpack/guide#recommended-app-arch)
- [Android Components Architecture in a Modular Word](https://proandroiddev.com/android-components-architecture-in-a-modular-word-7414a0631969)

------

### License

<details>
    <summary>
        Click to reveal License
    </summary>

```
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
</details>
