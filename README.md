#Product Discovery
Product discovery is sample project which is built with MVVM and latest android jetpack features.
Essential dependencies are Dagger2, RxJava2 with RxAndroid, Room, Retrofit and Glide.

###Project Structure
- **app**: contains App related class (Application, module, components)
- **data**: contains all local data, including local database (using Room) and SharePreferences
- **domain**: contains business logic, including api module, local repository (save/retrieve database)
- **ui**: contain all user interface and related ViewModel for each View
- **utils**: contains ultility classes
