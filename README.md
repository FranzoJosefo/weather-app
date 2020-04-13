# Weather-app
Android - Weather Forecast Application - MVVM

This app is a simple MVVM Java Android app, which displays current weather information to a user as default for his current location based on Ip Address.
Current Weather information includes the present weather forecast, and also hourly forecast. 
We also have a Daily tab which shows as per requirements up to 5 days weather forecast. If needed the code is easy to adapt in order to show even more days.
Last but not least, the user can choose between Current Location based on IpAddress as explained above, or between 5 predefined cities from Europe.

- Roadmap
For future release it would be nice to add some animations, and also add detail Activity to see more information for the daily weather report,
for that reason our model includes even more fields that aren't currently being used. That way we can take advantage of those in the future,
by working on an enhancement. Also Caching of network calls will be included, together with notifications or alarms for weather alerts (such as storms).

## Installation & Project setup

Make sure you have Android Studio latest version
You can download from: https://developer.android.com/studio

Once installed, install latest SDK version. 
If you need help with that, you can find steps here: https://developer.android.com/studio/intro/update

Once you are all of setup with Android Studio:
Clone this repository and import into **Android Studio**
```bash
git clone https://github.com/FranzoJosefo/weather-app.git
```
Wait for indexing and gradle build and you should be set to hit Run and try the app on a device or emulator.

## Architecture Overview
This is an MVVM app which only acts as a client, 
every operation or mutation of data are done by calling API endpoints. 

We don't really modify any data within the app, 
it's a read-only from three different services in order to get needed data.

The domain model objects are stored under a `model` package. All of those are Java objects, 
in which we store serialized data with the help of retrofit and okhttp libraries.
This way we make sure the rest of the app doesn't know about the model layer
in terms of how this is implemented or where the data comes from.

Right now with every state change we are updating data.
On screen rotation, or when killing activities, or even resuming activities (Since we use a `FragmentStateAdapter`) we will get new data.
Also we have pull-to-refresh implemented with `SwipeRefreshLayout` in order to fetch data whenever the user needs to.

Activities and fragments are for displaying data to the user only (`View`). Each
activity or fragment have a shared `WeatherViewModel` (`ViewModel`) in this case, in which business
logic is placed. The `ViewModel` reacts to MutableLiveData updates by the use of an Observer pattern, 
and tells the fragment/activity how to update itself.
Also we are using ReactiveX to make sure we dipose Single objects when no longer needed.
The idea also behind using `viewModel` is the fact that activities/fragments are transient, meaning their lifeycle can finish unexpectedly
With the use of `ViewModel` we can always have a saved state without needed to use a Bundle or savedInstanceState which becomes more complex.

Todo Work:
It would be ineteresting to Migrate this app to Kotlin fully, and implement Databinding, also trying JetPack Compose would be a good idea.
Also, Right now we are NOT caching any data, but in feature releases it would be good to implement Room or other caching library
so that we don't consume that much data, and to avoid annoying spinners all of the time. Specially since we have Pull to refresh implemented.

## External dependencies
The following are the external APIs/Services used for this app:

- *OpenWeatherMap* (https://openweathermap.org/api/one-call-api)
  The main API used for Weather data.
  Speficially we use the One Call API feature. 
  Relevant endpoint can be found in `data` > `OpenWeatherMapApiService`.

- *IP-API* (https://ip-api.com/docs/api:json)
  In Order to get Location based on IP address. 
  Relevant endpoint can be found in `data` > `IpApiService`

- *LocationIQ* (https://locationiq.com/docs#forward-geocoding)
  Specifically we are using forward-geolocation to get lat long (needed for OpenWeatherMap call) by passing a city Name.
  Relevant endpoint can be found in `data` > `LocationIQService`

Libraries used are:

- *Retrofit2* - https://square.github.io/retrofit/
  Used to fetch data from API and seralize responses into model objects.

- *Dagger2* - https://github.com/google/dagger
  Used to Inject dependencies within the code to remove un-needed duplicated code.
  
- *Picasso* - https://square.github.io/picasso/
  Used to load images throughout the app in a simple manner.
  
- *Butterknife* - http://jakewharton.github.io/butterknife/
  Bind views to activities/fragments for better code readability. (Will Update to data-binding on next version).
  
- *rxJava2/RxAndroid* - https://github.com/ReactiveX/RxAndroid
  Manage Asynch tasks with Observable streams (works together with ViewModel classes and MutableLiveData).

## Build variants
Use the Android Studio *Build Variants* button to choose between debug and release build types.

## Maintainers
This project is mantained by:
* [Francisco Olivero](http://github.com/franzojosefo)

## Contributing
1. Fork it
2. Create your feature branch (git checkout -b my-new-feature)
3. Commit your changes (git commit -m 'Add some feature')
5. Push your branch (git push origin my-new-feature)
6. Create a new Pull Request
